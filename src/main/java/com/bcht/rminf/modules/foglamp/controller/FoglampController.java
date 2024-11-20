package com.bcht.rminf.modules.foglamp.controller;

import com.bcht.rminf.modules.foglamp.model.CommandEntity;
import com.bcht.rminf.modules.foglamp.model.ResultVo;
import com.bcht.rminf.modules.sys.dto.PageList;
import com.bcht.rminf.modules.terminal.model.Result;
import io.swagger.annotations.*;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.noear.snack.ONode;
import org.noear.solon.Utils;
import org.noear.solon.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Api("雾灯控制器")
@Controller
public class FoglampController {

    private static final Logger log = LoggerFactory.getLogger(FoglampController.class);
    @Inject
    private Vertx vertx;

    @ApiOperation("发送指令")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "command", value = "命令", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "subcommand", value = "子命令", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sbip", value = "设备IP", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "port", value = "设备端口", required = true, dataType = "Int", paramType = "query"),
    })
    @Get
    @Mapping("sendCommand")
    public ResultVo sendCommand(String command, String subcommand, String sbip, int port) throws ExecutionException, InterruptedException {
        if (Utils.isEmpty(command) || Utils.isEmpty(subcommand)) {
            log.info("接收到的参数为空！");
            return ResultVo.FAIL("参数不能为空！");
        }
        if (Utils.isEmpty(sbip) || port == 0) {
            log.info("设备IP或端口为空！");
            return ResultVo.FAIL("设备IP或端口为空！");
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommand(command);
        commandEntity.setSubcommand(subcommand);
        commandEntity.setSbip(sbip);
        commandEntity.setPort(port);
        Future<Message<String>> messageFuture = vertx.eventBus().request("foglamp.sendCommand", ONode.serialize(commandEntity));
        // 等待异步操作完成
        CompletableFuture<Message<String>> completableFuture = messageFuture.toCompletionStage().toCompletableFuture();
        Result<String> result = ONode.deserialize(completableFuture.get().body());
        if (Result.CODE_SUCCESS == result.getStatus()) {
            return ResultVo.OK();
        } else {
            return ResultVo.FAIL(result.getMsg());
        }
    }

    @ApiOperation("发送指令（用于前端调用测试）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "command", value = "命令", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "subcommand", value = "子命令", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sbip", value = "设备IP", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "port", value = "设备端口", required = true, dataType = "Int", paramType = "query"),
    })
    @Post
    @Mapping("foglamp/sendCommand")
    public Result<String> foglampSendCommand(String command, String subcommand, String sbip, int port) throws ExecutionException, InterruptedException {
        if (Utils.isEmpty(command) || Utils.isEmpty(subcommand)) {
            log.info("接收到的参数为空！");
            return Result.error("参数不能为空！");
        }
        if (Utils.isEmpty(sbip) || port == 0) {
            log.info("设备IP或端口为空！");
            return Result.error("设备IP或端口为空！");
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommand(command);
        commandEntity.setSubcommand(subcommand);
        commandEntity.setSbip(sbip);
        commandEntity.setPort(port);
        Future<Message<String>> messageFuture = vertx.eventBus().request("foglamp.sendCommand", ONode.serialize(commandEntity));
        // 等待异步操作完成
        CompletableFuture<Message<String>> completableFuture = messageFuture.toCompletionStage().toCompletableFuture();
        return ONode.deserialize(completableFuture.get().body());
    }

    @ApiOperation("设备列表")
    @Get
    @Mapping("foglamp")
    public Result<PageList<ONode>> deviceList() throws ExecutionException, InterruptedException {
        Future<Message<String>> messageFuture = vertx.eventBus().request("foglamp.deviceList", new JsonObject());
        // 等待异步操作完成
        CompletableFuture<Message<String>> completableFuture = messageFuture.toCompletionStage().toCompletableFuture();
        List<ONode> list = ONode.deserialize(completableFuture.get().body(), List.class);
        return Result.data(new PageList<>(list, list.size()));
    }

}
