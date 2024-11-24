package com.bcht.rminf.modules.dhkafka;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrafficVechilePassInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 过车编号 由设备编号_号牌号码_号牌颜色_车辆序号
     */
    private String gcbh;
    /**
     * 设备序号
     */
    private String sbxh;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 号牌种类
     */
    private String hpzl;
    /**
     * 号牌颜色
     */
    private String hpys;
    /**
     * 车外廓长 单位：cm，为空时用0填充
     */
    private float cwkc;
    /**
     * 车辆（车身）颜色
     */
    private String clys;
    /**
     * 车辆类型
     */
    private String cllx;
    /**
     * 车辆速度 单位：km/h
     */
    private float sd;
    /**
     * 车道编号
     */
    private int cdbh;
    /**
     * 方向类型
     */
    private String fx;
    /**
     * 电子卡号
     */
    private String cid;
    /**
     * 电子芯片号
     */
    private String tid;
    /**
     * 图片URL(WEB访问地址)
     */
    private String tpurl;
    /**
     * 图片URL(WEB访问地址)
     */
    private String tpurl1;
    /**
     * 图片URL(WEB访问地址)
     */
    private String tpurl2;
    /**
     * 车辆经过时间
     */
    private Date jgsj;

    /**-----以下为V2新增字段-----*/
    /**
     * 人脸图片URL
     */
    private String faceUrl;
    /**
     * 副驾驶人脸图片URL
     */
    private String fjsFaceUrl;
    /**
     * 号牌特征图片URL
     */
    private String hptzTpurl;

    private String wfdm;

}
