package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.UserService;

@RestController
@RequestMapping("/light/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ReturnInfo list() {
        return userService.userList();
    }
}
