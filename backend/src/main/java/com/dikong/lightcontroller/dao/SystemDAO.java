package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.SystemConfig;
import com.dikong.lightcontroller.vo.SystemSearch;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月24日上午11:07
 * @see
 *      </P>
 */
@Mapper
public interface SystemDAO {

    @Select({"select type_id from system order by type_id desc limit 1"})
    Integer selectLastTypeId();

    @Select({"select count(0) from system where type_value=#{typeValue}"})
    int selectTypeIsExist(@Param("typeValue") String typeValue);

    @Insert({
            "insert into system (value,value_type,type_id,type_value) values(#{config.value},#{config.valueType},#{config.typeId},#{config.typeValue})"})
    int insertSystem(@Param("config") SystemConfig systemConfig);

    @Update({"update system set value=#{config.value},value_type=#{config.valueType} where type_id=#{config.typeId}"})
    int updateSystemByTypeId(@Param("config") SystemConfig systemConfig);

    @Select({"select * from system where type_id=#{typeId}"})
    SystemConfig selectByTypeId(@Param("typeId") Integer typeId);

    @Delete({"delete from system where id=#{id}"})
    int deleteById(@Param("id") Long id);

    @Select({"select * from system where id=#{id}"})
    SystemConfig selectById(@Param("id")Long id);

    @Select({"<script>" + "select * from system " + "<where>"
            + "<if test=\"search.value!=null\">"
            + " value like concat(concat('%',#{search.value}),'%')" + "</if>" + "</where>"
            + "</script>"})
    List<SystemConfig> selectAll(@Param("search") SystemSearch systemSearch);
}
