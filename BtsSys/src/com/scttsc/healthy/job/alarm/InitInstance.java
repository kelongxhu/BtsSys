package com.scttsc.healthy.job.alarm;

import com.scttsc.healthy.model.WyDbcon;
import com.scttsc.healthy.util.DbConn;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-9-6
 * Time: 下午2:38
 */
public class InitInstance {
    private static InitInstance initInstance = null;
    //存储告警数据库连接信息map
    public Map<String, DbConn> dbConMap = new HashMap<String, DbConn>();
    //bscId和bscName的匹配
    public Map<String, String> bscMap = new HashMap<String, String>();

    private InitInstance() {
    }

    public synchronized static InitInstance getInstance() {
        if (initInstance == null) {
            initInstance = new InitInstance();
        }
        return initInstance;
    }

    public String getBsc(String bscId) {
        return bscMap.get(bscId);
    }

    public Map<String, DbConn> getDbConMap() {
        return dbConMap;
    }

    public void putDbConMap(WyDbcon wyDbcon) {
        DbConn dbConn = new DbConn(wyDbcon.getDriver(), wyDbcon.getUrl(), wyDbcon.getUsername(), wyDbcon.getPwd(), wyDbcon.getType());
        dbConMap.put(wyDbcon.getName(), dbConn);
    }

    public void clearDbMap(){
        dbConMap.clear();
    }
}
