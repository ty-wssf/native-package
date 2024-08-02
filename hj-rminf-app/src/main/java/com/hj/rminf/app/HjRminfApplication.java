
package com.hj.rminf.app;

import com.tmri.tfc.webservice.TransWebServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.SolonMain;

@Import(scanPackages = "com.tmri.tfc.webservice")
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
        });
        Endpoint.publish(Solon.cfg().get("webservice.addr"), new TransWebServiceImpl());
        // WebServiceHelper.createWebClient(Solon.cfg().get("webservice.addr"), TransWebService.class);
        // EndpointImpl endpoint = new EndpointImpl(BusFactory.getDefaultBus(), new TransWebServiceImpl());
        // endpoint.publish("/service");
    }

}
