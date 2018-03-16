package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.TimingCron;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月30日下午5:11
 * @see </P>
 */
@Repository
public interface TimingCronDAO extends Mapper<TimingCron>{


    @Select({"select * from timing_cron where timing_id=#{timingId}"})
    List<TimingCron> selectAllByTimingId(@Param("timingId")Long timingId);


    @Delete({"delete from timing_cron where id=#{id}"})
    int deleteDelCronById(@Param("id")Long id);
}
