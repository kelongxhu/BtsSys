package com.scttsc.admin.model;

import com.scttsc.common.model.TreeNode;

public class CityTreeNode extends TreeNode {

	public CityTreeNode(City city) {
		super.setId(city.getId() + "");
		super.setPid(city.getParentId() + "");
		super.setText(city.getCityName());
	}
}
