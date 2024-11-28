package com.bcht.rminf.config;

import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.runtime.ConnectionManager;
import org.babyfish.jimmer.sql.runtime.Executor;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class GlobalConfig {

    @Bean
    public JSqlClient sqlClient(DataSource ds) {
        return JSqlClient.newBuilder()
                /*.setCacheFactory(new CacheFactory() {
                    @Override
                    public Cache<?, ?> createObjectCache(ImmutableType type) {
                        return new ChainCacheBuilder<>()
                                .add(CaffeineValueBinder
                                        .forObject(type)
                                        .maximumSize(1024)
                                        .duration(Duration.ofMinutes(2))
                                        .build()
                                )
                                .build();
                    }
                })*/
                .setConnectionManager(ConnectionManager.simpleConnectionManager(ds))
                .setExecutor(Executor.log())
                .build();
    }

    public static void initH2(DataSource dataSource) throws Exception {
        try (Connection con = dataSource.getConnection()) {
            DatabaseMetaData metaData = con.getMetaData();
            String productName = metaData.getDatabaseProductName();
            if ("H2".equalsIgnoreCase(productName)) {
                String[] types = {"TABLE"};
                ResultSet tables = metaData.getTables(null, null, "%", types);
                boolean exists = false;
                List<String> tableList = new ArrayList<>();
                while (tables.next()) {
                    String table = tables.getString("TABLE_NAME");
                    tableList.add(table.toLowerCase());
                }
                tables.close();
                if (!tableList.contains("sys_config")) {
                    con.createStatement().execute("create table if not exists sys_config\n" +
                            "(\n" +
                            "    id identity(100, 1) not null,\n" +
                            "    config_key    varchar(50) not null,\n" +
                            "    config_value  varchar(200),\n" +
                            "    config_category  varchar(50),\n" +
                            "    created_time  timestamp   not null,\n" +
                            "    modified_time timestamp   not null\n" +
                            ");\n" +
                            "alter table sys_config\n" +
                            "    add constraint business_key_sys_config\n" +
                            "        unique (config_key)\n" +
                            ";");
                }
                if (!tableList.contains("sys_relation")) {
                    con.createStatement().execute("create table if not exists sys_relation\n" +
                            "(\n" +
                            "    id identity(100, 1) not null,\n" +
                            "    object_id    varchar(200) not null,\n" +
                            "    target_id  varchar(200),\n" +
                            "    category  varchar(50),\n" +
                            "    created_time  timestamp   not null,\n" +
                            "    modified_time timestamp   not null\n" +
                            "    );\n" +
                            "alter table sys_relation\n" +
                            "    add constraint business_key_sys_relation\n" +
                            "        unique (object_id, category)\n" +
                            ";");
                }
            }
        }
    }

}
