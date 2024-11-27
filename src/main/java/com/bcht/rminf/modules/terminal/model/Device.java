package com.bcht.rminf.modules.terminal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Device {

    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("status 1：在线 0：离线")
    private String status;
    @ApiModelProperty("statusName")
    private String statusName;
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
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("经度")
    private String longitude1;

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
