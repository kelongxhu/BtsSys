package com.scttsc.common.web;

import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import com.scttsc.common.util.SystemConfiguration;

public class FileDownLoadAction extends BaseAction{
	
	public InputStream getInputStream() throws Exception {
		String url = SystemConfiguration.getString("SMS_MODEL_PATH")+"/"+SystemConfiguration.getString("SMS_MODEL_FILE");
		return ServletActionContext.getServletContext()
				.getResourceAsStream(url);
	}

	public String getDownloadFileName() {
		return SystemConfiguration.getString("SMS_MODEL_FILE");
	}

	public String getSmsModel() throws Exception {
		return SUCCESS;
	}

}
