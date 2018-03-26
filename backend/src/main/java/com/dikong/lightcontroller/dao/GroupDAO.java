package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.vo.GroupList;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午3:28
 * @see
 *      </P>
 */
@Repository
public interface GroupDAO extends Mapper<Group> {

    @Select({"<script>"
            + "select id,group_name from `group` where proj_id=#{groupList.projId} AND is_delete=#{groupList.isDelete}"
            + "<if test=\" groupList.groupName != null \">"
            + " AND group_name like concat(concat('%',#{groupList.groupName}),'%')" + "</if>"
            + "</script>"})
    List<Group> selectAllGroup(@Param("groupList") GroupList groupList);

    @Insert({"insert into `group` (group_name,group_code,proj_id,create_by) "
            + "values (#{group.groupName},#{group.groupCode},#{group.projId},#{group.createBy})"})
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    int addGroup(@Param("group") Group group);


    @Update({"update `group` set is_delete=#{isDelete},update_by=#{updateBy} where id=#{id}"})
    int updateIsDelete(@Param("id") Long id,@Param("updateBy")Integer updateBy, @Param("isDelete") Byte isDelete);

    @Select({
            "select group_code from `group` where is_delete = #{isDelete} AND proj_id = #{projId} order by group_code desc limit 1"})
    Integer selectLastCode(@Param("isDelete") Byte isDelete, @Param("projId") Integer projId);

    @Select({"select id from `group` where proj_id=#{projId} AND id != #{id}"})
    List<Long> selectByOtherGroup(@Param("projId") Integer projId, @Param("id") Long id);

    @Select({"select group_code from `group` where id=#{id}"})
    String selectCodeById(@Param("id") Long id);

    @Select({
            "select id,group_name,group_code from `group` where proj_id=#{projId} AND is_delete=#{isDelete}"})
    List<Group> selectByProjId(@Param("projId") Integer projId, @Param("isDelete") Byte isDelete);


    @Select({
            "select count(0) from `group` where proj_id=#{projId} and group_name=#{groupName} and is_delete=#{isDelete}"})
    int selectByNameAndProj(@Param("projId") Integer projId, @Param("groupName") String name,
            @Param("isDelete") Byte isDelete);

    @Select({"select * from `group` where id=#{id}"})
    Group selectByGroupId(@Param("id") Long id);
}
