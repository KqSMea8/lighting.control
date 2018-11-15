package com.dikong.lightcontroller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class HistoryExportReq {

	//开始时间
	//yyyy-MM-dd HH:mm:ss
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date startTime;
	//结束时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date endTime;

	private List<Varble> varbles;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Varble> getVarbles() {
		return varbles;
	}

	public void setVarbles(List<Varble> varbles) {
		this.varbles = varbles;
	}

	public class Varble{
		//变量id
		private Long varId;
		//变量类型
		private Integer varType;

		public Long getVarId() {
			return varId;
		}

		public void setVarId(Long varId) {
			this.varId = varId;
		}

		public Integer getVarType() {
			return varType;
		}

		public void setVarType(Integer varType) {
			this.varType = varType;
		}
	}
}
