package com.tmri.tfc.webservice;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author wyl
 * @date 2024年08月01日 15:48
 */
@Service
@WebService(serviceName = "Trans", // 与接口中指定的name一致, 都可以不写
        // targetNamespace = "http://webservice.tfc.tmri.com/", // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
        targetNamespace = "", // 与接口中的命名空间一致,一般是接口的包名倒，都可以不用写
        endpointInterface = "" // 接口类全路径
)
public class TransWebServiceImpl implements TransWebService {

    private Logger log = LoggerFactory.getLogger(TransWebServiceImpl.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public long initTrans(@WebParam(name = "kkbh") String kkbh,
                          @WebParam(name = "fxlx") String fxlx,
                          @WebParam(name = "cdh") String cdh,
                          @WebParam(name = "info") String info) {
        return 1;
    }

    @Override
    public long writeVehicleInfo(String kkbh, String fxlx, String cdh, String hphm, String hpzl,
                                 String gcsj, String clsd, String clxs, String wfdm, String cwkc,
                                 String hpys, String cllx, String fzhpzl, String fzhphm, String fzhpys,
                                 String clpp, String clwx, String csys, String tplj, String tp1,
                                 String tp2, String tp3, String tztp, String cid, String tid, String zkrs) {

        if (StrUtil.isNotEmpty(tp1)) {
            tp1 = tplj + tp1;
        }
        if (StrUtil.isNotEmpty(tp2)) {
            tp2 = tplj + tp2;
        }
        if (StrUtil.isNotEmpty(tp3)) {
            tp3 = tplj + tp3;
        }
        JSONObject obj = JSONUtil.createObj().set("kkbh", kkbh).set("fxlx", fxlx).set("cdh", cdh).set("hphm", hphm).set("hpzl", hpzl)
                .set("gcsj", gcsj).set("clsd", clsd).set("clxs", clxs).set("wfdm", wfdm).set("cwkc", cwkc)
                .set("hpys", hpys).set("cllx", cllx).set("fzhpzl", fzhpzl).set("fzhphm", fzhphm).set("fzhpys", fzhpys)
                .set("clpp", clpp).set("clwx", clwx).set("csys", csys).set("tplj", tplj).set("tp1", tp1)
                .set("tp2", tp2).set("tp3", tp3).set("tztp", tztp).set("cid", cid).set("tid", tid).set("zkrs", zkrs);
        rabbitTemplate.convertAndSend(obj.toString());
        log.info("writeVehicleInfo：{}", obj);
        return 1;
    }

}
