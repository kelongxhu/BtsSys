package com.scttsc.healthy.model;

import com.scttsc.business.model.Bts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WyGrade {
    private Long intId;

    private Double infoGrade;

    private Double inspGrade;

    private Double alarmGrade;

    private Double wirelessGrade;

    private Double totalGrade;

    private Date updatetime;

    private String cityName;

    private String countyName;

    private Bts bts;

    private List<WyRulecfg> wyRulecfgList;

    private List<WyGradeDetail> wyGradeDetailList;

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public Double getInfoGrade() {
        return infoGrade;
    }

    public void setInfoGrade(Double infoGrade) {
        this.infoGrade = infoGrade;
    }

    public Double getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(Double alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public Double getInspGrade() {
        return inspGrade;
    }

    public void setInspGrade(Double inspGrade) {
        this.inspGrade = inspGrade;
    }

    public Double getWirelessGrade() {
        return wirelessGrade;
    }

    public void setWirelessGrade(Double wirelessGrade) {
        this.wirelessGrade = wirelessGrade;
    }

    public Double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(Double totalGrade) {
        this.totalGrade = totalGrade;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Bts getBts() {
        return bts;
    }

    public void setBts(Bts bts) {
        this.bts = bts;
    }

    public List<WyRulecfg> getWyRulecfgList() {
        return wyRulecfgList;
    }

    public void setWyRulecfgList(List<WyRulecfg> wyRulecfgList) {
        this.wyRulecfgList = wyRulecfgList;
    }

    public List<WyGradeDetail> getWyGradeDetailList() {
        return wyGradeDetailList;
    }

    public void setWyGradeDetailList(List<WyGradeDetail> wyGradeDetailList) {
        this.wyGradeDetailList = wyGradeDetailList;
    }
}