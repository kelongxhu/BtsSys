package com.scttsc.healthy.service.impl;

import com.scttsc.business.model.Bts;
import com.scttsc.business.model.Cell;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.CellManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.StringUtil;
import com.scttsc.healthy.job.alarm.AlarmStat;
import com.scttsc.healthy.job.alarm.BtsAlarm;
import com.scttsc.healthy.job.alarm.InitInstance;
import com.scttsc.healthy.model.*;
import com.scttsc.healthy.service.GradeManager;
import com.scttsc.healthy.service.JobManager;
import com.scttsc.healthy.util.DbConn;
import com.scttsc.healthy.util.GradeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: Administrator
 * Date: 13-8-30
 * Time: 下午4:34
 */
@Service("jobManager")
@Transactional(readOnly = true)
public class JobManagerImpl implements JobManager {

    static Logger logger = Logger.getLogger(GradeManagerImpl.class);

    @Autowired
    GradeManager gradeManger;
    @Autowired
    CellManager cellManager;
    @Autowired
    BtsManager btsManager;

    /**
     * 给一条巡检数据对应基站打分
     *
     * @param check
     * @param ruleCfgs
     */
    public void gradeByInspectCheck(GzBtsCheck check, List<WyRulecfg> ruleCfgs) {
        List<WyGradeDetail> wyGradeDetailList = new ArrayList<WyGradeDetail>();
        try {
            double grade = 0;
            if (ruleCfgs != null) {
                for (WyRulecfg ruleCfg : ruleCfgs) {
                    boolean flag = inspectCheck(check, ruleCfg);
                    if (flag) {
                        double subGrade = GradeUtil.grade(ruleCfg, check.getGrade());
                        grade += subGrade;
                        WyGradeDetail wyGradeDetail = new WyGradeDetail();
                        wyGradeDetail.setGradecfgid(ruleCfg.getId());
                        wyGradeDetail.setGrade(subGrade);
                        wyGradeDetail.setIntId(check.getIntId());
                        wyGradeDetailList.add(wyGradeDetail);
                    }
                }
            }

            WyGrade wyGrade = gradeManger.selectByPrimaryKey(check.getIntId());
            if (wyGrade == null) {
                //插入
                wyGrade = new WyGrade();
                wyGrade.setIntId(check.getIntId());
                wyGrade.setInspGrade(grade);
                wyGrade.setUpdatetime(new Date());
                wyGrade.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.insert(wyGrade);
            } else {
                //更新
                WyGrade record = new WyGrade();
                record.setIntId(wyGrade.getIntId());
                record.setInspGrade(grade);
                record.setUpdatetime(new Date());
                record.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.updateByPrimaryKeySelective(record);
            }
        } catch (Exception e) {
            logger.equals("++++打分异常.....");
            e.printStackTrace();
        }
    }


    /**
     * @param check
     * @param ruleCfg
     * @return
     */

