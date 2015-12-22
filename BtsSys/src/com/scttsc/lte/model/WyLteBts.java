package com.scttsc.lte.model;

import java.math.BigDecimal;
import java.util.Date;

public class WyLteBts {
    private BigDecimal intId;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String isIndoor;

    private String isRru;

    private BigDecimal relateEnbIntid;

    private String enbName;

    private String circuitroomOwnership;

    private String transOwnership;

    private String venderName;

    private String serviceLevel;

    private String siteTogether;

    private String hightrainFlag;

    private Short redlineFlag;

    private Integer cityId;

    private Integer countryId;

    private Short manualFlag;

    private Short deleteFlag;

    private Date intime;

    private Date deletetime;

    private Date updatetime;

    private Short deleteResoncode;

    private String deleteText;

    //冗余字段
    private String cityName;//地市

    private String countryName;//区县

    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getIsIndoor() {
        return isIndoor;
    }

    public void setIsIndoor(String isIndoor) {
        this.isIndoor = isIndoor == null ? null : isIndoor.trim();
    }

    public String getIsRru() {
        return isRru;
    }

    public void setIsRru(String isRru) {
        this.isRru = isRru == null ? null : isRru.trim();
    }

    public BigDecimal getRelateEnbIntid() {
        return relateEnbIntid;
    }

    public void setRelateEnbIntid(BigDecimal relateEnbIntid) {
        this.relateEnbIntid = relateEnbIntid;
    }

    public String getEnbName() {
        return enbName;
    }

    public void setEnbName(String enbName) {
        this.enbName = enbName == null ? null : enbName.trim();
    }

    public String getCircuitroomOwnership() {
        return circuitroomOwnership;
    }

    public void setCircuitroomOwnership(String circuitroomOwnership) {
        this.circuitroomOwnership = circuitroomOwnership == null ? null : circuitroomOwnership.trim();
    }

    public String getTransOwnership() {
        return transOwnership;
    }

    public void setTransOwnership(String transOwnership) {
        this.transOwnership = transOwnership == null ? null : transOwnership.trim();
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName == null ? null : venderName.trim();
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel == null ? null : serviceLevel.trim();
    }

    public String getSiteTogether() {
        return siteTogether;
    }

    public void setSiteTogether(String siteTogether) {
        this.siteTogether = siteTogether == null ? null : siteTogether.trim();
    }

    public String getHightrainFlag() {
        return hightrainFlag;
    }

    public void setHightrainFlag(String hightrainFlag) {
        this.hightrainFlag = hightrainFlag == null ? null : hightrainFlag.trim();
    }

    public Short getRedlineFlag() {
        return redlineFlag;
    }

    public void setRedlineFlag(Short redlineFlag) {
        this.redlineFlag = redlineFlag;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Short getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Short manualFlag) {
        this.manualFlag = manualFlag;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Short getDeleteResoncode() {
        return deleteResoncode;
    }

    public void setDeleteResoncode(Short deleteResoncode) {
        this.deleteResoncode = deleteResoncode;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(String deleteText) {
        this.deleteText = deleteText == null ? null : deleteText.trim();
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
}