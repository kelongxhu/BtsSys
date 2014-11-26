package com.scttsc.charge.job;

import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.service.WyBtsChargeManager;
import com.scttsc.common.util.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 发送短信定时Job
 * Created by _think on 2014/11/23.
 */
@Service("smsJob")
public class SmsJob {
    static Logger LOG = Logger.getLogger("jobLog");
    @Autowired
    private WyBtsChargeManager wyBtsChargeManager;

    /**
     * 短信提醒用戶缴费
     */
    public void sendMsg() {
        try {
            LOG.info("job Start==============");
            Map<String, Object> btsParam = new HashMap<String, Object>();
            //室外
            List<WyBtsCharge> btsChargeList = wyBtsChargeManager.selectWyBtsChargeListByMap(btsParam, 1);
            if (btsChargeList != null) {
                for (WyBtsCharge wyBtsCharge : btsChargeList) {
                    checkBts(wyBtsCharge);
                }
            }
            LOG.info("分析室外基站费用数据:" + (btsChargeList == null ? 0 : btsChargeList.size()));
            //纯bbu
            List<WyBtsCharge> bbuChargeList = wyBtsChargeManager.selectWyBtsChargeListByMap(btsParam, 2);
            if (bbuChargeList != null) {
                for (WyBtsCharge wyBtsCharge : bbuChargeList) {
                    checkBts(wyBtsCharge);
                }
            }
            LOG.info("分析纯Bbu费用数据:" + (bbuChargeList == null ? 0 : bbuChargeList.size()));
            //室外
            List<WyBtsCharge> indoorChargeList = wyBtsChargeManager.selectWyBtsChargeListByMap(btsParam, 3);
            if (indoorChargeList != null) {
                for (WyBtsCharge wyBtsCharge : indoorChargeList) {
                    checkBts(wyBtsCharge);
                }
            }
            LOG.info("分析室内覆盖费用数据:" + (indoorChargeList == null ? 0 : indoorChargeList.size()));
            //隧道
            List<WyBtsCharge> tunelChargeList = wyBtsChargeManager.selectWyBtsChargeListByMap(btsParam, 6);
            if (tunelChargeList != null) {
                for (WyBtsCharge wyBtsCharge : tunelChargeList) {
                    checkBts(wyBtsCharge);
                }
            }
            LOG.info("分析隧道覆盖数据:" + (tunelChargeList == null ? 0 : tunelChargeList.size()));
            LOG.info("job End=======");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 更新每个基站的下次缴费时间、计算提醒时间、符合提醒条件发短信提醒
     *
     * @param wyBtsCharge
     */
    public void checkBts(WyBtsCharge wyBtsCharge) {
        try {
            Date lastPayTime = wyBtsCharge.getLastPayTime();//基站设置上次缴费时间
            Date nextPayTime = wyBtsCharge.getNextPayTime();//基站设置的下次缴费时间
            Date now = new Date();//当前时间
            int payCycle = wyBtsCharge.getPayCycle();//缴费周期
            int payDay = wyBtsCharge.getPayDay();//缴费天数
            int aheadDay = wyBtsCharge.getAheadDay();//提前多少天提醒
            if (lastPayTime == null) {
                lastPayTime = wyBtsCharge.getInTime();
            }
            if(lastPayTime==null){
                return;
            }
            Date curNextPayTime = getNextPayTime(lastPayTime, payCycle, payDay);//当前计算下次缴费时间（有更改周期的情况）
            //是否更新下次提醒时间,更改周期情况。
            boolean nextPayFlag = false;
            if (nextPayTime != null) {
                int betweenDay = DateUtils.daysBetween(nextPayTime, curNextPayTime);
                if (betweenDay != 0) {
                    nextPayFlag = true;
                }
            } else {
                nextPayFlag = true;
            }
            int betweenNowAndNextPayDay = DateUtils.daysBetween(now, curNextPayTime);
            Map<String, Object> paramMap = null;
            Date newNextPayTime = null;
            if (betweenNowAndNextPayDay < 0) {
                //下次缴费时间超过现在
                //更新上次缴费时间和下次缴费时间
                Date newLastPayTime = curNextPayTime;
                newNextPayTime = getNextPayTime(newLastPayTime, payCycle, payDay);
                paramMap = new HashMap<String, Object>();
                paramMap.put("intId", wyBtsCharge.getIntId());
                paramMap.put("costType", wyBtsCharge.getCostType());
                paramMap.put("lastPayTime", newLastPayTime);
                paramMap.put("nextPayTime", newLastPayTime);
                paramMap.put("isRemind", 0);//未提醒
                LOG.info("更新基站下次缴费时间、上次缴费时间====>." + wyBtsCharge.getIntId() + "_" + wyBtsCharge.getCostType());
            } else {
                //是否更新下次缴费时间
                newNextPayTime = curNextPayTime;
                if (nextPayFlag) {
                    paramMap = new HashMap<String, Object>();
                    paramMap.put("intId", wyBtsCharge.getIntId());
                    paramMap.put("costType", wyBtsCharge.getCostType());
                    paramMap.put("nextPayTime", curNextPayTime);
                    LOG.info("更新基站下次缴费时间===>" + wyBtsCharge.getIntId() + "_" + wyBtsCharge.getCostType());
                }
            }
            wyBtsCharge.setNextPayTime(curNextPayTime);
            if (paramMap != null) {
                wyBtsChargeManager.updateByMap(paramMap);
            }
            isSend(newNextPayTime, now, wyBtsCharge);//检测是否发送短信
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 发送短信
     *
     * @param newNextPayTime
     * @param now
     * @param wyBtsCharge
     */
    public void isSend(Date newNextPayTime, Date now, WyBtsCharge wyBtsCharge) {
        try {
            int aheadDay = wyBtsCharge.getAheadDay();
            int isRemind = wyBtsCharge.getIsRemind();
            Date aheadTime = getRemindTime(newNextPayTime, aheadDay);//提醒时间
            int betweenRemindDay = DateUtils.daysBetween(aheadTime, now);
            //当前时间>=提醒时间，发送提醒短信
            if (betweenRemindDay >= 0 && isRemind == 0) {
                wyBtsChargeManager.sendMsg(wyBtsCharge);
                LOG.info("任务发送提醒短信===>" + wyBtsCharge.getIntId());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 根据上次缴费月份
     *
     * @param lastPayTime
     * @param payCycle
     * @param payDay
     * @return
     */
    public Date getNextPayTime(Date lastPayTime, int payCycle, int payDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastPayTime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + payCycle);
        calendar.set(Calendar.DAY_OF_MONTH, payDay);
        return calendar.getTime();
    }

    /**
     * 根据下次缴费时间获取提醒时间
     *
     * @param nextPayTime
     * @param aheadDay
     * @return
     */
    public Date getRemindTime(Date nextPayTime, int aheadDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextPayTime);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - aheadDay);
        return calendar.getTime();
    }
}
