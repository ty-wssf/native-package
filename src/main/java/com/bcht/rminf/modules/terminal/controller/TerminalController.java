package com.bcht.rminf.modules.terminal.controller;

import com.bcht.rminf.modules.sys.dto.PageList;
import com.bcht.rminf.modules.terminal.model.Device;
import com.bcht.rminf.modules.terminal.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Api("终端控制器")  //v2.3.8 之前要用：@Api(tags = "控制器")
@Mapping("terminal")
@Controller
public class TerminalController {

    private static final Logger log = LoggerFactory.getLogger(TerminalController.class);

    @Inject
    Vertx vertx;

    @ApiOperation("设备列表")
    @Get
    @Mapping()
    public Result<PageList<Device>> deviceList() throws ExecutionException, InterruptedException {
        Future<Message<String>> messageFuture = vertx.eventBus().request("terminal.deviceList", new JsonObject());
        // 等待异步操作完成
        CompletableFuture<Message<String>> completableFuture = messageFuture.toCompletionStage().toCompletableFuture();
        List<Device> list = ONode.deserialize(completableFuture.get().body(), List.class);
        return Result.data(new PageList<>(list, list.size()));
    }

    @ApiOperation("设备信息")
    @Get
    @Mapping("deviceinfo/{ip}")
    public Result<Device> deviceinfo(String ip) throws ExecutionException, InterruptedException {
        Future<Message<String>> messageFuture = vertx.eventBus().request("terminal.deviceinfo", ip);
        // 等待异步操作完成
        CompletableFuture<Message<String>> completableFuture = messageFuture.toCompletionStage().toCompletableFuture();
        Device device = ONode.deserialize(completableFuture.get().body(), Device.class);
        log.info("device：{}", device);
        return Result.data(device);
    }

}
