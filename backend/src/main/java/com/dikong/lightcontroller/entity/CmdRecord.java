package com.dikong.lightcontroller.entity;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:23:30
 */
public class CmdRecord {

    private Long recordId;
    /**
     * dtu注册码
     */
    private String deviceCode;
    /**
     * 串口设备地址
     */
    private String devCode;
    /**
     * 变量地址
     */
    private String regisAddr;
    /**
     * 发送的命令内容
     */
    private String cmdInfo;
    private String result;
    private int createBy;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getRegisAddr() {
        return regisAddr;
    }

    public void setRegisAddr(String regisAddr) {
        this.regisAddr = regisAddr;
    }

    public String getCmdInfo() {
        return cmdInfo;
    }

    public void setCmdInfo(String cmdInfo) {
        this.cmdInfo = cmdInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

}
