package com.dikong.lightcontroller.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "alarm_message")
public class AlarmMessageEntity {

	@Id
	private Integer id;
	//告警消息
	private String message;
	//用户id
	private Integer userId;
	//是否查看,1->未查看,2->已查看
	private Integer view;
	//项目id
	private Integer projectId;
	//更新人
	private Integer updateBy;

	public AlarmMessageEntity(String message, Integer userId, Integer projectId) {
		this.message = message;
		this.userId = userId;
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
}
