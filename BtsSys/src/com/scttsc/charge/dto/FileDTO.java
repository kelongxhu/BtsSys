package com.scttsc.charge.dto;

/**
 * Created by _think on 2014/11/16.
 */
public class FileDto {
    private int status = 1;//1=上传成功,0=上传失败
    private String uuid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void fail() {
        status = 0;
    }


}
