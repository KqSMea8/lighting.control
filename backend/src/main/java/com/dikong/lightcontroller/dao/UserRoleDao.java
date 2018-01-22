package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.UserRole;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午10:44:36
 */
public interface UserRoleDao extends Mapper<UserRole>, InsertListMapper<UserRole> {

    @Select("SELECT role_id FROM user_role WHERE user_id =#{userId}")
    public List<Integer> roleIds(int userId);
}
