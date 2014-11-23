package com.scttsc.healthy.model;

/**
 * User: Administrator
 * Date: 13-9-6
 * Time: 下午1:32
 */
public class AlarmCounter {
    String intId;//基站intId
    private String alarmCode; //告警条码
    private int alarmCount;//告警数


    public AlarmCounter(String intId, String alarmCode, int alarmCount) {
        this.intId = intId;
        this.alarmCode = alarmCode;
        this.alarmCount = alarmCount;
    }

    /**
     * 计数统计
     */
    public void count() {
        alarmCount++;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getIntId() {
        return intId;
    }
}
