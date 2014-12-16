package com.scttsc.baselibs.model;

import com.scttsc.admin.model.City;

import java.math.BigDecimal;

public class WyLibVillage {
    private Long id;

    private BigDecimal cityId;

    private BigDecimal countryId;

    private String town;

    private String village;

    private String villageName;//村名

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String cityName;

    private String countryName;

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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village == null ? null : village.trim();
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

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
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