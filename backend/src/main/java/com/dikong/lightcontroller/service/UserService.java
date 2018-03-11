package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.ChangePwdReq;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.dto.UserListReq;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.entity.UserProject;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午7:48:48
 */
public interface UserService {

    public ReturnInfo login(LoginReqDto loginReqDto);

    public ReturnInfo loginOut(String token);

    public ReturnInfo userList(UserListReq userListReq);

    public ReturnInfo add(User user);

    public ReturnInfo del(int userId);

    public ReturnInfo update(User user);

    public ReturnInfo onlineUserList();

    public ReturnInfo userAddProject(UserProject userProjectReq);

    public ReturnInfo userDelProject(UserProject userProjectReq);

    public ReturnInfo userProjectList(Integer userId);

    public ReturnInfo enterProject(String token, Integer projectId);

    public ReturnInfo changeUserInfo(User user);

    public ReturnInfo changeUserPwd(ChangePwdReq changePwdReq);
}
