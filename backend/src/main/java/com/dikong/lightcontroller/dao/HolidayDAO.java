package com.dikong.lightcontroller.dao;

import java.util.List;

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

    @Insert({"<script>" + "insert into holiday (holiday_time,proj_id) values "
            + "<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\"> "
            + " (#{item.holidayTime},#{item.projId}) " + "</foreach>" + "</script>"})
    int insertList(@Param("list") List<Holiday> holidays);

    @Select({"<script>" + "select * from holiday where holiday_time in "
            + "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + "#{item}" + "</foreach>" + "</script>"})
    List<Holiday> selectAllHoliday(@Param("list") List<String> weekTimes);
}
