package com.scttsc.baselibs.model;

import com.scttsc.admin.model.City;

import java.math.BigDecimal;
import java.util.Date;

public class WyLibScene {
    private Long id;

    private BigDecimal cityId;

    private BigDecimal countryId;

    private BigDecimal sceneType;

    private BigDecimal sceneLevel;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String remark;

    private Date inTime;

    private Long inUser;

    private Date updateTime;

    private Integer deleteFlag;

    private String sceneTypeName;
    private String sceneLevelName;

    private City city;
    private City country;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getCountryId() {
        return countryId;
    }

    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
    }

    public BigDecimal getSceneType() {
        return sceneType;
    }

    public void setSceneType(BigDecimal sceneType) {
        this.sceneType = sceneType;
    }

    public BigDecimal getSceneLevel() {
        return sceneLevel;
    }

    public void setSceneLevel(BigDecimal sceneLevel) {
        this.sceneLevel = sceneLevel;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Long getInUser() {
        return inUser;
    }

    public void setInUser(Long inUser) {
        this.inUser = inUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public String getSceneTypeName() {
        return sceneTypeName;
    }

    public void setSceneTypeName(String sceneTypeName) {
        this.sceneTypeName = sceneTypeName;
    }

    public String getSceneLevelName() {
        return sceneLevelName;
    }

    public void setSceneLevelName(String sceneLevelName) {
        this.sceneLevelName = sceneLevelName;
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
}