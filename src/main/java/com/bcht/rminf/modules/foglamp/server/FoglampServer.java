package com.bcht.rminf.modules.foglamp.server;

import com.bcht.rminf.modules.foglamp.model.CommandEntity;
import com.bcht.rminf.modules.terminal.model.Result;
import com.bcht.rminf.modules.terminal.server.TerminalProtocolServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 百纳雾灯服务端
 */
@Component
public class FoglampServer extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(TerminalProtocolServer.class);
    private NetServer server;
    // 添加启动停止状态标识
    private boolean isStarted = false;
    private final Map<String, NetSocket> socketRepo = new ConcurrentHashMap<>();
    private final Map<String, byte[]> controlAddressRepo = new ConcurrentHashMap<>();
    // 发送数据的头
    private static final byte[] HEAD = {(byte) 0x8A, (byte) 0x99};
    private static final byte[] TAIL = {(byte) 0xFF};

    @Override
    public void start() throws Exception {
        start0();
        // 订阅配置变更
        subscribeConfigChange();
        vertx.eventBus().consumer("foglamp.sendCommand").handler(msg -> {
            CommandEntity commandEntity = ONode.deserialize((String) msg.body());
            Buffer buffer = Buffer.buffer();
            if (socketRepo.get(commandEntity.getSbip()) == null || controlAddressRepo.get(commandEntity.getSbip()) == null) {
                msg.reply(ONode.serialize(Result.error("设备不存在或者离线：" + commandEntity.getSbip()))); // 发送响应
            } else {
                switch (commandEntity.getCommand()) {
                    case "A":
                        // 工作状态-Subcommand 打开-1、关闭-2
                        if ("1".equals(commandEntity.getSubcommand())) {
                            buffer.appendBytes(HEAD);
                            buffer.appendBytes(controlAddressRepo.get(commandEntity.getSbip()));
                            buffer.appendByte((byte) 0x03); // 下发控制命令参数
                            buffer.appendBytes(hexStringToByteArray("022074037403030000")); // 内容部分
                            buffer.appendBytes(TAIL); // 下发控制命令参数
                        } else {
                            buffer.appendBytes(HEAD);
                            buffer.appendBytes(controlAddressRepo.get(commandEntity.getSbip()));
                            buffer.appendByte((byte) 0x03); // 下发控制命令参数
                            buffer.appendBytes(hexStringToByteArray("04")); // 内容部分
                            buffer.appendBytes(TAIL); // 下发控制命令参数
                        }
                        log.info("发送指令到设备：{} -> {}", commandEntity.getSbip(), bytesToHex(buffer.getBytes()));
                        socketRepo.get(commandEntity.getSbip()).write(buffer);
                        break;
                    default:
                        log.info("未知命令或未实现：{}", commandEntity.getCommand());
                }
                msg.reply(ONode.serialize(Result.ok())); // 发送响应
            }
        });

        // 设备列表
        vertx.eventBus().consumer("foglamp.deviceList").handler(msg -> {
            List<ONode> devList = new ArrayList<>();
            socketRepo.keySet().forEach(key -> {
                devList.add(
                        ONode.newObject().set("ip", key)
                                .set("status", controlAddressRepo.containsKey(key) ? 1 : 0)
                );
            });
            msg.reply(ONode.serialize(devList)); // 发送响应
        });
    }

    private void start0() throws Exception {
        if (Solon.cfg().getBool("foglamp.enabled", false)) {
            if (!isStarted) { // 检查服务是否已经启动
                log.info("foglamp server is starting...");
                start1();
            } else {
                log.info("foglamp server is already running.");
            }
        } else {
            log.info("foglampserver is disabled.");
        }
    }

    private void start1() {
        NetServerOptions options = new NetServerOptions()
                .setPort(Solon.cfg().getInt("foglamp.server.port", 1235));
        server = vertx.createNetServer(options);
        server.connectHandler(socket -> {
            String host = socket.remoteAddress().host();
            socketRepo.put(host, socket);
            log.info("foglamp device connected: {}", host);

            // 定义一个缓冲区来存储接收到的数据
            final Buffer[] receivedData = {Buffer.buffer()};
            // 数据处理器
            socket.handler(buffer -> {
                log.info("receive data===：{}", bytesToHex(buffer.getBytes()));
                receivedData[0].appendBuffer(buffer);
                /// 检查是否收到了完整的请求
                while (receivedData[0].length() >= 5) { // 假设最小请求长度为4
                    // 检查请求头和尾部
                    if (receivedData[0].getByte(0) == (byte) 0x99 && receivedData[0].getByte(1) == (byte) 0x8A) {
                        // 找到第一个尾部是FF的数据段
                        int endIndex = -1;
                        for (int i = 2; i < receivedData[0].length(); i++) {
                            if (receivedData[0].getByte(i) == (byte) 0xFF) {
                                endIndex = i + 1; // 包括FF在内的完整请求长度
                                break;
                            }
                        }
                        if (endIndex != -1) {
                            // 截取完整的数据处理
                            Buffer completeRequest = receivedData[0].slice(0, endIndex);
                            processRequest(socket, completeRequest);

                            // 从receivedData中移除已处理的数据
                            // 移除已处理的数据
                            Buffer remainingData = receivedData[0].slice(endIndex, receivedData[0].length());
                            receivedData[0] = Buffer.buffer(remainingData.getBytes());
                        } else {
                            // 如果没有找到尾部的FF，继续接收数据
                            break;
                        }
                    } else {
                        Buffer remainingData = receivedData[0].slice(1, receivedData[0].length());
                        receivedData[0] = Buffer.buffer(remainingData.getBytes());
                        // 如果没有收到完整的请求，继续接收数据
                    }
                }
            });
            // 关闭连接
            socket.endHandler(v -> {
                socketRepo.remove(host);
                controlAddressRepo.remove(host);
                log.info("foglamp device disconnected: {}. Remaining connection count: {}", host, socketRepo.size());
            });
        });

        server.listen(result -> {
            if (result.succeeded()) {
                log.info("foglamp server has started successfully on port {}",
                        Solon.cfg().getInt("foglamp.server.port", options.getPort()));
                isStarted = true; // 标记服务已启动
            } else {
                log.error("Failed to bind to port {}!", options.getPort(), result.cause());
            }
        });

    }

    // 处理请求的方法
    private void processRequest(NetSocket netSocket, Buffer request) {
        log.info("receive data：{}", bytesToHex(request.getBytes()));
        if (request.getByte(4) == 0x0B) { // 心跳
            controlAddressRepo.put(netSocket.remoteAddress().host(), request.getBytes(2, 4));
        }
    }

    @Override
    public void stop() throws Exception {
        if (isStarted) { // 检查服务是否已经停止
            log.info("foglamp server is stopping...");
            if (server != null) {
                server.close();
            }
            log.info("foglamp server has stopped successfully.");
            isStarted = false; // 标记服务已停止
        } else {
            log.info("foglamp server is already stopped or not started.");
        }
    }

    public void subscribeConfigChange() {
        Solon.cfg().onChange((key, value) -> {
            if (key.startsWith("foglamp.enabled")) {
                boolean enabled = Solon.cfg().getBool("foglamp.enabled", false);
                if (enabled && !isStarted) { // 检查服务是否已经启动
                    try {
                        start0();
                    } catch (Exception e) {
                        log.error("Failed to start foglamp server.", e);
                        throw new RuntimeException(e);
                    }
                } else if (!enabled && isStarted) { // 检查服务是否已经停止
                    try {
                        stop();
                    } catch (Exception e) {
                        log.error("Failed to stop foglamp server.", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
