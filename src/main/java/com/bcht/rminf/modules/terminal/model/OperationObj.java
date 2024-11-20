package com.bcht.rminf.modules.terminal.model;

import io.vertx.core.buffer.Buffer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public enum OperationObj {

    /**
     * 通用部分
     */
    RESERVED(0x00, "保留", 0, new HashMap<>()),
    ONLINE_HEARTBEAT(0x01, "联机/心跳", 0, new HashMap<OperationType, Consumer<Context>>() {
        {
            put(OperationType.QUERY_REQUEST, (context) -> {
                Buffer response = Buffer.buffer(6);
                response.appendByte((byte) 0x83);
                response.appendByte((byte) 0x01);
                response.appendBytes(new byte[]{0x00, 0x00, 0x00, 0x00});
                // 查询应答
                context.getNetSocket().write(response);
            });
            put(OperationType.SET_REQUEST, (context) -> {
                Buffer response = Buffer.buffer(6);
                response.appendByte((byte) 0x84);
                response.appendByte((byte) 0x01);
                response.appendBytes(new byte[]{0x00, 0x00, 0x00, 0x00});
                // 联机设置应答
                context.getNetSocket().write(response);
            });
        }
    }),
    NET_INTERFACE_PARAMS(0x02, "网口参数", 19, new HashMap<>()),
    SERIAL_PORT_PARAMS(0x03, "串口参数", 7, new HashMap<>()),
    FILE_TRANSFER(0x04, "文件传输", 0, new HashMap<>()),
    DEVICE_TIME(0x05, "设备时间", 6, new HashMap<>()),
    DEVICE_INFO(0x06, "设备信息", 32, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.QUERY_REQUEST, context -> {
            Buffer response = Buffer.buffer(6);
            response.appendByte(OperationType.QUERY_REQUEST.getCode());
            response.appendByte(OperationObj.DEVICE_INFO.getCode());
            response.appendBytes(new byte[]{0x00, 0x00, 0x00, 0x00});
            // 发送查询设备信息的请求到设备
            context.getNetSocket().write(response);
        });
        put(OperationType.QUERY_RESPONSE, context -> {
            // 00000000(附加) 42434854(制造厂商) 0300(产品ID) 0500(型号ID) 5f41ba2b333e(出厂日期) 0300(批次编号) 02000f00e8070709(当前版本) 00000000 00000000
            Buffer packet = context.getBuffer();
            Device device = context.getDevice();
            device.setManufacturer(packet.getString(4, 8));
            device.setProductId(packet.getUnsignedShortLE(8));
            device.setProductModel(packet.getUnsignedShortLE(10));
            int millisecond = Integer.parseInt(bytesToBinary(packet.getBytes(12, 14)).substring(0, 10), 2);
            int second = Integer.parseInt(bytesToBinary(packet.getBytes(12, 14)).substring(10, 16), 2);
            int minute = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(0, 6), 2);
            int hour = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(6, 11), 2);
            int week = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(11, 14), 2);
            int day = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(14, 19), 2);
            int month = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(19, 23), 2);
            int year = Integer.parseInt(bytesToBinary(packet.getBytes(14, 18)).substring(23, 32), 2);

            device.setBatchNumber(packet.getUnsignedShortLE(18));
            // device.setSoftwareVersion();
            // 区域号（读写） 4个字节
            // 经度（读写） 4个字节

            // device.setProductionDate(packet.getString(12, 18));
            // System.out.println();
        });
    }}),
    UPDATE_STATUS(0x07, "升级状态", 8, new HashMap<>()),
    REMOTE_CONTROL(0x08, "远程控制", 4, new HashMap<>()),
    POWER_MANAGEMENT(0x09, "电源管理", 0, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.INITIATIVE_REPORT, (context) -> {
        });
    }}),
    APN_ACCESS(0x0A, "APN接入", 192, new HashMap<>()),
    REAL_TIME_MEDIA_STREAM_CONTROL(0x0B, "实时媒体流控制", 8, new HashMap<>()),
    RESERVED_2(0x0C, "保留", 0, new HashMap<>()),
    RESERVED_3(0x0D, "保留", 0, new HashMap<>()),
    RESERVED_4(0x0E, "保留", 0, new HashMap<>()),
    RESERVED_5(0x0F, "保留", 0, new HashMap<>()),

    /**
     * 智能终端部分协议
     */
    INTERFACE_ALARM(0x10, "接口与报警", 10, new HashMap<>()),
    INTERFACE_STATUS(0x11, "接口状态", 5, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.INITIATIVE_REPORT, (context) -> {
        });
    }}),
    TIMER_POWER_SUPPLY(0x12, "定时供电", 4, new HashMap<>()),
    INTERFACE_COUNT(0x13, "接口数量", 0, new HashMap<>()),
    ONLINE_DEVICE_MONITORING(0x14, "在线设备监测", 37, new HashMap<>()),
    OTHER_PARAMS(0x15, "其他参数", 0, new HashMap<>()),
    GPS_AND_BEIDOU(0x16, "GPS与北斗", 16, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.INITIATIVE_REPORT, (context) -> {
        });
    }}),
    NTP_SYNC_INFO(0x17, "NTP校时信息", 48, new HashMap<>()),
    CAMERA_BINDING(0x18, "像机绑定", 8, new HashMap<>()),
    DEVICE_ID_2(0x19, "设备ID", 8, new HashMap<>()),
    PROTECTIVE_DEVICE_REPORT(0x20, "保护器主动上报", 30, new HashMap<>()),
    PROTECTIVE_DEVICE_QUERY(0x21, "保护器查询", 8, new HashMap<>()),
    PROTECTIVE_DEVICE_SETTING(0x22, "保护器设置", 2, new HashMap<>());

    private int code;
    private String name;
    private int length;
    private Map<OperationType, Consumer<Context>> handlers;

    OperationObj(int code, String name, int length, Map<OperationType, Consumer<Context>> handlers) {
        this.code = code;
        this.name = name;
        this.length = length;
        this.handlers = handlers;
    }

    public byte getCode() {
        return (byte) code;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Map<OperationType, Consumer<Context>> getHandlers() {
        return handlers;
    }

    public static OperationObj getOperationObjByCode(int code) {
        for (OperationObj obj : OperationObj.values()) {
            if (obj.getCode() == code) {
                return obj;
            }
        }
        return null; // 如果没有找到匹配的code，返回null
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String bytesToBinary(byte[] bytes) {
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            // 将每个字节转换为8位的二进制字符串，并添加到结果中
            binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return binaryStringBuilder.toString();
    }

}