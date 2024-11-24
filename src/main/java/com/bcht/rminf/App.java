package com.bcht.rminf;

import com.bcht.rminf.config.GlobalConfig;
import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.sys.model.SysConfig;
import org.babyfish.jimmer.sql.JSqlClient;
import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.core.event.AppInitEndEvent;

import javax.sql.DataSource;
import java.util.List;

@SolonMain
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            // 重定向首页2
            app.get("/", ctx -> ctx.redirect("index.html"));
            app.onEvent(AppInitEndEvent.class, e -> {
                Solon.context().subBeansOfType(JSqlClient.class, jSqlClient -> {
                    try {
                        GlobalConfig.initH2(Solon.context().getBean(DataSource.class));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    List<SysConfig> list = jSqlClient.createQuery(Tables.SYS_CONFIG_TABLE)
                            .select(Tables.SYS_CONFIG_TABLE)
                            .execute();
                    if (list != null) {
                        for (SysConfig sysConfig : list) {
                            Solon.cfg().put(sysConfig.configKey().replace("_", "."), sysConfig.configValue());
                        }
                    }
                });
            });
        });
    }

}