package com.dikong.lightcontroller.entity;

/**
 * 群组表
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Group {

    private Long id;
    /**
     * 群组名称
     */
    private String groupName;
    /**
     * 群组编码，Group1,内部变量依次累加
     */
    private String groupCode;

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

}