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
    interface USER {
        int NOTDELETE = 1;// 未删除
        int ISDELETE = 2;// 已删除
        int SUPER_MANAGER = 0;
        int PROJECT_MANAGER_CONTROL = 1;
        int PROJECT_MANAGER_CONFIG = 2;
        int PROJECT_SUPER_MANAGER = 3;
        int NOT_AUTH = 4;
        String AUTH_LIST = "need.auth.uri.list";
        String TYPE_AUTH_REALT = "type.aut.uri.relationship";
    }

    interface CMD {
        int LOCK_TIME_OUT = 5;
    }
}
