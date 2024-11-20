package com.bcht.rminf.modules.terminal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Device {

    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("制造厂商")
    private String manufacturer;
    @ApiModelProperty("设备名称")
    private String name;
    @ApiModelProperty("产品ID")
    private Integer productId;
    @ApiModelProperty("产品类型")
    private String productIdName;
    @ApiModelProperty("产品型号")
    private Integer productModel;
    @ApiModelProperty("产品型号")
    private String productModelName;
    @ApiModelProperty("批次编号")
    private Integer batchNumber;
    @ApiModelProperty("出厂日期")
    private String productionDate;
    @ApiModelProperty("软件版本")
    private String softwareVersion;
    @ApiModelProperty("序列号")
    private String serialNumber;
    @ApiModelProperty("设备名称")
    private String deviceName;

    public Device(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        this.productIdName = ProductID.findByCode((byte) productId);
    }

    public int getProductModel() {
        return productModel;
    }

    public void setProductModel(int productModel) {
        this.productModel = productModel;
        this.productModelName = ProductModel.findByCode((byte) productModel);
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "Device{" +
                "ip='" + ip + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", productId=" + productId +
                ", productIdName='" + productIdName + '\'' +
                ", productModel=" + productModel +
                ", productModelName='" + productModelName + '\'' +
                ", batchNumber=" + batchNumber +
                ", productionDate='" + productionDate + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