    public boolean inspectCheck(GzBtsCheck check, WyRulecfg ruleCfg) {
        Map<String, String> resultMap = parsingCheck(check);
        if (resultMap == null) {
            return false;
        }
        List<WyRuleterm> ruleTerms = ruleCfg.getWyRuletermList();
        if (ruleTerms != null && ruleTerms.size() > 0) {
            boolean flag = false;
            for (WyRuleterm rule : ruleTerms) {
                WySubcfg subCfg = rule.getWySubcfg();
                boolean result = false;
                if (subCfg != null) {
                    String key = subCfg.getCode1() + "_" + subCfg.getCode2();
                    //在数据中取出此规则定义的项的值
                    String value = resultMap.get(key);
                    if (value != null) {
                        //取出规则配置的A、B值
                        String value1 = rule.getValue1();
                        String value2 = rule.getValue2();
                        int dataType = rule.getDataType();
                        int symbol = rule.getSymbol();
                        result = GradeUtil.judge(dataType, symbol, value1, value2, value);
                    }
                }
                if (!result) {
                    //一项不符合，则此规则都不符合
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    private Map<String, String> parsingCheck(GzBtsCheck check) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String descption = check.getDescription();
        if (descption == null || "".equals(descption)) {
            return null;
        }
        String[] dp = descption.split("&");
        //大项*小项*照片*结果
        if (dp != null && dp.length > 0) {
            for (String subject : dp) {
                String[] sub = subject.split("\\*");
                if (sub.length >= 4) {
                    String key = sub[0] + "_" + sub[1];
                    resultMap.put(key, sub[3]);
                }
            }
        }
        return resultMap;
    }

    /**
     * 一个基站对应基础信息配置是否符合规则，
     *
     * @param map
     * @param ruleCfg
     * @return
     */
    public boolean infoCheck(Map map, WyRulecfg ruleCfg) {
        List<WyRuleterm> ruleTerms = ruleCfg.getWyRuletermList();
        if (ruleTerms != null && ruleTerms.size() > 0) {
            boolean flag = false;
            for (WyRuleterm rule : ruleTerms) {
                WySubcfg subCfg = rule.getWySubcfg();
                boolean result = false;
                if (subCfg != null) {
                    String column = subCfg.getColumnname();
                    String value = Common.null2String(map.get(column));
                    //取出规则配置的A、B值
                    String value1 = rule.getValue1();
                    String value2 = rule.getValue2();
                    int dataType = rule.getDataType();
                    int symbol = rule.getSymbol();
                    result = GradeUtil.judge(dataType, symbol, value1, value2, value);
                }
                if (!result) {
                    flag = true;//一项不符合，则规则不符合
                    break;
                }
            }
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 基础信息评分
     *
     * @param map
     * @param ruleCfgs
     */
    public void gradeByInfoCheck(Map map, List<WyRulecfg> ruleCfgs) {
        List<WyGradeDetail> wyGradeDetailList = new ArrayList<WyGradeDetail>();
        Long intId = StringUtil.null2Long0(map.get("INT_ID"));
        String serviceLevel = StringUtil.null2String(map.get("SERVICE_LEVEL"));
        try {
            double grade = 0;
            if (ruleCfgs != null) {
                for (WyRulecfg ruleCfg : ruleCfgs) {
                    boolean flag = infoCheck(map, ruleCfg);
                    if (flag) {
                        double subGrade = GradeUtil.grade(ruleCfg, serviceLevel);
                        grade += subGrade;
                        WyGradeDetail wyGradeDetail = new WyGradeDetail();
                        wyGradeDetail.setGradecfgid(ruleCfg.getId());
                        wyGradeDetail.setGrade(subGrade);
                        wyGradeDetail.setIntId(intId);
                        wyGradeDetailList.add(wyGradeDetail);
                    }
                }
            }

            WyGrade wyGrade = gradeManger.selectByPrimaryKey(intId);
            if (wyGrade == null) {
                //插入
                wyGrade = new WyGrade();
                wyGrade.setIntId(intId);
                wyGrade.setInfoGrade(grade);
                wyGrade.setUpdatetime(new Date());
                wyGrade.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.insert(wyGrade);
            } else {
                //更新
                WyGrade record = new WyGrade();
                record.setIntId(wyGrade.getIntId());
                record.setInfoGrade(grade);
                record.setUpdatetime(new Date());
                record.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.updateByPrimaryKeySelective(record);
            }
        } catch (Exception e) {
            logger.error("++++基础信息打分异常.....");
            e.printStackTrace();
        }
    }

    /**
     * 将告警数据统计到基站
     *
     * @param record
     */
    public void statByNowAlarm(Map map) {
        try {
            //格式cdma.omm=185202-bssb=0-bts=364
            String alarmsource = StringUtil.null2String(map.get("alarmsource"));
            //例：cdma.rack=3,cdma.shelf=2,cdma.board=7,cdma.sunit=1
            String subposition1 = StringUtil.null2String(map.get("subposition1"));
            String alarmCode = StringUtil.null2String(map.get("code"));
            String bscId = "";
            String btsId = "";
            int rack = 0;
            int cellId = -1;
            if (!Common.isEmpty(alarmsource)) {
                String[] asArr = alarmsource.split("-");
                String[] ommArr = asArr[0].split("=");
                bscId = ommArr[1];
                String[] btsArr = asArr[2].split("=");
                btsId = btsArr[1];
            }
            if (!Common.isEmpty(subposition1)) {
                String[] subArr = subposition1.split(",");
                String[] rackArr = subArr[0].split("=");
                rack = Common.null2Int(rackArr[1]);
            }
            if (rack == 1) {
                //非拉远基站，覆盖所有物理站点
                noLyAlarm(bscId, btsId, alarmCode);
            } else if (rack > 1) {
                //拉远基站,cellId=rack-2
                cellId = rack - 2;
                lyAlarm(bscId, btsId, cellId, alarmCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("统计当前告警异常++++" + e.getMessage());
        }
    }

    /**
     * 统计历史告警到基站
     *
     * @param map
     */
    public void statByHisAlarm(Map map) {
        try {
            // Server=185902,Bss=0,System=207,Rack=2,Shelf=1,Slot=1,SubUnit=1,
            String location = StringUtil.null2String(map.get("location"));
            String alarmCode = StringUtil.null2String(map.get("alarmCode"));
            String bscId = "";
            String btsId = "";
            int rack = 0;
            int cellId = -1;
            if (!Common.isEmpty(location)) {
                String[] locArr = location.split(",");
                if (locArr == null && locArr.length < 4) {
                    return;
                }
                String[] bscIdArr = locArr[0].split("=");
                bscId = bscIdArr[1];
                String[] btsIdArr = locArr[2].split("=");
                btsId = btsIdArr[1];
                String[] rackArr = locArr[3].split("=");
                rack = Common.null2Int(rackArr[1]);
                if (rack == 1) {
                    //非拉远基站，覆盖所有物理站点
                    noLyAlarm(bscId, btsId, alarmCode);
                } else if (rack > 1) {
                    //拉远基站,cellId=rack-2
                    cellId = rack - 2;
                    lyAlarm(bscId, btsId, cellId, alarmCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("统计历史告警数据异常++++" + e.getMessage());
            return;
        }
    }

    /**
     * 统计非拉远告警到物理站点
     *
     * @param bscId         ：bscId
     * @param btsId:btsId
     * @param alarmCode:告警码
     */
    private void noLyAlarm(String bscId, String btsId, String alarmCode) {
        String bscName = InitInstance.getInstance().getBsc(bscId);
        if (Common.isEmpty(bscName) || Common.isEmpty(btsId)) {
            return;
        }
        try {
            Map map = new HashMap();
            map.put("bscName", bscName);
            map.put("btsId", btsId);
            List<Map> btsList = btsManager.selectWyBtsByMap(map);
            if (btsList != null && btsList.size() > 0) {
                for (Map bts : btsList) {
                    String intId = StringUtil.null2String(bts.get("int_id"));
                    if (!Common.isEmpty(intId)) {
                        AlarmStat.stat(intId, alarmCode);//统计告警
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 统计拉远基站告警到物理站点
     *
     * @param bscId
     * @param btsId
     * @param cellId
     * @param alarmCode
     */
    private void lyAlarm(String bscId, String btsId, int cellId, String alarmCode) {
        String bscName = InitInstance.getInstance().getBsc(bscId);
        if (Common.isEmpty(bscName) || Common.isEmpty(btsId) || cellId != -1) {
            return;
        }
        Map map = new HashMap();
        map.put("bscName", bscName);
        map.put("btsId", btsId);
        map.put("cellId", cellId);
        try {
            List<Cell> cells = cellManager.selectCellByMap(map);
            if (cells != null && cells.size() > 0) {
                Cell cell = cells.get(0);
                Long wybtsId = cell.getWyBtsIntId();
                AlarmStat.stat(wybtsId + "", alarmCode);//统计告警
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前告警数据
     *
     * @param dbConn
     * @return
     */
    public List<Map> getNowAlarm(DbConn dbConn) {
        List<Map> list = new ArrayList<Map>();
        try {
            Connection con = dbConn.getConnection();
            String sql = "SELECT alarmsource,subposition1,code,alarmraisedtime,moc\n" +
                    "  FROM [UEP4X_CAF_FM].[dbo].[CURRENTALARM] where moc='cdma.bts' and datediff( month, alarmraisedtime, getdate())<=3";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map map = new HashMap();
                map.put("alarmsource", rs.getString("alarmsource"));
                map.put("subposition1", rs.getString("subposition1"));
                map.put("code", rs.getString("code"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取历史告警数据异常++++" + dbConn.getUrl() + "异常信息:" + e.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return list;
    }

    /**
     * 获取历史告警，注意大数据查询
     *
     * @param dbConn
     * @return
     */
    public List<Map> getHisAlarm(DbConn dbConn,String tableName) {
        List<Map> list = new ArrayList<Map>();
        try {
            Connection con = dbConn.getConnection();
            // history_alarm_9
            String sql = "select alarmCode,location from "+tableName;
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map map = new HashMap();
                map.put("location", rs.getString("location"));
                map.put("alarmCode", rs.getString("alarmCode"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取历史告警数据异常++++" + dbConn.getUrl() + "异常信息:" + e.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return list;
    }

    /**
     * 告警规则验证...
     *
     * @param map
     * @param ruleCfg
     * @return
     */
    public boolean alarmCheck(BtsAlarm btsAlarm, WyRulecfg ruleCfg) {
        List<WyRuleterm> ruleTerms = ruleCfg.getWyRuletermList();
        if (ruleTerms != null && ruleTerms.size() > 0) {
            boolean flag = false;
            for (WyRuleterm rule : ruleTerms) {
                WySubcfg subCfg = rule.getWySubcfg();
                boolean result = false;
                if (subCfg != null) {
                    String column = subCfg.getColumnname();
                    AlarmCounter counter = btsAlarm.getBtsAlarm(column);
                    if (counter != null) {
                        String value = counter.getAlarmCount() + ""; //统计到的告警数
                        //取出规则配置的A、B值
                        String value1 = rule.getValue1();
                        String value2 = rule.getValue2();
                        int dataType = rule.getDataType();
                        int symbol = rule.getSymbol();
                        result = GradeUtil.judge(dataType, symbol, value1, value2, value);
                    }
                }
                if (!result) {
                    flag = true;//一项不符合，则规则不符合
                    break;
                }
            }
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对一个基站的所配规则进行评分
     *
     * @param btsAlarm
     * @param ruleCfgs
     */
    public void gradeByAlarm(BtsAlarm btsAlarm, List<WyRulecfg> ruleCfgs) {
        List<WyGradeDetail> wyGradeDetailList = new ArrayList<WyGradeDetail>();
        Long intId = StringUtil.null2Long0(btsAlarm.getIntId());
        try {
            Bts bts = btsManager.getById(intId);
            String serviceLevel = bts.getServiceLevel();
            double grade = 0;
            if (ruleCfgs != null) {
                for (WyRulecfg ruleCfg : ruleCfgs) {
                    boolean flag = alarmCheck(btsAlarm, ruleCfg);
                    if (flag) {
                        double subGrade = GradeUtil.grade(ruleCfg, serviceLevel);
                        grade += subGrade;
                        WyGradeDetail wyGradeDetail = new WyGradeDetail();
                        wyGradeDetail.setGradecfgid(ruleCfg.getId());
                        wyGradeDetail.setGrade(subGrade);
                        wyGradeDetail.setIntId(intId);
                        wyGradeDetailList.add(wyGradeDetail);
                    }
                }
            }

            WyGrade wyGrade = gradeManger.selectByPrimaryKey(intId);
            if (wyGrade == null) {
                //插入
                wyGrade = new WyGrade();
                wyGrade.setIntId(intId);
                wyGrade.setInfoGrade(grade);
                wyGrade.setUpdatetime(new Date());
                wyGrade.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.insert(wyGrade);
            } else {
                //更新
                WyGrade record = new WyGrade();
                record.setIntId(wyGrade.getIntId());
                record.setInfoGrade(grade);
                record.setUpdatetime(new Date());
                record.setWyGradeDetailList(wyGradeDetailList);
                gradeManger.updateByPrimaryKeySelective(record);
            }
        } catch (Exception e) {
            logger.error("++++告警打分异常.....");
            e.printStackTrace();
        }
    }


}
