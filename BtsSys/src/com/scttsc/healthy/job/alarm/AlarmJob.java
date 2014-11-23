package com.scttsc.healthy.job.alarm;

import com.scttsc.common.util.StringUtil;
import com.scttsc.healthy.job.inspect.InspectJob;
import com.scttsc.healthy.model.AlarmCounter;
import com.scttsc.healthy.model.WyBtsAlarm;
import com.scttsc.healthy.model.WyDbcon;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.service.JobManager;
import com.scttsc.healthy.service.RuleManager;
import com.scttsc.healthy.util.DbConn;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * User: Administrator
 * Date: 13-9-3
 * Time: 下午2:51
 */
public class AlarmJob {
    static Logger logger = Logger.getLogger(InspectJob.class);
    @Autowired
    private JobManager jobManager;
    @Autowired
    private RuleManager ruleManager;

    /**
     * 告警评分
     */
    public void gradeByAlarm() {
        try {
            //获取所有告警的sqlserver连接
            logger.debug("++++开始统计基站告警。");
            statAlarm();
            //每个数据连接都统计完成，开始收割统计结果
            finishAlarm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计所有bsc当前告警和历史告警到基站
     */
    private void statAlarm() {
        logger.info("++++++开始统计基站告警.....");
        try {
            List<WyDbcon> wyDbcons = ruleManager.selectAll();
            if (wyDbcons != null) {
                for (WyDbcon dbCon : wyDbcons) {
                    InitInstance.getInstance().putDbConMap(dbCon);
                }
            }
            int dbSize = (wyDbcons == null) ? 0 : wyDbcons.size();
            logger.info("++++初始化告警数据库连接," + dbSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, DbConn> dbConMap = InitInstance.getInstance().getDbConMap();
        for (DbConn dbCon : dbConMap.values()) {
            try {
                int type = dbCon.getType();
                if (type == 1) {
                    //统计当前告警
                    List<Map> datas = jobManager.getNowAlarm(dbCon);
                    int size = (datas == null) ? 0 : datas.size();
                    logger.debug("++++统计当前告警数量:" + size);
                    if (datas != null) {
                        for (Map data : datas) {
                            jobManager.statByNowAlarm(data);
                        }
                    }
                } else {
                    //各个bsc的历史告警
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH) + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("数据连接异常，" + dbCon.getUrl() + "======" + e.getMessage());
            }
        }
        InitInstance.getInstance().clearDbMap();
    }

    /**
     * 告警评分
     */
    private void finishAlarm() {
        try {
            logger.info("+++++++++告警开始评分....");
            Map<String, BtsAlarm> alarmMap = AlarmStat.alarmMap;
            List<WyBtsAlarm> btsAlarmList = new ArrayList<WyBtsAlarm>();
            Map map = new HashMap();
            map.put("gradetype", 3);
            map.put("status", "1,3");
            List<WyRulecfg> ruleCfgs = ruleManager.selectRuleCfgByMap(map);//定义的巡检规则
            for (BtsAlarm btsAlarm : alarmMap.values()) {
                jobManager.gradeByAlarm(btsAlarm, ruleCfgs);
                Map<String, AlarmCounter> btsAlarmMap = btsAlarm.getBtsAlarmMap();
                for (AlarmCounter alarmCounter : btsAlarmMap.values()) {
                    WyBtsAlarm wyBtsAlarm = new WyBtsAlarm();
                    wyBtsAlarm.setIntId(StringUtil.null2Long0(alarmCounter.getIntId()));
                    wyBtsAlarm.setAlarmCode(StringUtil.null2Integer0(alarmCounter.getAlarmCode()));
                    wyBtsAlarm.setAlarmNum(alarmCounter.getAlarmCount());
                    wyBtsAlarm.setUpdatetime(new Date());
                    btsAlarmList.add(wyBtsAlarm);
                }
            }

            ruleManager.insertBtsAlarm(btsAlarmList);

            AlarmStat.finish();
            logger.info("+++++++++告警评分结束.....");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("+++++++++告警评分异常.....");
        }
    }

    public int[] getThreeMonth(int month) {
        int[] monthArr = new int[3];
        switch (month) {
            case 1:
                monthArr[0] = 10;
                monthArr[1] = 11;
                monthArr[2] = 12;
                break;
            case 2:
                monthArr[0] = 11;
                monthArr[1] = 12;
                monthArr[2] = 1;
                break;
            case 3:
                monthArr[0] = 12;
                monthArr[1] = 1;
                monthArr[2] = 2;
                break;
            default:
                for (int i = 0; i < 3; i++) {
                    monthArr[i] = month + 1 - i;
                }
                break;

        }
        return monthArr;
    }

}
