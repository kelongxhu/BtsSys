package com.scttsc.lte.dao;

import com.scttsc.lte.model.WyLteBts;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WyLteBtsDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyLteBts record);

    int insertSelective(WyLteBts record);

    WyLteBts selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyLteBts record);

    int updateByPrimaryKey(WyLteBts record);

    List<WyLteBts> findWyLteBts(Map<String,Object> param);

    int countWyLteBts(Map<String,Object> param);
}