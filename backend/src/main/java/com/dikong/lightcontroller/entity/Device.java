package com.dikong.lightcontroller.entity;

/**
 * 设备表 串口设备
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Device {

    private Long id;
    /**
     * dtu id
     */
    private Long dtuId;
    /**
     * 对外id第三方控制时需引用的地址号
     */
    private String externalId;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备编码
     * 串口设备id号,ID号可以是01-16. 最小为1， 最大为16.
     */
    private String code;
    /**
     * 设备通讯电表文件可以存为设备型号
     */
    private String model;
    /**
     * 在线状态，0->离线，1->在线
     */
    private Integer status;
    /**
     * 显示连线的统计次数
     */
    private Integer connectCount;
    /**
     * 未连接的统计次数
     */
    private Integer disconnectCount;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDtuId() {
        return dtuId;
    }
    public void setDtuId(Long dtuId) {
        this.dtuId = dtuId;
    }
    public String getExternalId() {
        return externalId;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getConnectCount() {
        return connectCount;
    }
    public void setConnectCount(Integer connectCount) {
        this.connectCount = connectCount;
    }
    public Integer getDisconnectCount() {
        return disconnectCount;
    }
    public void setDisconnectCount(Integer disconnectCount) {
        this.disconnectCount = disconnectCount;
    }

}
