package com.dikong.lightcontroller.utils;

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
        return 1;
        // LoginRes userInfo = currentUser.get();
        // if (Objects.isNull(userInfo)) {
        // throw new NullPointerException();
        // }
        // return userInfo.getUserInfo().getUserId();
    }
}

