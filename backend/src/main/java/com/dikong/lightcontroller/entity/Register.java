package com.dikong.lightcontroller.entity;

/**
 * <p>
 * Description 设备变量表
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:46
 * @see
 *      </P>
 */
public class Register {

    public static final String BV = "BV";
    public static final String BI = "BI";
    public static final String AV = "AV";
    public static final String AI = "AI";


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

    /**
     * 项目id
     */
    private Integer projId;

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

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public void addDefault(){
        this.setVarName("CONNECT");
        this.setRegisName("CONNECT");
        this.setRegisAddr("10001");
        this.setRegisType(BI);
    }
}
