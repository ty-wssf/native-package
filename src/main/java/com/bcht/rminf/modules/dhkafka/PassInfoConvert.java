package com.bcht.rminf.modules.dhkafka;

import lombok.extern.slf4j.Slf4j;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.core.exception.ConvertException;

import java.util.Date;

@Slf4j
public class PassInfoConvert implements Converter<String, TrafficVechilePassInfo> {

    @Override
    public TrafficVechilePassInfo convert(String value) throws ConvertException {
        ONode oNode = ONode.loadStr(value);
        TrafficVechilePassInfo passInfo = new TrafficVechilePassInfo();
        if ("trafficJunction".equals(oNode.get("event").getString())) {
            passInfo.setGcbh(oNode.get("recordId").getString());
            // 设备序号转变
            passInfo.setSbxh(oNode.get("channelId").getString());
            String hphm = oNode.get("plateNum").getString();
            passInfo.setHphm("00000000".equals(hphm) ? "-" : hphm);
            passInfo.setHpzl(oNode.get("plateType").getString());
            passInfo.setHpys(oNode.get("plateColor").getString());
            passInfo.setCwkc(oNode.get("carLength").getFloat());
            passInfo.setClys(oNode.get("carColor").getString());
            passInfo.setCllx(oNode.get("carType").getString());
            passInfo.setSd(oNode.get("carSpeed").getFloat());
            passInfo.setCdbh(oNode.get("carWayCode").getInt());
            passInfo.setFx("1");
            passInfo.setCdbh(oNode.get("carWayCode").getInt());
            passInfo.setJgsj(new Date(oNode.get("capTime").getLong()));
            oNode.get("imageList").forEach((v) -> {
                // 根据图片类型判断设置到哪个字段 imgType 0,6,2  imgIdx
                if ("0".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 1) {
                    passInfo.setTpurl(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
                if ("0".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 2) {
                    passInfo.setTpurl1(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
                if ("0".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 3) {
                    passInfo.setTpurl2(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
                if ("1".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 1) { // imgType类型1是合成图
                    if (Utils.isEmpty(passInfo.getTpurl())) {
                        passInfo.setTpurl(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                    } else if (Utils.isEmpty(passInfo.getTpurl1())) {
                        passInfo.setTpurl1(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                    } else if (Utils.isEmpty(passInfo.getTpurl2())) {
                        passInfo.setTpurl2(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                    }
                }
                if ("2".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 1) {
                    passInfo.setHptzTpurl(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
                if ("3".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 1) {
                    passInfo.setFaceUrl(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
                if ("4".equals(v.get("imgType").getString()) && v.get("imgIdx").getInt() == 1) {
                    passInfo.setFjsFaceUrl(Solon.cfg().get("solon.cloud.dhkafka.event.image.url.gateway") + v.get("imgUrl").getString());
                }
            });
            if ("0".equals(oNode.get("recType").getString())) { // 过车
                passInfo.setWfdm("0");
            } else {
                passInfo.setWfdm(oNode.get("recCode").getString());
            }
        } else {
            log.warn("接收到其它事件类型的数据，丢弃");
            return null;
        }
        return passInfo;
    }

}
