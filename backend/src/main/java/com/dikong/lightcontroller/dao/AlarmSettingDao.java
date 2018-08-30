package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.AlarmSettingEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 冷荣富
 */
@Repository
public interface AlarmSettingDao extends Mapper<AlarmSettingEntity> {

	@Select("select * from alarm_setting where project_id=#{projectId}")
	List<AlarmSettingEntity> selectByProjId(@Param("projectId")Integer projectId);
}
