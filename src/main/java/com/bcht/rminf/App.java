package com.bcht.rminf;

import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.sys.model.SysConfig;
import org.babyfish.jimmer.sql.JSqlClient;
import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;
import org.noear.solon.core.event.AppInitEndEvent;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

@SolonMain
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            // 重定向首页
            app.get("/", ctx -> ctx.redirect("index.html"));
            app.onEvent(AppInitEndEvent.class, e -> {
                Solon.context().subBeansOfType(JSqlClient.class, jSqlClient -> {
                    try {
                        initH2(Solon.context().getBean(DataSource.class));
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

    private static void initH2(DataSource dataSource) throws Exception {
        try (Connection con = dataSource.getConnection()) {
            DatabaseMetaData metaData = con.getMetaData();
            String productName = metaData.getDatabaseProductName();
            if ("H2".equalsIgnoreCase(productName)) {
                String[] types = {"TABLE"};
                ResultSet tables = metaData.getTables(null, null, "%", types);
                boolean exists = false;
                while (tables.next()) {
                    String table = tables.getString("TABLE_NAME");
                    if ("sys_config".equalsIgnoreCase(table)) {
                        exists = true;
                        break;
                    }
                }
                tables.close();
                if (!exists) {
                    InputStream inputStream = Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("sql/h2/V1__Initial_create_table.sql");
                    if (inputStream == null) {
                        throw new RuntimeException("no `sql/h2/V1__Initial_create_table.sql`");
                    }
                    try (Reader reader = new InputStreamReader(inputStream)) {
                        char[] buf = new char[1024];
                        StringBuilder builder = new StringBuilder();
                        while (true) {
                            int len = reader.read(buf);
                            if (len == -1) {
                                break;
                            }
                            builder.append(buf, 0, len);
                        }
                        con.createStatement().execute(builder.toString());
                    }
                }
            }
        }
    }

}