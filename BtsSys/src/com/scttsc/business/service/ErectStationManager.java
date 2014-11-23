package com.scttsc.business.service;

import com.scttsc.business.model.ErectStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ErectStationManager {
    int countByExample(Map example);

    int deleteByExample(Map example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(ErectStation record);

    int importInsert(Map record)throws Exception;

    int insertSelective(ErectStation record);

    List<ErectStation> selectByExample(Map example);

    ErectStation selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(ErectStation record);

    int updateByExample(ErectStation record);

    int updateByPrimaryKeySelective(ErectStation record);

    int updateByPrimaryKey(ErectStation record);
    
}
