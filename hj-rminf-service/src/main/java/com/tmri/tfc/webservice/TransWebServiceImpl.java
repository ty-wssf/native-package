package com.tmri.tfc.webservice;

import com.hj.rminf.dao.entity.RminfJgVehicleinfo;
import io.nop.api.core.beans.ApiRequest;
import io.nop.api.core.beans.ApiResponse;
import io.nop.api.core.ioc.BeanContainer;
import io.nop.biz.service.BizActionInvoker;
import io.nop.commons.util.StringHelper;
import io.nop.core.context.ServiceContextImpl;
import io.nop.core.lang.json.JsonTool;
import io.nop.file.core.MediaTypeHelper;
import io.nop.file.core.UploadRequestBean;
import io.nop.graphql.core.IGraphQLExecutionContext;
import io.nop.graphql.core.ast.GraphQLOperationType;
import io.nop.graphql.core.engine.GraphQLEngine;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.noear.solon.Solon;
import org.noear.solon.core.NvMap;
import org.noear.solon.core.handle.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wyl
 * @date 2024年08月01日 15:48
 */
// @BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
@WebService(/*name = "Trans", */serviceName = "Trans", // 与接口中指定的name一致, 都可以不写
        targetNamespace = "" // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
        // targetNamespace = "http://webservice.tfc.tmri.com/" // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
)
public class TransWebServiceImpl implements TransWebService {

    private Logger log = LoggerFactory.getLogger(TransWebServiceImpl.class);

    public long InitTrans(@WebParam(name = "kkbh") String kkbh,
                          @WebParam(name = "fxlx") String fxlx,
                          @WebParam(name = "cdh") String cdh,
                          @WebParam(name = "info") String info) {
        Map<String, Object> map = new HashMap<>();
        map.put("kkbh", kkbh);
        map.put("fxlx", fxlx);
        map.put("cdh", cdh);
        map.put("info", info);
        log.info("InitTrans：{}", JsonTool.serializeToJson(map));
        return 1;
    }

    public long WriteVehicleInfo(@WebParam(name = "kkbh") String kkbh,
                                 @WebParam(name = "fxlx") String fxlx,
                                 @WebParam(name = "cdh") String cdh,
                                 @WebParam(name = "hphm") String hphm,
                                 @WebParam(name = "hpzl") String hpzl,
                                 @WebParam(name = "gcsj") LocalDateTime gcsj,
                                 @WebParam(name = "clsd") String clsd,
                                 @WebParam(name = "clxs") String clxs,
                                 @WebParam(name = "wfdm") String wfdm,
                                 @WebParam(name = "cwkc") String cwkc,
                                 @WebParam(name = "hpys") String hpys,
                                 @WebParam(name = "cllx") String cllx,
                                 @WebParam(name = "fzhpzl") String fzhpzl,
                                 @WebParam(name = "fzhphm") String fzhphm,
                                 @WebParam(name = "fzhpys") String fzhpys,
                                 @WebParam(name = "clpp") String clpp,
                                 @WebParam(name = "clwx") String clwx,
                                 @WebParam(name = "csys") String csys,
                                 @WebParam(name = "tplj") String tplj,
                                 @WebParam(name = "tp1") String tp1,
                                 @WebParam(name = "tp2") String tp2,
                                 @WebParam(name = "tp3") String tp3,
                                 @WebParam(name = "tztp") String tztp,
                                 @WebParam(name = "cid") String cid,
                                 @WebParam(name = "tid") String tid,
                                 @WebParam(name = "zkrs") Integer zkrs) {
        if (!tp1.contains("http")) {
            if (StringHelper.isNotEmpty(tp1)) {
                tp1 = tplj + tp1;
            }
            if (StringHelper.isNotEmpty(tp2)) {
                tp2 = tplj + tp2;
            }
            if (StringHelper.isNotEmpty(tp3)) {
                tp3 = tplj + tp3;
            }
        }

        try {
            // 转存图片
            String prefix = "http://" + Solon.cfg().get("server.ip") + ":" + Solon.cfg().get("server.port");
            if (StringHelper.isNotEmpty(tp1)) {
                tp1 = prefix + upload(tp1, "tp1").get("value");
            }
            if (StringHelper.isNotEmpty(tp2)) {
                tp2 = prefix + upload(tp2, "tp2").get("value");
            }
            if (StringHelper.isNotEmpty(tp3)) {
                tp3 = prefix + upload(tp3, "tp3").get("value");
            }

            Map<String, Object> map = new HashMap<>();
            NvMap mvMap = Context.current().paramMap();
            mvMap.put("tp1", tp1);
            mvMap.put("tp2", tp2);
            mvMap.put("tp3", tp3);
            map.put("data", mvMap);
            BizActionInvoker.invokeActionSync(RminfJgVehicleinfo.class.getSimpleName(), "save", map,
                    null, new ServiceContextImpl());
        } catch (Exception e) {
            // log.error("WriteVehicleInfo：", e);
            return 0;
        }
        return 1;
    }

    // 上传
    public Map<String, String> upload(String filUurl, String fieldName) throws IOException {
        HttpURLConnection httpURLConnection = null;

        URL url = new URL(filUurl);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置请求方法
        httpURLConnection.setRequestMethod("GET");

        // 连接超时设置
        httpURLConnection.setConnectTimeout(5000);

        // 读取超时设置
        httpURLConnection.setReadTimeout(15000);

        // 获取输入流
        InputStream inputStream = httpURLConnection.getInputStream();

        UploadRequestBean requestBean = buildUploadRequestBean(inputStream, StringHelper.fileFullName(filUurl), httpURLConnection.getContentLength(),
                httpURLConnection.getContentType(), RminfJgVehicleinfo.class.getSimpleName(), fieldName);

        IGraphQLExecutionContext ctx = BeanContainer.getBeanByType(GraphQLEngine.class).newRpcContext(GraphQLOperationType.mutation,
                "NopFileStore__upload", ApiRequest.build(requestBean));
        ApiResponse<?> response = BeanContainer.getBeanByType(GraphQLEngine.class).executeRpc(ctx);
        return (Map) response.get();
    }

    protected UploadRequestBean buildUploadRequestBean(
            InputStream is, String fileName, long fileSize, String contentType, String bizObjName, String fieldName
    ) {
        String mimeType = MediaTypeHelper.getMimeType(contentType, StringHelper.fileExt(fileName));

        UploadRequestBean request = new UploadRequestBean(is, fileName, fileSize, mimeType);

        request.setBizObjName(bizObjName);
        request.setFieldName(fieldName);

        return request;
    }

}
