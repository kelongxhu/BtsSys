package com.scttsc.common.model;

import java.util.List;

public class TreeNodeHelper {
	private final List<TreeNode> list;
	private TreeNode root;
	String rootId;

	public TreeNodeHelper(List paramList) {
		this.list = paramList;
		buildTree();
	}
	
	public TreeNodeHelper(List paramList,String rootId) {
		this.rootId = rootId;
		this.list = paramList;
		buildTree();
	}

	public TreeNode getRoot() {
		return this.root;
	}

	private void buildTree() {
		if ((this.list != null) && (!(this.list.isEmpty())))
			for (int i = 0; i < this.list.size(); ++i) {
				TreeNode localTreeNode = (TreeNode) this.list.get(i);
				
				if(rootId==null){
					if (localTreeNode.isRoot()) {
						localTreeNode.setState(null);
						this.root = localTreeNode;
					}
				}else {
					if (localTreeNode.isRoot(rootId)) {
						localTreeNode.setState(null);
						this.root = localTreeNode;
					}
				}
				
			}
		if (this.root != null)
			loadChildren(this.root);

	}

	private void loadChildren(TreeNode paramTreeNode) {
		if ((this.list != null) && (!(this.list.isEmpty())))
			for (int i = 0; i < this.list.size(); ++i) {
				TreeNode localTreeNode = (TreeNode) this.list.get(i);
				if ((localTreeNode.getPid() != null)
						&& (paramTreeNode.getId() != null)
						&& (localTreeNode.getPid()
								.equals(paramTreeNode.getId()))) {
					paramTreeNode.addChild(localTreeNode);
					loadChildren(localTreeNode);
				}
			}
		if (paramTreeNode.isLeaf()) {
			paramTreeNode.setState(null);
		}
	}
}
