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
        int CLEAN_DAYS = 7;
        int RETRY_TIME = 2;
    }

    interface RESERT_CMD {
        String KEY_PROFILE = "LIGHT_";
    }
    interface TREE_NOD {// 图控-树节点删除标志 1->未删除 2->删除
        int NOT_DELETE = 1;
        int DELETED = 2;
    }

    interface ALARM{
        String REDIS_ALARM_KEY = "alarm.timing.key";
        //1->等于，2->大于,3->小于,4->区间,5->不等于,6->大于等于,7->小于等于
        Integer eq = 1;
        Integer gt = 2;
        Integer lt = 3;
        Integer between = 4;
        Integer neq = 5;
        String ALARM_KEY = "Alarm_";
        String ALARM_CLEAN_KEY = "Alarm_CLEAN_";
        //告警状态， 1->正常状态,2->告警状态
        Integer CLEAR = 1;
        Integer ALARM = 2;
        //触发状态,1->未触发,2->已触发
        Integer NO_TRIGGER = 1;
        Integer TRIGGER = 2;
        //启用和禁用,1->启用,2->禁用
        Integer Enable = 1;
        Integer Disable= 2 ;
        //告警触发类型,1->发生告警,2->消除告警
        Integer ALARM_TYPE = 1;
        Integer CLEAR_TYPE = 2;
    }

    interface PROJECT {
        String PROJECT_CRONTAB = "project.crontab";
    }
}
