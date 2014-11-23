package com.scttsc.business.service.impl;

import com.scttsc.business.dao.*;
import com.scttsc.business.model.*;
import com.scttsc.business.service.WyLogManager;
import com.scttsc.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: Administrator
 * Date: 13-7-31
 * Time: 下午3:25
 */
@Service("wyLogManager")
@Transactional(readOnly = true)
public class WyLogManagerImpl implements WyLogManager {
    @Autowired
    private WyLogDao wyLogDao;
    @Autowired
    private BtsManualDao btsManualDao;
    @Autowired
    private BbuManualDao bbuManualDao;
    @Autowired
    private IndoorManualDao indoorManualDao;
    @Autowired
    private CellManualDao cellManualDao;

    public int insert(WyLog record) {
        return wyLogDao.insert(record);
    }

    /**
     * 记录更新日志
     *
     * @param obj
     * @throws Exception
     */
    public void updateLog(Object obj) throws Exception {
        String content = "";
        Long userId = null;
        String logfun = "";
        String logobj = "";
        if (obj instanceof BtsManual) {
            //更新物理站点手工数据
            BtsManual newObj = (BtsManual) obj;
            BtsManual oldObj = btsManualDao.getById(newObj.getIntId());
            if (oldObj != null) {
                content = oldObj.compareEntity(newObj);
            }
            userId = newObj.getUpdateuser();
            logobj = Constants.LOG_OBJ_1;
        } else if (obj instanceof BbuManual) {
            BbuManual newObj = (BbuManual) obj;
            BbuManual oldObj = bbuManualDao.getById(newObj.getIntId());
            if (oldObj != null) {
                content = oldObj.compareEntity(newObj);
            }
            userId = newObj.getUpdateuser();
            logobj = Constants.LOG_OBJ_2;
        } else if (obj instanceof IndoorManual) {
            IndoorManual newObj = (IndoorManual) obj;
            IndoorManual oldObj = indoorManualDao.selectByPrimaryKey(newObj.getIntId());
            if (oldObj != null) {
                content = oldObj.compareEntity(newObj);
            }
            userId = newObj.getUpdateuser();
            logobj = Constants.LOG_OBJ_3;
        } else if (obj instanceof CellManual) {
            CellManual newObj = (CellManual) obj;
            CellManual oldObj = cellManualDao.selectById(Long.parseLong(newObj.getIntId()));
            if (oldObj != null) {
                content = oldObj.compareEntity(newObj);
            }
            userId = newObj.getUpdateuser();
            logobj = Constants.LOG_OBJ_4;
        }
        logfun = Constants.LOG_FUN_1;
        WyLog wyLog = new WyLog();
        wyLog.setLogfun(logfun);
        wyLog.setLogobj(logobj);
        wyLog.setLogcontent(content);
        wyLog.setLogtime(new Date());
        wyLog.setUserid(userId);
        insert(wyLog);//插入日志
    }

    /**
     * 插入日志c
     *
     * @param object
     * @throws Exception
     */
    public void insertLog(Object obj) throws Exception {
        String content = "";
        Long userId = null;
        String logfun = "";
        String logobj = "";
        if (obj instanceof BtsManual) {
            BtsManual btsManual = (BtsManual) obj;
            userId = btsManual.getUpdateuser();
            logobj = Constants.LOG_OBJ_1;
            content="["+btsManual.getName()+"]";
        } else if (obj instanceof BbuManual) {
            BbuManual bbuManual = (BbuManual) obj;
            userId = bbuManual.getUpdateuser();
            logobj = Constants.LOG_OBJ_2;
            content="["+bbuManual.getName()+"]";
        } else if (obj instanceof IndoorManual) {
            IndoorManual indoorManual = (IndoorManual) obj;
            userId = indoorManual.getUpdateuser();
            logobj = Constants.LOG_OBJ_3;
            content="["+indoorManual.getName()+"]";
        } else if (obj instanceof CellManual) {
            CellManual cellManual = (CellManual) obj;
            userId = cellManual.getUpdateuser();
            logobj = Constants.LOG_OBJ_4;
            content="["+cellManual.getName()+"]";
        }
        logfun = Constants.LOG_FUN_2;
        WyLog wyLog = new WyLog();
        wyLog.setLogfun(logfun);
        wyLog.setLogobj(logobj);
        wyLog.setLogcontent(content);
        wyLog.setLogtime(new Date());
        wyLog.setUserid(userId);
        insert(wyLog);//插入日志
    }
}
