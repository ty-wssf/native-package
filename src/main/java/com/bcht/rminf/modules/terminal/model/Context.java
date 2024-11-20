package com.bcht.rminf.modules.terminal.model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

/**
 * 上下文参数
 */
public class Context {

    private NetSocket netSocket;
    // 来自设备的上下文数据
    private Buffer buffer;
    // 本地记录的设备数据
    private Device device;

    public NetSocket getNetSocket() {
        return netSocket;
    }

    public void setNetSocket(NetSocket netSocket) {
        this.netSocket = netSocket;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}
