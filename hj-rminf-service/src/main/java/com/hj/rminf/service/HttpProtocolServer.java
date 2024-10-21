package com.hj.rminf.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpProtocolServer extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(HttpProtocolServer.class);

    @Override
    public void start() throws Exception {

        // 创建一个 Router 对象
        Router router = Router.router(vertx);

        // 创建一个路由，当 GET 请求到 "/hello" 时触发
        router.get("/hello").handler(this::handleHelloRequest);

        // 处理移动gps数据
        router.post("/hj/gps/yd").handler(new GpsYdHandle());

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router)
                .listen(HjRminfConfigs.VERTX_HTTP_PORT.get(), res -> {
                    if (res.succeeded()) {
                        log.info("http Server is now listening，port: {}", 8081);
                    } else {
                        log.info("Failed to bind!", res.cause());
                    }
                });
    }

    private void handleHelloRequest(RoutingContext context) {
        // 获取请求参数
        String name = context.request().getParam("name");
        // 构建响应消息
        String response = "Hello " + (name == null ? "World" : name);
        // 设置响应头和状态码
        context.response()
                .putHeader("content-type", "text/plain; charset=utf-8")
                .setStatusCode(200);
        // 写入响应体并结束响应
        context.response().end(response);
    }

}
