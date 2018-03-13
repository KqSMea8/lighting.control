package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import com.dikong.lightcontroller.entity.EquipmentMonitor;
import com.dikong.lightcontroller.entity.Menu;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface EquipmentMonitorDao extends Mapper<EquipmentMonitor>,
        InsertListMapper<EquipmentMonitor> {
    @Select("<script>"
            + " UPDATE equipment_monitor SET is_delete = 2 WHERE "
            + "  source_id in "
            + " <foreach collection=\"varIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\" >"
            + " #{item} </foreach> " + "</script>")
    public List<Menu> delByVarIds(@Param("varIds") List<Long> varIds);
}
