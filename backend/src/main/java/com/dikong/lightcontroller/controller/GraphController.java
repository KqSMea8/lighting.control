package com.dikong.lightcontroller.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.GraphControlTreeNode;
import com.dikong.lightcontroller.service.GraphService;
import com.dikong.lightcontroller.utils.TreeNodebuild;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author huangwenjun
 * @version 2018年8月5日 下午1:14:41
 */
@Api(value = "GraphController")
@RestController
@RequestMapping("/light/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @ApiOperation(value = "新增节点")
    @ApiImplicitParam(required = true, dataType = "TreeNodeDto", name = "treeNode")
    @PostMapping("/node/add")
    public ReturnInfo<GraphControlTreeNode> addNewNode(TreeNodeDto treeNode) {
        return graphService.addNewNode(treeNode);
    }

    @ApiOperation(value = "编辑接口-检查是否存在公司编码和公司主体")
    @ApiImplicitParam(name = "nodeId", value = "树id", required = true, dataType = "Integer",
            paramType = "path")
    @Delete("/node/del/{nodeId}")
    public ReturnInfo delNodeInfo(@PathVariable("nodeId") Integer nodeId) {
        return graphService.delNodeInfo(nodeId);
    }

    @ApiOperation(value = "更新节点")
    @ApiImplicitParam(required = true, dataType = "TreeNodeDto", name = "treeNode")
    @PostMapping("/node/update")
    public ReturnInfo<GraphControlTreeNode> updateNode(TreeNodeDto treeNode) {
        return graphService.updateNode(treeNode);
    }

    @ApiOperation(value = "编辑接口-检查是否存在公司编码和公司主体")
    @ApiImplicitParam(name = "parentId", value = "父id", required = true, dataType = "Integer",
            paramType = "path")
    @Delete("/node/list/{parentId}")
    public ReturnInfo<List<TreeNodebuild>> listTree(@PathVariable("parentId") Integer parentId) {
        if (parentId == null) {
            parentId = 0;
        }
        return graphService.listTree(parentId);
    }

}
