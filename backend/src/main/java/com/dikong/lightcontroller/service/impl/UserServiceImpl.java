package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.dikong.lightcontroller.dto.ChangePwdReq;
import com.dikong.lightcontroller.dto.EnterProjectRes;
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
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.utils.MD5Util;
import com.github.pagehelper.PageHelper;

import redis.clients.jedis.Jedis;
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
    public ReturnInfo login(LoginReqDto loginReqDto, String token) {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        LoginRes loginRes = null;
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

        // 校验当前mac地址是否可以登陆
        if (!StringUtils.isEmpty(userInfo.getMacAddr())) {
            if (loginReqDto.getMacAddr() == null) {
                return ReturnInfo.create(CodeEnum.MAC_FORBID_LOGIN, token);
            }
            for (String tempMac : loginReqDto.getMacAddr().split(",")) {
                if (userInfo.getMacAddr().indexOf(tempMac) > 0) {
                    break;
                }
            }
            return ReturnInfo.create(CodeEnum.MAC_FORBID_LOGIN, token);
        }
        boolean smsFlag = false;
        if (!StringUtils.isEmpty(token)) {// 校验token合法性
            String smsCode = jedis.get(token);
            if (smsCode != null) {
                if (smsCode.equals(loginReqDto.getSmsCode())) {
                    // 返回信息给前端
                    jedis.del(token);
                    smsFlag = true;
                } else {
                    // 返回信息给前端
                    return ReturnInfo.create(CodeEnum.SMS_CODE_ERROR);
                }
            }
        }
        if (!smsFlag) {
            token = UUID.randomUUID().toString();
        }
        // 校验是否需要短信验证码登陆
        if (!smsFlag && !StringUtils.isEmpty(userInfo.getPhoneNumber())) {// 手机号不为空表示需要短信验证码
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("isSetSms", true);
            jedis.setex(token, 60, "1234");
            // TODO 发送短信
            return ReturnInfo.createReturnSuccessOne(result);
        }
        String oldToken =
                jedis.hget(Constant.LOGIN.ONLINE_USERS_KEY, String.valueOf(userInfo.getUserId()));
        if (!StringUtils.isEmpty(oldToken)) {
            jedis.hdel(Constant.LOGIN.ONLINE_USERS_KEY, String.valueOf(userInfo.getUserId()));
            jedis.del(oldToken);
        }
        // 角色信息
        List<Integer> roleIds = userRoleDao.roleIds(userInfo.getUserId());
        if (CollectionUtils.isEmpty(roleIds)) {
            return ReturnInfo.create(CodeEnum.NOCOMPETENCE);
        }
        List<Role> roles = roleDao.roleList(roleIds);
        int managerType = Constant.USER.PROJECT_MANAGER_CONTROL;
        for (Role role : roles) {
            if (role.getRoleId() == Constant.ROLE.SUPER_MANAGER_ID) {
                managerType = Constant.USER.SUPER_MANAGER;
            }
        }
        List<Integer> menuIds = null;
        if (!CollectionUtils.isEmpty(roleIds)) {
            // 菜单信息
            menuIds = roleMenuDao.menuIds(roleIds);
        }
        List<Menu> menus = null;
        if (!CollectionUtils.isEmpty(menuIds)) {
            menus = menuDao.menuList(menuIds);
        } else {
            menus = new ArrayList<Menu>();
        }
        List<Resource> resources = new ArrayList<Resource>();
        if (!CollectionUtils.isEmpty(menuIds)) {
            resources = resourceDao.resourceList(menuIds);
        }
        userInfo.setPassword("");
        loginRes = new LoginRes(token, userInfo, menus, resources);
        loginRes.setManagerType(managerType);
        LoginRes loginUserInfo = new LoginRes(token, userInfo, menus, resources);
        loginUserInfo.setRoles(roles);
        loginUserInfo.setCurrentProjectId(0);
        loginUserInfo.setManagerType(managerType);
        jedis.setex(token, Constant.TIME.HALF_HOUR, JSON.toJSONString(loginUserInfo));
        jedis.setex(String.valueOf(userInfo.getUserId()), Constant.TIME.HALF_HOUR,
                String.valueOf(userInfo.getUserId()));
        jedis.hset(Constant.LOGIN.ONLINE_USERS_KEY, String.valueOf(userInfo.getUserId()), token);
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
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        String userId = String.valueOf(AuthCurrentUser.getUserId());
        String oldToken = jedis.hget(Constant.LOGIN.ONLINE_USERS_KEY, userId);
        if (StringUtils.isEmpty(oldToken) || !oldToken.equals(token)) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        jedis.hdel(Constant.LOGIN.ONLINE_USERS_KEY, userId);
        jedis.setex(userId, 1, userId);
        jedis.del(token);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo userList(UserListReq userListReq) {
        PageHelper.startPage(userListReq.getPageNo(), userListReq.getPageSize());
        Example example = new Example(User.class);
        example.selectProperties("userId", "userName", "userStatus", "isDelete");
        example.createCriteria().andEqualTo("isDelete", "1");
        List<User> users = userDao.selectByExample(example);
        for (User user : users) {
            if (user.getUserId() == 1) {
                users.remove(user);
                break;
            }
        }
        if (users == null) {
            users = new ArrayList<>();
        }
        return ReturnInfo.createReturnSucces(users);
    }

    @Override
    public ReturnInfo add(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", user.getUserName());
        List<User> users = userDao.selectByExample(example);
        if (users.size() > 0) {
            User oldUser = users.get(0);
            if (oldUser.getIsDelete() == Constant.USER.NOTDELETE) {
                return ReturnInfo.create(CodeEnum.USER_EXIST);
            }
            user.setUserId(oldUser.getUserId());
            user.setIsDelete(Constant.USER.NOTDELETE);
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
            userDao.updateByPrimaryKeySelective(user);
            return ReturnInfo.create(CodeEnum.SUCCESS);
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
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", user.getUserName());
        List<User> users = userDao.selectByExample(example);
        if (users.size() > 0) {
            User oldUser = users.get(0);
            if (!oldUser.getUserId().equals(user.getUserId())) {
                return ReturnInfo.create(CodeEnum.USER_EXIST);
            }
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        } else {
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
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
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        Map<String, String> onlineUsers = jedis.hgetAll(Constant.LOGIN.ONLINE_USERS_KEY);
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
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        String userInfo = jedis.get(token);
        if (StringUtils.isEmpty(userInfo)) {
            return ReturnInfo.create(CodeEnum.NO_LOGIN);
        }
        LoginRes currentUserInfo = JSON.parseObject(userInfo, LoginRes.class);
        currentUserInfo.setCurrentProjectId(projectId);
        EnterProjectRes projectRes = new EnterProjectRes();
        if (AuthCurrentUser.isManager()) {
            List<Menu> menus = menuDao.selectAll();
            projectRes.setManagerType(Constant.USER.PROJECT_SUPER_MANAGER);
            projectRes.setMenus(menus);
            jedis.set(token, JSON.toJSONString(currentUserInfo));// 更新用户信息
            return ReturnInfo.createReturnSuccessOne(projectRes);
        }
        List<Integer> manageTypeIds =
                userProjectDao.manageTypeIds(AuthCurrentUser.getUserId(), projectId);
        if (manageTypeIds.size() >= 2) {
            currentUserInfo.setManagerType(Constant.USER.PROJECT_SUPER_MANAGER);
            projectRes.setManagerType(Constant.USER.PROJECT_SUPER_MANAGER);
        }
        if (manageTypeIds.size() == 1) {
            currentUserInfo.setManagerType(manageTypeIds.get(0));
            projectRes.setManagerType(manageTypeIds.get(0));
        }
        if (manageTypeIds.size() == 0) {
            currentUserInfo.setManagerType(Constant.USER.NOT_AUTH);
            projectRes.setManagerType(Constant.USER.NOT_AUTH);
        }
        jedis.set(token, JSON.toJSONString(currentUserInfo));// 更新用户信息
        List<Menu> menus = new ArrayList<Menu>();
        if (manageTypeIds.size() == 0) {
            projectRes.setMenus(menus);
            return ReturnInfo.createReturnSuccessOne(projectRes);
        }

        List<Integer> menuIds = manageTypeMenuDao.menuIds(manageTypeIds);
        if (!CollectionUtils.isEmpty(menuIds)) {
            menus = menuDao.menuList(menuIds);
        }
        projectRes.setMenus(menus);
        return ReturnInfo.createReturnSuccessOne(projectRes);

    }

    @Override
    public ReturnInfo userProjectList(Integer userId) {
        Example example = new Example(UserProject.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<UserProject> userProjects = userProjectDao.selectByExample(example);
        return ReturnInfo.createReturnSuccessOne(userProjects);
    }

    @Override
    public ReturnInfo changeUserInfo(User user) {
        user.setIsDelete(null);
        user.setUserStatus(null);
        user.setUserId(AuthCurrentUser.getUserId());
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        user.setUpdateBy(AuthCurrentUser.getUserId());
        userDao.updateByPrimaryKeySelective(user);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo changeUserPwd(ChangePwdReq changePwdReq) {
        User user = userDao.selectByPrimaryKey(AuthCurrentUser.getUserId());
        if (!user.getPassword().equals(MD5Util.getMD5Str(changePwdReq.getOldPwd()))) {
            return ReturnInfo.create(CodeEnum.OLD_PWD_ERROR);
        }
        user.setPassword(MD5Util.getMD5Str(changePwdReq.getNewPwd()));
        user.setUpdateBy(AuthCurrentUser.getUserId());
        userDao.updateByPrimaryKeySelective(user);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public int checkUserAuth(String token, String reqUri) {
        LOG.info("token=" + token + " reqUri=" + reqUri);
        Jedis jedis = jedisPool.getResource();
        String userInfo = jedis.get(token);
        if (StringUtils.isEmpty(userInfo)) {
            return Constant.IMG_AUTH.NOT_AUTH;
        }
        LoginRes currentUserInfo = JSON.parseObject(userInfo, LoginRes.class);
        if (currentUserInfo.getManagerType() == Constant.USER.SUPER_MANAGER) {
            return Constant.IMG_AUTH.HAVE_AUTH;
        }
        String[] uris = reqUri.split("/");
        if (currentUserInfo.getCurrentProjectId().equals(Integer.valueOf(uris[0]))) {
            return Constant.IMG_AUTH.HAVE_AUTH;
        }
        return Constant.IMG_AUTH.NOT_AUTH;
    }
}
