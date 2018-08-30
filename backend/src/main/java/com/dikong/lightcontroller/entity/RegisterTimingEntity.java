package com.dikong.lightcontroller.entity;

import javax.persistence.Id;

public class RegisterTimingEntity {

	@Id
	private Long id;
	//变量id
	private Integer registerId;
	//序列号id,0,1,2,3,4,5
	private Integer sequence;
	//时序id
	private Integer timingId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getTimingId() {
		return timingId;
	}

	public void setTimingId(Integer timingId) {
		this.timingId = timingId;
	}
}
