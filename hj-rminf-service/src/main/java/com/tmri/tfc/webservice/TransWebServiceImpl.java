package com.tmri.tfc.webservice;

import io.nop.commons.util.StringHelper;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wyl
 * @date 2024年08月01日 15:48
 */
// @BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
@WebService(/*name = "Trans", */serviceName = "Trans", // 与接口中指定的name一致, 都可以不写
        /*targetNamespace = ""*/ // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
        targetNamespace = "http://webservice.tfc.tmri.com/" // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
)
public class TransWebServiceImpl implements TransWebService {

    private Logger log = LoggerFactory.getLogger(TransWebServiceImpl.class);
    /*@Autowired
    private RabbitTemplate rabbitTemplate;*/

    public long InitTrans(@WebParam(name = "kkbh") String kkbh,
                          @WebParam(name = "fxlx") String fxlx,
                          @WebParam(name = "cdh") String cdh,
                          @WebParam(name = "info") String info) {
        return 1;
    }

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
                                 @WebParam(name = "zkrs") String zkrs) {

        if (StringHelper.isNotEmpty(tp1)) {
            tp1 = tplj + tp1;
        }
        if (StringHelper.isNotEmpty(tp2)) {
            tp2 = tplj + tp2;
        }
        if (StringHelper.isNotEmpty(tp3)) {
            tp3 = tplj + tp3;
        }
        /*JsonTool.stringify("")
        JSONObject obj = JSONUtil.createObj().set("kkbh", kkbh).set("fxlx", fxlx).set("cdh", cdh).set("hphm", hphm).set("hpzl", hpzl)
                .set("gcsj", gcsj).set("clsd", clsd).set("clxs", clxs).set("wfdm", wfdm).set("cwkc", cwkc)
                .set("hpys", hpys).set("cllx", cllx).set("fzhpzl", fzhpzl).set("fzhphm", fzhphm).set("fzhpys", fzhpys)
                .set("clpp", clpp).set("clwx", clwx).set("csys", csys).set("tplj", tplj).set("tp1", tp1)
                .set("tp2", tp2).set("tp3", tp3).set("tztp", tztp).set("cid", cid).set("tid", tid).set("zkrs", zkrs);
        rabbitTemplate.convertAndSend(obj.toString());
        log.info("writeVehicleInfo：{}", obj);*/
        return 1;
    }


}
