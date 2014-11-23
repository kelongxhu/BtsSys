package com.scttsc.business.dao;

import com.scttsc.business.model.ColumnsConfig;

import java.math.BigDecimal;
import java.util.List;

public interface ColumnsConfigDao {

    ColumnsConfig selectByPrimaryKey(BigDecimal id) throws Exception;

    List<ColumnsConfig> selectByType(BigDecimal type) throws Exception;

    List<ColumnsConfig> selectByConds(Object map) throws Exception;
}