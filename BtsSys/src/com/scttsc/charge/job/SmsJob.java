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
            Map<String, Object> btsParam = new HashMap<String, Object>();
            List<WyBtsCharge> btsChargeList = wyBtsChargeManager.selectWyBtsChargeListByMap(btsParam, 1);
            for (WyBtsCharge wyBtsCharge : btsChargeList) {
                checkBts(wyBtsCharge);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


    public void checkBts(WyBtsCharge wyBtsCharge) {
        try {
            Date lastPayTime = wyBtsCharge.getLastPayTime();//基站设置上次缴费时间
            Date nextPayTime = wyBtsCharge.getNextPayTime();//基站设置的下次缴费时间
            Date now = new Date();//当前时间
            int payCycle = wyBtsCharge.getPayCycle();//缴费周期
            int payDay = wyBtsCharge.getPayDay();//缴费天数
            int aheadDay = wyBtsCharge.getAheadDay();//提前多少天提醒
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
            if (paramMap != null) {
                wyBtsChargeManager.updateByMap(paramMap);
            }
            isSend(newNextPayTime, now, wyBtsCharge);//检测是否发送短信
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


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
