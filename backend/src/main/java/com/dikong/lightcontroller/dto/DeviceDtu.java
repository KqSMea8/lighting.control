package com.dikong.lightcontroller.dto;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午5:49
 * @see </P>
 */
public class DeviceDtu {
    private Long id;

    private String dtuName;

    private String deviceCode;

    private String externalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDtuName() {
        return dtuName;
    }

    public void setDtuName(String dtuName) {
        this.dtuName = dtuName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
