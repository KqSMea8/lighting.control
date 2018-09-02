package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.GraphControlEditNodeDto;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.GraphControlEditNode;
import com.dikong.lightcontroller.entity.GraphControlTreeNode;
import com.dikong.lightcontroller.utils.TreeNodebuild;

/**
 * @author huangwenjun
 * @version 2018年8月5日 下午1:49:09
 */
public interface GraphService {

    /*************** 图控-树节点编辑 ********************/
    public ReturnInfo<GraphControlTreeNode> addNewNode(TreeNodeDto treeNode);

    public ReturnInfo delNodeInfo(Integer nodeId);

    public ReturnInfo<GraphControlTreeNode> updateNode(TreeNodeDto treeNode);

    public ReturnInfo<List<TreeNodebuild>> listTree(Integer parentId);

    /**************** 图控-编辑页面节点编辑 *******************/
    public ReturnInfo<GraphControlEditNode> addGraphEditNode(GraphControlEditNode editNode);

    public ReturnInfo<GraphControlEditNode> updateGraphEditNode(GraphControlEditNode editNode);

    public ReturnInfo<List<GraphControlEditNodeDto>> listGraphEditNodes(Integer treeNodeId);

    public ReturnInfo delGraphEditNode(Integer editNodeId);

    public ReturnInfo changeValue(Integer editNodeId, String value);

    public ReturnInfo refreshValue(Integer editNodeId);

}
