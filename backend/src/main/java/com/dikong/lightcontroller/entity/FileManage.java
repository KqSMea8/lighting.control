package com.dikong.lightcontroller.entity;

import java.util.Date;

/**
 * @author huangwenjun
 * @version 2018年8月21日 上午12:47:09
 */
public class FileManage {

    private Integer id;

    /**
     * 所属项目id
     */
    private Integer projectId;

    // 文件类型 1->图片 2->音频 3->点位文件
    private Integer fileType;

    /**
     * 文件路径
     */
    private String filePath;

    private Integer createBy;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileManage [id=" + id + ", projectId=" + projectId + ", fileType=" + fileType
                + ", filePath=" + filePath + ", createBy=" + createBy + ", createTime=" + createTime
                + "]";
    }
}
