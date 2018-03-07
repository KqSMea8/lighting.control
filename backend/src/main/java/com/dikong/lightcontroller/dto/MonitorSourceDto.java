package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年3月5日 下午9:12:24
 */
public class MonitorSourceDto {
    private long id;
    private int externalId;
    private String name;
    private String sysOrDtu;
    private String deviceId;
    private String local;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysOrDtu() {
        return sysOrDtu;
    }

    public void setSysOrDtu(String sysOrDtu) {
        this.sysOrDtu = sysOrDtu;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "MonitorSourceDto [id=" + id + ", externalId=" + externalId + ", name=" + name
                + ", sysOrDtu=" + sysOrDtu + ", deviceId=" + deviceId + ", local=" + local + "]";
    }
}
