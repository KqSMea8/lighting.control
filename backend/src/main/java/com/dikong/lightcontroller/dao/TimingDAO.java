package com.dikong.lightcontroller.dao;

import java.util.List;

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


    @Select({"<script>" + "select * from timing where is_delete=#{isDelete} and "
            + "<if test=\" projId != null \">" + " proj_id=#{projId} and " + "</if>"
            + "node_content_run_time &gt;='00:00:00' AND node_content_run_time &lt;=DATE_FORMAT(NOW(),\"%T\") "
            + "AND week_list like CONCAT(CONCAT('%',#{week}),'%') "
            + "or month_list like CONCAT(CONCAT('%',#{dataNow}),'%') order by node_content_run_time"
            + "</script>"})
    List<Timing> selectLastOne(@Param("week") String week, @Param("dataNow") String dataNow,
            @Param("isDelete") Byte isDelete, @Param("projId") Integer projId);


    @Select({"select * from timing where id=#{id}"})
    Timing selectById(@Param("id") Long id);


    @Select({"<script>"
            + "select node_name from timing where proj_id=#{projId} AND is_delete=#{isDelete} order by node_name desc limit 1"
            + "</script>"})
    Timing selectByLastNodeName(@Param("projId") Integer projId, @Param("isDelete") Byte isDelete);


    @Update({"<script>" + "update timing set task_name=#{taskName} where id=#{id}" + "</script>"})
    int updateTaskNameByID(@Param("id") Long id, @Param("taskName") String taskName);
}
