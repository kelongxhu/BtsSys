package com.scttsc.admin.web;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.scttsc.admin.model.User;

/**
 * 会话拦截器
 */
@SuppressWarnings("serial")
public class AuthInterceptor extends AbstractInterceptor {

	String out = "";
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        String result = "";
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        ServletContext applicationContext = ServletActionContext.getServletContext();
        User user = (User) session.get("user");
        if (user == null) {
            result = "relogin";
        } else {
            result = invocation.invoke();
        }
        return result;
	}
	
	
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	
	
	
	
}
