package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.vo.RegisterList;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月21日下午11:13
 * @see
 *      </P>
 */
@Mapper
public interface RegisterDAO {

    @Insert({"<script>"
            + "insert IGNORE into register (device_id,var_name,regis_name,regis_addr,regis_type,proj_id)"
            + " values "
            + "<foreach collection=\" registers \" item=\"item\" index=\"index\" separator=\",\">"
            + " (#{item.deviceId},#{item.varName},#{item.regisName},#{item.regisAddr},#{item.regisType},#{item.projId})"
            + "</foreach>" + "</script>"})
    int insertMultiItem(@Param("registers") List<Register> registers);

    @Select({"<script>" + "select id,device_id,var_name,regis_name,regis_addr,regis_type"
            + " from register " + "<where>" + "<if test = \" register.deviceId != null \">"
            + " device_id = #{register.deviceId}" + "</if>"
            + "<if test = \" register.regisType != null \">"
            + " regis_type = #{register.regisType} " + "</if>" + "</where>" + "</script>"})
    List<Register> selectRegisterById(@Param("register") RegisterList registerList);

    @Select({"<script>"
            + "select id,var_name,regis_name,regis_addr,regis_type from register where id in"
            + "<foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + " #{item} " + "</foreach>" + "</script>"})
    List<Register> selectRegisterInId(@Param("ids") List<Long> ids);
}
