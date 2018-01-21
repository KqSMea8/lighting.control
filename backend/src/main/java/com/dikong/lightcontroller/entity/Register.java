package com.dikong.lightcontroller.entity;


public class Register {
    /**
     * 变量id
     */
    private Long id;
    /**
     * 设备id,系统变量的设备id用0来代替
     */
    private Long deviceId;
    /**
     * 变量显示名称
     */
    private String varName;
    /**
     * 寄存器名字
     */
    private String regisName;
    /**
     * 寄存器地址
     */
    private String regisAddr;
    /**
     * 接口类型，模拟还是数字，BV,AI,AV,BI
     */
    private String regisType;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
    public String getVarName() {
        return varName;
    }
    public void setVarName(String varName) {
        this.varName = varName;
    }
    public String getRegisName() {
        return regisName;
    }
    public void setRegisName(String regisName) {
        this.regisName = regisName;
    }
    public String getRegisAddr() {
        return regisAddr;
    }
    public void setRegisAddr(String regisAddr) {
        this.regisAddr = regisAddr;
    }
    public String getRegisType() {
        return regisType;
    }
    public void setRegisType(String regisType) {
        this.regisType = regisType;
    }

}