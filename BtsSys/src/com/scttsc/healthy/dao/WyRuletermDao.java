package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.model.WyRuleterm;

import java.util.List;
import java.util.Map;

public interface WyRuletermDao {
    int deleteByRuleCfg(Long ruleCfgId);

    int insert(WyRuleterm record);

    WyRuleterm selectByPrimaryKey(Long id);

    List<WyRuleterm> selectByRuleId(Long ruleCfgId)throws Exception;



}