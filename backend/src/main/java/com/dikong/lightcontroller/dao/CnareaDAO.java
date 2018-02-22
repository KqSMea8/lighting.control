package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.Cnarea2016;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月24日下午4:17
 * @see </P>
 */
@Mapper
public interface CnareaDAO {

    @Select({"select id,lng,lat,name from cnarea_2016 where id=#{id}"})
    Cnarea2016 selectCnarea(@Param("id")Long id);


    @Select({"select id,name from cnarea_2016 where parent_id=#{parentId}"})
    List<Cnarea2016> selectAllByParentId(@Param("parentId")Long parentId);
}
