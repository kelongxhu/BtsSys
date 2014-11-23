package com.scttsc.healthy.service;

import com.scttsc.healthy.model.*;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-8-20
 * Time: 下午1:14
 */
public interface RuleManager {
    List<WyRulecfg> selectRuleCfgList(Map record) throws Exception;

    int selectRuleCfgCount(Map record) throws Exception;

    List<WySubcfg> selectSubCfgByMap(Map record)throws Exception;

    int insertRuleCfg(WyRulecfg record)throws Exception;

    WyRulecfg selectRuleCfgByPrimaryKey(Long id)throws Exception;

    int updateRuleCfg(WyRulecfg record) throws Exception;

    int deleteRuleCfg(Long id)throws Exception;

    List<WyRulecfg> selectRuleCfgByMap(Map record) throws Exception;

    List<GzBtsCheck> selectBy(Map map) throws Exception;

    int insertBtsAlarm(List<WyBtsAlarm> wyBtsAlarmList)throws Exception;

    double selectSumGradeByType(Long gradeType)throws Exception;

    List<WyRuleterm> selectByRuleId(Long ruleCfgId)throws Exception;

    List<WyDbcon> selectAll()throws Exception;


}
