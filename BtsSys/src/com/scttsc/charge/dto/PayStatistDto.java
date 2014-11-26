package com.scttsc.charge.dto;

/**
 * Created by _think on 2014/11/26.
 */
public class PayStatistDto {
    private String cityName;
    private String countryName;
    private Integer costType;
    private Double money;
    private String costTypeStr;

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

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }


    public void setCostTypeStr(String costTypeStr) {
        this.costTypeStr = costTypeStr;
    }

    public String getCostTypeStr() {
        if (costType == null) {
            return "";
        }
        switch (costType) {
            case 1:
                costTypeStr = "房租";
                break;
            case 2:
                costTypeStr = "物业";
                break;
            case 3:
                costTypeStr = "电费";
                break;
            default:
                break;
        }
        return costTypeStr;
    }
}
