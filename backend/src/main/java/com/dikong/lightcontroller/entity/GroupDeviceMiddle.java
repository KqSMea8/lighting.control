package com.dikong.lightcontroller.entity;


public class GroupDeviceMiddle {

    private Long id;
    //设备id
    private Long deviceId;
    //群组id
    private Long groupId;
    //变量id
    private Long regisId;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRegisId() {
        return regisId;
    }

    public void setRegisId(Long regisId) {
        this.regisId = regisId;
    }
}
