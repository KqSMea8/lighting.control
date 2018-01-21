package com.dikong.lightcontroller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Description 经纬度计算天亮和天黑时间
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月18日上午11:49
 * @see
 *      </P>
 */
public class TimeCalculate {
    private static final double pi = Math.PI;
    private static final double rad = 180 * 3600 / 3.14159265;

    private static final Byte SUNRISE = 1;// 日出
    private static final Byte NOON = 2;// 中天时间
    private static final Byte DAWN = 3;// 天亮时间

    /**
     * 获取日出时间
     * 
     * @return
     */
    public static Date getSunriseTime(double JD, double WD) {
        double sunrise = timeCalculate(SUNRISE, JD, WD);
        String sun = timeStr(sunrise);
        System.out.println(sun);
        return double2Date(sunrise);
    }

    public static void main(String[] args) {
        double jd = 116.407526;
        double wd = 39.90403;
        getSunriseTime(jd,wd);
        getNoonTime(jd,wd);
        getDawnTime(jd,wd);
    }
    /**
     * 获取中午时间
     */
    public static Date getNoonTime(double JD, double WD) {
        double noon = timeCalculate(NOON, JD, WD);
        String sun = timeStr(noon);
        System.out.println(sun);
        return double2Date(noon);
    }

    /**
     * 获取天亮时间
     */
    public static Date getDawnTime(double JD, double WD) {
        double dawn = timeCalculate(DAWN, JD, WD);
        String sun = timeStr(dawn);
        System.out.println(sun);
        return double2Date(dawn);
    }

    /**
     * 获取日落时间
     */
    public static Date getSunsetTime(double JD, double WD) {
        double sunset = timeCalculate(NOON, JD, WD) * 2 - timeCalculate(SUNRISE, JD, WD);
        return double2Date(sunset);
    }

    /**
     * 获取夜晚时间
     */
    public static Date getNightTime(double JD, double WD) {
        double night = timeCalculate(NOON, JD, WD) * 2 - timeCalculate(DAWN, JD, WD);
        return double2Date(night);
    }

    /**
     * 获取昼长时间
     */
    public static void getDayLongTime(double JD, double WD) {

    }

    public static Date double2Date(Double d) {
        Date t = null;
        try {
            Calendar base = Calendar.getInstance();
            SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // Delphi的日期类型从1899-12-30开始
            base.set(1899, 11, 30, 0, 0, 0);
            base.add(Calendar.DATE, d.intValue());
            base.add(Calendar.MILLISECOND, (int) ((d % 1) * 24 * 60 * 60 * 1000));
            String format = outFormat.format(base.getTime());
            t = outFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return t;
    }

    private static double timeCalculate(byte type, double JD, double WD) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month = cal.get(Calendar.MONTH)+1;// 获取月份
        int date = cal.get(Calendar.DATE);// 获取日
        double richu;
        double L = -JD / 180 * pi;
        double fa = WD / 180 * pi;
        richu = timeToDouble(year, month, date, 0, 0, 0) - 2451544.5;
        switch (type) {
            case 1:
                return sunRise(richu, L, fa, 8.0 / 24);
            case 2:
                return noon(richu, L, 8.0 / 24);
            case 3:
                return dawn(richu, L, fa, 8.0 / 24);
        }
        return 0;
    }

    private static int timeToDouble(int y, int M, int d, int h, int m, int s) {
        double time = 0;
        if (M <= 2) {
            M += 12;
            y -= 1;
        }
        if (y * 372.0 + M * 31 + d >= 588829) {
            time = (int) (y / 100);
            time = 2 - time + (int) (time / 4);
        }
        time += (int) (365.25 * (y + 4716) + 0.01) + (int) (30.60001 * (M + 1)) + d
                + (h * 3600.0 + m * 60.0 + s) / (24.0 * 3600) - 1524.5;
        return (int) (time);
    }

