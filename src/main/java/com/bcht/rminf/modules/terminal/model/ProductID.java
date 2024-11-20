package com.bcht.rminf.modules.terminal.model;

public enum ProductID {
    ALL_DEVICES((byte)0x0000, "所有设备"),
    SIGNAL_MACHINE_GB((byte)0x0001, "信号机(国标)"),
    SPEEDOMETER((byte)0x0002, "测速仪"),
    INTELLIGENT_ALARM_TERMINAL((byte)0x0003, "智能报警终端"),
    CURVED_ROAD((byte)0x0004, "弯道"),
    SERVER((byte)0x0005, "服务器"),
    SIGNAL_MACHINE_NEW_PROTOCOL((byte)0x0006, "信号机(新协议)"),
    EDGE_TERMINAL((byte)0x0007, "边缘终端");

    private byte code;
    private String name;

    ProductID(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据code查找对应的枚举描述
    public static String findByCode(byte code) {
        for (ProductID productId : ProductID.values()) {
            if (productId.getCode() == code) {
                return productId.getName();
            }
        }
        return null; // 如果没有找到匹配的code，返回未知产品ID
    }
}
