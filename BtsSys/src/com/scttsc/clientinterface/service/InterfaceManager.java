package com.scttsc.clientinterface.service;

import com.scttsc.clientinterface.model.Alarm;
import com.scttsc.healthy.util.DbConn;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午5:08
 */
public interface InterfaceManager {
    /**
     * 查询地市告警
     * @param cityName
     * @return
     * @throws Exception
     */
    public List<Alarm> getAlarms(String cityName)throws Exception;
}
