package com.scttsc.business.dao;

import com.scttsc.business.model.DryStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DryStationDao {

    int countByExample(Map example);

    int deleteByExample(Map example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(DryStation record);

    int insertSelective(Object record);

    List<DryStation> selectByExample(Map example);

    DryStation selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(DryStation record);

    int updateByExample(DryStation record);

    int updateByPrimaryKeySelective(Object record);

    int updateByPrimaryKey(DryStation record);
}