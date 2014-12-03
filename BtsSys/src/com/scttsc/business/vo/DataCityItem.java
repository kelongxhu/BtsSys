package com.scttsc.business.vo;

/**
 * Created by _think on 2014/12/2.
 */
public class DataCityItem {
    private Long cityId;
    private String cityName;
    private int outBtsCount;
    private int bbuCount;
    private int indoorBtsCount;
    private int tunelCount;
    private int outCellCount;
    private int indoorCellCount;
    private int tunelCellCount;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getOutBtsCount() {
        return outBtsCount;
    }

    public void setOutBtsCount(int outBtsCount) {
        this.outBtsCount = outBtsCount;
    }

    public int getBbuCount() {
        return bbuCount;
    }

    public void setBbuCount(int bbuCount) {
        this.bbuCount = bbuCount;
    }

    public int getIndoorBtsCount() {
        return indoorBtsCount;
    }

    public void setIndoorBtsCount(int indoorBtsCount) {
        this.indoorBtsCount = indoorBtsCount;
    }

    public int getTunelCount() {
        return tunelCount;
    }

    public void setTunelCount(int tunelCount) {
        this.tunelCount = tunelCount;
    }

    public int getOutCellCount() {
        return outCellCount;
    }

    public void setOutCellCount(int outCellCount) {
        this.outCellCount = outCellCount;
    }

    public int getIndoorCellCount() {
        return indoorCellCount;
    }

    public void setIndoorCellCount(int indoorCellCount) {
        this.indoorCellCount = indoorCellCount;
    }

    public int getTunelCellCount() {
        return tunelCellCount;
    }

    public void setTunelCellCount(int tunelCellCount) {
        this.tunelCellCount = tunelCellCount;
    }
}
