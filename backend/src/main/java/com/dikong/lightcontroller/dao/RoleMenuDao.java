package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.RoleMenu;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午11:03:38
 */
public interface RoleMenuDao extends Mapper<RoleMenu>, InsertListMapper<RoleMenu> {
    @Select("<script>" + " select * from role_menu where role_id in "
            + " <foreach collection=\"roleId\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Integer> menuIds(@Param("roleId") List<Integer> roleId);
}
