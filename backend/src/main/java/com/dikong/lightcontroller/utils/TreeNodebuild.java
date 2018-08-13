package com.dikong.lightcontroller.utils;

import java.util.ArrayList;
import java.util.List;

import com.dikong.lightcontroller.entity.GraphControlTreeNode;

public class TreeNodebuild extends GraphControlTreeNode {

    private List<TreeNodebuild> children; // 孩子节点的List

    // 将 组织集合 转成 树节点集合
    public static List<TreeNodebuild> toTreeNodes(List<TreeNodebuild> listPer, Integer parentId) {
        List<TreeNodebuild> listNodes = new ArrayList<TreeNodebuild>();
        // 生成 树节点时，根据 pid=0的根节点 来生成
        loadTreeNode(listPer, listNodes, parentId);
        return listNodes;
    }

    // 将 组织集合 转成 树节点集合
    public static List<TreeNodebuild> toTreeNodes(List<TreeNodebuild> listPer) {
        List<TreeNodebuild> listNodes = new ArrayList<TreeNodebuild>();
        // 生成 树节点时，根据 pid=0的根节点 来生成
        loadTreeNode(listPer, listNodes, 0);
        return listNodes;
    }

    // 递归组织集合 创建 树节点集合
    public static void loadTreeNode(List<TreeNodebuild> listPer, List<TreeNodebuild> listNodes,
            Integer pid) {
        for (TreeNodebuild treeNode : listPer) {
            // 如果组织父id=参数
            if (treeNode.getParentId().equals(pid)) {
                // 将节点 加入到 树节点集合
                listNodes.add(treeNode);
                // 递归 为这个新创建的 树节点找 子节点
                loadTreeNode(listPer, treeNode.children, treeNode.getId());
            }
        }
    }

    public static List<Integer> getAllChilderNode(List<TreeNodebuild> treeNodebuilds) {
        List<Integer> childs = new ArrayList<Integer>();
        getChild(treeNodebuilds, childs);
        return childs;
    }

    private static void getChild(List<TreeNodebuild> treeNodebuilds, List<Integer> childs) {
        for (TreeNodebuild treeNodebuild : treeNodebuilds) {
            if (treeNodebuild.getChildren().size() > 0) {
                getChild(treeNodebuild.getChildren(), childs);
            } else {
                childs.add(treeNodebuild.getId());
                continue;
            }
        }
        return;
    }

    public List<TreeNodebuild> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodebuild> children) {
        this.children = children;
    }
}
