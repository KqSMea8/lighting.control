package com.dikong.lightcontroller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午12:56
 * @see
 *      </P>
 */
public class DateToCronUtils {

    public static String cronFormt(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String cronFormt(String date) {
        String formatTimeStr = null;
        try {
            SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = dateSdf.parse(date);
            String dateFormat = "ss mm HH dd MM ? yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            if (date != null) {
                formatTimeStr = sdf.format(parse);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTimeStr;
    }

    public static String cronFormtHHssMM(String date, String week) {
        String formatTimeStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hhSSMM = null;
            hhSSMM = sdf.parse(date);
            String dateFormat = "ss mm HH ? * " + weekFormat(week) + " *";
            SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
            if (date != null) {
                formatTimeStr = sdf2.format(hhSSMM);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTimeStr;
    }

    private static String weekFormat(String week) {
        String[] weeks = week.split(",");
        StringBuilder builder = new StringBuilder();
        for (String w : weeks) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            if (Integer.valueOf(w) == 6) {
                builder.append("7");
                continue;
            }
            int w_1 = Integer.valueOf(w) + 1;
            builder.append(w_1);
        }
        return builder.toString();
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(weekFormat("3"));
        System.out.println(cronFormtHHssMM("10:00:00", "1,6"));
        System.out.println(UUID.randomUUID().toString());
    }
}
