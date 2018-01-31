package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dikong.lightcontroller.entity.Dtu;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:25
 * @see
 *      </P>
 */
@Repository
public interface DtuDAO extends Mapper<Dtu>{


    @Select({"select * from dtu where is_delete = 1 AND proj_id=#{projId}"})
    List<Dtu> selectAllByPage(@Param("isDelete")Byte isDelete,@Param("projId") Integer projId);

    @Delete({"update dtu set is_delete=#{isDelete} where id=#{id}"})
    int updateIsDelete(@Param("id") Long id, @Param("isDelete") Byte isDelete);

    @Insert({"insert into dtu (device,device_name,device_code,beat_content,beat_time) "
            + "values (#{dtu.device},#{dtu.deviceName},#{dtu.deviceCode},#{dtu.beatContent},#{dtu.beatTime})"})
    int insertDtu(@Param("dtu") Dtu dtu);

    @Select({"select device from dtu where id=#{id}"})
    String selectAllNameById(@Param("id") Long id);

    @Select({"select * from dtu where id=#{id}"})
    Dtu selectDtuById(@Param("id") Long id);

    @Select({"select id,device from dtu where is_delete = #{isDelete} AND proj_id=#{projId}"})
    List<Dtu> selectAllDtuId(@Param("projId") Integer projId, @Param("isDelete") Byte isDelete);


    @Update({"update dtu set online_status=#{onlineStatus} where device_code=#{deviceCode}"})
    int updateOnlineStatusByCode(@Param("deviceCode")String deviceCode,@Param("onlineStatus")Byte onlineStatus);
}
