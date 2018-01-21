package com.dikong.lightcontroller.utils;

import com.dikong.lightcontroller.dto.AuthCurrentUserDto;

public class AuthCurrentUser {
    private static ThreadLocal<AuthCurrentUserDto> currentUser =
            new ThreadLocal<AuthCurrentUserDto>();

    public static void set(AuthCurrentUserDto user) {
        currentUser.set(user);
    }

    public static AuthCurrentUserDto get() {
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
        // AuthCurrentUserDto userInfo = currentUser.get();
        // if (Objects.isNull(userInfo)) {
        // throw new NullPointerException();
        // }
        // return userInfo.getUserInfo().getId();
    }
}

