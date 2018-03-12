package com.dikong.lightcontroller.utils;

/**
 * @author huangwenjun
 * @version 2018年3月12日 下午11:39:09
 */
public class UriUtil {
    public static boolean uriCheck(String requestUri, String accessUri) {
        if (requestUri.equals(accessUri)) {
            return true;
        }
        int flag = 0;
        String[] accessUris = accessUri.split("\\*");
        if (requestUri.indexOf(accessUris[0]) != -1) {
            return true;
        }
        return false;
    }
}
