package com.scttsc.charge.model;

import java.math.BigDecimal;
import java.util.Date;

public class WySmsLog {
    private BigDecimal id;

    private BigDecimal intId;

    private Short btsType;

    private Short costType;

    private String remindUser;

    private String remindTel;

    private String content;

    private Short status;

    private Date inTime;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public Short getBtsType() {
        return btsType;
    }

    public void setBtsType(Short btsType) {
        this.btsType = btsType;
    }

    public String getRemindUser() {
        return remindUser;
    }

    public void setRemindUser(String remindUser) {
        this.remindUser = remindUser == null ? null : remindUser.trim();
    }

    public String getRemindTel() {
        return remindTel;
    }

    public void setRemindTel(String remindTel) {
        this.remindTel = remindTel == null ? null : remindTel.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Short getCostType() {
        return costType;
    }

    public void setCostType(Short costType) {
        this.costType = costType;
    }
}