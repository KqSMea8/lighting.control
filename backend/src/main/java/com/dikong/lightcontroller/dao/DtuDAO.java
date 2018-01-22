package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Dtu;

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
@Mapper
public interface DtuDAO {


    @Select({"select * from dtu where is_delete = 1 AND proj_id=#{projId}"})
    List<Dtu> selectAllByPage(@Param("projId") Integer projId);

    @Delete({"update dtu set is_delete=#{isDelete} where id=#{id}"})
    int updateIsDelete(@Param("id") Long id, @Param("isDelete") Byte isDelete);

    @Insert({"insert into dtu (device,device_name,device_code,beat_content,beat_time) "
            + "values (#{dtu.device},#{dtu.deviceName},#{dtu.deviceCode},#{dtu.beatContent},#{dtu.beatTime})"})
    int insertDtu(@Param("dtu") Dtu dtu);

    @Select({"select device from dtu where id=#{id}"})
    String selectAllNameById(@Param("id") Long id);

    @Select({"select id,device from dtu where is_delete = #{isDelete} AND proj_id=#{projId}"})
    List<Dtu> selectAllDtuId(@Param("projId") Integer projId,@Param("isDelete") Byte isDelete);
}
