package com.bcht.rminf.modules.hkpostgresql;

import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.hkpostgresql.model.WarningInstanceInfo;
import com.bcht.rminf.modules.hkpostgresql.model.WarningInstanceInfoTable;
import com.bcht.rminf.modules.hkpostgresql.model.WeatherInfo;
import com.bcht.rminf.modules.hkpostgresql.model.WeatherInfoTable;
import com.zaxxer.hikari.HikariDataSource;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.runtime.ConnectionManager;
import org.babyfish.jimmer.sql.runtime.Executor;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.util.RunUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 接入海航postgresql数据
 */
@Slf4j
@Component
public class HkpostgresqlLifecycleBean extends AbstractVerticle {

    private HikariDataSource dataSource;
    private LocalDateTime baseDateTime;
    private JSqlClient sqlClient;
    private RabbitMQClient rabbitMQClient; // 声明为成员变量
    // 添加启动停止状态标识
    private boolean isStarted = false;
    ScheduledFuture<?> future;
    private static final HkWeatherConvert convert = new HkWeatherConvert();
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void start() {
        if (Solon.cfg().getBool("hkpostgresql.db.enable", false)) {
            syncTask();
        }
        subscribeConfigChange();
    }

    private void initDataSource() {
        dataSource = new HikariDataSource();
        dataSource.setDriverClassName(Solon.cfg().get("hkpostgresql.db.driverClassName"));
        dataSource.setJdbcUrl(Solon.cfg().get("hkpostgresql.db.jdbcUrl"));
        dataSource.setUsername(Solon.cfg().get("hkpostgresql.db.username"));
        dataSource.setPassword(Solon.cfg().get("hkpostgresql.db.password"));

        sqlClient = JSqlClient.newBuilder()
                .setConnectionManager(ConnectionManager.simpleConnectionManager(dataSource))
                .setExecutor(Executor.log())
                .build();
    }


    private void syncTask() {
        future = RunUtil.delayAndRepeat(new Runnable() {
            @Override
            public void run() {
                if (dataSource == null) {
                    initDataSource();
                }
                // 同步警情数据
                syncWarningInstanceInfo();
                // 同步气象信息
                syncWeatherInfo();
            }
        }, Solon.cfg().getInt("hkpostgresql.db.interval", 5) * 1000L);
        isStarted = true;
        log.info("syncTask start success");
    }

    private void syncWarningInstanceInfo() {
        WarningInstanceInfoTable table = Tables.WARNING_INSTANCE_INFO_TABLE;
        List<WarningInstanceInfo> list = sqlClient
                .createQuery(table)
                .where(table.updateTime().gt(baseDateTime == null ? LocalDateTime.now().minusHours(1) : baseDateTime))
                .orderBy(table.updateTime())
                .select(table)
                .execute();

        if (!list.isEmpty()) {
            baseDateTime = list.get(list.size() - 1).updateTime();
        }
        log.info("查询到海康警情数据条数：{}", list.size());

        publishToRabbitMQ(list, Solon.cfg().get("hkpostgresql.rabbitmq.routingkey.warning"), Object::toString);
    }

    private void syncWeatherInfo() {
        WeatherInfoTable table = Tables.WEATHER_INFO_TABLE;
        List<WeatherInfo> list = sqlClient
                .createQuery(table)
                .select(
                        table
                )
                .execute();

        log.info("查询到海气象数据条数：{}", list.size());
        publishToRabbitMQ(list.stream().map(convert::convert).collect(Collectors.toList()),
                Solon.cfg().get("hkpostgresql.rabbitmq.routingkey.weather"), ONode::stringify);
    }

    private void publishToRabbitMQ(List<?> list, String routingKey, Function<Object, String> toStrFunc) {
        if (Solon.cfg().getBool("hkpostgresql.rabbitmq.enable", false)) {
            if (rabbitMQClient == null) { // 检查是否已创建
                try {
                    semaphore.acquire();
                    if (rabbitMQClient == null) {
                        RabbitMQOptions config = new RabbitMQOptions()
                                .setUser(Solon.cfg().get("hkpostgresql.rabbitmq.username"))
                                .setPassword(Solon.cfg().get("hkpostgresql.rabbitmq.password"))
                                .setHost(Solon.cfg().get("hkpostgresql.rabbitmq.host"))
                                .setPort(Solon.cfg().getInt("hkpostgresql.rabbitmq.port", 5672));

                        rabbitMQClient = RabbitMQClient.create(vertx, config);
                        Future<Void> startF = rabbitMQClient.start();
                        startF.onComplete(ar -> {
                            if (ar.succeeded()) {
                                list.forEach(message -> publishMessage(message, routingKey, toStrFunc));
                            } else {
                                log.error("RabbitMQ client start failed", ar.cause());
                            }
                        });
                        startF.toCompletionStage().toCompletableFuture().get();
                        semaphore.release();
                    } else {
                        list.forEach(message -> publishMessage(message, routingKey, toStrFunc)); // 如果已创建，直接发布消息
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            } else {
                list.forEach(message -> publishMessage(message, routingKey, toStrFunc)); // 如果已创建，直接发布消息
            }
        }
    }

    private void publishMessage(Object message, String routingKey, Function<Object, String> toStrFunc) {
        if (rabbitMQClient == null) { // 检查是否已初始化
            log.error("RabbitMQ client is not initialized");
            return;
        }
        String msgStr = toStrFunc.apply(message);// ImmutableSpi
        log.info("start published rabbitmq：{}", msgStr);
        rabbitMQClient.basicPublish(Solon.cfg().get("hkpostgresql.rabbitmq.exchange"), routingKey,
                Buffer.buffer(msgStr), result -> {
                    if (result.succeeded()) {
                        log.info("Message published rabbitmq success：{}", msgStr);
                    } else {
                        log.error("Message published rabbitmq fail：{}", msgStr);
                        log.error(result.cause().getMessage(), result.cause());
                    }
                });
    }

    public void subscribeConfigChange() {
        Solon.cfg().onChange((key, value) -> {
            if (key.startsWith("hkpostgresql.db.enable")) {
                boolean enabled = Solon.cfg().getBool("hkpostgresql.db.enable", false);
                if (enabled && !isStarted) { // 检查服务是否已经启动
                    syncTask();
                } else if (!enabled && isStarted) { // 检查服务是否已经停止
                    stop();
                }
            }
        });
    }

    @Override
    public void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
        if (dataSource != null) {
            dataSource.close();
        }
        if (rabbitMQClient != null) {
            rabbitMQClient.stop();
        }
        if (sqlClient != null) {
            sqlClient = null;
        }
        isStarted = false;
        log.info("syncTask stop success");
    }

}
