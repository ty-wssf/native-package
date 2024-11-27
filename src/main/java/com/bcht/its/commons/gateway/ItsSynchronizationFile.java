package com.bcht.its.commons.gateway;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 边界同步实体类
 * 作    者: 陶诗德
 * 创建时间: 2017/5/26.
 */
public class ItsSynchronizationFile implements Serializable {
    /**过车数据json1*/
    private String passInfoJson;
    /**图片数据*/
    private Map<String,byte[]> imageData = new LinkedHashMap<String, byte[]>();

    public String getPassInfoJson() {
        return passInfoJson;
    }

    public void setPassInfoJson(String passInfoJson) {
        this.passInfoJson = passInfoJson;
    }

    public Map<String, byte[]> getImageData() {
        return imageData;
    }

    public void setImageData(Map<String, byte[]> imageData) {
        this.imageData = imageData;
    }
}
