package com.dikong.lightcontroller.entity;

import java.util.Date;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月22日下午4:18
 * @see </P>
 */
public class SysVar extends BaseSysVar{
    private Date  updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
