package com.dikong.lightcontroller.utils;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月24日下午4:47
 * @see
 *      </P>
 */
public class ArraysUtils {

    public static String toString(Object[] objects){
        return toString(objects,",");
    }


    public static String toString(Object[] objects,String joinSymbol){
        if (null == objects || objects.length == 0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i< objects.length; i++) {
            if (stringBuilder.length() > 0){
                stringBuilder.append(joinSymbol);
            }
            stringBuilder.append(String.valueOf(objects[i]));
        }
        return stringBuilder.toString();
    }
}
