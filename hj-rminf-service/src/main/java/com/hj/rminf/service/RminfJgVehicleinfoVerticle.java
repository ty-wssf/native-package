package com.hj.rminf.service;

import io.vertx.core.AbstractVerticle;
import org.noear.solon.annotation.Component;

@Component
public class RminfJgVehicleinfoVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("test", message -> {
            message.body();
        });
    }

    @Override
    public void stop() throws Exception {

    }

}
