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
    // 文件类型 1->图片 2->音频 3->点位文件
    interface FILE_TYPE {
        int ALL = 0;
        int IMG = 1;
        int AUDIO = 2;
        int POINT_INFO = 3;
    }

    interface IMG_AUTH {
        int NOT_AUTH = 0;
        int HAVE_AUTH = 1;
    }
}
