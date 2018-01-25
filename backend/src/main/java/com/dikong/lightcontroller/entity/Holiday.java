package com.dikong.lightcontroller.entity;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日下午6:07
 * @see </P>
 */
public class Holiday {

    private Long id;
    private String holidayTime;
    private Integer projId;

    public Holiday(){

    }

    public Holiday(String holidayTime, Integer projId) {
        this.holidayTime = holidayTime;
        this.projId = projId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayTime() {
        return holidayTime;
    }

    public void setHolidayTime(String holidayTime) {
        this.holidayTime = holidayTime;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }
}
