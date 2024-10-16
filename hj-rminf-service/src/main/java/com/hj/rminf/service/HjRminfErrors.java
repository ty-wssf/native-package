package com.hj.rminf.service;

import io.nop.api.core.exceptions.ErrorCode;

import static io.nop.api.core.exceptions.ErrorCode.define;

public interface HjRminfErrors {

    String RABBITMQ_EXCHANGE_NAME = "exchangeName";
    String RABBITMQ_TOPIC = "topic";
    String DATA = "data";

    ErrorCode ERR_RMINF_SEND_TO_MQ = define("nop.err.rminf.send-to-mq",
            "发送数据到rabbitmq异常:exchangeName={exchangeName} topic={topic} data={data}", RABBITMQ_EXCHANGE_NAME, RABBITMQ_TOPIC, DATA);

}
