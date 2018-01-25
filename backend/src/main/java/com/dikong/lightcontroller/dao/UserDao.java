package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.User;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午7:51:44
 */
public interface UserDao extends Mapper<User> {
    @Select("<script>" + " select user_id,user_name,user_status,is_delete from user where "
            + " is_delete=1 " + " AND user_id in "
            + " <foreach collection=\"userIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<User> userListByIds(@Param("userIds") List<Integer> userIds);
}
