package com.dikong.lightcontroller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月24日上午10:52
 * @see
 *      </P>
 */
public class TimeOrdinaryNodeAdd {

    // 自动生成
    private String nodeName;
    // 日期

    private Integer[] weekList;
    // 开始时间
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private String startTime;
    // 开始时间类型
    @NotNull
    private Integer startTimeType;
    // 市id,选择到市区就行
    private Long cityId;
    // 省id,
    private Long provinceId;

    // 设备位置/群组 类型，1->群组类型，2->设备类型
    @NotNull
    private Integer runType;
    // 设备位置/群组 执行类型的id,群组id,设备id
    @NotNull
    private Long runId;
    // 设备位置/群组 中选定的是某一群组，这里将直接显示对应群组变量名，如：Group1，Group2，
    // 如果是群组就为空，如果是设备，就保存设备变量
    // 变数显示名称
    private Long runVar;
    // 设定值
    @NotNull
    private String runVarlue;
    // 在节假日停止工作
    @NotNull
    private Byte stopWorkOnHoliday;



    public Integer[] getWeekList() {
        return weekList;
    }

    public void setWeekList(Integer[] weekList) {
        this.weekList = weekList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(Integer startTimeType) {
        this.startTimeType = startTimeType;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Long getRunVar() {
        return runVar;
    }

    public void setRunVar(Long runVar) {
        this.runVar = runVar;
    }

    public String getRunVarlue() {
        return runVarlue;
    }

    public void setRunVarlue(String runVarlue) {
        this.runVarlue = runVarlue;
    }

    public Byte getStopWorkOnHoliday() {
        return stopWorkOnHoliday;
    }

    public void setStopWorkOnHoliday(Byte stopWorkOnHoliday) {
        this.stopWorkOnHoliday = stopWorkOnHoliday;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
}
