package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WySubcfg;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WySubcfgDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(WySubcfg record);

    int insertSelective(WySubcfg record);

    WySubcfg selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(WySubcfg record);

    int updateByPrimaryKey(WySubcfg record);

    List<WySubcfg> selectSubCfgByMap(Map record) throws Exception;
}