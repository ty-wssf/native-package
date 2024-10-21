package com.bcht.its.das.commons.beans;


/**
 * 监控文件类型<br/>
 * 因为此类只描述了文件摆渡时监听文件类型，但其描述的功能与汇聚平台中所有的数据类型一致，
 * 此类将在下个版本中进行移除，请优先使用{@link CollectDataType}
 * @author taoshide
 */
public enum MonitorFileType {
    /**
     * 车辆过车
     */
    VEH_PASS("00", "车辆过车"),
    /**
     * 普通车辆过车图片名称解析结构化数据
     */
    VEH_PASS_IMG("20","普通车辆过车图片名称解析结构化数据"),
    /**
     * 车辆违法
     */
    VEH_VIO("01", "车辆违法"),
    /**
     * 普通车辆违法图片名称解析结构化数据
     */
    VEH_VIO_IMG("21","普通车辆违法图片名称解析结构化数据"),
    /**
     * 事故
     */
    ACCIDENT("02", "事故"),

    /**
     * 区间违法
     */
    REGION_VIO("03", "区间违法"),
    /**
     * 对讲机GPS位置信息
     */
    MOBILE_GPS("04", "对讲机GPS位置信息"),
    /**
     * 对讲机用户信息
     */
    MOBILE_USER("05", "对讲机用户信息"),
    /**
     * 雷达事件数据
     */
    RADAR_EVENT("11","雷达事件数据"),

    /**
     设备状态数据
     */
    DEV_STATE("12", "设备状态"),

    /**
     交通流量信息数据
     */
    TRAFFIC_FLOW("13", "交通流量"),

    /**
     交通事件信息数据
     */
    TRAFFIC_INCIDENT("14", "交通事件"),

    /**
     * 警车定位数据
     */
    TRAFFIC_GIS("15", "警车定位"),

    /**
     * 人脸比对数据
     */
    FACE_ALIGNMENT("16", "人脸比对"),

    /**
     * 气象数据
     */
    FOG_WEATHER("17", "气象"),

    /**
     * 交通事件
     */
    INCIDENT("18", "事件"),

    /**
     * 第三方接口数据
     */
    DSF_INTERFACE("30", "第三方接口数据"),


    INTERNET_CSWF("31", "互联网测速违法"),

    /**
     * 百度互联网数据
     * */
    BAIDU_INCIDETN("32", "百度互联网数据"),


    JCZH_ALARM("33", "集成指挥预警"),

    VOICE("34", "语音信息"),

    /**
     * 皖通重点车辆预警
     */
    ALARM_STRESS_VEH_WT("10", "皖通重点车辆预警");

    private String value;
    private String name;

    MonitorFileType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
