package com.dikong.lightcontroller.dto;

public class AlarmList {

	private Integer id;

	/**
	 * 告警内容
	 */
	private String phoneAlarmContent;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 变量名称
	 */
	private String registerName;

	private Integer registerId;


	/**
	 * 参数
	 */
	private String alarmValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneAlarmContent() {
		return phoneAlarmContent;
	}

	public void setPhoneAlarmContent(String phoneAlarmContent) {
		this.phoneAlarmContent = phoneAlarmContent;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(String alarmValue) {
		this.alarmValue = alarmValue;
	}

	public Integer getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}
}
