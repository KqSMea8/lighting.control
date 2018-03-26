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
    //开始任务,节假日的当天00:00
    private String startTask;

    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 更新人
     */
    private Integer updateBy;

    public Holiday(){

    }

    public Holiday(String holidayTime, Integer projId,String startTask,Integer createBy) {
        this.holidayTime = holidayTime;
        this.projId = projId;
        this.startTask = startTask;
        this.createBy = createBy;
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

    public String getStartTask() {
        return startTask;
    }

    public void setStartTask(String startTask) {
        this.startTask = startTask;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
}
