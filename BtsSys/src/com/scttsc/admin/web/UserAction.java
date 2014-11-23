package com.scttsc.admin.web;

import com.scttsc.admin.service.UserManager;
import com.scttsc.common.web.BaseAction;

public class UserAction extends BaseAction {
	private UserManager userManager;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	
	public String index2(){
		return SUCCESS;
	}

}
