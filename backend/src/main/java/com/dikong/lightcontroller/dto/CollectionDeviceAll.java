package com.dikong.lightcontroller.dto;

import java.util.Map;

public class CollectionDeviceAll {

	private Integer Code;

	private Map<String,DtuStatus> Data;

	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public Map<String, DtuStatus> getData() {
		return Data;
	}

	public void setData(Map<String, DtuStatus> data) {
		Data = data;
	}

	public class DtuStatus{
		private Integer Status;

		public Integer getStatus() {
			return Status;
		}

		public void setStatus(Integer status) {
			Status = status;
		}
	}
}
