package com.scttsc.baselibs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.*;
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
	 * 组装場景庫
	 * @param schoolLibs
	 * @param secneryLibs
	 * @return
	 */
	public static TreeNode buildTreeNodeBySceneLib(List<WyLibScene> wyLibSceneList){
		TreeNode root = new TreeNode("请选择", "0", "-1");
		for(WyLibScene wyLibScene:wyLibSceneList){
			TreeNode node=new TreeNode(wyLibScene.getName(),wyLibScene.getId()+"","-1");
            root.addChild(node);
		}
		return root;
	}
}
