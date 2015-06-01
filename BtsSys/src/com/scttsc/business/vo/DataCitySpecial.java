package com.scttsc.business.vo;

/**
 * Created by _think on 2015/5/28.
 */
public class DataCitySpecial {
    private Long cityId;
    private String cityName;
    private int noAcceptBtsCount;
    private int testBtsCount;
    private int upgradeBtsCount;
    private int noAcceptCellCount;
    private int testCellCount;
    private int upgradeCellCount;

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

    public int getNoAcceptBtsCount() {
        return noAcceptBtsCount;
    }

    public void setNoAcceptBtsCount(int noAcceptBtsCount) {
        this.noAcceptBtsCount = noAcceptBtsCount;
    }

    public int getTestBtsCount() {
        return testBtsCount;
    }

    public void setTestBtsCount(int testBtsCount) {
        this.testBtsCount = testBtsCount;
    }

    public int getUpgradeBtsCount() {
        return upgradeBtsCount;
    }

    public void setUpgradeBtsCount(int upgradeBtsCount) {
        this.upgradeBtsCount = upgradeBtsCount;
    }

    public int getNoAcceptCellCount() {
        return noAcceptCellCount;
    }

    public void setNoAcceptCellCount(int noAcceptCellCount) {
        this.noAcceptCellCount = noAcceptCellCount;
    }

    public int getTestCellCount() {
        return testCellCount;
    }

    public void setTestCellCount(int testCellCount) {
        this.testCellCount = testCellCount;
    }

    public int getUpgradeCellCount() {
        return upgradeCellCount;
    }

    public void setUpgradeCellCount(int upgradeCellCount) {
        this.upgradeCellCount = upgradeCellCount;
    }
}
