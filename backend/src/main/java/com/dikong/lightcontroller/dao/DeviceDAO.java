package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.dikong.lightcontroller.dto.DeviceDtu;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.vo.DeviceAdd;
import com.dikong.lightcontroller.vo.DeviceBoardList;
import com.dikong.lightcontroller.vo.DeviceOnlineList;

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
@Repository
public interface DeviceDAO extends Mapper<Device> {

    @Select({"select id,external_id,name,code,model from device where dtu_id=#{dtuId} AND is_delete=#{isDelete}"})
    List<Device> selectAllByDtuId(@Param("dtuId") Long dtuId, @Param("isDelete") Byte isDelete);

    @Update({"update device set is_delete=#{isDelete} where id=#{id}"})
    int updateDeleteById(@Param("id") Long id, @Param("isDelete") Byte isDelete);

    @Select({"select count(0) from device where dtu_id=#{dtuId} AND code=#{code} AND is_delete=#{isDelete}"})
    int selectByDtuIdAndCode(@Param("dtuId") Long dtuId, @Param("code") String code,
            @Param("isDelete") Byte isDelete);

    @Insert({"insert into device (dtu_id,external_id,name,code,model) "
            + "values (#{add.dtuId},#{add.externalId},#{add.name},#{add.code},#{add.model})"})
    @Options(useGeneratedKeys = true, keyProperty = "add.id")
    Long insertDevice(@Param("add") DeviceAdd deviceAdd);

    @Update({"update device set model_file=#{filePath},model=#{model} where id=#{id}"})
    int updateModeFilePathById(@Param("id") Long id, @Param("model") String model,
            @Param("filePath") String filePath);


    @Select({"select id,code from device where dtu_id=#{dtuId} AND is_delete=#{isDelete} "})
    List<Device> selectIdList(@Param("dtuId") Long dtuId, @Param("isDelete") Byte isDelete);

    @Select({"select * from device where id=#{devId}"})
    Device selectDeviceById(@Param("devId") long devId);


    @Select({"<script>"
            + "SELECT d.id,dt.device AS `dtu_name`,d.code AS `device_code`,d.external_id from device d LEFT JOIN dtu dt ON d.dtu_id=dt.id where d.id in "
            + "<foreach collection=\" ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + " #{item} " + "" + "</foreach>" + "</script>"})
    List<DeviceDtu> selectByDeviceId(@Param("ids") List<Long> ids);


    @Select({"SELECT d.id,dt.device AS `dtu_name`,d.name AS `device_name`,d.code AS `device_code`,d.external_id from device d LEFT JOIN dtu dt ON d.dtu_id=dt.id where dt.proj_id=#{projId} AND d.is_delete=#{isDelete}"})
    List<DeviceBoardList> selectNotIn(@Param("projId") Integer projId,@Param("isDelete")Byte isDelete);

    @Select({"SELECT d.id,dt.device AS `dtu_name`,d.name AS `device_name`,d.code AS `device_code`,d.external_id from device d LEFT JOIN dtu dt ON d.dtu_id=dt.id where dt.proj_id=#{projId} and d.dtu_id=#{dtuId} AND d.is_delete=#{isDelete}"})
    List<DeviceBoardList> selectByProjIdAndDutId(@Param("projId") Integer projId,
            @Param("dtuId") Integer dtuId,@Param("isDelete")Byte isDelete);

    @Select({"select status from device where id=#{id}"})
    Integer selectConntionStatus(@Param("id") Long id);


    @Select({"SELECT d.code AS `device_code`,dt.device AS `dtu_name` FROM device d LEFT JOIN dtu dt ON d.dtu_id=dt.id WHERE d.id=#{id}"})
    DeviceDtu selectById(@Param("id") Long id);

    @Select({"select model_file from device where id=#{id}"})
    String selectModeFile(@Param("id") Long id);

    @Select({"<script>"
            + "SELECT d.id as `device_id`,d.external_id,dt.device as `dtu_name`,d.name as `device_name`,d.code as `device_code`,d.status as `online_status`,d.last_call,d.use_times,d.connect_count,d.disconnect_count "
            + "from device d LEFT JOIN dtu dt on d.dtu_id=dt.id "
            + "WHERE dt.proj_id=#{projId} and d.is_delete=#{deviceIsDelet} and dt.is_delete=#{dtuIsDelet} ORDER BY d.create_time DESC"
            + "</script>"})
    List<DeviceOnlineList> selectOnlineStatus(@Param("projId") Integer projId,
            @Param("deviceIsDelet") Byte deviceIsDelet, @Param("dtuIsDelet") Byte dtuIsDelet);


    @Delete({"delete from device where dtu_id=${dtuId}"})
    int deleteByDtuId(@Param("dtuId")Long dtuId);

    @Update({"update device set is_delete=#{isDelete} where dtu_id=#{dtuId}"})
    int updateDeleteByDtuId(@Param("dtuId") Long id, @Param("isDelete") Byte isDelete);
}
