package com.scttsc.gis.model;

import java.math.BigDecimal;

/**
 * User: pengzhi
 * Date: 13-10-25
 * Time: 下午4:05
 * Email: wokzhen@vip.qq.com
 */
public class LatLng {

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    public LatLng(){}

    public LatLng(String name, BigDecimal latitude, BigDecimal longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
