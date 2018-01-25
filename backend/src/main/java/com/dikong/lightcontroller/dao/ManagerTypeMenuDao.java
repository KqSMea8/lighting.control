package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.ManagerTypeMenu;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
public interface ManagerTypeMenuDao extends Mapper<ManagerTypeMenu>, MySqlMapper<ManagerTypeMenu> {
    @Select("<script>" + " select menu_id from manager_type_menu where type_id in "
            + " <foreach collection=\"manageTypeIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Integer> menuIds(@Param("manageTypeIds") List<Integer> manageTypeIds);
}
