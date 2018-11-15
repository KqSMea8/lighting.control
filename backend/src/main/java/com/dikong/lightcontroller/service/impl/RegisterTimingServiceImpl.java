package com.dikong.lightcontroller.service.impl;

import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.service.RegisterTimingService;


@Service
public class RegisterTimingServiceImpl implements RegisterTimingService {



	/**
	 *
	 * @param time 表示时间，如19:00 表示为1900
	 * @param type 1-手动录入时间，2-天黑或者天亮，0-无效
	 * @return
	 */
    @Override
    public String nodeTime(String time, Integer type) {
        return null;
    }


	/**
	 *
	 * @param regAddr 变量地址
	 * @param value 变量值，时许中的值
	 * @return
	 */
	@Override
    public String nodeVariable(String regAddr, String value) {
        return null;
    }

	/**
	 *
	 * @param timeType 时间类型：
	 * @param runType
	 * @param runTime
	 * @param scheduleWeek
	 * @return
	 */
    @Override
    public String nodeWeek(String timeType, String runType, Integer runTime, Integer scheduleWeek) {
        return null;
    }
}
