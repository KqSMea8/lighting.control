package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.AlarmMessageEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 冷荣富
 * @Date
 */
@Repository
public interface AlarmMessageDao extends Mapper<AlarmMessageEntity>,InsertListMapper<AlarmMessageEntity> {

	@Select("<script>"
			+ "select count(*) from alarm_message where user_id=#{userId}"
			+ "<if test=\" projectId != null \">"
			+ " and project_id=#{projectId}"
			+ "</if>"
			+ "</script>")
	Integer countByUserId(@Param("userId")Integer userId,@Param("projectId")Integer projectId);


	@Update("<script>"
			+ "update alarm_message set view=2 where user_id=#{userId}"
			+ "<if test=\" projectId != null \">"
			+ " and project_id=#{projectId} "
			+ "</if>"
			+ "</script>")
	void updateViewAll(@Param("userId")Integer userId,@Param("projectId")Integer projectId);

	@Update("update alarm_message set view=2 where id=#{id}")
	void updateViewById(@Param("id")Integer id);
}
