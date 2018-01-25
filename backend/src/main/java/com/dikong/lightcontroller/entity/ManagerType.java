package com.dikong.lightcontroller.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
public class ManagerType {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer typeId;
    private String managerTypeName;
    /**
     * 1->启用，2->禁用
     */
    private Integer typeStatus;
    /**
     * 1->未删除 2->删除
     */
    private Integer isDelete;
    private Integer createBy;
    private Integer updateBy;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getManagerTypeName() {
        return managerTypeName;
    }

    public void setManagerTypeName(String managerTypeName) {
        this.managerTypeName = managerTypeName;
    }

    public Integer getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
