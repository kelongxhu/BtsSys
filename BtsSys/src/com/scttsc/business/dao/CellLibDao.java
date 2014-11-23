package com.scttsc.business.dao;

import com.scttsc.business.model.CellLib;

import java.util.List;
import java.util.Map;

public interface CellLibDao {
    int insert(CellLib record);

    List<CellLib> selectByMap(Object map) throws Exception;

    int deleteByMap(Object map) throws Exception;

    //更新现有库所属小区
    int updateByMap(Map record)throws Exception;

    //校园库覆盖
    List<Map> selectSchoolLibCells(Object map) throws Exception;

    int countBySchoolLibCells(Object map) throws Exception;

    //风景库覆盖
    List<Map> selectSecneryLibCells(Object map) throws Exception;

    int countBySecneryLibCells(Object map) throws Exception;

    //农村库覆盖
    List<Map> selectVitoLibCells(Object map) throws Exception;

    int countByVitoLibCells(Object map) throws Exception;

    //道路库覆盖
    List<Map> selectRoadLibCells(Object map) throws Exception;

    int countByRoadLibCells(Object map) throws Exception;

    //隧道库覆盖
    List<Map> selectTunnelLibCells(Object map) throws Exception;

    int countByTunnelLibCells(Object map) throws Exception;
}