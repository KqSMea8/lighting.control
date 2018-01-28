package com.dikong.lightcontroller.utils.cmd;

/**
 * @author huangwenjun
 * @Datetime 2018年1月26日
 */
public class SimpleStringUtils {

    public static String repair4Char(String oldStr) {
        return repairChar(4, '0', oldStr);
    }

    public static String repairChar(int totalLength, String oldStr) {
        return repairChar(totalLength, '0', oldStr);
    }

    public static String repairChar(int totalLength, char addChar, String oldStr) {
        int oldLength = oldStr.length();
        if (oldLength >= totalLength) {
            return oldStr;
        }
        StringBuilder stringBuilder = new StringBuilder(addChar);
        for (int i = 0; i < totalLength - oldLength; i++) {
            stringBuilder.append(addChar);
        }
        stringBuilder.append(oldStr);
        return stringBuilder.toString();
    }
}
