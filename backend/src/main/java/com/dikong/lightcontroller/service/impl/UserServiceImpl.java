package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.controller.UserController;
import com.dikong.lightcontroller.dao.ManagerTypeMenuDao;
import com.dikong.lightcontroller.dao.MenuDao;
import com.dikong.lightcontroller.dao.ResourceDao;
import com.dikong.lightcontroller.dao.RoleDao;
import com.dikong.lightcontroller.dao.RoleMenuDao;
import com.dikong.lightcontroller.dao.UserDao;
import com.dikong.lightcontroller.dao.UserProjectDao;
import com.dikong.lightcontroller.dao.UserRoleDao;
import com.dikong.lightcontroller.dto.LoginReqDto;
import com.dikong.lightcontroller.dto.LoginRes;
import com.dikong.lightcontroller.dto.UserListReq;
import com.dikong.lightcontroller.entity.Menu;
import com.dikong.lightcontroller.entity.Resource;
import com.dikong.lightcontroller.entity.Role;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.entity.UserProject;
import com.dikong.lightcontroller.entity.UserRole;
import com.dikong.lightcontroller.service.UserService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.MD5Util;
import com.github.pagehelper.PageHelper;

import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午7:49:22
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserProjectDao userProjectDao;

    @Autowired
    private ManagerTypeMenuDao manageTypeMenuDao;

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public ReturnInfo login(LoginReqDto loginReqDto) {
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("userName", loginReqDto.getUsername());
        List<User> userEntities = userDao.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userEntities)) {
            return ReturnInfo.create(CodeEnum.LOGIN_FAIL);
        }
        User userInfo = userEntities.get(0);
        String password = MD5Util.getMD5Str(loginReqDto.getPassword());
        if (!userInfo.getPassword().equals(password)) {
            return ReturnInfo.create(CodeEnum.LOGIN_FAIL);
        }
        String oldToken = jedisPool.getResource().hget(Constant.LOGIN.ONLINE_USERS_KEY,
                String.valueOf(userInfo.getUserId()));
        if (!StringUtils.isEmpty(oldToken)) {
            jedisPool.getResource().hdel(Constant.LOGIN.ONLINE_USERS_KEY,
                    String.valueOf(userInfo.getUserId()));
            jedisPool.getResource().del(oldToken);
        }
        String token = UUID.randomUUID().toString();
        // 角色信息
        List<Integer> roleIds = userRoleDao.roleIds(userInfo.getUserId());
        if (CollectionUtils.isEmpty(roleIds)) {
            return ReturnInfo.create(CodeEnum.NOCOMPETENCE);
        }
        List<Role> roles = roleDao.roleList(roleIds);
        // 菜单信息
        List<Integer> menuIds = roleMenuDao.menuIds(roleIds);
        List<Menu> menus = null;
        if (!CollectionUtils.isEmpty(menuIds)) {
            menus = menuDao.menuList(menuIds);
        } else {
            menus = new ArrayList<Menu>();
        }
        List<Resource> resources = resourceDao.resourceList(menuIds);
        userInfo.setPassword("");
        LoginRes loginRes = new LoginRes(token, userInfo, menus, resources);
        LoginRes loginUserInfo = new LoginRes(token, userInfo, menus, resources);
        loginUserInfo.setRoles(roles);
        loginUserInfo.setCurrentProjectId(0);
        jedisPool.getResource().setex(token, Constant.TIME.HALF_HOUR,
                JSON.toJSONString(loginUserInfo));
        jedisPool.getResource().hset(Constant.LOGIN.ONLINE_USERS_KEY,
                String.valueOf(userInfo.getUserId()), token);
        LOG.info("用户登陆成功：" + JSON.toJSONString(userInfo));
        return ReturnInfo.createReturnSuccessOne(loginRes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dikong.lightcontroller.service.UserService#loginOut(java.lang.String)
     */
    @Override
    public ReturnInfo loginOut(String token) {
        String userId = String.valueOf(AuthCurrentUser.getUserId());
        String oldToken = jedisPool.getResource().hget(Constant.LOGIN.ONLINE_USERS_KEY, userId);
        if (StringUtils.isEmpty(oldToken) || !oldToken.equals(token)) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        jedisPool.getResource().hdel(Constant.LOGIN.ONLINE_USERS_KEY, userId);
        jedisPool.getResource().del(token);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo userList(UserListReq userListReq) {
        PageHelper.startPage(userListReq.getPageNo(), userListReq.getPageSize());
        Example example = new Example(User.class);
        example.selectProperties("userId", "userName", "userStatus", "isDelete");
        example.createCriteria().andEqualTo("isDelete", "1");
        List<User> users = userDao.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        return ReturnInfo.createReturnSucces(users);
    }

    @Override
    public ReturnInfo add(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", user.getUserName());
        List<User> users = userDao.selectByExample(example);
        if (users.size() > 0) {
            return ReturnInfo.create(CodeEnum.USER_EXIST);
        }
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        userDao.insertSelective(user);
        user.setCreateBy(AuthCurrentUser.getUserId());
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(2);
        userRoleDao.insertSelective(userRole);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo del(int userId) {
        User user = new User();
        user.setUserId(userId);
        user.setIsDelete(2);
        user.setUpdateBy(AuthCurrentUser.getUserId());
        userDao.updateByPrimaryKeySelective(user);

        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("userId", userId);
        userRoleDao.deleteByExample(example);

        example = new Example(UserProject.class);
        example.createCriteria().andEqualTo("userId", userId);
        userProjectDao.deleteByExample(example);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo update(User user) {
        user.setUpdateBy(AuthCurrentUser.getUserId());
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userDao.updateByPrimaryKeySelective(user);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dikong.lightcontroller.service.UserService#onlineUserList()
     */
    @Override
    public ReturnInfo onlineUserList() {
        Map<String, String> onlineUsers =
                jedisPool.getResource().hgetAll(Constant.LOGIN.ONLINE_USERS_KEY);
        if (onlineUsers == null || onlineUsers.size() == 0) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        Set<String> userInfos = onlineUsers.keySet();
        List<Integer> userIds = new ArrayList<Integer>();
        for (String userId : userInfos) {
            userIds.add(Integer.valueOf(userId));
        }
        List<User> users = userDao.userListByIds(userIds);
        return ReturnInfo.createReturnSuccessOne(users);
    }

    @Override
    public ReturnInfo userAddProject(UserProject userProjectReq) {
        userProjectReq.setCreateBy(AuthCurrentUser.getUserId());
        userProjectDao.insertSelective(userProjectReq);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo userDelProject(UserProject userProjectReq) {
        Example example = new Example(UserProject.class);
        example.createCriteria().andEqualTo("userId", userProjectReq.getUserId())
                .andEqualTo("projectId", userProjectReq.getProjectId())
                .andEqualTo("managerTypeId", userProjectReq.getManagerTypeId());
        userProjectDao.deleteByExample(example);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo enterProject(String token, Integer projectId) {
        String userInfo = jedisPool.getResource().get(token);
        if (StringUtils.isEmpty(userInfo)) {
            return ReturnInfo.create(CodeEnum.NO_LOGIN);
        }
        LoginRes currentUserInfo = JSON.parseObject(userInfo, LoginRes.class);
        if (AuthCurrentUser.isManager()) {
            List<Menu> menus = menuDao.selectAll();
            return ReturnInfo.createReturnSuccessOne(menus);
        }
        currentUserInfo.setCurrentProjectId(projectId);
        jedisPool.getResource().set(token, JSON.toJSONString(currentUserInfo));// 更新用户信息
        List<Integer> manageTypeIds =
                userProjectDao.manageTypeIds(AuthCurrentUser.getUserId(), projectId);
        List<Integer> menuIds = manageTypeMenuDao.menuIds(manageTypeIds);
        List<Menu> menus = null;
        if (!CollectionUtils.isEmpty(menuIds)) {
            menus = menuDao.menuList(menuIds);
        } else {
            menus = new ArrayList<Menu>();
        }
        return ReturnInfo.createReturnSuccessOne(menus);
    }
}
