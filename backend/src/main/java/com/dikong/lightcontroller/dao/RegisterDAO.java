package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dikong.lightcontroller.entity.Register;

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
            + "insert into register (device_id,var_name,regis_name,regis_addr,regis_type,proj_id)"
            + " values "
            + "<foreach collection=\" registers \" item=\"item\" index=\"index\" separator=\",\">"
            + " #{item.deviceId},#{item.varName},#{item.regisName},#{item.regisAddr},#{item.regisType},#{item.projId} "
            + "</foreach>" + "</script>"})
    int insertMultiItem(@Param("registers") List<Register> registers);
}
