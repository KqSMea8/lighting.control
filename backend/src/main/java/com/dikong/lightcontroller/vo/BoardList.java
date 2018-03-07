package com.dikong.lightcontroller.vo;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月15日上午11:08
 * @see </P>
 */
public class BoardList {
    // 设备id
    private Long deviceIdOrGroupId;
    // 对外id
    private String externalId;
    // dtu名字
    private String dtuOrSysName;
    // 设备名称
    private String deviceOrGroupName;
    // 设备id
    private String deviceCodeOrGroup;
    // 设备位置
    private String deviceLocation;
    // 类型 是设备还是群组
    private Integer itemType;

    public Long getDeviceIdOrGroupId() {
        return deviceIdOrGroupId;
    }

    public void setDeviceIdOrGroupId(Long deviceIdOrGroupId) {
        this.deviceIdOrGroupId = deviceIdOrGroupId;
    }


    public String getDtuOrSysName() {
        return dtuOrSysName;
    }

    public void setDtuOrSysName(String dtuOrSysName) {
        this.dtuOrSysName = dtuOrSysName;
    }

    public String getDeviceOrGroupName() {
        return deviceOrGroupName;
    }

    public void setDeviceOrGroupName(String deviceOrGroupName) {
        this.deviceOrGroupName = deviceOrGroupName;
    }

    public String getDeviceCodeOrGroup() {
        return deviceCodeOrGroup;
    }

    public void setDeviceCodeOrGroup(String deviceCodeOrGroup) {
        this.deviceCodeOrGroup = deviceCodeOrGroup;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
