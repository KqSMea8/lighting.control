package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Resource;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
public interface ResourceDao extends Mapper<Resource> {
    @Select("<script>" + " select * from resource where " + " is_delete=1 " + " AND resource_id in "
            + " <foreach collection=\"menuIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Resource> resourceList(@Param("menuIds") List<Integer> menuIds);
}
