package com.dikong.lightcontroller.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.ChangePwdReq;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.dto.UserListReq;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.entity.UserProject;
import com.dikong.lightcontroller.service.UserService;
import com.github.pagehelper.util.StringUtil;

import io.swagger.annotations.Api;

@Api(value = "UserController", description = "用户管理")
@RestController
@RequestMapping("/light/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ReturnInfo login(@RequestBody LoginReqDto loginReqDto, HttpServletRequest request) {
        if (StringUtil.isEmpty(loginReqDto.getUsername())
                || StringUtil.isEmpty(loginReqDto.getPassword())
                || loginReqDto.getPassword().length() < 6) {
            return ReturnInfo.create(CodeEnum.PWD_FORMAT_ERROR);
        }
        String token = request.getHeader(Constant.LOGIN.TOKEN);
        return userService.login(loginReqDto, token);
    }

    @RequestMapping("/login-out")
    public ReturnInfo loginOut(HttpServletRequest request) {
        String token = request.getHeader(Constant.LOGIN.TOKEN);
        return userService.loginOut(token);
    }

    @PutMapping("/change/info")
    public ReturnInfo changeUserInfo(@RequestBody User user) {
        if (!StringUtil.isEmpty(user.getPassword()) && user.getPassword().length() < 6) {
            return ReturnInfo.create(CodeEnum.PWD_FORMAT_ERROR);
        }
        return userService.changeUserInfo(user);
    }

    @PutMapping("/change/pwd")
    public ReturnInfo changeUserPwd(@RequestBody ChangePwdReq changePwdReq) {
        if (!StringUtil.isEmpty(changePwdReq.getNewPwd())
                && changePwdReq.getNewPwd().length() < 6) {
            return ReturnInfo.create(CodeEnum.PWD_FORMAT_ERROR);
        }
        return userService.changeUserPwd(changePwdReq);
    }

    @RequestMapping("/list")
    public ReturnInfo list(@RequestBody UserListReq userListReq) {
        return userService.userList(userListReq);
    }

    @PostMapping("/add")
    public ReturnInfo add(@RequestBody User user) {
        user.setUserId(null);
        if (!StringUtil.isEmpty(user.getPassword()) && user.getPassword().length() < 6) {
            return ReturnInfo.create(CodeEnum.PWD_FORMAT_ERROR);
        }
        return userService.add(user);
    }

    @RequestMapping("/del/{userId}")
    public ReturnInfo del(@PathVariable("userId") int userId) {
        return userService.del(userId);
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody User user) {
        if (!StringUtil.isEmpty(user.getPassword()) && user.getPassword().length() < 6) {
            return ReturnInfo.create(CodeEnum.PWD_FORMAT_ERROR);
        }
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

    @RequestMapping("/project/enter/{project-id}")
    public ReturnInfo enterProject(@PathVariable("project-id") Integer projectId,
            HttpServletRequest request) {
        if (projectId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        String token = request.getHeader(Constant.LOGIN.TOKEN);
        return userService.enterProject(token, projectId);
    }

    @GetMapping("/project/list/{user-id}")
    public ReturnInfo userProjectList(@PathVariable("user-id") Integer userId) {
        if (userId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return userService.userProjectList(userId);
    }

    /**
     * 主要用户校验图片访问权限 1 ->有权限访问 0->无权限
     * 
     * @param request
     * @return
     */
    @GetMapping("/check/user/auth")
    public int checkUserAuth(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            return Constant.IMG_AUTH.NOT_AUTH;
        }
        String reqUri = request.getParameter("uri");
        if (StringUtils.isEmpty(reqUri)) {
            return Constant.IMG_AUTH.NOT_AUTH;
        }
        int result = Constant.IMG_AUTH.NOT_AUTH;
        try {
            result = userService.checkUserAuth(token, reqUri);
        } catch (Exception e) {
            LOG.error("图片权限校验失败" + e.toString());
            e.printStackTrace();
        }
        LOG.info("图片校验结果:" + result);
        return result;
    }
}
