package com.scttsc.business.vo;

/**
 * Created by _think on 2014/12/3.
 */
public class DataCityItemNoManual {
    private Long cityId;
    private String cityName;
    private int outBtsCount;
    private int bbuCount;
    private int outCellCount;
    private int indoorCellCount;
    private int tunelCellCount;
    private int wrongNameCount;


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

    public int getWrongNameCount() {
        return wrongNameCount;
    }

    public void setWrongNameCount(int wrongNameCount) {
        this.wrongNameCount = wrongNameCount;
    }
}
