package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.GroupDeviceMiddle;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午5:30
 * @see
 *      </P>
 */
@Mapper
public interface GroupDeviceMiddleDAO {

    @Select({
            "select id,device_id,group_id,regis_id from group_device_middle where group_id=#{groupId}"})
    List<GroupDeviceMiddle> selectByGroupId(@Param("groupId") Long groupId);

    @Delete({"delete from group_device_middle where id=#{id}"})
    int deleteDeviceById(@Param("id") Long id);

    @Delete({"delete from group_device_middle where group_id=#{groupId}"})
    int deleteByGroupId(@Param("groupId") Long groupId);


    @Insert({"insert into group_device_middle (device_id,group_id,regis_id) "
            + "values (#{middle.deviceId},#{middle.groupId},#{middle.regisId})"})
    int insert(@Param("middle") GroupDeviceMiddle groupDeviceMiddle);

    @Select({"<script>" + "select distinct(device_id) from group_device_middle where group_id in "
            + "<foreach collection=\"groupIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + " #{item} " + "</foreach>" + "</script>"})
    List<Long> selectDeviceId(@Param("groupIds") List<Long> groupIds);

    @Select({"select regis_id from group_device_middle where group_id=#{groupId}"})
    List<Long> selectAllRegisId(@Param("groupId")Long groupId);
}
