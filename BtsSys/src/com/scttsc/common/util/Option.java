/*
 * @Title: Option.java @Package cn.sccl.common.util @Description:
 * TODO(用于页面元素select的构建) @author YangDong @date 2011-3-11 上午09:58:18
 * 
 * @version V1.0
 */
package com.scttsc.common.util;

/**
 * @ClassName: Option
 * @Description: TODO(用于页面元素select的构建)
 * @author YangDong
 * @date 2011-3-11 上午09:58:18
 */
public class Option
{
	String description;

	String key;

	public Option(String key, String description)
	{
		this.key = key;
		this.description = description;
	}

	public String getKey()
	{
		return key;
	}

	public String getDescription()
	{
		return description;
	}

}
