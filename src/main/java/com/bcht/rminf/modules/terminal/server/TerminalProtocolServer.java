package com.bcht.rminf.modules.terminal.server;

import com.bcht.rminf.modules.terminal.model.Context;
import com.bcht.rminf.modules.terminal.model.Device;
import com.bcht.rminf.modules.terminal.model.OperationObj;
import com.bcht.rminf.modules.terminal.model.OperationType;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

/**
 * 百诚慧通智能终端设备协议服务
 */
@Component
public class TerminalProtocolServer extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(TerminalProtocolServer.class);
    private NetServer server;
    // 添加启动停止状态标识
    private boolean isStarted = false;
    private final Map<String, NetSocket> socketRepo = new ConcurrentHashMap<>();
    private final Map<String, Device> deviceRepo = new ConcurrentHashMap<>();
    // 回调函数仓库
    private final Map<String, BlockingQueue<Consumer<String>>> callbackRepo = new ConcurrentHashMap<>();

    @Override
    public void start() throws Exception {
        start0();
        // 订阅配置变更
        subscribeConfigChange();
        // 订阅总线请求
        // 设备列表
        vertx.eventBus().consumer("terminal.deviceList").handler(msg -> {
            msg.reply(ONode.serialize(deviceRepo.values())); // 发送响应
        });
        vertx.eventBus().consumer("terminal.deviceinfo").handler(msg -> {
            String ip = (String) msg.body();
            Context context = new Context();
            context.setNetSocket(socketRepo.get(ip));
            context.setDevice(deviceRepo.get(ip));
            OperationObj.DEVICE_INFO.getHandlers().get(OperationType.QUERY_REQUEST).accept(context);
            callbackRepo.computeIfAbsent(
                    byteToHex(OperationType.QUERY_RESPONSE.getCode()) + byteToHex(OperationObj.DEVICE_INFO.getCode()),
                    k -> new LinkedBlockingDeque<>()
            ).add(s -> {
                // 在设备查询响应回调参数中返回数据
                msg.reply(ONode.serialize(deviceRepo.get(ip))); // 发送响应
            });

        });
    }

    private void start0() throws Exception {
        if (Solon.cfg().getBool("smart.terminal.enabled", false)) {
            if (!isStarted) { // 检查服务是否已经启动
                log.info("Smart terminal server is starting...");
                start1();
            } else {
                log.info("Smart terminal server is already running.");
            }
        } else {
            log.info("Smart terminal service is disabled.");
        }
    }

    private void start1() {
        NetServerOptions options = new NetServerOptions()
                .setPort(Solon.cfg().getInt("smart.terminal.server.port", 1234));
        server = vertx.createNetServer(options);
        server.connectHandler(socket -> {
            String host = socket.remoteAddress().host();
            socketRepo.put(host, socket);
            deviceRepo.put(host, new Device(host));
            log.info("Smart terminal device connected: {}. Current connection count: {}", host, socketRepo.size());

            // 构造parser
            RecordParser parser = RecordParser.newFixed(2);
            parser.setOutput(new Handler<Buffer>() {
                int size = -1;
                // // 操作类型
                short operationType;
                // 操作对象
                short operationObject;

                @Override
                public void handle(Buffer packet) {
                    //找到头
                    if (-1 == size) {
                        operationType = packet.getUnsignedByte(0);
                        operationObject = packet.getUnsignedByte(1);
                        if (OperationObj.getOperationObjByCode(operationObject) != null) {
                            size = OperationObj.getOperationObjByCode(operationObject).getLength() + 4;
                            parser.fixedSizeMode(size);
                        } else {
                            log.warn("没有定义消息对象：{}", operationObject);
                        }
                    } else {
                        OperationObj operationObj_ = OperationObj.getOperationObjByCode(operationObject);
                        OperationType operationType_ = OperationType.getOperationTypeByCode(operationType);
                        if (operationType_ == null) {
                            log.warn("没有定义消息类型：{}", operationType);
                        } else {
                            Map<OperationType, Consumer<Context>> handlers = operationObj_.getHandlers();
                            if (handlers.get(operationType_) != null) {
                                log.info("receive data operationObject为 [{}] operationType为 [{}] 的数据",
                                        operationObj_.getName(), operationType_.getName());
                                Context context = new Context();
                                context.setBuffer(packet);
                                context.setNetSocket(socket);
                                context.setDevice(deviceRepo.get(host));
                                handlers.get(operationType_).accept(context);

                                String key = byteToHex(operationType_.getCode()) + byteToHex(operationObj_.getCode());
                                // 确保键存在，如果不存在则创建一个空的LinkedBlockingDeque
                                callbackRepo.computeIfAbsent(key, k -> new LinkedBlockingDeque<>());

                                while (true) {
                                    // 从队列中取出一个回调函数，如果队列为空，则返回null，退出循环
                                    Consumer<String> callback = callbackRepo.get(key).poll();
                                    if (callback == null) {
                                        break; // 如果没有回调函数，退出循环
                                    }
                                    // 如果有回调函数，执行它
                                    callback.accept(null);
                                }
                            } else {
                                log.warn("没有定义操作对象 {} 下面的操作类型 {} 对应的处理器", operationObj_.getName(), operationType_.getName());
                            }
                        }
                        size = -1;
                        parser.fixedSizeMode(2);
                    }
                }
            });

            socket.handler(parser);

            // 关闭连接
            socket.endHandler(v -> {
                socketRepo.remove(host);
                deviceRepo.remove(host);
                log.info("Smart terminal device disconnected: {}. Remaining connection count: {}", host, socketRepo.size());
            });
        });
        server.listen(result -> {
            if (result.succeeded()) {
                log.info("Smart terminal server has started successfully on port {}",
                        Solon.cfg().getInt("smart.terminal.server.port", options.getPort()));
                isStarted = true; // 标记服务已启动
            } else {
                log.error("Failed to bind to port {}!", options.getPort(), result.cause());
            }
        });
    }

    @Override
    public void stop() throws Exception {
        if (isStarted) { // 检查服务是否已经停止
            log.info("Smart terminal server is stopping...");
            if (server != null) {
                server.close();
            }
            log.info("Smart terminal server has stopped successfully.");
            isStarted = false; // 标记服务已停止
        } else {
            log.info("Smart terminal server is already stopped or not started.");
        }
    }

    public void subscribeConfigChange() {
        Solon.cfg().onChange((key, value) -> {
            if (key.startsWith("smart.terminal.enabled")) {
                boolean enabled = Solon.cfg().getBool("smart.terminal.enabled", false);
                if (enabled && !isStarted) { // 检查服务是否已经启动
                    try {
                        start0();
                    } catch (Exception e) {
                        log.error("Failed to start smart terminal server.", e);
                        throw new RuntimeException(e);
                    }
                } else if (!enabled && isStarted) { // 检查服务是否已经停止
                    try {
                        stop();
                    } catch (Exception e) {
                        log.error("Failed to stop smart terminal server.", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
            return "0" + hex;
        } else {
            return hex;
        }
    }

}