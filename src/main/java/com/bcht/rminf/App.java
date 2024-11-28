package com.bcht.rminf;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;

@SolonMain
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            // 重定向首页
            app.get("/", ctx -> ctx.redirect("index.html"));
        });
    }

}