package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午4:48
 * @see
 *      </P>
 */
@Mapper
public interface HistoryDAO {

    @Select({"select * from history where var_id=#{varId} AND var_type=#{varType}"})
    List<History> selectAllByVarId(@Param("varId") Long varId, @Param("varType") Integer varType);

    @Select({
            "select * from history where var_id=#{varId} AND var_type=#{varType} order by create_time desc limit 1"})
    History selectLastHistory(@Param("varId") Long varId, @Param("varType") Integer varType);

    @Insert({
            "insert into history (var_id,var_type,var_value,create_by) values (#{history.varId},#{history.varType},#{history.varValue},#{history.createBy})"})
    int insertHistory(@Param("history") History history);

    @Select({"<script>"
            + "SELECT dt.device_name as dtu_name,d.name as device_name,r.regis_name as regis_name,h.var_value,h.create_time,u.user_name as create_by "
            + "from history h LEFT JOIN user u on h.create_by=u.user_id LEFT JOIN register r on h.var_id=r.id LEFT JOIN device d on r.device_id=d.id LEFT JOIN dtu dt on d.dtu_id=dt.id "
            + "WHERE h.var_type=#{search.varType} " + "and h.var_id=#{search.varId} "
            + "and h.create_time &gt;=#{search.startTime} "
            + "and h.create_time &lt;=#{search.endTime}" + " order by create_time desc "
            + "</script>"})
    List<HistoryList> selectAll(@Param("search") HistorySearch historySearch);

    @Select({"<script>"
            + "SELECT sv.var_name as regis_name,h.var_value,h.create_time,u.user_name as create_by "
            + "from history h LEFT JOIN user u on h.create_by=u.user_id LEFT JOIN sys_var sv on h.var_id=sv.id "
            + "where h.var_type=#{search.varType} " + "and h.var_id=#{search.varId} "
            + "and h.create_time &gt;=#{search.startTime} "
            + "and h.create_time &lt;=#{search.endTime}" + " order by create_time desc "
            + "</script>"})
    List<HistoryList> selectSysVar(@Param("search") HistorySearch historySearch);


    @Select("<script>"
            + "SELECT dt.device_name as dtu_name,d.name as device_name,r.regis_name as regis_name,h.var_value,h.create_time,u.user_name as create_by "
            + "from history h LEFT JOIN user u on h.create_by=u.user_id LEFT JOIN register r on h.var_id=r.id LEFT JOIN device d on r.device_id=d.id LEFT JOIN dtu dt on d.dtu_id=dt.id "
            + "WHERE h.var_type=#{varType} "
            + " and h.var_id in"
            + "<foreach collection=\"varIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + "#{item}"
            + "</foreach>"
            + "and h.create_time &gt;=#{search.startTime} "
            + "and h.create_time &lt;=#{search.endTime}" + " order by create_time desc "
            + "</script>")
    List<HistoryList> selectAllIn(@Param("varType")Integer varType,@Param("varIds")List<Long> varIds);


    @Select({"<script>"
            + "SELECT sv.var_name as regis_name,h.var_value,h.create_time,u.user_name as create_by "
            + "from history h LEFT JOIN user u on h.create_by=u.user_id LEFT JOIN sys_var sv on h.var_id=sv.id "
            + "where h.var_type=#{varType} "
            + "and h.var_id in"
            + "<foreach collection=\"varIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + "#{item}"
            + "</foreach>"
            + "and h.create_time &gt;=#{search.startTime} "
            + "and h.create_time &lt;=#{search.endTime}" + " order by create_time desc "
            + "</script>"})
    List<HistoryList> selectSysVarIn(@Param("varType")Integer varType,@Param("varIds")List<Long> varIds);
}
