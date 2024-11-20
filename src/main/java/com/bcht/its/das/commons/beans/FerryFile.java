package com.bcht.its.das.commons.beans;

import java.io.Serializable;

/**
 * 摆渡文件
 * @author taoshide
 */
public class FerryFile<T extends Serializable>  implements Serializable{
    private static final long serialVersionUID = 1L;
    /**文件类型*/
    private MonitorFileType monitorFileType;
    /**文件数据*/
    T fileData;

    public MonitorFileType getMonitorFileType() {
        return monitorFileType;
    }

    public void setMonitorFileType(MonitorFileType monitorFileType) {
        this.monitorFileType = monitorFileType;
    }

    public T getFileData() {
        return fileData;
    }

    public void setFileData(T fileData) {
        this.fileData = fileData;
    }
}
