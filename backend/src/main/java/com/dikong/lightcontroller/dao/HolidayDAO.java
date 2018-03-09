package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.Holiday;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日下午6:08
 * @see
 *      </P>
 */
@Mapper
public interface HolidayDAO {

    @Insert({"<script>" + "insert into holiday (holiday_time,proj_id,start_task) values "
            + "<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\"> "
            + " (#{item.holidayTime},#{item.projId},#{item.startTask}) " + "</foreach>"
            + "</script>"})
    int insertList(@Param("list") List<Holiday> holidays);

    @Select({"<script>" + "select * from holiday where proj_id=#{projId} AND holiday_time in "
            + "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + "#{item}" + "</foreach>" + "</script>"})
    List<Holiday> selectAllHoliday(@Param("list") List<String> weekTimes,
            @Param("projId") Integer projId);

    @Select({"select count(0) from holiday where holiday_time=#{today} AND proj_id=#{projId}"})
    int selectTodayIsHoliday(@Param("today") String today, @Param("projId") int projId);

    @Select({
            "select * from holiday where proj_id=#{projId} AND holiday_time like concat(#{time},'%')"})
    List<Holiday> selectHoliday(@Param("time") String time, @Param("projId") Integer projId);


    @Delete({" delete from holiday where holiday_time=#{time} and proj_id=#{projId}"})
    int deleteHoliday(@Param("time") String time, @Param("projId") Integer projId);
}
