package com.scttsc.charge.model;

/**
 * Created by _think on 2014/11/24.
 */
public class Smgp {
    private Long id;
    private String serviceId;
    private String desttermId;
    private String msgContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDesttermId() {
        return desttermId;
    }

    public void setDesttermId(String desttermId) {
        this.desttermId = desttermId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
