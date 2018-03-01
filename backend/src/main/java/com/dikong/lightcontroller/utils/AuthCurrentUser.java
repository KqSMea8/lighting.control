package com.dikong.lightcontroller.utils;

import java.util.Objects;

import com.dikong.lightcontroller.dto.LoginRes;

public class AuthCurrentUser {
    private static ThreadLocal<LoginRes> currentUser = new ThreadLocal<LoginRes>();

    public static void set(LoginRes user) {
        currentUser.set(user);
    }

    public static LoginRes get() {
        return currentUser.get();
    }

    public static void remove() {
        currentUser.remove();
    }

    public static int getCurrentProjectId() {
        return get().getCurrentProjectId();
    }

    public static int getUserId() {
        LoginRes userInfo = get();
        if (userInfo == null) {
            return 0;
        }
        if (Objects.isNull(userInfo)) {
            throw new NullPointerException();
        }
        return userInfo.getUserInfo().getUserId();
    }

    public static boolean isManager() {
        return get().isManager();
    }
}
