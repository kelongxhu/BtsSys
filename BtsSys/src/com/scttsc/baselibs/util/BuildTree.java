package com.scttsc.baselibs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.model.SchoolLib;
import com.scttsc.baselibs.model.SecneryLib;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.model.TreeNodeHelper;

public class BuildTree {
	public static TreeNode buildTreeNode(List<RoadLib> list) {
		TreeNode root = new TreeNode("本地网", "10001", "-1");
		Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
		for (RoadLib roadLib : list) {
			TreeNode node = nodeMap.get(roadLib.getCityId() + "");
			if (node == null) {
				node = new TreeNode(roadLib.getCityName(), roadLib.getCityId()
						+ "", "10001");
				nodeMap.put(roadLib.getCityId() + "", node);
			}
			TreeNode roadNode = new TreeNode(roadLib.getName(), roadLib.getId()
					+ "", roadLib.getCityId() + "");
			node.addChild(roadNode);
		}

		for (TreeNode node : nodeMap.values()) {
			root.addChild(node);
		}
		return root;
	}

	/**
	 * 通过道路和隧道的关联关系组装道路隧道树型结构
	 * 
	 * @param roadLibs
	 * @param tunnelLibs
	 * @return
	 */

	public static TreeNode buildTreeNodeByRoad(List<RoadLib> roadLibs,
			List<TunnelLib> tunnelLibs) {
		TreeNode root = new TreeNode("请选择", "0", "-1");
		List<TreeNode> paramList = new ArrayList<TreeNode>();
		for (RoadLib roadLib : roadLibs) {
			TreeNode node = new TreeNode(roadLib.getName(), "-2_"+roadLib.getId(), "0");
			paramList.add(node);// 增加道路库
		}
		for (TunnelLib tunnelLib : tunnelLibs) {
			TreeNode node = new TreeNode(tunnelLib.getName(), "-3_"+tunnelLib.getId(), "-2_"+tunnelLib.getRoadId());
			paramList.add(node);
		}

		paramList.add(root);

		TreeNodeHelper helper = new TreeNodeHelper(paramList, 0 + "");

		return helper.getRoot();
	}
	
	/**
	 * 组装校园库和风景库的树
	 * @param schoolLibs
	 * @param secneryLibs
	 * @return
	 */
	public static TreeNode buildTreeNodeBySchool(List<SchoolLib> schoolLibs,List<SecneryLib> secneryLibs){
		TreeNode root = new TreeNode("请选择", "0", "-1");
		TreeNode node1=new TreeNode("校园库","-2","0");
		TreeNode node2=new TreeNode("风景库","-3","0");
		for(SchoolLib schoolLib:schoolLibs){
			TreeNode node=new TreeNode(schoolLib.getName(),"-2_"+schoolLib.getId(),"-2");
			node1.addChild(node);
		}
		for(SecneryLib secneryLib:secneryLibs){
			TreeNode node=new TreeNode(secneryLib.getSceName(), "-3_"+secneryLib.getId()+"", "-3");
			node2.addChild(node);
		}
		root.addChild(node1);
		root.addChild(node2);
		
		return root;
	}
	

}
