package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.GraphControlTreeNodeDao;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.GraphControlTreeNode;
import com.dikong.lightcontroller.service.GraphService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.TreeNodebuild;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @version 2018年8月5日 下午1:49:16
 */
@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private GraphControlTreeNodeDao treeNodeDao;

    @Override
    public ReturnInfo<GraphControlTreeNode> addNewNode(TreeNodeDto treeNode) {
        GraphControlTreeNode graphControlTreeNode = new GraphControlTreeNode();
        BeanUtils.copyProperties(treeNode, graphControlTreeNode);
        graphControlTreeNode.setId(null);
        graphControlTreeNode.setProjectId(AuthCurrentUser.getCurrentProjectId());
        graphControlTreeNode.setCreateBy(AuthCurrentUser.getUserId());
        treeNodeDao.insertSelective(graphControlTreeNode);
        return ReturnInfo.createReturnSuccessOne(graphControlTreeNode);
    }

    @Override
    public ReturnInfo delNodeInfo(Integer nodeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReturnInfo<GraphControlTreeNode> updateNode(TreeNodeDto treeNode) {
        GraphControlTreeNode graphControlTreeNode = new GraphControlTreeNode();
        BeanUtils.copyProperties(treeNode, graphControlTreeNode);
        treeNodeDao.updateByPrimaryKeySelective(graphControlTreeNode);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo<List<TreeNodebuild>> listTree(Integer parentId) {
        Example example = new Example(GraphControlTreeNode.class);
        example.createCriteria().andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId());
        List<GraphControlTreeNode> treeNodes = treeNodeDao.selectByExample(example);
        List<TreeNodebuild> retsult;
        if (CollectionUtils.isEmpty(treeNodes)) {
            retsult = new ArrayList<TreeNodebuild>();
            return ReturnInfo.createReturnSuccessOne(retsult);
        }
        List<TreeNodebuild> treeNodebuilds = new ArrayList<TreeNodebuild>();
        for (GraphControlTreeNode treeNode : treeNodes) {
            TreeNodebuild tempTreeNodeBuild = (TreeNodebuild) treeNode;
            tempTreeNodeBuild.setChildren(new ArrayList<TreeNodebuild>());
            treeNodebuilds.add(tempTreeNodeBuild);
        }
        retsult = TreeNodebuild.toTreeNodes(treeNodebuilds);
        return ReturnInfo.createReturnSuccessOne(retsult);
    }

}
