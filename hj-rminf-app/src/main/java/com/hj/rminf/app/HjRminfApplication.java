
package com.hj.rminf.app;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;

@Import(scanPackages = "com.tmri.tfc.webservice")
@SolonMain
public class HjRminfApplication {

    String xmlResponse = "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:WriteVehicleInfoResponse xmlns:ns2=\"http://webservice.tfc.tmri.com/\"><return>1</return></ns2:WriteVehicleInfoResponse></S:Body></S:Envelope>";

    public static void main(String... args) {
        Solon.start(HjRminfApplication.class, args, solonApp -> {
            solonApp.filter((ctx, chain) -> {
                if ("/".equals(ctx.pathNew())) { //ContextPathFilter 就是类似原理实现的
                    ctx.pathNew("/index.html");
                }
                chain.doFilter(ctx);
            });
            /*solonApp.http("/rminf/services/Trans", new Handler() {
                @Override
                public void handle(Context ctx) throws Throwable {
                    // webservice协议实现
                    System.out.println(0);
                    ctx.render(1);
                    // 根据xml找到对应的方法

                    // 返回对应的xml结果

                    // 自己实现webservice编解码器
                    XNode node = XNodeParser.instance().parseFromText(null, ctx.body());
                    System.out.println();
                }
            });*/
        });
    }

}
