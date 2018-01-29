package com.dikong.lightcontroller.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 群组表
 * 
 * @author lengrongfu
 * @date 2018年01月20日
 */
@Table(name = "group")
public class Group {
    // 删除
    public static final Byte DEL_YES = 2;
    // 未删除
    public static final Byte DEL_NO = 1;

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    /**
     * 群组名称
     */
    private String groupName;
    /**
     * 群组编码，Group1,内部变量依次累加
     */
    private String groupCode;

    /**
     * 项目id
     */
    private Integer projId;

    private Byte isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}
