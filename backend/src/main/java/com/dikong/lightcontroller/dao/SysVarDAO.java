package com.dikong.lightcontroller.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.SysVar;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午10:52
 * @see </P>
 */
@Mapper
public interface SysVarDAO {
    @Select({"select count(0) from sys_var where proje_id=#{projId} AND sys_var_type=#{sysVarType}"})
    Integer selectExisSysVar(@Param("projId")Integer projId,@Param("sysVarType")Integer sysVarType);

    @Insert({"insert into sys_var (var_name,var_type,var_id,var_value,proj_id,sys_var_type) values (#{sysVar.varName},#{sysVar.varType},#{sysVar.varAddr},#{sysVar.varValue},#{sysVar.projId},#{sysVar.sysVarType})"})
    int insertSysVar(@Param("sysVar") SysVar sysVar);

    @Update({"update sys_var set(var_value=#{varValue}) where id=#{id}"})
    int updateSysVar(@Param("varValue")String String,@Param("id")Long id);

    @Select({"select * from sys_var where sys_var_type=#{sysVarType}"})
    List<SysVar> selectAll(@Param("sysVarType") Integer sysVarType);

    @Delete({"delete from sys_var where var_id=#{var_id} AND sys_var_type=#{sysVarType}"})
    int delete(@Param("varAddr") Long varAddr,@Param("sysVarType") Integer sysVarType);
}
