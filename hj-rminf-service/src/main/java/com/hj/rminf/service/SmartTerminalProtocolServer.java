package com.hj.rminf.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 智能终端设备协议服务
 */
public class SmartTerminalProtocolServer extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(SmartTerminalProtocolServer.class);

    @Override
    public void start() {
        NetServerOptions options = new NetServerOptions().setPort(1234);
        NetServer server = vertx.createNetServer(options);
        server.connectHandler(socket -> {
            log.info("设备连接: " + socket.remoteAddress());
            // 根据设备类型进行不同的处理
            handleDevice(socket);
        }).listen(result -> {
            if (result.succeeded()) {
                log.info("tcp Server is now listening，port: {}", options.getPort());
            } else {
                log.info("Failed to bind!");
            }
        });
    }

    private void handleDevice(NetSocket socket) {
        // 读取数据
        socket.handler(buffer -> {
            // 根据协议进行解码
            String message = buffer.toString();
            System.out.println("reveive data: " + message);

            // 根据协议进行处理
            handleMessage(message, socket);
        });

        // 关闭连接
        socket.endHandler(v -> log.warn("设备断开连接：{}", socket.remoteAddress()));
    }

    private void handleMessage(String message, NetSocket socket) {
        // 这里需要根据具体的协议进行解码和处理
        // 例如，可以解析消息头来确定是哪种设备发来的数据
        // 然后根据设备类型调用不同的处理函数
        if (isMotorcycleDevice(message)) {
            handleMotorcycleDevice(message, socket);
        } else if (isYTJDevice(message)) {
            handleYTJDevice(message, socket);
        }
    }

    private boolean isMotorcycleDevice(String message) {
        // 实现摩托车设备消息的识别逻辑
        return false;
    }

    private void handleMotorcycleDevice(String message, NetSocket socket) {
        // 实现摩托车设备协议的具体处理逻辑
    }

    private boolean isYTJDevice(String message) {
        // 实现智能终端设备消息的识别逻辑
        return false;
    }

    private void handleYTJDevice(String message, NetSocket socket) {
        // 实现智能终端设备协议的具体处理逻辑
    }

}
