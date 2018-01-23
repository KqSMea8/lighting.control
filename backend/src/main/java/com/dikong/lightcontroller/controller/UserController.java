package com.dikong.lightcontroller.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.entity.UserProject;
import com.dikong.lightcontroller.service.UserService;
import com.github.pagehelper.util.StringUtil;

@RestController
@RequestMapping("/light/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
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

    @RequestMapping("/login-out/{user-id}")
    public ReturnInfo loginOut(HttpServletRequest request, @PathVariable("user-id") String userId) {
        String token = request.getHeader(Constant.LOGIN.TOKEN);
        return userService.loginOut(userId, token);
    }

    @RequestMapping("/list")
    public ReturnInfo list() {
        return userService.userList();
    }

    @RequestMapping("/add")
    public ReturnInfo add(@RequestBody User user) {
        return userService.add(user);
    }

    @RequestMapping("/del/{userId}")
    public ReturnInfo del(@PathVariable("userId") int userId) {
        return userService.del(userId);
    }

    @RequestMapping("/update")
    public ReturnInfo update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping("/online/list")
    public ReturnInfo onlineUsers() {
        return userService.onlineUserList();
    }

    @RequestMapping("/project/add")
    public ReturnInfo userAddProject(@RequestBody UserProject userProjectReq) {
        return userService.userAddProject(userProjectReq);
    }

    @RequestMapping("/project/del")
    public ReturnInfo userDelProject(@RequestBody UserProject userProjectReq) {
        return userService.userDelProject(userProjectReq);
    }

    @RequestMapping("/project/enter")
    public ReturnInfo enterProject() {
        return null;
    }
}
