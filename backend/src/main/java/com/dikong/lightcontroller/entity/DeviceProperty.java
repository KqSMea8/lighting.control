package com.dikong.lightcontroller.entity;


public class DeviceProperty {

    private Long id;
    /**
     * 设备id
     */
    private Long devId;
    /**
     * 系统参数设置名字
     */
    private String systemName;
    /**
     * 系统参数设置值
     */
    private String systemValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

}
