package com.scttsc.common.util;

import java.util.Date;

public class UploadFileInfo {
	private String fileName;
	private String fileType;
	private Date createTime;


	public UploadFileInfo() {
	}
	public UploadFileInfo(String fileName, String fileType) {
		this.fileName = fileName;
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
