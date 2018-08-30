package com.dikong.lightcontroller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/**
	 * 时间调整
	 * @param nowTime
	 * @param adjust
	 * @return
	 */
	public static String TimeAdjust(String nowTime,Integer adjust){
		if (adjust == null || adjust == 0){
			return nowTime;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date = sdf.parse(nowTime);
			long time = adjust * 60 * 1000; //adjust 分钟
			Date adjustDate = new Date(date.getTime() + time);
			String adjustTime = sdf.format(adjustDate);
			return adjustTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowTime;
	}
}
