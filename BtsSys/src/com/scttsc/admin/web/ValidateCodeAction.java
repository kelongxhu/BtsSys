/*
 * @Title: ValidateCodeAction.java
 * @Package com.sccttsc.common.web
 * @Description: 随机图片验证码
 * @author yangdong
 * @date 2010-10-25 上午10:52:25
 * @version V1.0
 */
package com.scttsc.admin.web;

import java.io.ByteArrayInputStream;

import com.scttsc.common.util.ValidateCodeCreator;
import com.scttsc.common.web.BaseAction;

/**
 * @ClassName: ValidateCodeAction
 * @Description: 随机图片验证码
 */
public class ValidateCodeAction extends BaseAction
{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -6794199659674653279L;
	
	private ByteArrayInputStream inputStream;

    /**
     * 校驗
     * @return
     * @throws Exception
     */
	public String execute() throws Exception
	{
		ValidateCodeCreator rdnu = ValidateCodeCreator.Instance();
		this.setInputStream(rdnu.getImage());			// 取得带有随机字符串的图片
		this.getSession().setAttribute("validateCode", rdnu.getString());// 取得随机字符串放入HttpSession
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream()
	{
		return inputStream;
	}
}
