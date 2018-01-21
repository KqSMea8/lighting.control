package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.Device;
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
 * @create 2018年01月20日下午1:50
 * @see </P>
 */
@Mapper
public interface DeviceDAO {

    @Select({"select id,external_id,name,code,model where dtu_id=#{dtuId}"})
    List<Device> selectAllByDtuId(@Param("dtuId")Long dtuId);
}
