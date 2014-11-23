package com.scttsc.business.service;

import com.scttsc.business.model.WyLog;

/**
 * User: Administrator
 * Date: 13-7-31
 * Time: 下午3:24
 */
public interface WyLogManager {
    int insert(WyLog record);
    public void updateLog(Object obj) throws Exception ;
    public void insertLog(Object object)throws Exception;
}
