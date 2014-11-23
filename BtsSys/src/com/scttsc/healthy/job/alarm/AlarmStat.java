package com.scttsc.healthy.job.alarm;

import com.scttsc.healthy.model.AlarmCounter;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-9-6
 * Time: 下午12:27
 */
public class AlarmStat {
    public static Map<String, BtsAlarm> alarmMap = new HashMap<String, BtsAlarm>();

    /**
     * 统计告警
     *
     * @param intId
     * @param alarmCode
     */
    public static void stat(String intId, String alarmCode) {
        BtsAlarm btsAlarm = alarmMap.get(intId);
        if (btsAlarm == null) {
            btsAlarm = new BtsAlarm(intId, new HashMap<String, AlarmCounter>());
            btsAlarm.count(alarmCode);
            alarmMap.put(intId, btsAlarm);
        } else {
            btsAlarm.count(alarmCode);
        }
    }

    /**
     * 收割告警
     */
    public static void finish() {
        alarmMap.clear();//清除统计结果
    }
}
