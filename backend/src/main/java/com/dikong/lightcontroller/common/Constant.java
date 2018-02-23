package com.dikong.lightcontroller.common;

/**
 * @author huangwenjun
 * @Datetime 2018年1月23日
 */
public interface Constant {
    interface LOGIN {
        String ONLINE_USERS_KEY = "online.users.key";
        String TOKEN = "token";
    }

    interface TIME {
        int SECOND = 1;
        int MINUTE = SECOND * 60;
        int HALF_HOUR = MINUTE * 30;
        int HOUR = MINUTE * 60;
        int DAY = HOUR * 24;
        int oneDayMillisecond = 1000 * 3600 * 24;// 一天的毫秒数
    }
    interface ROLE {
        int SUPER_MANAGER_ID = 1;
    }
}
