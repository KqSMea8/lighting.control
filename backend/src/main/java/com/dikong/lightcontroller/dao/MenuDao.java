package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Menu;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午11:15:19
 */
public interface MenuDao extends Mapper<Menu> {

    @Select("<script>" + " select * from menu where " + " is_delete=1 " + " AND menu_id in "
            + " <foreach collection=\"menuIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Menu> menuList(@Param("menuIds") List<Integer> menuIds);
}
