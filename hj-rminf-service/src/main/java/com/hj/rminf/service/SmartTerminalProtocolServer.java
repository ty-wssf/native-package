package com.hj.rminf.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 智能终端设备协议服务
 */
public class SmartTerminalProtocolServer extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(SmartTerminalProtocolServer.class);

    private static final byte[] EMPTY_ADDITIONAL = new byte[]{0x00, 0x00, 0x00, 0x00};
    private final Map<String, NetSocket> socketRepo = new ConcurrentHashMap<>();

    @Override
    public void start() {
        if (HjRminfConfigs.SMART_TERMINAL_ENABLED.get()) {
            NetServerOptions options = new NetServerOptions().setPort(HjRminfConfigs.SMART_TERMINAL_TCP_SERVER_PORT.get());
            NetServer server = vertx.createNetServer(options);
            server.connectHandler(socket -> {
                socketRepo.put(socket.remoteAddress().host(), socket);
                log.info("SmartTerminal 设备连接: " + socket.remoteAddress());
                // 根据设备类型进行不同的处理
                handleDevice(socket);
                // 关闭连接
                socket.endHandler(v -> {
                    socketRepo.remove(socket.remoteAddress().host());
                    log.warn("SmartTerminal 设备断开连接：{}", socket.remoteAddress());
                });
            }).listen(result -> {
                if (result.succeeded()) {
                    log.info("SmartTerminal tcp Server is now listening，port: {}", options.getPort());
                } else {
                    log.info("Failed to bind!");
                }
            });
        }
    }

    private void handleDevice(NetSocket socket) {
        AtomicReference<Buffer> accumulatedData = new AtomicReference<>(Buffer.buffer());
        // 读取数据
        socket.handler(packet -> {
            // System.out.println(bufferToHex(packet));
            // 追加上一次剩余的消息
            packet = accumulatedData.get().appendBuffer(packet);
            accumulatedData.set(Buffer.buffer());
            //accumulatedData = Buffer.buffer();
            int messageLength = 0;
            while (packet.length() >= 6) {
                //accumulatedData = Buffer.buffer();
                // 操作类型
                short operationType = packet.getUnsignedByte(0);
                short operationObject = packet.getByte(1);
                if (operationType == 0x80) { // 查询请求
                    if (operationObject == 0x01) { // 心跳
                        messageLength = 6;
                    } else {
                        log.warn("============= {} {}", operationType, operationObject);
                    }
                } else if (operationType == 0x81) { // 设置请求
                    if (operationObject == 0x01) { // 联机
                        messageLength = 6;
                    } else {
                        log.warn("============= {} {}", operationType, operationObject);
                    }
                } else if (operationType == 0x82) { // 主动上报
                    if (operationObject == 0x09) { // 电源管理
                        messageLength = 6;
                    } else if (operationObject == 0x11) { // 接口状态
                        messageLength = 11;
                    } else if (operationObject == 0x16) { // GPS与北斗
                        messageLength = 22;
                    } else {
                        log.warn("============= {} {}", operationType, operationObject);
                    }
                } else if (operationType == 0x83) {
                    log.warn("============= {} {}", operationType, operationObject);
                } else if (operationType == 0x84) {
                    log.warn("============= {} {}", operationType, operationObject);
                } else if (operationType == 0x85) {
                    log.warn("============= {} {}", operationType, operationObject);
                } else if (operationType == 0x86) {
                    log.warn("============= {} {}", operationType, operationObject);
                } else {
                    log.warn("============= {} {}", operationType, operationObject);
                }

                // 消息长度不够，等待下次处理
                if (packet.length() < messageLength) {
                    accumulatedData.get().appendBuffer(packet);
                    return;
                } else {
                    // 处理消息
                    processMessage(socket, packet.getBuffer(0, messageLength), operationType, operationObject);
                    packet = packet.getBuffer(messageLength, packet.length());
                }
            }
            if (packet.length() < 6) { // 消息长度不够，等待下次处理
                accumulatedData.get().appendBuffer(packet);
            }
        });
    }

    private void processMessage(NetSocket socket, Buffer packet, short operationType, short operationObject) {
        log.info("操作类型：{} 操作对象：{}", operationType, operationObject);
        if (operationType == 0x80) { // 查询请求
            if (operationObject == 0x01) { // 心跳
                handleHeartbeat(socket, packet);
            }
        } else if (operationType == 0x81) { // 设置请求
            if (operationObject == 0x01) { // 联机
                handleOnline(socket, packet);
            }
        } else if (operationType == 0x82) { // 主动上报
            if (operationObject == 0x09) { // 电源管理
                handlePowerManagement(socket, packet);
            } else if (operationObject == 0x11) { // 接口状态
                handleInterfaceStatus(socket, packet);
            } else if (operationObject == 0x16) { // GPS与北斗
                handleGps(socket, packet);
            }
        } else if (operationType == 0x83) {
        } else if (operationType == 0x84) {
        } else if (operationType == 0x85) {
        } else if (operationType == 0x86) {
        }
    }


    // 处理心跳  回复  83 01 00 00 00 00
    private void handleHeartbeat(NetSocket socket, Buffer packet) {
        log.info("收到查询请求 心跳");
        packet.getBuffer(2, 6); // 附加

        Buffer response = Buffer.buffer(6);
        response.appendByte((byte) 0x83);
        response.appendByte((byte) 0x01);
        response.appendBytes(EMPTY_ADDITIONAL);

        // 查询应答
        socket.write(response);
    }

    // 处理联机请求 0x81 01 00 00 00 00   回复  84 01 00 00 00 00
    private void handleOnline(NetSocket socket, Buffer packet) {
        log.info("收到设置请求 联机");
        packet.getBuffer(2, 6); // 附加

        Buffer response = Buffer.buffer(6);
        response.appendByte((byte) 0x84);
        response.appendByte((byte) 0x01);
        response.appendBytes(EMPTY_ADDITIONAL);

        // 联机设置应答
        socket.write(response);
    }

    // 接口状态
    private void handleInterfaceStatus(NetSocket socket, Buffer packet) {
        log.info("收到主动上报 接口状态");
        // 根据附加字段第一个字节的数据，判断上报的是什么消息
        short firstByte = packet.getUnsignedByte(2);
        // AC型
        if (firstByte == 0x01) {
            // 0：无效 1：电压 2：电流 3：功率 4：功耗
            short secondByte = packet.getUnsignedByte(3);
            if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                log.info("端口ID：{}，电压：{}V", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 10);
            } else if (secondByte == 0x02) {
                log.info("端口ID：{}，电流：{}mA", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 10);
            } else if (secondByte == 0x03) {
                log.info("端口ID：{}，功率：{}w", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 1000);
            } else if (secondByte == 0x04) {// 8211 0104 0180 2900000000
                log.info("端口ID：{}，功耗：{}kw.h", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 1000);
            }
        } else if (firstByte == 0x02) { // 串口型232
            // 0：无效 1：俯仰角度 2：横滚角度 3：方位角度
            short secondByte = packet.getUnsignedByte(3);
            if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                log.info("端口ID：{}，俯仰角度：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 100);
            } else if (secondByte == 0x02) {
                log.info("端口ID：{}，横滚角度：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 100);
            } else if (secondByte == 0x03) {
                log.info("端口ID：{}，方位角度：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 100);
            }
        } else if (firstByte == 0x03) { // 串口型485
            // 0：无效 1：温度（单位：℃） 2：湿度（单位：%）
            short secondByte = packet.getUnsignedByte(3);
            if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                log.info("端口ID：{}，温度：{}℃", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 10);
            } else if (secondByte == 0x02) {
                log.info("端口ID：{}，湿度：{}%", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6) / 10);
            }
        } else if (firstByte == 0x04) { // 模拟量型
            // 0：无效 1：风速（单位：m/s） 2：PM2.5（单位：ug/m³） 3：PM10（单位：ug/m³）
            short secondByte = packet.getUnsignedByte(3);
            if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                log.info("端口ID：{}，风速：{}m/s", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            } else if (secondByte == 0x02) {
                log.info("端口ID：{}，PM2.5：{}ug/m³", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            } else if (secondByte == 0x03) {
                log.info("端口ID：{}，PM10：{}ug/m³", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            }
        } else if (firstByte == 0x05) { // IO触发型
            // 0：无效 1：门磁（单位：0常开、1常闭） 2：振动（单位：0常开、1常闭） 3：烟雾（单位：0常开、1常闭） 4：水浸（单位：0常开、1常闭）
            short secondByte = packet.getUnsignedByte(3);
            if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                log.info("端口ID：{}，门磁：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            } else if (secondByte == 0x02) {
                log.info("端口ID：{}，振动：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            } else if (secondByte == 0x03) {
                log.info("端口ID：{}，烟雾：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            } else if (secondByte == 0x04) {
                log.info("端口ID：{}，水浸：{}", packet.getUnsignedByte(4), (float) packet.getUnsignedIntLE(6));
            }
        }

        short status = packet.getUnsignedByte(10); // 异常状态
        log.info("接口状态：{}", status == 0 ? "正常" : "异常");
    }

    // 电源管理
    private void handlePowerManagement(NetSocket socket, Buffer packet) {
        log.info("收到主动上报 电源管理");
        // 主电源断电处理方式
        packet.getBuffer(2, 4);
        String alarmmethod = null;
        if (packet.getByte(2) == 0x00) {
            alarmmethod = "无报警方式";
        } else if (packet.getByte(2) == 0x01) {
            alarmmethod = "声光（本地）";
        } else if (packet.getByte(2) == 0x02) {
            alarmmethod = "短信";
        } else if (packet.getByte(2) == 0x03) {
            alarmmethod = "FTP";
        } else if (packet.getByte(2) == 0x04) {
            alarmmethod = "邮件";
        }
        log.info("电源断电处理方式：{}", alarmmethod);
        // 主电源电量
        byte mainPowerSupplyCapacity = packet.getByte(4);
        log.info("主电源电量：{}%", mainPowerSupplyCapacity);
        // 备用电源电量
        byte backupPowerSupplyCapacity = packet.getByte(5);
        log.info("备用电源电量：{}%", backupPowerSupplyCapacity);
    }

    // GPS与北斗
    private void handleGps(NetSocket socket, Buffer packet) {
        log.info("收到主动上报 GPS与北斗");
        log.info("GPS卫星数：{}颗 北斗卫星数：{}颗 经度：{} 纬度：{} 高度：{} GPS信号强度：{} 4G信号强度：{}", packet.getByte(6), packet.getByte(7),
                packet.getUnsignedInt(8), packet.getUnsignedInt(9), packet.getUnsignedInt(10),
                packet.getByte(6), packet.getByte(7));
    }

    /**
     * 将Buffer转换为16进制字符串。
     *
     * @param buffer 要转换的Buffer对象
     * @return 16进制字符串
     */
    public static String bufferToHex(Buffer buffer) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : buffer.getBytes()) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexBuilder.append('0');
            }
            hexBuilder.append(hex);
        }
        return hexBuilder.toString();
    }

}
