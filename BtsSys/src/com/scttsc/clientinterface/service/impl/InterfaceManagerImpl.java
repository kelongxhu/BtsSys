package com.scttsc.clientinterface.service.impl;

import com.scttsc.business.dao.CBtsDao;
import com.scttsc.clientinterface.model.Alarm;
import com.scttsc.clientinterface.service.InterfaceManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.StringUtil;
import com.scttsc.healthy.util.DbConn;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-1-20
 * Time: 下午5:08
 */
@Service("interfaceManager")
public class InterfaceManagerImpl implements InterfaceManager {
    static Logger logger = Logger.getLogger(InterfaceManagerImpl.class);
    @Autowired
    private CBtsDao cBtsDao;

    /**
     * 获取该告警码的告警数据
     *
     * @param dbConn
     * @param alarmCode
     * @return
     * @throws Exception
     */
    public List<Map> selectAlarmByCode(String alarmCode) {
        String url = "jdbc:microsoft:sqlserver://10.231.200.97:1433;DatabaseName=UEP4X_CAF_FM";
        String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        String uid = "sa";
        String pwd = "ZXcdma2000";
        List<Map> list = new ArrayList<Map>();
        logger.info("++++获取告警数据...");
        DbConn dbConn = new DbConn(url, driver, uid, pwd);
        try {
            Connection con = dbConn.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT alarmsource,code,CONVERT(varchar(100), alarmraisedtime, 20) alarmraisedtime FROM [UEP4X_CAF_FM].[dbo].[CURRENTALARM] where moc='cdma.bts' and code='" + alarmCode + "'");
            PreparedStatement pstmt = con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map map = new HashMap();
                map.put("alarmsource", rs.getString("alarmsource"));
                map.put("code", rs.getString("code"));
                map.put("alarmraisedtime", rs.getString("alarmraisedtime"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取告警数据异常++++" + dbConn.getUrl() + "异常信息:" + e.getMessage());
            return null;
        } finally {
            dbConn.closeConnection();
        }
        logger.info("获取告警数据成功," + list.size());
        return list;
    }

    /**
     * 将告警数据和基站数据关联
     *
     * @return
     * @throws Exception
     */
    public List<Alarm> getAlarms(String cityName) throws Exception {
        List<Alarm> alarms = new ArrayList();
//        try {
            List<Map> alarmData = selectAlarmByCode("133157");
            if (alarmData != null) {
                for (Map map : alarmData) {
                    String alarmsource = StringUtil.null2String(map.get("alarmsource"));
                    String bscId;
                    String btsId;
                    if (!Common.isEmpty(alarmsource)) {
                        String[] sourceArr = alarmsource.split("-");
                        String[] bscArr = sourceArr[0].split("=");
                        bscId = bscArr[1];
                        String[] btsArr = sourceArr[2].split("=");
                        btsId = btsArr[1];
                        Map record = new HashMap();
                        record.put("bscId", bscId);
                        record.put("btsId", btsId);
                        if (!Common.isEmpty(cityName)) {
                            record.put("cityName", cityName);
                        }
                        List<Map> btsData = cBtsDao.selectBtsByConds(record);
                        if (btsData != null && btsData.size() > 0) {
                            Map data = btsData.get(0);
                            Alarm alarm = new Alarm();
                            alarm.setAlarmCode("133157");
                            alarm.setAlarmName("BTS掉站");
                            alarm.setBtsName(StringUtil.null2String(data.get("NAME")));
                            alarm.setAlarmLevel("严重");
                            alarm.setBtsGrade(StringUtil.null2String(data.get("GRADE")));
                            alarm.setBscId(StringUtil.null2String(data.get("BSCID")));
                            alarm.setBtsId(StringUtil.null2String(data.get("BTSID")));
                            alarm.setBscName(StringUtil.null2String(data.get("BSC_NAME")));
                            alarm.setCityName(StringUtil.null2String(data.get("CITYNAME")));
                            alarm.setAlarmTime(StringUtil.null2String(map.get("alarmraisedtime")));
                            alarms.add(alarm);
                        }
                    }
                }
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("告警数据和基站数据关联异常," + e.getMessage());
//            return null;
//        }
        return alarms;
    }
}
