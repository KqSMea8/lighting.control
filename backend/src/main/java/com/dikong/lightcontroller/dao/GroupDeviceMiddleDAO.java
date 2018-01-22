package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import org.apache.ibatis.annotations.Delete;
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
 * @create 2018年01月22日下午5:30
 * @see </P>
 */
@Mapper
public interface GroupDeviceMiddleDAO {

    @Select({"select id,device_id,group_id,regis_id from group_device_middle where group_id=#{groupId}"})
    List<GroupDeviceMiddle> selectByGroupId(@Param("groupId")Long groupId);

    @Delete({"delete from group_device_middle where id=#{id}"})
    int deleteDeviceById(@Param("id") Long id);

    @Delete({"delete from group_device_middle where group_id=#{groupId}"})
    int deleteByGroupId(@Param("groupId")Long groupId);
}
