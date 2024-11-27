package com.bcht.rminf.modules.terminal.model;

import lombok.Data;

@Data
public class DeviceState {

    private String id; // 端口ID（物理插口）
    private float voltage; // 电压
    private float electricCurrent; // 电流
    private float power; // 功率
    private float powerConsumption; // 功耗
    private float pitch; // 俯仰角度
    private float rollAngle; // 横滚角度
    private float azimuthAngle; // 方位角度
    private float temperature; // 温度
    private float humidity; // 湿度
    private float windSpeed; // 风速
    private float PM2_5; // PM2.5
    private float PM10; // PM10
    private float doorContact; // 门磁
    private float vibrate; // 振动
    private float smoke; // 烟雾
    private float waterImmersion; // 水浸

}
