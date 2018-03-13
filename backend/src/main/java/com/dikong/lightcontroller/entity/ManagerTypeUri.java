package com.dikong.lightcontroller.entity;

import java.util.Date;

/**
 * @author huangwenjun
 * @version 2018年3月12日 下午9:54:17
 */
public class ManagerTypeUri {
    private int id;
    private int managerTypeId;
    private int backUriId;
    private int createBy;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerTypeId() {
        return managerTypeId;
    }

    public void setManagerTypeId(int managerTypeId) {
        this.managerTypeId = managerTypeId;
    }

    public int getBackUriId() {
        return backUriId;
    }

    public void setBackUriId(int backUriId) {
        this.backUriId = backUriId;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
