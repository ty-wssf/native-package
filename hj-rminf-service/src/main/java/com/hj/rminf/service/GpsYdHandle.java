package com.hj.rminf.service;

import com.bcht.its.commons.gateway.ItsSynchronizationFile;
import com.bcht.its.das.commons.beans.FerryFile;
import com.bcht.its.das.commons.beans.MonitorFileType;
import io.nop.commons.util.StringHelper;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

public class GpsYdHandle implements Handler<RoutingContext> {

    private static final Logger log = LoggerFactory.getLogger(HttpProtocolServer.class);
    private volatile FTPClient ftpClient;

    @Override
    public void handle(RoutingContext routingContext) {
        // 获取表单数据
        routingContext.request().body().onComplete(ar -> {
            if (ar.succeeded()) {
                try {
                    log.info("receive yd gps data：{}", ar.result());

                    JsonArray receiveGpsJsonArray = new JsonArray(ar.result());
                    receiveGpsJsonArray.forEach(json -> {
                        JsonObject receiveJson = (JsonObject) json;
                        // 处理gps数据
                        JsonObject gpsJson = new JsonObject();
                        gpsJson.put("lsh", StringHelper.generateUUID());
                        gpsJson.put("sbbh", receiveJson.getString("deviceId"));
                        gpsJson.put("sbsj", receiveJson.getString("at"));
                        gpsJson.put("jd", receiveJson.getString("longitude"));
                        gpsJson.put("wd", receiveJson.getString("latitude"));
                        gpsJson.put("sd", receiveJson.getString("speed"));
                        gpsJson.put("fx", receiveJson.getString("direction"));
                        log.info("警车gps数据：{}", gpsJson);
                        // gpsJson.put("sbbh", json.);
                        // 写文件或者远程ftp
                        outPutFile(gpsJson.encode(), HjRminfConfigs.HJ_JC_GPS_PATH.get(), MonitorFileType.TRAFFIC_GIS);
                    });

                    // 构建 JSON 响应体
                    JsonObject responseJson = new JsonObject();
                    responseJson.put("result", 0)
                            .put("resultNote", "success");


                    // 设置响应头和状态码
                    routingContext.response()
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .setStatusCode(200);

                    // 写入响应体并结束响应
                    routingContext.response().end(responseJson.encode());
                } catch (Exception e) {
                    log.error("数据处理失败", e);
                    // 构建 JSON 响应体
                    JsonObject responseJson = new JsonObject();
                    responseJson.put("result", 1)
                            .put("resultNote", e.getMessage());
                    // 写入响应体并结束响应
                    routingContext.response().end(responseJson.encode());
                }
            } else {
                // 构建 JSON 响应体
                JsonObject responseJson = new JsonObject();
                responseJson.put("result", 1)
                        .put("resultNote", ar.cause());
                log.error("数据接收失败", ar.cause());
                // 写入响应体并结束响应
                routingContext.response().end(responseJson.encode());
                // routingContext.fail(ar.cause());
            }
        });
    }

    // 发送到ftp
    public void outPutFile(String json, String pathName, MonitorFileType monitorFileType) {
        ItsSynchronizationFile itsSynchronizationFile = new ItsSynchronizationFile();
        itsSynchronizationFile.setPassInfoJson(json);

        FerryFile ferryFile = new FerryFile();
        ferryFile.setMonitorFileType(monitorFileType);
        ferryFile.setFileData(itsSynchronizationFile);


        try {
            // 连接FTP服务器
            if (!ftpClient.isAvailable()) {
                ftpClient = null;
            }
            if (ftpClient == null) {
                synchronized (this) {
                    if (ftpClient == null) {
                        FTPClient ftpClient_ = new FTPClient();
                        log.info("登录参数：{} {} {} {}", HjRminfConfigs.HJ_JC_GPS_FTP_HOST.get(), HjRminfConfigs.HJ_JC_GPS_FTP_PORT.get(),
                                HjRminfConfigs.HJ_JC_GPS_FTP_USERNAME.get(), HjRminfConfigs.HJ_JC_GPS_FTP_PASSWORD.get());
                        ftpClient_.connect(HjRminfConfigs.HJ_JC_GPS_FTP_HOST.get(), HjRminfConfigs.HJ_JC_GPS_FTP_PORT.get()); // FTP服务器地址
                        boolean loginResult = ftpClient_.login(HjRminfConfigs.HJ_JC_GPS_FTP_USERNAME.get(), HjRminfConfigs.HJ_JC_GPS_FTP_PASSWORD.get()); // 登录FTP服务器
                        if (!loginResult) {
                            throw new IOException("登录失败");
                        }
                        log.info("登录成功");
                        ftpClient = ftpClient_;
                    }
                }
            }

            // 设置文件类型为二进制，防止文件损坏
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 设置被动模式，因为大多数客户端都是从NAT后面进行连接
            ftpClient.enterLocalPassiveMode();

            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + ".data";
            String remoteFilePath = pathName + "/" + fileName;
            existAndCreateDirectoryAllPath(ftpClient, pathName);

            // 序列化FerryFile对象
            byte[] datas = serialize(ferryFile);

            // 创建输入流
            InputStream inputStream = new ByteArrayInputStream(datas);

            // 上传文件到FTP服务器
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            if (done) {
                log.info("摆渡至指定文件夹成功: " + ferryFile.getMonitorFileType().getName());
            } else {
                log.error("Failed to upload file: " + remoteFilePath);
            }
        } catch (IOException e) {
            log.error("写文件失败", e);
        } /*finally {
            // 登出并断开连接
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                log.error("断开FTP连接失败", ex);
            }
        }*/
    }

    /**
     * 检查全路径 从FTP根路径检查当前传入的全路径是否存在，如果 不存在，自动创建目录并切换到指定目录
     *
     * @param path
     * @return
     * @throws IOException
     */
    public boolean existAndCreateDirectoryAllPath(FTPClient ftpClient, String path) {
        try {
            ftpClient.cwd("/");
            String[] allPath = path.split("/");
            for (String p : allPath) {
                if (!existDirectory(ftpClient, p)) {
                    ftpClient.makeDirectory(p);
                }
                ftpClient.changeWorkingDirectory(p);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 检查目录在服务器上是否存在 true：存在 false：不存在
     *
     * @param path
     * @return
     * @throws IOException
     */
    public boolean existDirectory(FTPClient ftpClient, String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
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

    public static void main(String[] args) {
        String server = "10.20.11.206";
        int port = 8082;
        String user = "791128058@qq.com";
        String pass = "wu@fang@top";

        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            ftp.login(user, pass);

            // 二进制文件传输
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            // 本地文件路径
            String localFilePath = "H:\\bcht\\demo-0.0.1-SNAPSHOT.jar";
            // 远程文件路径
            String remoteFilePath = "/path/to/remote/file.txt";

            // 使用OutputStream将本地文件写入远程服务器
            FileInputStream input = new FileInputStream(localFilePath);
            boolean success = ftp.storeFile(remoteFilePath, input);
            input.close();

            if (success) {
                System.out.println("文件成功写入到远程服务器。");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
