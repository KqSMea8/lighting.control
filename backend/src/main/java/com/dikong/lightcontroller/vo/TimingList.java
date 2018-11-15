package com.dikong.lightcontroller.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月24日下午10:14
 * @see </P>
 */
public class TimingList {
    private Long id;

    private String nodeName;
    @ApiModelProperty(name = "时间点")
    private String nodeTime;


    private String city;

    private String device;

    @ApiModelProperty(name = "功能")
    private String features;

    private String[] week;

//    private String nodeContet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeTime() {
        return nodeTime;
    }

    public void setNodeTime(String nodeTime) {
        this.nodeTime = nodeTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String[] getWeek() {
        return week;
    }

    public void setWeek(String[] week) {
        this.week = week;
    }
}
