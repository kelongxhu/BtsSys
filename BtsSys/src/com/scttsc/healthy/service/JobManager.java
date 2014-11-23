package com.scttsc.healthy.service;

import com.scttsc.healthy.job.alarm.BtsAlarm;
import com.scttsc.healthy.model.GzBtsCheck;
import com.scttsc.healthy.model.WyBtsAlarm;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.util.DbConn;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-8-30
 * Time: 下午4:34
 */
public interface JobManager {
    boolean inspectCheck(GzBtsCheck check, WyRulecfg ruleCfg); //巡检

    void gradeByInspectCheck(GzBtsCheck check, List<WyRulecfg> ruleCfgs);//巡检

    boolean infoCheck(Map map, WyRulecfg ruleCfg);//基础信息

    void gradeByInfoCheck(Map map, List<WyRulecfg> ruleCfgs);//基础信息

    void statByNowAlarm(Map record);//统计当前告警到物理站点

    void statByHisAlarm(Map map);//统计历史告警到物理站点

    List<Map> getNowAlarm(DbConn dbConn);//获取当前告警数据

    List<Map> getHisAlarm(DbConn dbConn,String tableName);//获取历史告警数据

    boolean alarmCheck(BtsAlarm btsAlarm,WyRulecfg ruleCfg);//告警信息

    void gradeByAlarm(BtsAlarm btsAlarm, List<WyRulecfg> ruleCfgs);//告警信息评分
}
