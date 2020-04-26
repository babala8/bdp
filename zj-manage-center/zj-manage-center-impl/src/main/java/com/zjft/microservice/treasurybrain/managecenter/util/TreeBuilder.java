package com.zjft.microservice.treasurybrain.managecenter.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 预排序工具类
 *
 * @author 吴磊
 */
public class TreeBuilder {

	List<OrgNode> nodes;


	private List<OrgNode> allNodes = new ArrayList<>();

	public TreeBuilder(List<OrgNode> nodes) {
		super();
		this.nodes = nodes;
	}

	/**
	 * 构建JSON树形结构
	 *
	 * @return
	 */
	public String buildJSONTree() {
		List<OrgNode> nodeTree = buildTree();
		rebuild_tree(nodeTree.get(0), 1);

		String jsonString = JSON.toJSONString(nodeTree);
		return jsonString;
	}

	/**
	 * 构建左右子树，left，right
	 *
	 * @param org
	 * @param left
	 * @return
	 */
	public int rebuild_tree(OrgNode org, int left) {
		int right = left + 1;
		if (org.getChildOrgs() != null && org.getChildOrgs().size() > 0) {
			for (OrgNode newOrg : org.getChildOrgs()) {
				right = rebuild_tree(newOrg, right);
			}
		}
		org.setLeft(left);
		org.setRight(right);
		allNodes.add(org);
		// System.out.println(org.getLeft()+" "+org.getNo()+"
		// "+org.getParentOrg()+" "+org.getRight()+" "+org.getTreelev());
		return right + 1;
	}

	/**
	 * 构建树形结构
	 *
	 * @return List<OrgNode>
	 */
	public List<OrgNode> buildTree() {
		List<OrgNode> treeNodes = new ArrayList<OrgNode>();
		List<OrgNode> rootNodes = getRootNodes();
		for (OrgNode rootNode : rootNodes) {
			rootNode.setTreelev(1);
			buildChildNodes(rootNode);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}

	/**
	 * 构建树形结构，包含左右序号节点树
	 *
	 * @return
	 */
	public List<OrgNode> buildTree2() {
		List<OrgNode> treeNodes = new ArrayList<OrgNode>();
		List<OrgNode> rootNodes = getRootNodes();
		for (OrgNode rootNode : rootNodes) {
			rootNode.setTreelev(1);
			buildChildNodes(rootNode, 1);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}

	/**
	 * 递归子节点
	 *
	 * @param node
	 */
	public void buildChildNodes(OrgNode node) {
		List<OrgNode> children = getChildNodes(node);
		if (!children.isEmpty()) {
			for (OrgNode child : children) {
				child.setTreelev(node.getTreelev() + 1);
				buildChildNodes(child);
			}
			node.setChildOrgs(children);
		}
	}

	/**
	 * 递归子节点2及左右子树
	 *
	 * @param node
	 * @param left
	 */
	public int buildChildNodes(OrgNode node, int left) {
		int right = left + 1;
		List<OrgNode> children = getChildNodes(node);
		if (!children.isEmpty()) {
			for (OrgNode child : children) {
				child.setTreelev(node.getTreelev() + 1);
				right = buildChildNodes(child, right);
			}
			node.setChildOrgs(children);
		}
		node.setLeft(left);
		node.setRight(right);
		allNodes.add(node);
		return right + 1;
	}

	/**
	 * 获取父节点下所有的子节点
	 *
	 * @param pnode
	 * @return List<OrgNode>
	 */
	public List<OrgNode> getChildNodes(OrgNode pnode) {
		List<OrgNode> childNodes = new ArrayList<OrgNode>();
		for (OrgNode n : nodes) {
			if (pnode.getNo().equals(n.getParentOrg())) {
				childNodes.add(n);
			}
		}
		return childNodes;
	}

	/**
	 * 函数名:getAllSubNodes
	 * 函数功能：获取指定机构下的所有子机构
	 * 创建信息：hqhan/2017年11月15日 下午3:34:56
	 * 修改信息：
	 */
	public List<OrgNode> getAllSubNodes(OrgNode pnode) {
		List<OrgNode> subNodeList = new ArrayList<OrgNode>();
		int left = pnode.getLeft();
		int right = pnode.getRight();
		for (int i = 0; i < nodes.size(); i++) {
			OrgNode node = nodes.get(i);
			if (node.getLeft() >= left && node.getRight() <= right) {
				subNodeList.add(node);
			}
		}
		return subNodeList;
	}

	/**
	 * 判断是否为根节点
	 *
	 * @param node 节点
	 * @return boolean
	 */
	public boolean rootNode(OrgNode node) {
		boolean isRootNode = true;
		for (OrgNode n : nodes) {
			if (node.getParentOrg() == null || "".equals(node.getParentOrg())) {
				break;
			}
			if (node.getParentOrg().equals(n.getNo())) {
				isRootNode = false;
				break;
			}
		}
		return isRootNode;
	}

	/**
	 * 获取集合中所有的根节点
	 *
	 * @return List<OrgNode>
	 */
	public List<OrgNode> getRootNodes() {
		List<OrgNode> rootNodes = new ArrayList<OrgNode>();
		for (OrgNode n : nodes) {
			if (rootNode(n)) {
				rootNodes.add(n);
			}
		}
		return rootNodes;
	}

	public List<OrgNode> getAllNodes() {
		return this.allNodes;
	}
}
