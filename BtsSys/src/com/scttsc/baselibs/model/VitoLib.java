package com.scttsc.baselibs.model;

import java.math.BigDecimal;
import java.util.Date;

public class VitoLib {
  
    private Long id;
    private Long cityId;
    private Long countryId;
    private Long parentId;
    private String name;
    private double gpsLong;
    private double gpsLat;
    private double cmda1xCovgrate;
    private double mobgsmCovgrate;
    private double covgDifCovgrate;
    private double unicgsmCovgrate;
    private double evdoCovgrate;
    private double cdma1xMos;
    private double mobgsmMos;
    private double unicgsmMos;
    private String testtime;
    private String testuser;
    private Date updatetime;
    private Long updateuser;
    private String remark;
    private Integer deleteFlag;
    
    
    //冗余
    private String cityName;//地市名称
    private String countryName;//乡镇名称
    private String parentName;//父节点名称


    VitoLib parent;//所属乡镇库


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public double getCmda1xCovgrate() {
        return cmda1xCovgrate;
    }

    public void setCmda1xCovgrate(double cmda1xCovgrate) {
        this.cmda1xCovgrate = cmda1xCovgrate;
    }

    public double getMobgsmCovgrate() {
        return mobgsmCovgrate;
    }

    public void setMobgsmCovgrate(double mobgsmCovgrate) {
        this.mobgsmCovgrate = mobgsmCovgrate;
    }

    public double getCovgDifCovgrate() {
        return covgDifCovgrate;
    }

    public void setCovgDifCovgrate(double covgDifCovgrate) {
        this.covgDifCovgrate = covgDifCovgrate;
    }

    public double getUnicgsmCovgrate() {
        return unicgsmCovgrate;
    }

    public void setUnicgsmCovgrate(double unicgsmCovgrate) {
        this.unicgsmCovgrate = unicgsmCovgrate;
    }

    public double getEvdoCovgrate() {
        return evdoCovgrate;
    }

    public void setEvdoCovgrate(double evdoCovgrate) {
        this.evdoCovgrate = evdoCovgrate;
    }

    public double getCdma1xMos() {
        return cdma1xMos;
    }

    public void setCdma1xMos(double cdma1xMos) {
        this.cdma1xMos = cdma1xMos;
    }

    public double getMobgsmMos() {
        return mobgsmMos;
    }

    public void setMobgsmMos(double mobgsmMos) {
        this.mobgsmMos = mobgsmMos;
    }

    public double getUnicgsmMos() {
        return unicgsmMos;
    }

    public void setUnicgsmMos(double unicgsmMos) {
        this.unicgsmMos = unicgsmMos;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public VitoLib getParent() {
        return parent;
    }

    public void setParent(VitoLib parent) {
        this.parent = parent;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}