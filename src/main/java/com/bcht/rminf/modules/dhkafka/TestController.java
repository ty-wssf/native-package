package com.bcht.rminf.modules.dhkafka;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api("测试发布消息到dhkafka控制器")
@Controller
public class TestController {

    @Inject
    Vertx vertx;
    KafkaProducer<String, String> producer;

    @ApiOperation("测试发布消息")
    @Mapping("/dhkafka/hello")
    public String hello() {
        //发布（找个地方安放一下）
        String data = "{\"sourceId\":\"54030200001325900176022024112219104201510\",\"country\":\"\",\"plateReliability\":100,\"carWidth\":0,\"plateTypeA\":\"02\",\"groupId\":\"1872801377\",\"carDirect\":\"1\",\"axleType\":0,\"orgCarType\":\"SaloonCar\",\"deviceId\":\"hQjxNKj9B1CJIQH97RCDE5\",\"plateColorA\":\"0\",\"machineName\":\"ITC902-RF2D\",\"objectSubType\":4,\"plateColor\":\"0\",\"recType\":0,\"recordId\":\"540302000013259001760220241122191042015100208330\",\"province\":\"\",\"tagCnt\":0,\"carLength\":0,\"viceSunvisor\":\"\",\"redLightDate\":0.0,\"sunCnt\":0,\"gpsAngle\":0.0,\"paperCnt\":0,\"carWayCode\":1,\"storageFlag\":2,\"carBrand\":\"2\",\"customCarDirect\":\"\",\"plateType\":\"02\",\"axleCount\":0,\"userChannelCode\":\"54030200001325900176\",\"plateNum\":\"藏BW6230\",\"tollsVehicleType\":0,\"picRecordId\":\"540302000013259001760220241122191042015100208330\",\"hasGps\":0,\"traceServiceInputTime\":1732273842837.0,\"dataSource\":0,\"dropCnt\":0,\"imageList\":[{\"imgUrl\":\"/image/efs_hQjxNKj9_001/43c273a876badea1e5a03b2e_trafficJunction_43_0/archivefile1-2024-11-22-121028-53E76EA818010D36:3008380928/744609.jpg\",\"imgWidth\":4096,\"cacheUrl\":\"http://54.13.1.38:39133/cameras/cache/picture?key=264414&uuid=4266238782929222217&saveTime=1732273842835&channelId=hQjxNKj9B1CJIQH97SAV9C\",\"imgSize\":744609,\"imgIdx\":1,\"imgHeight\":2224,\"time\":1732273840667.0,\"imgPixel\":\"4096*2224\",\"parentImgIdx\":0,\"imgType\":0},{\"imgWidth\":0,\"cacheUrl\":\"\",\"objBottom\":1989,\"imgSize\":0,\"imgIdx\":1,\"parentImgIdx\":1,\"imgUrl\":\"\",\"objLeft\":892,\"imgHeight\":0,\"time\":1732273840667.0,\"imgPixel\":\"0*0\",\"objRight\":1452,\"objTop\":1462,\"imgType\":6},{\"imgWidth\":160,\"cacheUrl\":\"http://54.13.1.38:39133/cameras/cache/picture?key=264415&uuid=4266238782929222217&saveTime=1732273842835&channelId=hQjxNKj9B1CJIQH97SAV9C\",\"objBottom\":1875,\"offset\":744609,\"imgSize\":0,\"imgIdx\":1,\"parentImgIdx\":1,\"imgUrl\":\"/image/efs_hQjxNKj9_001/43c273a876badea1e5a03b2e_plate_43_0/archivefile1-2024-11-22-160426-CCA5B5A318010D36:171974656/3256.jpg\",\"lenght\":3256,\"objLeft\":1024,\"imgHeight\":64,\"time\":1732273840667.0,\"imgPixel\":\"160*64\",\"objRight\":1144,\"objTop\":1837,\"imgType\":2}],\"presetId\":0,\"vehicleStyle\":\"0\",\"carColorA\":\"0\",\"featureData\":\"\",\"featureInfoUrl2\":\"\",\"featureInfoUrl4\":\"\",\"featureInfoUrl3\":\"\",\"featureInfoUrl5\":\"\",\"carSpeed\":0,\"bizExtCode\":\"\",\"mobileCnt\":2,\"smokeCnt\":2,\"uid\":\"1\",\"carColor\":\"0\",\"regionCode\":0,\"carType\":\"K33\",\"trafficLightState\":0,\"plateAttribute\":0,\"lapbeltSupCnt\":2,\"event\":\"trafficJunction\",\"minSpeed\":0,\"capTime\":1732273840667.0,\"channelId\":\"hQjxNKj9B1CJIQH97SAV9C\",\"featureId\":\"540302000013259001760220241122191042015100208330\",\"gpsX\":0.0,\"channelCode\":\"hQjxNKj9B1CJIQH97SAV9C\",\"gpsY\":0.0,\"carAttribute\":\"A\",\"carTypeA\":\"K33\",\"plateNumA\":\"藏BW6230\",\"wheelNum\":0,\"gpsHeight\":0.0,\"moveDirection\":0,\"transmit\":\"\",\"gpsSpeed\":0.0,\"maxSpeed\":70,\"parkingInfo\":{\"duration\":0,\"startParking\":0,\"allowedTime\":0},\"plateFlag\":\"\",\"recCode\":\"\",\"vehicleManufacturer\":\"2\",\"snapHeadstock\":0,\"extractedFlag\":0,\"lapbeltCnt\":2,\"isYellowLabel\":\"\",\"infoKind\":1,\"carHeight\":0,\"relatedFace\":0,\"isSupplement\":0,\"location\":\"\",\"endTime\":0.0,\"traceThingsInputTime\":1732273842834.0,\"vehicleMode\":\"0\",\"measureTemper\":{\"headTemperature\":0.0,\"leftTemperature\":0.0,\"rightTemperature\":0.0}}";
        ONode oNode = ONode.loadStr(data);
        log.info(oNode.get("imageList").toJson());

        // 创建一个Kafka Producer
        if (producer == null) {
            Map<String, String> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Solon.cfg().get("solon.cloud.dhkafka.event.server"));
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            producer = KafkaProducer.create(vertx, config);
        }
        KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(Solon.cfg().get("solon.cloud.dhkafka.event.topic"), oNode.toJson());
        producer.send(record, res -> {
            if (res.succeeded()) {
                log.info("Message produced successfully");
            } else {
                log.warn(res.cause().getMessage(), record.record());
            }
        });
        return "ok";
    }

}
