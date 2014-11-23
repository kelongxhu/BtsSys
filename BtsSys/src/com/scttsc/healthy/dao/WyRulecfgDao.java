package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyRulecfg;

import java.util.List;
import java.util.Map;

public interface WyRulecfgDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyRulecfg record);

    int insertSelective(WyRulecfg record);

    WyRulecfg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyRulecfg record);

    int updateByPrimaryKey(WyRulecfg record);

    List<WyRulecfg> selectRuleCfgList(Map record) throws Exception;

    int selectRuleCfgCount(Map record) throws Exception;

    List<WyRulecfg> selectRuleCfgByMap(Map record) throws Exception;

    double selectSumGradeByType(Long gradeType)throws Exception;

}