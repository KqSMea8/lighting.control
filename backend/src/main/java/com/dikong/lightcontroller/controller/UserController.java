package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.service.UserService;
import com.github.pagehelper.util.StringUtil;

@RestController
@RequestMapping("/light/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ReturnInfo login(@RequestBody LoginReqDto loginReqDto) {
        if (StringUtil.isEmpty(loginReqDto.getUsername())
                || StringUtil.isEmpty(loginReqDto.getPassword())
                || loginReqDto.getPassword().length() < 6) {
            return ReturnInfo.create(CodeEnum.LOGIN_FAIL);
        }
        return userService.login(loginReqDto);
    }

    @RequestMapping("/list")
    public ReturnInfo list() {
        return userService.userList();
    }

    @RequestMapping("/add")
    public ReturnInfo add(User user) {
        return null;
    }

    @RequestMapping("/del")
    public ReturnInfo del(int userId) {
        return null;
    }

    @RequestMapping("/update")
    public ReturnInfo update(User user) {
        return null;
    }
}
