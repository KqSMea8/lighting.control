package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.vo.GroupList;

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
@Mapper
public interface GroupDAO {

    @Select({"<script>"
            + "select id,group_name from `group` where proj_id=#{groupList.projId} AND is_delete=#{groupList.isDelete}"
            + "<if test=\" groupList.groupName != null \">"
            + " AND group_name like concat(concat('%',#{groupList.groupName}),'%')" + "</if>"
            + "</script>"})
    List<Group> selectAll(@Param("groupList") GroupList groupList);

    @Insert({"insert into `group` (group_name,group_code,proj_id) "
            + "values (#{group.groupName},#{group.groupCode},#{group.projId})"})
    int addGroup(@Param("group") Group group);


    @Update({"update `group` set is_delete=#{isDelete} where id=#{id}"})
    int updateIsDelete(@Param("id") Long id, @Param("isDelete") Byte isDelete);

    @Select({
            "select group_code from `group` where is_delete = #{isDelete} AND proj_id = #{projId} order by group_code desc "})
    Integer selectLastCode(@Param("isDelete") Byte isDelete,@Param("projId")Integer projId);
}
