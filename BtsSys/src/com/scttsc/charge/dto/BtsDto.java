package com.scttsc.charge.dto;

import java.math.BigDecimal;

/**
 * Created by _think on 2014/11/19.
 */
public class BtsDto {
    private BigDecimal intId;//基站INT_ID
    private String name;
    private Integer cityId;
    private Integer countryId;
    private String bscName;
    private Integer btsId;
    private Integer btsType;//基站类型


    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public Integer getBtsType() {
        return btsType;
    }

    public void setBtsType(Integer btsType) {
        this.btsType = btsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
