package com.dikong.lightcontroller.vo;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午5:15
 * @see </P>
 */
public class GroupDeviceList {
    private Long id;
    //设备id
    private Long deviceId;
    //设备位置
    private String devicePlace;
    //变量显示名称
    private String varName;
    //变量名
    private String regisName;
    //对外id
    private String externalId;
    //变量id
    private Long regisId;
    //寄存器值
    private String regisAddr;
    //接口类型，模拟还是数字，BV,AI,AV,BI
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

    public String getDevicePlace() {
        return devicePlace;
    }

    public void setDevicePlace(String devicePlace) {
        this.devicePlace = devicePlace;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public Long getRegisId() {
        return regisId;
    }

    public void setRegisId(Long regisId) {
        this.regisId = regisId;
    }
}
