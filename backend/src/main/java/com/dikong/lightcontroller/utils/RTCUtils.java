package com.dikong.lightcontroller.utils;

import java.util.Calendar;

public class RTCUtils {

	public static Long[] DeviceRTC(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0代表1月，11代表12月
		int day = cal.get(Calendar.DAY_OF_MONTH);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		// 打印年月日，时分秒
		long rtcTime = GetRtcTime(year, month, day, hour, minute, second);
		Long[] deviceRTC = new Long[2];
		deviceRTC[0] = rtcTime & 0xffff;
		deviceRTC[1] = (rtcTime>>16) & 0xffff;
		return deviceRTC;
	}

	public static long GetRtcTime(int year ,int month,int day ,int hour,int min ,int sec)
	{
		long tm, temp, ytemp;
		tm=0;
		for(temp=1970; temp<year; temp++){		          //算出1970年到当看的秒数
			if(temp%4==0) tm=tm+((366*24)*3600);
			else tm=tm+((365*24)*3600);
		}
		ytemp=temp;
		for(temp=1; temp<month; temp++){		              //算出从1月到当前月的秒数
			if(ytemp%4==0&&temp==2) tm=tm+((29*24)*3600);
			else if(ytemp%4!=0&&temp==2) tm=tm+((28*24)*3600);
			else if(temp==4||temp==6||temp==9||temp==11) tm=tm+((30*24)*3600);
			else tm=tm+((31*24)*3600);
		}
		tm=tm+(((day-1)*24)*3600)+(hour*3600)+(min*60)+sec;
		return tm;
	}

    // public static void main(String[] args) {
    // Date date = new Date();
    // int year = date.getYear();
    // int month = date.getMonth();
    // int day = date.getDay();
    // int hours = date.getHours();
    // int minutes = date.getMinutes();
    // int seconds = date.getSeconds();
    //
    // System.out.println(GetRtcTime(year,month,day,hours,minutes,seconds));
    // }

//	public static void main(String[] args) {
//		Long[] longs = DeviceRTC();
//
//		System.out.println(longs[0]+":"+Integer.toHexString(longs[0].intValue()));
//		System.out.println(longs[1]+":"+Integer.toHexString(longs[1].intValue()));
//	}
}
