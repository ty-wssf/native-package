package com.bcht.rminf.modules.dhkafka;

import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.sys.model.SysRelation;
import com.bcht.rminf.modules.sys.model.SysRelationTable;
import com.zaxxer.hikari.HikariDataSource;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.babyfish.jimmer.sql.JSqlClient;
import org.noear.snack.ONode;
import org.noear.snack.core.Options;
import org.noear.snack.core.utils.DateUtil;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.MDC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Component
public class DHkafkaLifecycleBean extends AbstractVerticle {

    private volatile RabbitMQClient rabbitMQClient; // 声
    KafkaConsumer<String, String> consumer;
    HikariDataSource dataSource;
    private static final PassInfoConvert passInfoConvert = new PassInfoConvert();
    private static final Options options = Options.def();
    @Inject
    JSqlClient sqlClient;

    static {
        //添加编码器
        options.addEncoder(Date.class, (data, node) -> {
            node.val().setString(DateUtil.format(data, "yyyy-MM-dd HH:mm:ss.SSS"));
        });
    }

    @Override
    public void start() {
        if (Solon.cfg().getBool("solon.cloud.dhkafka.event.enabled", false)) {
            try {
                // 创建消费者
                Map<String, String> config = new HashMap<>();
                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Solon.cfg().get("solon.cloud.dhkafka.event.server"));
                config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
                config.put(ConsumerConfig.GROUP_ID_CONFIG, Solon.cfg().appGroup() + "_" + Solon.cfg().appName());
                config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                consumer = KafkaConsumer.create(vertx, config);

                CompletableFuture<?> rabbitC = new CompletableFuture<>();
                if (Solon.cfg().getBool("solon.cloud.dhkafkatarget.enable", false)) {
                    createRabbitMQClient(result -> {
                        if (result.failed()) {
                            log.warn(result.cause().getMessage(), result.cause());
                            rabbitC.completeExceptionally(result.cause());
                        } else {
                            rabbitC.complete(null);
                        }
                    });
                } else {
                    rabbitC.complete(null);
                }

                // 创建数据源
                CompletableFuture<?> dsFuture;
                if (Solon.cfg().getBool("DHkafka.db.enable", false)) {
                    dsFuture = createDataSource();
                } else {
                    dsFuture = new CompletableFuture<>();
                    dsFuture.complete(null);
                }

                CompletableFuture<?> allFutures = CompletableFuture.allOf(rabbitC, dsFuture);
                allFutures.whenComplete((result, exception) -> {
                    // 如果exception不为null，表示至少有一个CompletableFuture异常完成
                    if (exception == null) {
                        consumer.handler(record -> {
                            String logId = UUID.randomUUID().toString(); // 生成一个唯一的ID作为日志上下文标识
                            MDC.put("logId", logId); // 设置MDC的值

                            log.info("===dhkafka：{} -> {}", record.key(), record.record().value());
                            TrafficVechilePassInfo vechilePassInfo = passInfoConvert.convert(record.record().value());
                            if (vechilePassInfo != null) {
                                // 设备编号对应关系转换
                                SysRelationTable table = Tables.SYS_RELATION_TABLE;
                                Optional<SysRelation> sysRelation = sqlClient.createQuery(table)
                                        .where(table.objectId().eq(vechilePassInfo.getSbxh()))
                                        .where(table.category().eq("dh_bc_sbbh"))
                                        .select(table)
                                        .fetchOptional();
                                sysRelation.ifPresent(relation -> vechilePassInfo.setSbxh(relation.targetId()));

                                log.info("===vechilePassInfo：{}", ONode.stringify(vechilePassInfo, options));
                                // 推送到队列
                                if (Solon.cfg().getBool("solon.cloud.dhkafkatarget.enable", false)) {
                                    publishMessage(vechilePassInfo, Solon.cfg().get("solon.cloud.dhkafkatarget.event.routingkey"));
                                    if (!"0".equals(vechilePassInfo.getWfdm())) {
                                        // 违法数据推送一份到违法队列
                                        publishMessage(vechilePassInfo, Solon.cfg().get("solon.cloud.dhkafkatarget.event.routingkey.vio"));
                                    }
                                }

                                if (Solon.cfg().getBool("DHkafka.db.enable", false)) {
                                    // 保存到数据库
                                    saveVehiclePass(vechilePassInfo);
                                }
                            }

                            MDC.clear(); // 清理MDC的值
                        });
                        consumer.subscribe(Solon.cfg().get("solon.cloud.dhkafka.event.topic"));
                    }
                });
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    private void saveVehiclePass(TrafficVechilePassInfo passInfo) {
        // 确定需要存储的表 当数据为缓存数据时，只需插入到缓存表
        String tableName = (Utils.isNotEmpty(passInfo.getWfdm()) && (!"0".equals(passInfo.getWfdm()))) ? "ds_violation" : "ds_tfcpass";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        tableName += sdf.format(new Date());
        log.info("写入表:{}", tableName);
        String sql = "INSERT INTO " + tableName + "(gcbh,sbxh,gcsj,hphm,hpzl,hpys,cwkc,clys,cllx,sd,cdbh,fx,wfdm,tpurl,tpurl1,tpurl2," +
                "hptzTpurl,faceUrl,fjsFaceUrl)" +
                " VALUES(?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // 绑定参数到SQL语句
            pstmt.setString(1, passInfo.getGcbh());
            pstmt.setString(2, passInfo.getSbxh());
            pstmt.setTimestamp(3, new Timestamp(passInfo.getJgsj().getTime())); // 过车时间
            pstmt.setString(4, passInfo.getHphm());
            pstmt.setString(5, passInfo.getHpzl());
            pstmt.setString(6, passInfo.getHpys());
            pstmt.setFloat(7, passInfo.getCwkc());
            pstmt.setString(8, passInfo.getClys());
            pstmt.setString(9, passInfo.getCllx());
            pstmt.setFloat(10, passInfo.getSd());
            pstmt.setInt(11, passInfo.getCdbh());
            pstmt.setString(12, passInfo.getFx());
            pstmt.setString(13, passInfo.getWfdm());
            pstmt.setString(14, passInfo.getTpurl());
            pstmt.setString(15, passInfo.getTpurl1());
            pstmt.setString(16, passInfo.getTpurl2());
            pstmt.setString(17, passInfo.getHptzTpurl());
            pstmt.setString(18, passInfo.getFaceUrl());
            pstmt.setString(19, passInfo.getFjsFaceUrl());

            // 执行SQL语句
            int affectedRows = pstmt.executeUpdate(); // 89668547-6616-4260-a447-ae4273194728
            if (affectedRows > 0) {
                log.info("数据插入成功，影响行数：{}", affectedRows);
            } else {
                log.error("数据插入失败");
            }
        } catch (SQLException e) {
            log.error("数据库操作异常", e);
        }
    }

    private CompletableFuture<?> createDataSource() {
        CompletableFuture<?> future = new CompletableFuture<>();
        dataSource = new HikariDataSource();
        dataSource.setDriverClassName(Solon.cfg().get("DHkafka.db.driverClassName"));
        dataSource.setJdbcUrl(Solon.cfg().get("DHkafka.db.jdbcUrl"));
        dataSource.setUsername(Solon.cfg().get("DHkafka.db.username"));
        dataSource.setPassword(Solon.cfg().get("DHkafka.db.password"));
        try (Connection connection = dataSource.getConnection()) {
            future.complete(null);
        } catch (SQLException e) {
            // 处理SQLException，可能是连接失败或其他SQL错误
            log.warn(e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    private void createRabbitMQClient(Handler<AsyncResult<Void>> resultHandler) {
        RabbitMQOptions config = new RabbitMQOptions()
                .setUser(Solon.cfg().get("solon.cloud.dhkafkatarget.username"))
                .setPassword(Solon.cfg().get("solon.cloud.dhkafkatarget.password"))
                .setHost(Solon.cfg().get("solon.cloud.dhkafkatarget.server").split(":")[0])
                .setPort(Integer.parseInt(Solon.cfg().get("solon.cloud.dhkafkatarget.server").split(":")[1]));
        rabbitMQClient = RabbitMQClient.create(vertx, config);
        rabbitMQClient.start(resultHandler);
    }

    private void publishMessage(Object message, String routingKey) {
        String msgStr = ONode.stringify(message, options);
        log.info("start published rabbitmq");
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        rabbitMQClient.basicPublish(Solon.cfg().get("solon.cloud.dhkafkatarget.event.exchange"), routingKey,
                Buffer.buffer(msgStr), result -> {
                    MDC.setContextMap(contextMap);
                    if (result.succeeded()) {
                        log.info("Message published rabbitmq success：{}", routingKey);
                    } else {
                        log.error("Message published rabbitmq fail", result.cause());
                    }
                    MDC.clear();
                });
    }

    @Override
    public void stop() throws Exception {
        if (consumer != null) {
            consumer.close();
            consumer = null;
        }
        if (rabbitMQClient != null) {
            rabbitMQClient.stop();
            rabbitMQClient = null;
        }
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

}
