package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.UserProject;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午10:21:11
 */
public interface UserProjectDao extends Mapper<UserProject> {
    @Select("SELECT manager_type_id FROM user_project WHERE user_id=#{userId} AND project_id=#{projectId}")
    public List<Integer> manageTypeIds(@Param("userId") int userId,
            @Param("projectId") int projectId);
}
