package com.dikong.lightcontroller.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 冷荣富
 */
@Table(name = "alarm_setting")
public class AlarmSettingEntity {

	@Id
	private Integer id;
	/**
	 * 告警手机号
	 */
	private String alarmPhone;

	/**
	 * 是否开启短信告警，1->启用,2->禁用
	 */
	private Integer enableAlarmPhone;
	/**
	 * 告警邮件
	 */
	private String alarmEmail;
	/**
	 * 是否开启邮件告警，1->启用,2->禁用
	 */
	private Integer enableAlarmEmail;
	/**
	 * 是否开启语音告警，1->启用,2->禁用
	 */
	private Integer enableAlarmSound;
	/**
	 * 是否开启告警消失时发送语音短信，1->启用,2->禁用
	 */
	private Integer enableClearSound;
	/**
	 * 告警持续出现的时间
	 */
	private Integer interval;

	/**
	 * 告警消失时的文本信息
	 */
	private String clearAlarmCotent;
	/**
	 * 告警时的文本信息
	 */
	private String alarmCotent;
	/**
	 * 告警变量id
	 */
	@NotNull(message = "告警变量id不能为null")
	private Integer alarmRegisterId;
	/**
	 * 告警触发值
	 */
	@NotNull(message = "告警触发值不能为空")
	private String alarmCompareValue;
	/**
	 * 告警触发类型,1->等于，2->大于,3->小于,4->区间,5->不等于
	 */
	@NotNull(message = "告警触发类型,1->等于，2->大于,3->小于,4->区间,5->不等于 不能为空")
	private Integer alarmCompareType;
	/**
	 * 告警状态， 1->正常状态,2->告警状态
	 */
	private Integer alarmStatus;

	/**
	 * 触发状态,1->未触发,2->已触发
	 */
	private Integer triggerStatus;

	private Integer projectId;

	private Long create_by;
	private Long update_by;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public Integer getEnableAlarmPhone() {
		return enableAlarmPhone;
	}

	public void setEnableAlarmPhone(Integer enableAlarmPhone) {
		this.enableAlarmPhone = enableAlarmPhone;
	}

	public String getAlarmEmail() {
		return alarmEmail;
	}

	public void setAlarmEmail(String alarmEmail) {
		this.alarmEmail = alarmEmail;
	}

	public Integer getEnableAlarmEmail() {
		return enableAlarmEmail;
	}

	public void setEnableAlarmEmail(Integer enableAlarmEmail) {
		this.enableAlarmEmail = enableAlarmEmail;
	}

	public Integer getEnableAlarmSound() {
		return enableAlarmSound;
	}

	public void setEnableAlarmSound(Integer enableAlarmSound) {
		this.enableAlarmSound = enableAlarmSound;
	}

	public Integer getEnableClearSound() {
		return enableClearSound;
	}

	public void setEnableClearSound(Integer enableClearSound) {
		this.enableClearSound = enableClearSound;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getClearAlarmCotent() {
		return clearAlarmCotent;
	}

	public void setClearAlarmCotent(String clearAlarmCotent) {
		this.clearAlarmCotent = clearAlarmCotent;
	}

	public String getAlarmCotent() {
		return alarmCotent;
	}

	public void setAlarmCotent(String alarmCotent) {
		this.alarmCotent = alarmCotent;
	}

	public Integer getAlarmRegisterId() {
		return alarmRegisterId;
	}

	public void setAlarmRegisterId(Integer alarmRegisterId) {
		this.alarmRegisterId = alarmRegisterId;
	}

	public String getAlarmCompareValue() {
		return alarmCompareValue;
	}

	public void setAlarmCompareValue(String alarmCompareValue) {
		this.alarmCompareValue = alarmCompareValue;
	}

	public Integer getAlarmCompareType() {
		return alarmCompareType;
	}

	public void setAlarmCompareType(Integer alarmCompareType) {
		this.alarmCompareType = alarmCompareType;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public Long getCreate_by() {
		return create_by;
	}

	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}

	public Long getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(Long update_by) {
		this.update_by = update_by;
	}

	public Integer getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(Integer triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}
