package com.dikong.lightcontroller.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dikong.lightcontroller.entity.Timing;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午4:09
 * @see
 *      </P>
 */
@Repository
public interface TimingDAO extends Mapper<Timing> {

    @Update({"update timing set is_delete=#{isDelete}  where id=#{id}"})
    int updateDeleteById(@Param("id") Long id, @Param("isDelete") Byte isDelete);


    @Select({"<script>"
            + "select * from timing where week_list like CONCAT(CONCAT('%',#{week}),'%') or month_list like CONCAT(CONCAT('%',#{dataNow}),'%') order by node_content_run_time desc limit 1"
            + "</script>"})
    Timing selectLastOne(@Param("week") String week, @Param("dataNow") String dataNow);


    @Select({"select * from timing where id=#{id}"})
    Timing selectById(@Param("id")Long id);


    @Select({"select node_name from timing where proj_id=#{projId} AND is_delete={isDelete} order by node_name desc limit 1"})
    Timing selectByLastNodeName(@Param("projId")Integer projId,@Param("isDelete")Byte isDelete);
}
