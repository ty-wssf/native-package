package com.tmri.tfc.webservice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * @author wyl
 * @date 2024年08月01日 15:45
 */
@WebService(
        name = "Trans", // 暴露服务名称
        // targetNamespace = "http://webservice.tfc.tmri.com/"// 命名空间,一般是接口的包名倒序
        targetNamespace = ""// 命名空间,一般是接口的包名倒序
)
public interface TransWebService {

    @WebMethod(operationName = "InitTrans")
    long initTrans(
            @WebParam(name = "kkbh") String kkbh,
            @WebParam(name = "fxlx") String fxlx,
            @WebParam(name = "cdh") String cdh,
            @WebParam(name = "info") String info
    );

    @WebMethod(operationName = "WriteVehicleInfo")
    long writeVehicleInfo(
            @WebParam(name = "kkbh") String kkbh,
            @WebParam(name = "fxlx") String fxlx,
            @WebParam(name = "cdh") String cdh,
            @WebParam(name = "hphm") String hphm,
            @WebParam(name = "hpzl") String hpzl,
            @WebParam(name = "gcsj") String gcsj,
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
            @WebParam(name = "zkrs") String zkrs
    );

}
