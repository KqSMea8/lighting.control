package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.dto.AlarmHistoryList;
import com.dikong.lightcontroller.entity.AlarmHistoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 冷荣富
 */
@Repository
public interface AlarmHistoryDao extends Mapper<AlarmHistoryEntity> {

	@Select("<script>"
			+ "select ah.alarm_id,r.var_name as `regist_name`,ah.alarm_value,ah.create_time as `trigger_time`,ah.alarm_type "
			+ "from alarm_history ah left join register r on ah.alarm_register_id=r.id where ah.alarm_id in"
			+ "<foreach collection=\"alarmIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
			+ " #{item} "
			+ "</foreach>"
			+"</script>")
	List<AlarmHistoryList> selectList(@Param("alarmIds")List<Integer> alarmIds);
}
