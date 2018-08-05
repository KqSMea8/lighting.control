package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.GraphControlTreeNode;
import com.dikong.lightcontroller.utils.TreeNodebuild;

/**
 * @author huangwenjun
 * @version 2018年8月5日 下午1:49:09
 */
public interface GraphService {
    public ReturnInfo<GraphControlTreeNode> addNewNode(TreeNodeDto treeNode);

    public ReturnInfo delNodeInfo(Integer nodeId);

    public ReturnInfo<GraphControlTreeNode> updateNode(TreeNodeDto treeNode);

    public ReturnInfo<List<TreeNodebuild>> listTree(Integer parentId);
}
