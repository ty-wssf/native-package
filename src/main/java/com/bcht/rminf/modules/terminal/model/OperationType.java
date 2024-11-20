package com.bcht.rminf.modules.terminal.model;

/**
 * 操作类型
 */
public enum OperationType {

    QUERY_REQUEST((byte) 0x80, "查询请求", "该消息是查询消息"),
    SET_REQUEST((byte) 0x81, "设置请求", "该消息是设置消息"),
    INITIATIVE_REPORT((byte) 0x82, "主动上报", "该消息表示设备主动上报消息，无需应答"),
    QUERY_RESPONSE((byte) 0x83, "查询应答", "该消息是对查询消息的应答消息"),
    SET_RESPONSE((byte) 0x84, "设置应答", "该消息是对设置消息的确认消息"),
    ERROR_RESPONSE((byte) 0x85, "出错应答", "该消息表示发送方消息异常"),
    RESERVED((byte) 0x86, "保留", "\\");

    private byte code;

    private String name;

    private String desc;

    OperationType(byte code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static OperationType getOperationTypeByCode(int code) {
        for (OperationType type : OperationType.values()) {
            if (type.getCode() == (byte) code) {
                return type;
            }
        }
        return null; // 如果没有找到匹配的code，返回null
    }

}
