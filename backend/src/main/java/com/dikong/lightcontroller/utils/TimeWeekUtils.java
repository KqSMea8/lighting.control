package com.dikong.lightcontroller.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日下午5:11
 * @see
 *      </P>
 */
public class TimeWeekUtils {

    public static List<String> getWeekTime(String nowtime) throws ParseException {
        String monday = getMonday(nowtime);
        String tuesday = getTuesday(nowtime);
        String wednesday = getWednesday(nowtime);
        String thursday = getThursday(nowtime);
        String friday = getFriday(nowtime);
        String saturday = getSaturday(nowtime);
        String sunday = getSunday(nowtime);
        List<String> dayTime = new ArrayList<>();
        dayTime.add(monday);
        dayTime.add(tuesday);
        dayTime.add(wednesday);
        dayTime.add(thursday);
        dayTime.add(friday);
        dayTime.add(saturday);
        dayTime.add(sunday);
        return dayTime;
    }

    public static String getMonday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        return sdf.format(cal.getTime());
    }

    public static String getTuesday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }

    public static String getWednesday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 2);
        return sdf.format(cal.getTime());
    }

    public static String getThursday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 3);
        return sdf.format(cal.getTime());
    }

    public static String getFriday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 4);
        return sdf.format(cal.getTime());
    }

    public static String getSaturday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 5);
        return sdf.format(cal.getTime());
    }

    public static String getSunday(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = getCalendar(nowtime);
        cal.add(Calendar.DATE, 6);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当天时间是周几
     * @return
     */
    public static String getWeekNowDate() {
        Date dt = new Date();
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getLastWeek(int amount){
        Date dt = new Date();
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - (amount+1);
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 获取当天时期
     * @return
     */
    public static String getNowDateYearMonthDay(){
        Date dt = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dt);
    }


    public static String getLastYMD(int amount){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -amount);
        date = calendar.getTime();
        return sdf.format(date);
    }

    private static Calendar getCalendar(String nowtime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time = sdf.parse(nowtime);
        cal.setTime(time);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return cal;
    }

    public static void main(String[] args) {
        System.out.println(getLastWeek(0));
        System.out.println(getLastWeek(1));
        System.out.println(getLastWeek(2));
        System.out.println(getLastYMD(0));
        System.out.println(getLastYMD(1));
        System.out.println(getLastYMD(2));
    }
}
