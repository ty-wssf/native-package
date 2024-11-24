package com.bcht.rminf.modules.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;
import org.noear.solon.annotation.Post;
import org.noear.solon.core.handle.ModelAndView;

@Api("控制器")
@Controller
public class DemoController {

    @ApiOperation("接口")
    @Mapping("/hello")
    public String hello(@Param(defaultValue = "world") String name) {
        return String.format("Hello %s!", name);
    }

    // {"status":0,"msg":"保存成功","data":{"id":1}}
    @Post
    @Mapping("/saveForm")
    public String saveForm() {
        return "{\"status\":0,\"msg\":\"保存成功\",\"data\":{\"id\":1}}";
    }

}