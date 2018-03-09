package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.RegisterTime;
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

    @Select({"<script>"
            + "select id,device_id,var_name,regis_name,regis_addr,regis_type,regis_value,update_time"
            + " from register " + "<where>" + "<if test = \" register.deviceId != null \">"
            + " device_id = #{register.deviceId}" + "</if>"
            + "<if test = \" register.regisType != null \">"
            + " and regis_type = #{register.regisType} " + "</if>" + "</where>"
            + " order by regis_addr " + "</script>"})
    List<RegisterTime> selectRegisterById(@Param("register") RegisterList registerList);



    @Select({"<script>"
            + "select id,device_id,var_name,regis_name,regis_addr,regis_type,regis_value,update_time"
            + " from register " + " where id in "
            + "<foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + " #{item} " + "</foreach>" + "</script>"})
    List<RegisterTime> selectRegisterInIds(@Param("ids") List<Long> ids);

    @Select({"<script>"
            + "select id,var_name,regis_name,regis_addr,regis_type from register where id in"
            + "<foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + " #{item} " + "</foreach>" + "</script>"})
    List<Register> selectRegisterInId(@Param("ids") List<Long> ids);


    @Select({"select regis_addr from register where id=#{id}"})
    String selectById(@Param("id") Long id);

    @Select({"select regis_addr from register where device_id=#{deviceId}"})
    List<String> selectRegisAddrByDeviceId(@Param("deviceId") Long deviceId);

    @Update({"<script>" + "update register " + "<trim prefix=\"set\" suffixOverrides=\",\">"
            + "var_name=#{register.varName}," + "regis_name=#{register.regisName},"
            + "regis_type=#{register.regisType},"
            + "<if test=\"register.regisValue != null\">regis_value=#{register.regisValue},"
            + "</if>" + "</trim>" + "where regis_addr=#{regisAddr} AND device_id=#{deviceId}"
            + "</script>"})
    int updateByRegisAddrAndDeviceId(@Param("register") Register register,
            @Param("regisAddr") String regisAddr, @Param("deviceId") Long deviceId);


    @Update({"update register set regis_value=#{regisValue} where id=#{id}"})
    int updateRegisValueById(@Param("regisValue") String regisValue, @Param("id") Long id);

    @Select({"select * from register where id=#{id}"})
    Register selectRegisById(@Param("id") Long id);


    @Select({"select * from register where device_id=#{deviceId} order by regis_addr limit 1"})
    Register selectIdAndTypeByDeviceId(@Param("deviceId") Long deviceId);


    @Delete({"delete from register where id=#{id}"})
    int deleteRegister(@Param("id") Long id);
}
