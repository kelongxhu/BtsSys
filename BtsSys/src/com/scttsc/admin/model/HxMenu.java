package com.scttsc.admin.model;

import java.util.Date;
import java.util.List;

import com.scttsc.common.model.BaseModel;

public class HxMenu extends BaseModel {

	private int pid;
	private String name;
	private String url;
	private int orderby;
	private String icon;
	private Date intime;
	private String remark;
    private int flag;


    List<HxMenu> children;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public List<HxMenu> getChildren() {
        return children;
    }

    public void setChildren(List<HxMenu> children) {
        this.children = children;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
