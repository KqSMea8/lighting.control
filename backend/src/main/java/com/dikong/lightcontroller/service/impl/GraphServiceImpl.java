package com.dikong.lightcontroller.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.GraphControlEditNodeDao;
import com.dikong.lightcontroller.dao.GraphControlTreeNodeDao;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.dto.GraphControlEditNodeDto;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.dto.TreeNodeDto;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.GraphControlEditNode;
import com.dikong.lightcontroller.entity.GraphControlTreeNode;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.GraphService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TaskService;
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

    @Autowired
    private GraphControlEditNodeDao editNodeDao;

    @Autowired
    private SysVarService sysVarService;

    @Autowired
    private CmdService cmdService;

    @Autowired
    private TaskService taskService;

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

    @Override
    @Transactional
    public ReturnInfo<GraphControlEditNode> addGraphEditNode(GraphControlEditNode editNode) {
        // 组件类型1->底图 2->文字 3->开关量 4->模拟量 5->区域控件 6->曲线图控件
        editNode.setProjectId(AuthCurrentUser.getCurrentProjectId());
        editNode.setCreateBy(AuthCurrentUser.getUserId());
        editNodeDao.insertSelective(editNode);
        // TODO 如果添加的是曲线图控件，需要增加一个定时任务
        String cronStr = "0 0/" + 1 + " * * * ? ";
        ReturnInfo returnInfo = taskService.addGraphTask(editNode.getId());
        if (null != returnInfo.getData()) {
            // Device device = new Device();
            // device.setId(deviceAdd.getId());
            QuartzJobDto quartzJobDto = (QuartzJobDto) returnInfo.getData();
            // device.setTaskName(quartzJobDto.getJobDO().getName());
            // deviceDAO.updateByPrimaryKeySelective(device);
            System.out.println("job name=" + quartzJobDto.getJobDO().getName());
        }
        return ReturnInfo.createReturnSuccessOne(editNode);
    }

    @Override
    public ReturnInfo<GraphControlEditNode> updateGraphEditNode(GraphControlEditNode editNode) {
        editNode.setProjectId(AuthCurrentUser.getCurrentProjectId());
        editNode.setUpdateBy(AuthCurrentUser.getCurrentProjectId());
        editNodeDao.updateByPrimaryKey(editNode);
        return ReturnInfo.createReturnSuccessOne(editNode);
    }

    @Override
    public ReturnInfo<List<GraphControlEditNodeDto>> listGraphEditNodes(Integer treeNodeId) {
        List<GraphControlEditNodeDto> editNodeList =
                editNodeDao.listAllEditNodeByTreeId(treeNodeId);
        for (GraphControlEditNodeDto editNodeDto : editNodeList) {
            if (StringUtils.isEmpty(editNodeDto.getVarType())) {
                continue;
            }
            CmdRes<String> result = null;
            if (editNodeDto.getVarType().equals(Register.BI)
                    || editNodeDto.getVarType().equals(Register.BV)) {// 开关量
                result = cmdService.readOneSwitch((long) editNodeDto.getTargetId());
            } else {
                result = cmdService.readOneAnalog((long) editNodeDto.getTargetId());
            }
            if (StringUtils.isEmpty(result)) {// 如果查询失败，返回默认值
                editNodeDto.setCurrentValue(new BigDecimal(editNodeDto.getVarValue()));
                continue;
            }
            if (result.isSuccess()) {
                editNodeDto.setCurrentValue(new BigDecimal(result.getData()));
            } else {// TODO 如果查询失败，返回默认值
                editNodeDto.setCurrentValue(new BigDecimal(editNodeDto.getVarValue()));
            }
        }
        return ReturnInfo.createReturnSuccessOne(editNodeList);
    }

    @Override
    public ReturnInfo delGraphEditNode(Integer editNodeId) {
        editNodeDao.deleteByPrimaryKey(editNodeId);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo changeValue(Integer editNodeId, String value) {
        GraphControlEditNode editNode = editNodeDao.selectByPrimaryKey(editNodeId);
        if (!editNode.getAssemblyType().equals(Constant.ASSEMBLY_TYPE.SWITCH)
                && !editNode.getAssemblyType().equals(Constant.ASSEMBLY_TYPE.ANALOG)) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        int[] sendResult = new int[2];
        int sourceId = editNode.getTargetId();
        switch (editNode.getTargetType()) {
            case 1:
                BaseSysVar register = new BaseSysVar();
                if (register.getVarType().equals(Register.AI)
                        || register.getVarType().equals(Register.BI)) {
                    return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
                }
                register.setVarId((long) sourceId);
                register.setVarValue(value);
                ReturnInfo registerReturin = sysVarService.updateSysVar(register);
                sendResult = (int[]) registerReturin.getData();
                break;
            case 2:

                BaseSysVar group = new BaseSysVar();
                group.setSysVarType(BaseSysVar.GROUP);
                group.setVarId((long) sourceId);
                group.setVarValue(value);
                ReturnInfo returnInfo = sysVarService.updateSysVar(group);
                sendResult = (int[]) returnInfo.getData();
                break;
            case 3:
                // 查询时序信息和ID
                BaseSysVar baseSysVar = new BaseSysVar();
                baseSysVar.setVarId(new Long(sourceId));
                baseSysVar.setSysVarType(BaseSysVar.SEQUENCE);
                baseSysVar.setVarValue(value);
                sysVarService.updateSysVar(baseSysVar);
                sendResult[0]++;
                break;

            default:
                break;
        }
        if (sendResult[0] != 0) {
            // monitor.setCurrentValue(new BigDecimal(value));
            // monitorDao.updateByPrimaryKeySelective(monitor);
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("success", String.valueOf(sendResult[0]));
        result.put("fail", String.valueOf(sendResult[1]));
        return ReturnInfo.createReturnSuccessOne(result);
    }

    @Override
    public ReturnInfo refreshValue(Integer editNodeId) {
        Example queryExample = new Example(GraphControlEditNode.class);
        queryExample.createCriteria().andEqualTo("", editNodeId);
        return null;
    }
}


