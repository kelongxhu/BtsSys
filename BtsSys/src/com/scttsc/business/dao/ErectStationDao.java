package com.scttsc.business.dao;

import com.scttsc.business.model.ErectStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ErectStationDao {

    int countByExample(Map example);

    int deleteByExample(Map example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(ErectStation record);

    int insertSelective(Object record);

    List<ErectStation> selectByExample(Map example);

    ErectStation selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(ErectStation record);

    int updateByExample(ErectStation record);

    int updateByPrimaryKeySelective(Object record);

    int updateByPrimaryKey(ErectStation record);

}