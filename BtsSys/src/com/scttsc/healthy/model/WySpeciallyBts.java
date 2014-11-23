package com.scttsc.healthy.model;

import com.scttsc.business.model.Bts;
import com.scttsc.common.util.DateUtils;
import org.apache.commons.httpclient.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class WySpeciallyBts {
    private Long specid;

    private Long btsIntId;

    private Date updateTime;

    private String updateTimeStr;

    private Bts bts;

    public Long getSpecid() {
        return specid;
    }

    public void setSpecid(Long specid) {
        this.specid = specid;
    }

    public Long getBtsIntId() {
        return btsIntId;
    }

    public void setBtsIntId(Long btsIntId) {
        this.btsIntId = btsIntId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTime==null?"":  DateUtils.DateToStr(updateTime, "yyyy-MM-dd");
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Bts getBts() {
        return bts;
    }

    public void setBts(Bts bts) {
        this.bts = bts;
    }


}