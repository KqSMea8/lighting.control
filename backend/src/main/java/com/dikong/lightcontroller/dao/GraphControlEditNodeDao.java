package com.dikong.lightcontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.dikong.lightcontroller.dto.GraphControlEditNodeDto;
import com.dikong.lightcontroller.entity.GraphControlEditNode;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangwenjun
 * @version 2018年8月25日 下午3:04:57
 */
public interface GraphControlEditNodeDao extends Mapper<GraphControlEditNode> {

    @Select("SELECT gcen.*,sv.var_type,sv.var_value FROM graph_control_edit_node gcen "
            + "LEFT JOIN `sys_var` sv ON gcen.target_id = sv.id where tree_node_id =#{treeNodeId}")
    public List<GraphControlEditNodeDto> listAllEditNodeByTreeId(Integer treeNodeId);
}
