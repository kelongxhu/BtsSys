package com.scttsc.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeNode {
	private String text;
	private String id;
	String pid;
	List children;
	String state;
	Map<String, Object> attributes;
	boolean ischecked;
	String isexpand = "true";
    boolean nocheck;
    private String isParent;
    boolean checked;

	// 增加一个区分数据类型的字段
	private String typeId;

	public TreeNode() {
		super();
	}

	public TreeNode(String text, String id, String pid) {
		super();
		this.text = text;
		this.id = id;
		this.pid = pid;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isRoot() {
		return ((this.getPid() != null) && (this.getPid().equals("-1")));
	}

	public boolean isRoot(String rootId) {
		return ((this.id.equals(rootId)));
	}

	public boolean isLeaf() {
		return ((this.children == null) || (this.children.isEmpty()));
	}

	public void addChild(TreeNode paramTreeNode) {
		if (paramTreeNode == null)
			return;
		if (this.children == null)
			this.children = new ArrayList();
		if (!(this.children.contains(paramTreeNode))) {
			this.children.add(paramTreeNode);
			// paramTreeNode.setParent(this);
		}
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public String getIsexpand() {
		return isexpand;
	}

	public void setIsexpand(String isexpand) {
		this.isexpand = isexpand;
	}

	public void setChildExpand(String p) {
		List<TreeNode> childs = getChildren();
		if (null != childs) {
			for (int i = 0; i < childs.size(); i++) {
				TreeNode child = childs.get(i);
				child.setIsexpand(p);
			}
		}
	}

	public String getTypeId() {
		return pid + "_" + id;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public String getisParent() {
         return isParent;
     }

     public void setisParent(String parent) {
         isParent = parent;
     }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
