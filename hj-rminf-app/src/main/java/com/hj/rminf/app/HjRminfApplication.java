
package com.hj.rminf.app;

import com.hj.webservices.integration.WebServicePlugin;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;

@Import(scanPackages = {"com.tmri.tfc.webservice", "com.hj"})
@SolonMain
public class HjRminfApplication {

    public static void main(String... args) {
        Solon.start(HjRminfApplication.class, args, solonApp -> {
            solonApp.filter((ctx, chain) -> {
                if ("/".equals(ctx.pathNew())) { //ContextPathFilter 就是类似原理实现的
                    ctx.pathNew("/index.html");
                }
                chain.doFilter(ctx);
            });
            // webservices插件
            solonApp.pluginAdd(1, new WebServicePlugin());
        });
    }

}
