package com.scttsc.business.model;

import java.util.Date;

public class WyLog {
    private Long id;

    private String logfun;

    private String logobj;

    private String logcontent;

    private Date logtime;

    private Long userid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogfun() {
        return logfun;
    }

    public void setLogfun(String logfun) {
        this.logfun = logfun == null ? null : logfun.trim();
    }

    public String getLogobj() {
        return logobj;
    }

    public void setLogobj(String logobj) {
        this.logobj = logobj == null ? null : logobj.trim();
    }

    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent == null ? null : logcontent.trim();
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

}