package com.dikong.lightcontroller.service;

public interface RegisterTimingService {

	String nodeTime(String time,Integer type);

	String nodeVariable(String regAddr,String value);

	String nodeWeek(String timeType,String runType,Integer runTime,Integer scheduleWeek);
}
