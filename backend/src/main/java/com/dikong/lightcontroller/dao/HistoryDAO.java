package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.History;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午4:48
 * @see
 *      </P>
 */
@Mapper
public interface HistoryDAO {

    @Select({"select * from history where var_id=#{varId} AND var_type=#{varType}"})
    List<History> selectAllByVarId(@Param("varId") Long varId, @Param("varType") Integer varType);

    @Select({
            "select * from history where var_id=#{varId} AND var_type=#{varType} order by create_time"})
    History selectLastHistory(@Param("varId") Long varId, @Param("varType") Integer varType);

    @Insert({
            "insert into history (var_id,var_type,var_value,create_by) values (#{history.varId},#{history.varType},#{history.varValue},#{history.createBy})"})
    int insertHistory(@Param("history") History history);
}
