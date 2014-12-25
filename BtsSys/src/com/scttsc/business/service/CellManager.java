package com.scttsc.business.service;

import com.scttsc.business.model.*;

import java.util.List;
import java.util.Map;

public interface CellManager {
    List<Cell> selectByMap(Object map) throws Exception;

    Cell selectById(Long id) throws Exception;

    int countCarrierByCell(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    int updateByMap(Object map) throws Exception;

    Ccell selectCcellById(Long intId) throws Exception;

    CparCell selectCparCellById(Long intId) throws Exception;

    List<Cell> selectByBtsId(Long btsIntId) throws Exception;

    List<Ccell> selectByRelateBts(Long btsId) throws Exception;

    List<Ccell> selectCcellByBtsId(Long btsId) throws Exception;

    List<Map> selectExportCellDataByMap(Map map) throws Exception;

    //小区库查询相关
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

    //天线库
    List<Map> selectCellAirLibByCellId(Long intId)throws Exception;

    //多个条件查询小区

    List<Cell> selectCellByMap(Map map)throws Exception;

    List<Map> selectCellGroupByColumns(Map map) throws Exception;

    int countCellGroupByColumns(Map map) throws Exception;

    List<Cell> selectCellAssoTunelByMap(Map<String,Object> record)throws Exception;


}
