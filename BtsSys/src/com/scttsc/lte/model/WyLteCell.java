package com.scttsc.lte.model;

import java.math.BigDecimal;
import java.util.Date;

public class WyLteCell {
    private BigDecimal intId;

    private String name;

    private Integer cityId;

    private Integer countryId;

    private String vendorName;

    private String isIndoor;

    private String isRru;

    private String isGf;

    private Short isOr;

    private Short isRr;

    private Short isSr;

    private String siteTogether;

    private BigDecimal letIntId;

    private BigDecimal enbIntId;

    private String hightrainFlag;

    private Short redlineFlag;

    private Short manualFlag;

    private Short deleteFlag;

    private Date deletetime;

    private Date intime;

    private Date updatetime;

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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
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

    public String getIsGf() {
        return isGf;
    }

    public void setIsGf(String isGf) {
        this.isGf = isGf == null ? null : isGf.trim();
    }

    public Short getIsOr() {
        return isOr;
    }

    public void setIsOr(Short isOr) {
        this.isOr = isOr;
    }

    public Short getIsRr() {
        return isRr;
    }

    public void setIsRr(Short isRr) {
        this.isRr = isRr;
    }

    public Short getIsSr() {
        return isSr;
    }

    public void setIsSr(Short isSr) {
        this.isSr = isSr;
    }

    public String getSiteTogether() {
        return siteTogether;
    }

    public void setSiteTogether(String siteTogether) {
        this.siteTogether = siteTogether == null ? null : siteTogether.trim();
    }

    public BigDecimal getLetIntId() {
        return letIntId;
    }

    public void setLetIntId(BigDecimal letIntId) {
        this.letIntId = letIntId;
    }

    public BigDecimal getEnbIntId() {
        return enbIntId;
    }

    public void setEnbIntId(BigDecimal enbIntId) {
        this.enbIntId = enbIntId;
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
}