package com.dikong.lightcontroller.entity;

import javax.persistence.Id;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月30日下午5:10
 * @see </P>
 */
public class TimingCron {
    @Id
    private Long id;

    private Long timingId;

    private String cronJson;

    private String taskName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimingId() {
        return timingId;
    }

    public void setTimingId(Long timingId) {
        this.timingId = timingId;
    }

    public String getCronJson() {
        return cronJson;
    }

    public void setCronJson(String cronJson) {
        this.cronJson = cronJson;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
