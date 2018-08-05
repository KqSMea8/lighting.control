package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年8月5日 下午1:04:12
 */
public class TreeNodeDto {
    private Integer id;
    private Integer parentId;
    private String nodeName;// 节点名称
    private String editPageName;// 编辑页面名称
    private Integer refresh;// 刷新页面 0->不刷新 1->刷新

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getEditPageName() {
        return editPageName;
    }

    public void setEditPageName(String editPageName) {
        this.editPageName = editPageName;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }
}
