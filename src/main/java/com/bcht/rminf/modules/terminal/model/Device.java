package com.bcht.rminf.modules.terminal.model;

import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
public class Device {

    private String ip;
    private String status;
    private String statusName;
    private String manufacturer;
    private String name;
    private Integer productId;
    private String productIdName;
    private Integer productModel;
    private String productModelName;
    private Integer batchNumber;
    private String productionDate;
    private String softwareVersion;
    private String serialNumber;
    private String deviceName;
    private Map<String, DeviceState> deviceStateMap = new HashMap<>();
    private Collection<DeviceState> deviceStateList;
    private String powerOnHour;
    private String powerOnMinute;
    private String shutdownOnHour;
    private String shutdownMinute;

    public Device(String ip) {
        this.ip = ip;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        this.productIdName = ProductID.findByCode((byte) productId);
    }

    public void setProductModel(int productModel) {
        this.productModel = productModel;
        this.productModelName = ProductModel.findByCode((byte) productModel);
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusName = "0".equals(status) ? "离线" : "在线";
    }

}
