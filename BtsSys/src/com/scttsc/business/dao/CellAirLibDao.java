package com.scttsc.business.dao;

import com.scttsc.business.model.CellAirLib;

import java.util.List;
import java.util.Map;

public interface CellAirLibDao {
    int insert(CellAirLib record);

    List<CellAirLib> selectByCellId(Long intId)throws Exception;
    
    int deleteByMap(Object map)throws Exception;

    int updateByMap(Map record)throws Exception;
}