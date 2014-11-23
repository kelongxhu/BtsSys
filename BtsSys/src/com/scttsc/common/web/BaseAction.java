/*
 * @(#)BaseAction.java 2008-12-14
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.scttsc.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {

    public static Logger LOG = Logger.getLogger(BaseAction.class);
	public String jsonString;

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取 当前 Session
	 * 
	 * @return Session 当前 Session
	 */
	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 保存对象到 Session 里
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            待保存对象
	 */
	public void putObjToSession(String key, Object obj) {
		this.getSessionMap().put(key, obj);
	}

	/**
	 * 从 session 里删除对象
	 * 
	 * @param key
	 *            键
	 */
	public void removeObjFromSession(String key) {
		this.getSessionMap().remove(key);
	}

	/**
	 * 从Session中获取对象
	 * 
	 * @param key
	 *            键
	 * @return 获取到的对象
	 */
	public Object getObjFromSession(String key) {
		return this.getSessionMap().get(key);
	}

	/**
	 * 获得servlet上下文
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}

	/**
	 * 返回json数据
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void jsonResponse(String message) {
		// getResponse().setContentType("text/json;charset=UTF-8");
		try {
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter pw = getResponse().getWriter();
			pw.write(message);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接输出字符串.
	 */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 绕过Template,直接输出内容的简便函数.
	 */
	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
		}
		return null;
	}

	public String sortname;
	public String sortorder;
	public int page;

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public int pagesize;
	public Map<String, Object> jsonMap = new HashMap<String, Object>();

	public void setJsonMapTotal(int total) {
		jsonMap.put("Total", total);
	}

	public void setJsonMapRows(List list) {
		jsonMap.put("Rows", list);
	}

    public void setJsonMapCenterLongitude(BigDecimal centerLongit) {
        jsonMap.put("centerLongit", centerLongit);
    }

    public void setJsonMapCenterLatitude(BigDecimal centerLatit) {
        jsonMap.put("centerLatit", centerLatit);
    }

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	

}
