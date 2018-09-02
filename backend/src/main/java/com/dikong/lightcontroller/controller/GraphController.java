package com.dikong.lightcontroller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.GraphControlEditNodeDto;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.GraphControlEditNode;
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
    @PostMapping("/tree/node/add")
    public ReturnInfo<GraphControlTreeNode> addNewNode(@RequestBody TreeNodeDto treeNode) {
        return graphService.addNewNode(treeNode);
    }

    @ApiOperation(value = "删除节点")
    @ApiImplicitParam(name = "nodeId", value = "树id", required = true, dataType = "Integer",
            paramType = "path")
    @DeleteMapping("/tree/node/del/{nodeId}")
    public ReturnInfo delNodeInfo(@PathVariable("nodeId") Integer nodeId) {
        return graphService.delNodeInfo(nodeId);
    }

    @ApiOperation(value = "更新节点")
    @ApiImplicitParam(required = true, dataType = "TreeNodeDto", name = "treeNode")
    @PostMapping("/tree/node/update")
    public ReturnInfo<GraphControlTreeNode> updateNode(@RequestBody TreeNodeDto treeNode) {
        return graphService.updateNode(treeNode);
    }

    @ApiOperation(value = "树列表")
    @ApiImplicitParam(name = "parentId", value = "父id", required = true, dataType = "Integer",
            paramType = "path")
    @GetMapping("/tree/node/list/{parentId}")
    public ReturnInfo<List<TreeNodebuild>> listTree(@PathVariable("parentId") Integer parentId) {
        if (parentId == null) {
            parentId = 0;
        }
        return graphService.listTree(parentId);
    }


    @ApiOperation(value = "增加编辑页面节点")
    @ApiImplicitParam(required = true, dataType = "GraphControlEditNode", name = "graphControlPage")
    @PostMapping("/edit/node/add")
    public ReturnInfo<GraphControlEditNode> addGraphEditNode(
            @RequestBody GraphControlEditNode graphControlPage) {
        return graphService.addGraphEditNode(graphControlPage);
    }

    @ApiOperation(value = "更新编辑页面节点")
    @ApiImplicitParam(required = true, dataType = "GraphControlEditNode", name = "graphControlPage")
    @PostMapping("/edit/node/update")
    public ReturnInfo<GraphControlEditNode> updateGraphEditNode(
            @RequestBody GraphControlEditNode graphControlPage) {
        return graphService.updateGraphEditNode(graphControlPage);
    }

    @ApiOperation(value = "查询编辑页面节点列表")
    @ApiImplicitParam(required = true, dataType = "Integer", name = "treeNodeId",
            paramType = "path")
    @GetMapping("/edit/node/list/{tree-node-id}")
    public ReturnInfo<List<GraphControlEditNodeDto>> listGraphEditNodes(
            @PathVariable("tree-node-id") Integer treeNodeId) {
        if (treeNodeId == null || treeNodeId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return graphService.listGraphEditNodes(treeNodeId);
    }

    @ApiOperation(value = "删除编辑页面节点")
    @ApiImplicitParam(required = true, dataType = "Integer", name = "editNodeId",
            paramType = "path")
    @DeleteMapping("/edit/node/del/{tree-node-id}")
    public ReturnInfo delGraphEditNode(@PathVariable("tree-node-id") Integer editNodeId) {
        return graphService.delGraphEditNode(editNodeId);
    }
}
