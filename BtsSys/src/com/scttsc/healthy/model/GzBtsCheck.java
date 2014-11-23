package com.scttsc.healthy.model;

import java.math.BigDecimal;
import java.util.Date;

public class GzBtsCheck  {

    private Long intId;

    private String userName;

    private Date reportTime;

    private String btsName;

    private String btsType;

    private BigDecimal regionCode;

    private String imsi;

    private BigDecimal sumlevel;

    private BigDecimal state;

    private Date signinTime;

    private Date signoutTime;

    private String description;

    private String solution;

    private Object ordernumber;

    private Object isend;

    private String grade;

    private BigDecimal cityId;

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName == null ? null : btsName.trim();
    }

    public String getBtsType() {
        return btsType;
    }

    public void setBtsType(String btsType) {
        this.btsType = btsType == null ? null : btsType.trim();
    }

    public BigDecimal getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(BigDecimal regionCode) {
        this.regionCode = regionCode;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public BigDecimal getSumlevel() {
        return sumlevel;
    }

    public void setSumlevel(BigDecimal sumlevel) {
        this.sumlevel = sumlevel;
    }

    public BigDecimal getState() {
        return state;
    }

    public void setState(BigDecimal state) {
        this.state = state;
    }

    public Date getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(Date signinTime) {
        this.signinTime = signinTime;
    }

    public Date getSignoutTime() {
        return signoutTime;
    }

    public void setSignoutTime(Date signoutTime) {
        this.signoutTime = signoutTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }

    public Object getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Object ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Object getIsend() {
        return isend;
    }

    public void setIsend(Object isend) {
        this.isend = isend;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}