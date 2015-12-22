package com.scttsc.lte.model;

import java.math.BigDecimal;
import java.util.Date;

public class WyLteBbu {
    private BigDecimal intId;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String bbuNo;

    private String enbBtsName;

    private BigDecimal enbBtsId;

    private String circuitroomOwnership;

    private String transOwnership;

    private String vendorName;

    private Integer cityId;

    private Integer countyId;

    private Short isShare;

    private Short bbuType;

    private BigDecimal wyLteBtsid;

    private Short hightrainFlag;

    private Short redlineFlag;

    private Short manualFlag;

    private Short deleteFlag;

    private Date deletetime;

    private Date intime;

    private Date updatetime;

    private Long enbId;

    private String siteTogether;

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

    public String getBbuNo() {
        return bbuNo;
    }

    public void setBbuNo(String bbuNo) {
        this.bbuNo = bbuNo == null ? null : bbuNo.trim();
    }

    public String getEnbBtsName() {
        return enbBtsName;
    }

    public void setEnbBtsName(String enbBtsName) {
        this.enbBtsName = enbBtsName == null ? null : enbBtsName.trim();
    }

    public BigDecimal getEnbBtsId() {
        return enbBtsId;
    }

    public void setEnbBtsId(BigDecimal enbBtsId) {
        this.enbBtsId = enbBtsId;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Short getIsShare() {
        return isShare;
    }

    public void setIsShare(Short isShare) {
        this.isShare = isShare;
    }

    public Short getBbuType() {
        return bbuType;
    }

    public void setBbuType(Short bbuType) {
        this.bbuType = bbuType;
    }

    public BigDecimal getWyLteBtsid() {
        return wyLteBtsid;
    }

    public void setWyLteBtsid(BigDecimal wyLteBtsid) {
        this.wyLteBtsid = wyLteBtsid;
    }

    public Short getHightrainFlag() {
        return hightrainFlag;
    }

    public void setHightrainFlag(Short hightrainFlag) {
        this.hightrainFlag = hightrainFlag;
    }

    public Short getRedlineFlag() {
        return redlineFlag;
    }

    public void setRedlineFlag(Short redlineFlag) {
        this.redlineFlag = redlineFlag;
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

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getEnbId() {
        return enbId;
    }

    public void setEnbId(Long enbId) {
        this.enbId = enbId;
    }

    public String getSiteTogether() {
        return siteTogether;
    }

    public void setSiteTogether(String siteTogether) {
        this.siteTogether = siteTogether == null ? null : siteTogether.trim();
    }
}