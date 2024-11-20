package com.bcht.rminf.config;

import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.runtime.ConnectionManager;
import org.babyfish.jimmer.sql.runtime.Executor;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.LifecycleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Configuration
public class GlobalConfig implements LifecycleBean {

    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

    @Inject
    DataSource dataSource;

    @Bean
    public JSqlClient sqlClient(DataSource ds) {
        return JSqlClient.newBuilder()
                .setConnectionManager(ConnectionManager.simpleConnectionManager(ds))
                .setExecutor(Executor.log())
                .build();
    }

    @Override
    public void start() throws Throwable {
        initH2(dataSource);
    }

    private void initH2(DataSource dataSource) throws Exception {
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
                    InputStream inputStream = this.getClass()
                            .getClassLoader()
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
