package com.scttsc.business.service;

import com.scttsc.business.model.WyLog;

/**日志
 * User: Administrator
 * Date: 13-7-31
 * Time: 下午3:24
 */
public interface WyLogManager {
    /**
     * 插入日誌
     * @param record
     * @return
     */
    int insert(WyLog record);

    /**
     * 更新日志
     * @param obj
     * @throws Exception
     */
    public void updateLog(Object obj) throws Exception ;

    /**
     * 插入日志
     * @param object
     * @throws Exception
     */
    public void insertLog(Object object)throws Exception;
}