    // 中天时间
    private static double noon(double jd, double L, double TZ) {
        double H, T, gst, A;
        T = jd / 36525;
        double J = sunLat(T);
        double sinJ = Math.sin(J);
        double cosJ = Math.cos(J);
        double E = (84381.4060 - 46.836769 * T) / rad; // 黄赤交角
        A = Math.atan2(sinJ * Math.cos(E), cosJ); // 太阳赤经
        gst = 2 * pi * (0.7790572732640 + 1.00273781191135448 * jd) // 恒星时（子午圈位置）
                + (0.014506 + 4612.15739966 * T + 1.39667721 * T * T) / rad;
        H = gst - L - A; // 太阳时角
        return jd - rad2rrad(H) / pi / 2.0 + TZ; // 中天时间
    }

    // 天亮时间
    private static double dawn(double jd, double L, double fa, double TZ) {
        double H, T, gst, A;
        T = jd / 36525;
        double J = sunLat(T);
        double sinJ = Math.sin(J);
        double cosJ = Math.cos(J);
        double E = (84381.4060 - 46.836769 * T) / rad; // 黄赤交角
        A = Math.atan2(sinJ * Math.cos(E), cosJ); // 太阳赤经
        gst = 2 * pi * (0.7790572732640 + 1.00273781191135448 * jd) // 恒星时（子午圈位置）
                + (0.014506 + 4612.15739966 * T + 1.39667721 * T * T) / rad;
        H = gst - L - A; // 太阳时角
        double D = Math.asin(Math.sin(E) * sinJ); // 太阳赤纬
        double cosH1 = (Math.sin(-4 * 3600 / rad) - Math.sin(fa) * Math.sin(D))
                / (Math.cos(fa) * Math.cos(D)); // 天亮的时角计算，地平线下6度，若为航海请改为地平线下12度，这里用4度，等亮光大一些。
        double H1 = -Math.acos(cosH1);
        return jd - rad2rrad(H - H1) / pi / 2.0 + TZ; // 天亮时间
    }

    // 日出时间
    private static double sunRise(double jd, double L, double fa, double TZ) {
        double T, J, gst, E, A, D, cosH0, H0, H1, H, sinJ, cosJ;
        jd -= TZ;
        T = jd / 36525;
        J = sunLat(T);
        sinJ = Math.sin(J);
        cosJ = Math.cos(J);
        gst = 2 * pi * (0.7790572732640 + 1.00273781191135448 * jd) // 恒星时（子午圈位置）
                + (0.014506 + 4612.15739966 * T + 1.39667721 * T * T) / rad;
        E = (84381.4060 - 46.836769 * T) / rad; // 黄赤交角
        A = Math.atan2(sinJ * Math.cos(E), cosJ); // 太阳赤经
        D = Math.asin(Math.sin(E) * sinJ); // 太阳赤纬
        cosH0 = (Math.sin(-50 * 60 / rad) - Math.sin(fa) * Math.sin(D))
                / (Math.cos(fa) * Math.cos(D)); // 日出的时角计算
        if (cosH0 >= 1 || cosH0 <= -1)
            return 0;
        H0 = -Math.acos(cosH0); // 升点时角（日出）
        H = gst - L - A; // 太阳时角
        return jd - rad2rrad(H - H0) / pi / 2.0 + TZ; // 日出时间
    }

    private static double sunLat(double t) {
        double J;
        t += (32 * (t + 1.8) * (t + 1.8) - 20) / 86400 / 36535;
        J = 48950621.66 + 6283319653.318 * t + 53 * t * t - 994
                + 334166 * Math.cos(4.669257 + 628.307585 * t)
                + 3489 * Math.cos(4.6261 + 1256.61517 * t)
                + 2060.6 * Math.cos(2.67823 + 628.307585 * t) * t;
        return J / 10000000;
    }


    private static double rad2rrad(double v) {
        v = v - (2 * pi) * (int) (v / (2 * pi));
        if (v <= -pi)
            return v + 2 * pi;
        if (v > pi)
            return v - 2 * pi;
        return v;
    }

    private static String timeStr(double time) {
        double T=time+0.5;
        T=(T-Math.floor(T))*24;
        double jwH=Math.floor(T);
        T=(T-jwH)*60;
        double jwM=Math.floor(T);
        T=(T-jwM)*60;
        double jwS=Math.floor(T+0.5);
        StringBuilder builder = new StringBuilder();
        builder.append(jwH).append(":").append(jwM).append(":").append(jwS);
        return builder.toString();
    }

}
