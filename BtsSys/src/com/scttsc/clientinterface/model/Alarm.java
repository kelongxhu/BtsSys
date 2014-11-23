package com.scttsc.clientinterface.model;

import java.util.Date;

/**
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午3:59
 */
public class Alarm {
    private String alarmName;
    private String alarmCode;//告警代码
    private String alarmLevel;//告警级别
    private String btsName;
    private String btsGrade;
    private String cityName;
    private String alarmTime;
    private String bscId;
    private String btsId;
    private String bscName;


    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getBtsGrade() {
        return btsGrade;
    }

    public void setBtsGrade(String btsGrade) {
        this.btsGrade = btsGrade;
    }

    public String getBscId() {
        return bscId;
    }

    public void setBscId(String bscId) {
        this.bscId = bscId;
    }

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName;
    }
}
