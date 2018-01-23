package com.dikong.lightcontroller.entity;

/**
 * 更新时间
 * 
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Dtu {

    // 删除
    public static final Byte DEL_YES = 2;
    // 未删除
    public static final Byte DEL_NO = 1;

    private Long id;
    /**
     * DTU设备
     */
    private String device;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备码 DTU 注册包的信息
     */
    private String deviceCode;
    /**
     * 心跳包内容
     */
    private String beatContent;
    /**
     * 心跳包内容，默认180秒
     */
    private Integer beatTime;
    /**
     * 排序id
     */
    private Integer orderId;
    /**
     * 删除标记位
     */
    private Byte isDelete;

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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getBeatContent() {
        return beatContent;
    }

    public void setBeatContent(String beatContent) {
        this.beatContent = beatContent;
    }

    public Integer getBeatTime() {
        return beatTime;
    }

    public void setBeatTime(Integer beatTime) {
        this.beatTime = beatTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }
}
