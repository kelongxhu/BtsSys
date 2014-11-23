package com.scttsc.business.model;

import com.scttsc.admin.model.City;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.common.util.DateUtils;

import java.util.Date;

public class WyTunel {
    private Long intId;

    private String name;

    private Double longitude;

    private Double latitude;

    private String isTunel;

    private String isRru;

    private String btsName;

    private String bscName;

    private Integer btsId;

    private String circuitroomOwnership;

    private String transOwnership;

    private String vendorBtstype;

    private Long cityId;

    private Long countyId;

    private Long relatedBts;

    private Integer manualFlag;

    private Integer deleteFlag;

    private Date deletetime;

    private Date updatetime;

    private String serviceLevel;

    private Integer deleteResoncode;

    private String deleteText;

    private String hightrainFlag;

    private Integer redlineFlag;

    //冗余字段

    private String updateTimeStr;//更新時間

    private String deleteTimeStr;//删除时间

    private City city;
    private City country;


    private WyTunelManual wyTunelManual;

    private Cons reasonCons;


    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getIsTunel() {
        return isTunel;
    }

    public void setIsTunel(String isTunel) {
        this.isTunel = isTunel;
    }

    public String getIsRru() {
        return isRru;
    }

    public void setIsRru(String isRru) {
        this.isRru = isRru;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName;
    }

    public Integer getBtsId() {
        return btsId;
    }

    public void setBtsId(Integer btsId) {
        this.btsId = btsId;
    }

    public String getCircuitroomOwnership() {
        return circuitroomOwnership;
    }

    public void setCircuitroomOwnership(String circuitroomOwnership) {
        this.circuitroomOwnership = circuitroomOwnership;
    }

    public String getTransOwnership() {
        return transOwnership;
    }

    public void setTransOwnership(String transOwnership) {
        this.transOwnership = transOwnership;
    }

    public String getVendorBtstype() {
        return vendorBtstype;
    }

    public void setVendorBtstype(String vendorBtstype) {
        this.vendorBtstype = vendorBtstype;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getRelatedBts() {
        return relatedBts;
    }

    public void setRelatedBts(Long relatedBts) {
        this.relatedBts = relatedBts;
    }

    public Integer getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getDeleteResoncode() {
        return deleteResoncode;
    }

    public void setDeleteResoncode(Integer deleteResoncode) {
        this.deleteResoncode = deleteResoncode;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(String deleteText) {
        this.deleteText = deleteText;
    }

    public String getHightrainFlag() {
        return hightrainFlag;
    }

    public void setHightrainFlag(String hightrainFlag) {
        this.hightrainFlag = hightrainFlag;
    }

    public Integer getRedlineFlag() {
        return redlineFlag;
    }

    public void setRedlineFlag(Integer redlineFlag) {
        this.redlineFlag = redlineFlag;
    }

    public String getUpdateTimeStr() {
        return updatetime==null?"": DateUtils.DateToStr(updatetime, "yyyy-MM-dd");
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCountry() {
        return country;
    }

    public void setCountry(City country) {
        this.country = country;
    }

    public WyTunelManual getWyTunelManual() {
        return wyTunelManual;
    }

    public void setWyTunelManual(WyTunelManual wyTunelManual) {
        this.wyTunelManual = wyTunelManual;
    }

    public Cons getReasonCons() {
        return reasonCons;
    }

    public void setReasonCons(Cons reasonCons) {
        this.reasonCons = reasonCons;
    }

    public String getDeleteTimeStr() {
        return (deletetime==null)?"":DateUtils.DateToStr(deletetime, "yyyy-MM-dd");
    }

    public void setDeleteTimeStr(String deleteTimeStr) {
        this.deleteTimeStr = deleteTimeStr;
    }
}