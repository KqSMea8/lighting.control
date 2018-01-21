package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Role;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午10:44:05
 */
public interface RoleDao extends Mapper<Role> {
    @Select("<script>" + " select * from role where " + " is_delete=1 " + " AND role_id in "
            + " <foreach collection=\"roleIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Role> roleList(@Param("roleIds") List<Integer> roleIds);
}
