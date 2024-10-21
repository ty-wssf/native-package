package com.hj.rminf.service.config;

import com.hj.rminf.service.HttpProtocolServer;
import com.hj.rminf.service.SmartTerminalProtocolServer;
import io.nop.commons.service.LifeCycleSupport;
import io.vertx.core.Vertx;

public class VertxConfig extends LifeCycleSupport {

    private Vertx vertx;

    @Override
    protected void doStart() {
        vertx = Vertx.vertx();
        // 智能终端设备协议服务
        vertx.deployVerticle(new SmartTerminalProtocolServer());

        // http协议服务
        vertx.deployVerticle(new HttpProtocolServer());
    }

    @Override
    protected void doStop() {
        vertx.close();
    }

}
