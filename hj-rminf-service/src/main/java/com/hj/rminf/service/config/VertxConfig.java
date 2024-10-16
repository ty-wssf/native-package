package com.hj.rminf.service.config;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.LifecycleBean;

@Configuration
public class VertxConfig implements LifecycleBean {
    @Inject
    Vertx vertx;

    @Bean
    public Vertx vertx(){
        return Vertx.vertx(); //主要是给别的地方注入用
    }

    @Override
    public void start() throws Throwable {
        for(Verticle verticle : Solon.context().getBeansOfType(Verticle.class)){
            vertx.deployVerticle(verticle);
        }
    }

    @Override
    public void stop() throws Throwable {
        vertx.close();
    }
}
