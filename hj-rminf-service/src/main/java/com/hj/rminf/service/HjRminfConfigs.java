package com.hj.rminf.service;

import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.config.IConfigReference;
import io.nop.api.core.util.SourceLocation;

import static io.nop.api.core.config.AppConfig.varRef;

public interface HjRminfConfigs {

    SourceLocation s_loc = SourceLocation.fromClass(HjRminfConfigs.class);

    @Description("vertx http port")
    IConfigReference<Integer> VERTX_HTTP_PORT = varRef(s_loc, "vertx.http.port", Integer.class,
            8081);

    // 移动警车gps业务相关配置
    @Description("警车GPS数据上传ftp path")
    IConfigReference<String> HJ_JC_GPS_PATH = varRef(s_loc, "hj.jc.gps.ftp.path", String.class,
            "/TRAFFIC_GIS");

    @Description("警车GPS数据上传ftp host")
    IConfigReference<String> HJ_JC_GPS_FTP_HOST = varRef(s_loc, "hj.jc.gps.ftp.host", String.class,
            "10.20.11.206");

    @Description("警车GPS数据上传ftp port")
    IConfigReference<Integer> HJ_JC_GPS_FTP_PORT = varRef(s_loc, "hj.jc.gps.ftp.port", Integer.class,
            8082);

    @Description("警车GPS数据上传ftp username")
    IConfigReference<String> HJ_JC_GPS_FTP_USERNAME = varRef(s_loc, "hj.jc.gps.ftp.username", String.class,
            "791128058@qq.com");

    @Description("警车GPS数据上传ftp password")
    IConfigReference<String> HJ_JC_GPS_FTP_PASSWORD = varRef(s_loc, "hj.jc.gps.ftp.password", String.class,
            "wu@fang@top");

    // 百诚终端对接相关配置
    @Description("百诚智能终端业务功能是否启用")
    IConfigReference<Boolean> SMART_TERMINAL_ENABLED = varRef(s_loc, "smart.terminal.enabled", Boolean.class,
            true);
    @Description("百诚智能终端 tcp server port")
    IConfigReference<Integer> SMART_TERMINAL_TCP_SERVER_PORT = varRef(s_loc, "smart.terminal.tcp.server.port", Integer.class,
            1234);

}
