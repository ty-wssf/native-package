package com.bcht.rminf.modules.terminal.model;

import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
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
    DEVICE_INFO(0x06, "设备信息", 72, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.QUERY_REQUEST, context -> {
            Buffer response = Buffer.buffer(6);
            response.appendByte(OperationType.QUERY_REQUEST.getCode());
            response.appendByte(OperationObj.DEVICE_INFO.getCode());
            response.appendBytes(new byte[]{0x00, 0x00, 0x00, 0x00});
            // 发送查询设备信息的请求到设备
            context.getNetSocket().write(response);
        });
        put(OperationType.QUERY_RESPONSE, context -> {
            // 0000000042434854030005005f41ba2b333e030002000f00e80707090000000000000000000000000000000038000000aa9b047d000000880480e26648d6b61050b75311048000000a14297e
            // 00000000(附加) 42434854(制造厂商) 0300(产品ID) 0500(型号ID) 5f41ba2b333e(出厂日期) 0300(批次编号) 02000f00e8070709(当前版本) 00000000 00000000
            Buffer packet = context.getBuffer();
            Device device = context.getDevice();
            device.setManufacturer(packet.getString(4, 8));
            device.setProductId(packet.getUnsignedShortLE(8));
            device.setProductModel(packet.getUnsignedShortLE(10));
            // 2024-07-12 14:58:16.351 出场时间
            int millisecond = Integer.parseInt(String.format("%16s", Integer.toBinaryString(packet.getUnsignedShortLE(12))).replace(' ', '0').substring(6, 16), 2);
            int second = Integer.parseInt(String.format("%16s", Integer.toBinaryString(packet.getUnsignedShortLE(12))).replace(' ', '0').substring(0, 6), 2);
            int minute = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(26, 32), 2);
            int hour = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(21, 26), 2);
            // int week = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(18, 21), 2);
            int day = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(13, 18), 2);
            int month = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(9, 13), 2) + 1;
            int year = Integer.parseInt(String.format("%32s", Long.toBinaryString(packet.getUnsignedIntLE(14))).replace(' ', '0').substring(0, 9), 2) + 1900; // 11111100100
            device.setProductionDate(String.format("%d-%02d-%02d", year, month, day/*, hour, minute, second, millisecond*/));
            device.setBatchNumber(packet.getUnsignedShortLE(18));
            // V2.0.15.20240709 版本
            device.setSoftwareVersion(String.format("V%s.%s.%s.%s%02d%02d", packet.getUnsignedByte(20), packet.getUnsignedByte(21), packet.getUnsignedShortLE(22),
                    packet.getUnsignedShortLE(24), packet.getUnsignedByte(26), packet.getUnsignedByte(27)));
            // 5F41BA2B333E  唯一标识
            device.setSerialNumber(bytesToHex(packet.getBytes(12, 18)) + bytesToHex(packet.getBytes(18, 20)));
            // 区域号（读写） 4个字节
            // 经度（读写） 4个字节
            // packet.getFloat(32)
            try {
                device.setDeviceName(new String(packet.getBytes(40, 72), "GBK").trim());
            } catch (UnsupportedEncodingException e) {
                // throw new RuntimeException(e);
            }
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
    INTERFACE_ALARM(0x10, "接口与报警", 10, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.INITIATIVE_REPORT, (context) -> {
        });
    }}),
    INTERFACE_STATUS(0x11, "接口状态", 5, new HashMap<OperationType, Consumer<Context>>() {{
        put(OperationType.INITIATIVE_REPORT, (context) -> {
            Buffer packet = context.getBuffer();
            // 根据附加字段第一个字节的数据，判断上报的是什么消息
            short firstByte = packet.getUnsignedByte(0);
            Device device = context.getDevice();
            // AC型
            if (firstByte == 0x01) {
                // 0：无效 1：电压 2：电流 3：功率 4：功耗
                short secondByte = packet.getUnsignedByte(1);
                if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                    log.info("端口ID：{}，电压：{}V", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 10);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setVoltage((float) packet.getUnsignedIntLE(4) / 10);
                        return deviceState;
                    }).setVoltage((float) packet.getUnsignedIntLE(4) / 10);
                } else if (secondByte == 0x02) {
                    log.info("端口ID：{}，电流：{}mA", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 10);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setElectricCurrent((float) packet.getUnsignedIntLE(4) / 10);
                        return deviceState;
                    }).setElectricCurrent((float) packet.getUnsignedIntLE(4) / 10);
                } else if (secondByte == 0x03) {
                    log.info("端口ID：{}，功率：{}w", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 1000);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setPower((float) packet.getUnsignedIntLE(4) / 1000);
                        return deviceState;
                    }).setPower((float) packet.getUnsignedIntLE(4) / 10);
                } else if (secondByte == 0x04) {// 8211 0104 0180 2900000000
                    log.info("端口ID：{}，功耗：{}kw.h", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 1000);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setPowerConsumption((float) packet.getUnsignedIntLE(4) / 1000);
                        return deviceState;
                    }).setPowerConsumption((float) packet.getUnsignedIntLE(4) / 10);
                }
            } else if (firstByte == 0x02) { // 串口型232
                // 0：无效 1：俯仰角度 2：横滚角度 3：方位角度
                short secondByte = packet.getUnsignedByte(1);
                if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                    log.info("端口ID：{}，俯仰角度：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 100);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setPitch((float) packet.getUnsignedIntLE(4) / 100);
                        return deviceState;
                    }).setPitch((float) packet.getUnsignedIntLE(4) / 100);
                } else if (secondByte == 0x02) {
                    log.info("端口ID：{}，横滚角度：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 100);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setRollAngle((float) packet.getUnsignedIntLE(4) / 100);
                        return deviceState;
                    }).setRollAngle((float) packet.getUnsignedIntLE(4) / 100);
                } else if (secondByte == 0x03) {
                    log.info("端口ID：{}，方位角度：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 100);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setAzimuthAngle((float) packet.getUnsignedIntLE(4) / 100);
                        return deviceState;
                    }).setAzimuthAngle((float) packet.getUnsignedIntLE(4) / 100);
                }
            } else if (firstByte == 0x03) { // 串口型485
                // 0：无效 1：温度（单位：℃） 2：湿度（单位：%）
                short secondByte = packet.getUnsignedByte(1);
                if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                    log.info("端口ID：{}，温度：{}℃", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 10);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setTemperature((float) packet.getUnsignedIntLE(4) / 10);
                        return deviceState;
                    }).setTemperature((float) packet.getUnsignedIntLE(4) / 10);
                } else if (secondByte == 0x02) {
                    log.info("端口ID：{}，湿度：{}%", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4) / 10);
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setHumidity((float) packet.getUnsignedIntLE(4) / 10);
                        return deviceState;
                    }).setHumidity((float) packet.getUnsignedIntLE(4) / 10);
                }
            } else if (firstByte == 0x04) { // 模拟量型
                // 0：无效 1：风速（单位：m/s） 2：PM2.5（单位：ug/m³） 3：PM10（单位：ug/m³）
                short secondByte = packet.getUnsignedByte(1);
                if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                    log.info("端口ID：{}，风速：{}m/s", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setWindSpeed((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setWindSpeed((float) packet.getUnsignedIntLE(4));
                } else if (secondByte == 0x02) {
                    log.info("端口ID：{}，PM2.5：{}ug/m³", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setPM2_5((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setPM2_5((float) packet.getUnsignedIntLE(4));
                } else if (secondByte == 0x03) {
                    log.info("端口ID：{}，PM10：{}ug/m³", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setPM10((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setPM10((float) packet.getUnsignedIntLE(4));
                }
            } else if (firstByte == 0x05) { // IO触发型
                // 0：无效 1：门磁（单位：0常开、1常闭） 2：振动（单位：0常开、1常闭） 3：烟雾（单位：0常开、1常闭） 4：水浸（单位：0常开、1常闭）
                short secondByte = packet.getUnsignedByte(1);
                if (secondByte == 0x01) {// 8211 01 01 01 80 bd08000000
                    log.info("端口ID：{}，门磁：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setDoorContact((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setDoorContact((float) packet.getUnsignedIntLE(4));
                } else if (secondByte == 0x02) {
                    log.info("端口ID：{}，振动：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setVibrate((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setVibrate((float) packet.getUnsignedIntLE(4));
                } else if (secondByte == 0x03) {
                    log.info("端口ID：{}，烟雾：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setSmoke((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setSmoke((float) packet.getUnsignedIntLE(4));
                } else if (secondByte == 0x04) {
                    log.info("端口ID：{}，水浸：{}", packet.getUnsignedByte(2), (float) packet.getUnsignedIntLE(4));
                    device.getDeviceStateMap().computeIfAbsent(String.valueOf(packet.getUnsignedByte(2)), key -> {
                        DeviceState deviceState = new DeviceState();
                        deviceState.setId(key);
                        deviceState.setWaterImmersion((float) packet.getUnsignedIntLE(4));
                        return deviceState;
                    }).setWaterImmersion((float) packet.getUnsignedIntLE(4));
                }
            }

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
        return hexString.toString().toUpperCase();
    }

    public static String bytesToBinary(byte[] bytes) {
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            // 将每个字节转换为8位的二进制字符串，并添加到结果中
            binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return binaryStringBuilder.toString();
    }

    // 将十六进制字符串转换为字节数组的方法
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String hexStr = "42 43 48 54 03 00 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 BF B4 BE CD BF B4 BC B8 BD DA BF CE BF B4 BF B4 BF B4 00 00 6E 00 00 00 B0 6B 9C 5F";

        // 将十六进制字符串转换为字节数组
        byte[] bytes = hexStringToByteArray(hexStr.replace(" ", ""));

        // 将字节数组转换为字符串，这里假设编码为GBK
        String result = new String(bytes, "GB2312");

        // 打印结果
        System.out.println(result);
    }

}