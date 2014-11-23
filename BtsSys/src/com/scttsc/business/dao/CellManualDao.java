package com.scttsc.business.dao;

import com.scttsc.business.model.CellManual;

import java.util.Map;

public interface CellManualDao {
   

    CellManual selectById(Long intId)throws Exception;

	int insert(CellManual record)throws Exception;

    int update(CellManual record)throws Exception;

    int deleteById(Long intId)throws Exception;

    int updateByInterface(Map map)throws Exception;

    int updateByIntID(Map map)throws Exception;
}