package com.scttsc.healthy.job.alarm;

import com.scttsc.healthy.model.AlarmCounter;

import java.util.Map;

/**
 * User: Administrator
 * Date: 13-9-6
 * Time: 下午12:29
 */
public class BtsAlarm {
    private String intId;//基站int_id
    private Map<String, AlarmCounter> btsAlarmMap;

    public BtsAlarm(String intId, Map<String, AlarmCounter> btsAlarmMap) {
        this.intId = intId;
        this.btsAlarmMap = btsAlarmMap;
    }

    /**
     * 统计告警码告警数
     *
     * @param alarmCode
     */

    public void count(String alarmCode) {
        AlarmCounter counter = btsAlarmMap.get(alarmCode);
        if (counter == null) {
            counter = new AlarmCounter(intId, alarmCode, 1);
            btsAlarmMap.put(intId, counter);
        } else {
            counter.count();
        }

    }

    /**
     * 获取计数器信息
     * @param alarmCode
     * @return
     */
    public AlarmCounter getBtsAlarm(String alarmCode) {
        AlarmCounter alarmCounter = null;
        if (btsAlarmMap != null) {
            alarmCounter=btsAlarmMap.get(alarmCode);
        }
        return alarmCounter;
    }


    public String getIntId() {
        return intId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

    public Map<String, AlarmCounter> getBtsAlarmMap() {
        return btsAlarmMap;
    }

    public void setBtsAlarmMap(Map<String, AlarmCounter> btsAlarmMap) {
        this.btsAlarmMap = btsAlarmMap;
    }


}
