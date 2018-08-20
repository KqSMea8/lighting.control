package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
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
        if (treeNode.getParentId().equals(0)) {
            Example example = new Example(GraphControlTreeNode.class);
            example.createCriteria().andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId())
                    .andEqualTo("isDelete", Constant.TREE_NOD.NOT_DELETE).andEqualTo("parentId", 0);
            List<GraphControlTreeNode> treeNodes = treeNodeDao.selectByExample(example);
            if (!CollectionUtils.isEmpty(treeNodes)) {
                return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR.getCode(),
                        CodeEnum.REQUEST_PARAM_ERROR.getMsg() + ":一个项目只能有一个根节点");
            }
        }
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
        Example example = new Example(GraphControlTreeNode.class);
        example.createCriteria().andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId())
                .andEqualTo("isDelete", Constant.TREE_NOD.NOT_DELETE);
        List<GraphControlTreeNode> treeNodes = treeNodeDao.selectByExample(example);
        List<TreeNodebuild> retsult;
        if (CollectionUtils.isEmpty(treeNodes)) {
            retsult = new ArrayList<TreeNodebuild>();
            return ReturnInfo.createReturnSuccessOne(retsult);
        }
        List<TreeNodebuild> treeNodebuilds = new ArrayList<TreeNodebuild>();
        for (GraphControlTreeNode treeNode : treeNodes) {
            TreeNodebuild tempTreeNodeBuild = new TreeNodebuild();
            BeanUtils.copyProperties(treeNode, tempTreeNodeBuild);
            tempTreeNodeBuild.setChildren(new ArrayList<TreeNodebuild>());
            treeNodebuilds.add(tempTreeNodeBuild);
        }
        List<Integer> nodeIds = new ArrayList<Integer>();
        nodeIds.add(nodeId);
        TreeNodebuild.getAllNodeId(treeNodebuilds, nodeIds, nodeId);
        Example delExample = new Example(GraphControlTreeNode.class);
        delExample.createCriteria().andIn("id", nodeIds);
        // TODO 删除图控界面编辑的内容、监控什么的
        GraphControlTreeNode delNode = new GraphControlTreeNode();
        delNode.setIsDelete(Constant.TREE_NOD.DELETED);
        treeNodeDao.updateByExampleSelective(delNode, delExample);
        return ReturnInfo.createReturnSuccessOne(nodeIds);
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
        example.createCriteria().andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId())
                .andEqualTo("isDelete", Constant.TREE_NOD.NOT_DELETE);
        List<GraphControlTreeNode> treeNodes = treeNodeDao.selectByExample(example);
        List<TreeNodebuild> retsult;
        if (CollectionUtils.isEmpty(treeNodes)) {
            retsult = new ArrayList<TreeNodebuild>();
            return ReturnInfo.createReturnSuccessOne(retsult);
        }
        List<TreeNodebuild> treeNodebuilds = new ArrayList<TreeNodebuild>();
        for (GraphControlTreeNode treeNode : treeNodes) {
            TreeNodebuild tempTreeNodeBuild = new TreeNodebuild();
            BeanUtils.copyProperties(treeNode, tempTreeNodeBuild);
            tempTreeNodeBuild.setChildren(new ArrayList<TreeNodebuild>());
            treeNodebuilds.add(tempTreeNodeBuild);
        }
        retsult = TreeNodebuild.toTreeNodes(treeNodebuilds, parentId);
        return ReturnInfo.createReturnSuccessOne(retsult);
    }
}
