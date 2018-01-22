package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.vo.DeviceAdd;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:50
 * @see
 *      </P>
 */
@Mapper
public interface DeviceDAO {

    @Select({
            "select id,external_id,name,code,model from device where dtu_id=#{dtuId} AND is_delete=#{isDelete}"})
    List<Device> selectAllByDtuId(@Param("dtuId") Long dtuId, @Param("isDelete") Byte isDelete);

    @Update({"update device set is_delete=#{isDelete} where id=#{id}"})
    int updateDeleteById(@Param("id") Long id, @Param("isDelete") Byte isDelete);

    @Select({"select count(0) from device where dtu_id=#{dtuId} AND code=#{code}"})
    int selectByDtuIdAndCode(@Param("dtuId") Long dtuId, @Param("code") String code);

    @Insert({"insert into device (dtu_id,external_id,name,code,model) "
            + "values (#{add.dtuId},#{add.externalId},#{add.name},#{add.code},#{add.model})"})
    int insertDevice(@Param("add") DeviceAdd deviceAdd);

    @Update({"update device set model_file=#{filePath} where id=#{id}"})
    int updateModeFilePathById(@Param("id")Long id,@Param("filePath")String filePath);
}
