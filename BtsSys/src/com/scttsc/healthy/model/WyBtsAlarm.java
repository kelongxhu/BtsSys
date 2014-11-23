package com.scttsc.healthy.model;

import java.math.BigDecimal;
import java.util.Date;

public class WyBtsAlarm {
    private Long intId;

    private Integer alarmNum;

    private Integer alarmCode;

    private Date updatetime;


    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public Integer getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}