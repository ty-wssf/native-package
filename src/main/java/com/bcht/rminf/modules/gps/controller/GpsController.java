package com.bcht.rminf.modules.gps.controller;

import com.bcht.its.commons.gateway.ItsSynchronizationFile;
import com.bcht.its.das.commons.beans.FerryFile;
import com.bcht.its.das.commons.beans.MonitorFileType;
import com.bcht.rminf.modules.sys.dto.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Post;
import org.noear.solon.net.http.HttpUtils;
import org.noear.solon.serialization.prop.JsonPropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class GpsController {

    private static final Logger log = LoggerFactory.getLogger(GpsController.class);
    ObjectMapper mapper = new ObjectMapper();
    private volatile FTPClient ftpClient;

    @Post
    @Mapping("/hj/gps/yd")
    public String ydGps(Map<String, Object> data) throws IOException {
        if (!Solon.cfg().containsKey("hj.jc.gps.ftp.host")) {
            return "{\n" +
                    "    \"result\": 1,\n" +
                    "    \"resultNote\": \"请初始化配置\"\n" +
                    "}";
        }

        try {
            log.info("receive yd gps data：{}", data);

            // 处理gps数据
            Map<String, Object> gpsMap = new HashMap<>();
            gpsMap.put("lsh", UUID.randomUUID().toString().replace("-", ""));
            gpsMap.put("sbbh", data.get("deviceId"));
            gpsMap.put("sbsj", data.get("at"));
            gpsMap.put("jd", data.get("longitude"));
            gpsMap.put("wd", data.get("latitude"));
            gpsMap.put("sd", data.get("speed"));
            gpsMap.put("fx", data.get("direction"));
            log.info("警车gps数据：{}", gpsMap);
            // gpsJson.put("sbbh", json.);
            // 写文件或者远程ftp
            outPutFile(mapper.writeValueAsString(gpsMap), Solon.cfg().get("hj.jc.gps.ftp.path"), MonitorFileType.TRAFFIC_GIS);
            return "{\n" +
                    "    \"result\": 0,\n" +
                    "    \"resultNote\": \"success\"\n" +
                    "}";
        } catch (Exception e) {
            log.error("fail", e);
            return "{\n" +
                    "    \"result\": 1,\n" +
                    "    \"resultNote\": \"fail\"\n" +
                    "}";
        }
    }

    @Post
    @Mapping("/hj/gps/yd/debug")
    public Result<String> debug(Map<String, Object> data) throws IOException {
        try {
            String res = HttpUtils.http(String.format("http://localhost:%s/hj/gps/yd", Solon.cfg().getInt("server.port", 8080)))
                    .bodyOfBean(data)
                    .post();

            ONode oNodeRes = ONode.loadStr(res);
            if (0 == oNodeRes.get("result").getInt()) {
                return Result.ok("推送成功");
            } else {
                return Result.error("推送失败：" + oNodeRes.get("resultNote").getString());
            }
        } catch (Exception e) {
            return Result.error("推送失败：" + e.getMessage());
        }
    }

    // 发送到ftp
    public void outPutFile(String json, String pathName, MonitorFileType monitorFileType) throws IOException {
        ItsSynchronizationFile itsSynchronizationFile = new ItsSynchronizationFile();
        itsSynchronizationFile.setPassInfoJson(json);

        FerryFile ferryFile = new FerryFile();
        ferryFile.setMonitorFileType(monitorFileType);
        ferryFile.setFileData(itsSynchronizationFile);

        // 生成唯一的文件名
        String fileName = UUID.randomUUID().toString() + ".data";
        String remoteFilePath = pathName + "/" + fileName;

        // 序列化FerryFile对象
        byte[] datas = serialize(ferryFile);

        // 创建输入流
        InputStream inputStream = new ByteArrayInputStream(datas);

        createClient();
        // 上传文件到FTP服务器
        boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
        inputStream.close();

        if (done) {
            log.info("摆渡至指定文件夹成功: " + ferryFile.getMonitorFileType().getName());
        } else {
            log.error("Failed to upload file: " + remoteFilePath);
        }
    }

    public static byte[] serialize(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(obj, baos);
        return baos.toByteArray();
    }

    public static void serialize(Serializable obj, OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        ObjectOutputStream out = null;
        try {
            // stream closed in the finally
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    private void createClient() throws IOException {
        // 连接FTP服务器
        if (ftpClient == null) {
            synchronized (this) {
                if (ftpClient == null) {
                    FTPClient ftpClient_ = new FTPClient();
                    log.info("登录参数：{} {} {} {}", Solon.cfg().get("hj.jc.gps.ftp.host"), Solon.cfg().get("hj.jc.gps.ftp.port"),
                            Solon.cfg().get("hj.jc.gps.ftp.username"), Solon.cfg().get("hj.jc.gps.ftp.password"));
                    ftpClient_.connect(Solon.cfg().get("hj.jc.gps.ftp.host"), Solon.cfg().getInt("hj.jc.gps.ftp.port", 4444)); // FTP服务器地址
                    boolean loginResult = ftpClient_.login(Solon.cfg().get("hj.jc.gps.ftp.username"), Solon.cfg().get("hj.jc.gps.ftp.password")); // 登录FTP服务器
                    if (!loginResult) {
                        throw new IOException("登录失败");
                    }
                    log.info("登录成功");
                    ftpClient = ftpClient_;

                    // 设置文件类型为二进制，防止文件损坏
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    // 设置被动模式，因为大多数客户端都是从NAT后面进行连接
                    ftpClient.enterLocalPassiveMode();
                }
            }
        }
        if (ftpClient != null) {
            try {
                ftpClient.sendNoOp();
            } catch (IOException e) {
                ftpClient.disconnect();
                ftpClient = null;
                createClient();
                // throw new RuntimeException(e);
            }
        }

    }

}
