package com.bcht.rminf.modules.foglamp.model;

public class CommandEntity {

    private String sbip;

    private int port;

    /**
     * 命令
     * 工作状态 A
     * 工作模式 B
     * 闪烁频率 C
     * 尾迹长度 D
     * 发光亮度 E
     * 控制方式 F
     * 定时时间 G
     * 气象查询 H
     * 版本查询 I
     * 在线单元 J
     * 控制地址 K
     * 电压查询 U
     * 碰撞报警 L
     * 天气现象 M
     * SP能见度 N
     * 参数查询 Q
     * 时间设定 S
     * 当前时间 T
     * 配置预案 Y
     */
    private String command;

    /**
     * 子命令
     * 工作状态  打开-1、关闭-2
     * 工作模式  轮廓强化-1、主动诱导-2、追尾警示-3、雾霾警示-4、事故提醒-5
     * 闪烁频率  30/min-1、60/min-2、120/min-3、常亮-4
     * 尾迹长度  追尾警示模式尾迹组数      参数：1到8
     * 发光亮度  LED 灯亮度值            参数：1到8
     * 控制方式  星历-1、定时-2、阈值-3、后台-4
     * 定时时间  定时时间 第 1 字节开启时间，第 2 字节关闭时间（24h 时制） 【两个时间通逗号分割：】
     * 气象查询  查询能见度、气象六要素值、交互协议  【未实现】
     * 版本查询  版本号 查询控制箱主程序版本号      【未实现】
     * 在线单元  雾灯单元在线状态、电池电量百分比（被动接收）    【未实现】
     * 控制地址  信道、地址 雾灯单元安装区域控制器中心地址      【未实现】
     * 电压查询  雾灯单元在线状态、电池电量百分比（主动查询）    【未实现】
     * 碰撞报警  雾灯单元报警地址、碰撞信息（被动接收）         【未实现】
     * 天气现象  雾灯汉字模块显示：雨-1、雪-2、雾-3、冰-4、风-5、无【显示关闭】-6
     * SP能见度  平台解析视频能见度数据项值为 1-50000 米         【未实现】
     * 参数查询  查询当前运行参数                             【未实现】
     * 时间设定  当前时间 设定当前时间（年，月，日，时，分，秒）   【未实现】
     * 当前时间  查询当前时间                                【未实现】
     * 配置预案  运行方案 设定运行方案（状态、模式、闪频、尾长、亮度、控方） 工作状态+运行模式+闪烁频率+尾迹长度+发光亮度+控制方式【以逗号分割】
     */
    private String subcommand;

    /**
     * @return
     */
    public byte[] toBytes() {
        return null;
    }

    public String getSbip() {
        return sbip;
    }

    public void setSbip(String sbip) {
        this.sbip = sbip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSubcommand() {
        return subcommand;
    }

    public void setSubcommand(String subcommand) {
        this.subcommand = subcommand;
    }
}
