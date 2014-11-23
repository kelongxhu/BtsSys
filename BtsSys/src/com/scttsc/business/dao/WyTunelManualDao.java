package com.scttsc.business.dao;

import com.scttsc.business.model.WyTunelManual;
import java.math.BigDecimal;
import java.util.Map;

public interface WyTunelManualDao {
    int deleteByPrimaryKey(Long intId);

    int insert(WyTunelManual record);

    WyTunelManual selectByPrimaryKey(Long intId);

    int updateByPrimaryKeySelective(WyTunelManual record);

    int updateByIntID(Map record)throws Exception;
}