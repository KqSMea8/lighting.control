package com.dikong.lightcontroller.vo;

import com.dikong.lightcontroller.dto.BasePage;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月23日上午10:38
 * @see </P>
 */
public class HistorySearch extends BasePage{

    //开始时间
    //yyyy-MM-dd HH:mm:ss
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;
    //结束时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;
    //变量id
    private Long varId;
    //变量类型
    private Integer varType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public Integer getVarType() {
        return varType;
    }

    public void setVarType(Integer varType) {
        this.varType = varType;
    }
}
