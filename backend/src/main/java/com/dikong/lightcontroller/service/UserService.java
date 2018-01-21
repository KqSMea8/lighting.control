package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.entity.User;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午7:48:48
 */
public interface UserService {

    public ReturnInfo login(LoginReqDto loginReqDto);

    public ReturnInfo userList();

    public ReturnInfo add(User user);

    public ReturnInfo del(int userId);

    public ReturnInfo update(User user);
}
