package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.entity.GraphControlCurve;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年9月24日 上午10:41:05
 */
public interface GraphControlCurveDao extends Mapper<GraphControlCurve> {

    @Select("SELECT DISTINCT var_id FROM graph_control_curve  WHERE project_id=#{projectId}")
    public List<Integer> selectVarByPrjId(Integer projectId);
}
