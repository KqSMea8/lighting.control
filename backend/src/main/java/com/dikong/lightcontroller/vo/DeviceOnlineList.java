package com.dikong.lightcontroller.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月28日下午6:32
 * @see
 *      </P>
 */
public class DeviceOnlineList {
    // 设备id
    private Long deviceId;
    // 对外id
    private String externalId;
    // dtu
    private String dtuName;
    // 设备名字
    private String deviceName;
    // 设备地址
    private String deviceCode;
    // 在线状态
    private Integer onlineStatus;
    // 最后调用时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastCall;
    // 使用时长 单位s
    private String useTimes;
    // 连接次数
    private long connectCount;
    // 离线次数
    private long disconnectCount;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDtuName() {
        return dtuName;
    }

    public void setDtuName(String dtuName) {
        this.dtuName = dtuName;
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

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getLastCall() {
        return lastCall;
    }

    public void setLastCall(Date lastCall) {
        this.lastCall = lastCall;
    }

    public String getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(String useTimes) {
        this.useTimes = useTimes;
    }

    public long getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(long connectCount) {
        this.connectCount = connectCount;
    }

    public long getDisconnectCount() {
        return disconnectCount;
    }

    public void setDisconnectCount(long disconnectCount) {
        this.disconnectCount = disconnectCount;
    }
}
