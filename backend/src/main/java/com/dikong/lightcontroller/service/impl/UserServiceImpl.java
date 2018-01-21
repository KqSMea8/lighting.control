package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.UserDao;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.service.UserService;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午7:49:22
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ReturnInfo userList() {
        List<User> users = userDao.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
        return ReturnInfo.createReturnSuccessOne(users);
    }

}
