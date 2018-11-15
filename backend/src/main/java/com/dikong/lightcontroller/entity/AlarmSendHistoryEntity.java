package com.dikong.lightcontroller.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "alarm_send_history")
public class AlarmSendHistoryEntity {

	@Id
	private Integer id;
	/**
	 * 告警id
	 */
	private Integer alarmId;
	/**
	 * 告警发送信息
	 */
	private String  alarmSendReq;
	/**
	 * 告警响应信息
	 */
	private String alarmSendRsp;

	public AlarmSendHistoryEntity(Integer alarmId, String alarmSendReq, String alarmSendRsp) {
		this.alarmId = alarmId;
		this.alarmSendReq = alarmSendReq;
		this.alarmSendRsp = alarmSendRsp;
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

	public String getAlarmSendReq() {
		return alarmSendReq;
	}

	public void setAlarmSendReq(String alarmSendReq) {
		this.alarmSendReq = alarmSendReq;
	}

	public String getAlarmSendRsp() {
		return alarmSendRsp;
	}

	public void setAlarmSendRsp(String alarmSendRsp) {
		this.alarmSendRsp = alarmSendRsp;
	}
}
