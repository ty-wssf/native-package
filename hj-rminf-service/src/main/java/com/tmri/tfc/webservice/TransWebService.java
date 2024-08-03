package com.tmri.tfc.webservice;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * @author wyl
 * @date 2024年08月02日 18:00
 */
@WebService(/*name = "Trans", */serviceName = "Trans", // 与接口中指定的name一致, 都可以不写
        /*targetNamespace = ""*/ // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
        targetNamespace = "http://webservice.tfc.tmri.com/" // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
)
public interface TransWebService {

    public long InitTrans(@WebParam(name = "kkbh") String kkbh,
                          @WebParam(name = "fxlx") String fxlx,
                          @WebParam(name = "cdh") String cdh,
                          @WebParam(name = "info") String info);

    public long WriteVehicleInfo(@WebParam(name = "kkbh") String kkbh,
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
                                 @WebParam(name = "zkrs") String zkrs);

}
