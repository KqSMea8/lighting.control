package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Project;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午9:35:23
 */
public interface ProjectDao extends Mapper<Project> {
    @Select("<script>" + " select * from project where " + " is_delete=1 " + " AND project_id in "
            + " <foreach collection=\"projectIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Project> projectList(@Param("projectIds") List<Integer> projectIds);
}
