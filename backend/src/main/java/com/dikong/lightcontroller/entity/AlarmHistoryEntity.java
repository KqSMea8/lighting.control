package com.dikong.lightcontroller.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 冷荣富
 */
@Table(name = "alarm_history")
public class AlarmHistoryEntity {
	@Id
	private Integer id;
	/**
	 * 告警id
	 */
	private Integer alarmId;
	/**
	 * 告警变量id
	 */
	private Integer alarmRegisterId;
	/**
	 * 告警值
	 */
	private String alarmValue;
	/**
	 * 告警触发类型,1->发生告警,2->消除告警
	 */
	private Integer alarmType;

	public AlarmHistoryEntity(){
		super();
	}

	public AlarmHistoryEntity(Integer alarmId, Integer alarmRegisterId, String alarmValue) {
		this.alarmId = alarmId;
		this.alarmRegisterId = alarmRegisterId;
		this.alarmValue = alarmValue;
	}

	public AlarmHistoryEntity(Integer alarmId, Integer alarmRegisterId, String alarmValue,
			Integer alarmType) {
		this.alarmId = alarmId;
		this.alarmRegisterId = alarmRegisterId;
		this.alarmValue = alarmValue;
		this.alarmType = alarmType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public Integer getAlarmRegisterId() {
		return alarmRegisterId;
	}

	public void setAlarmRegisterId(Integer alarmRegisterId) {
		this.alarmRegisterId = alarmRegisterId;
	}

	public String getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(String alarmValue) {
		this.alarmValue = alarmValue;
	}

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}


}
