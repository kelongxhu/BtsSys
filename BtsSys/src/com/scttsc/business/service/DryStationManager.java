package com.scttsc.business.service;

import com.scttsc.business.model.DryStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DryStationManager {
    int countByExample(Map example);

    int importInsert(Map record)throws Exception;

    int deleteByExample(Map example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(DryStation record);

    int insertSelective(DryStation record);

    List<DryStation> selectByExample(Map example);

    DryStation selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(DryStation record);

    int updateByExample(DryStation record);

    int updateByPrimaryKeySelective(DryStation record);

    int updateByPrimaryKey(DryStation record);
    
}
