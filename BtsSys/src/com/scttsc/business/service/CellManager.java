package com.scttsc.business.service;

import com.scttsc.business.model.*;

import java.util.List;
import java.util.Map;

public interface CellManager {
    /**
     * 查询小区列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Cell> selectByMap(Object map) throws Exception;

    /**
     * 根据主键获取Cell信息
     * @param id
     * @return
     * @throws Exception
     */
    Cell selectById(Long id) throws Exception;

    /**
     * carrier总数
     * @param map
     * @return
     * @throws Exception
     */
    int countCarrierByCell(Object map) throws Exception;

    /**
     * 总数
     * @param map
     * @return
     * @throws Exception
     */
    int countByMap(Object map) throws Exception;

    /**
     * 更新小区
     * @param map
     * @return
     * @throws Exception
     */
    int updateByMap(Object map) throws Exception;

    /**
     * 获取CCell信息
     * @param intId
     * @return
     * @throws Exception
     */
    Ccell selectCcellById(Long intId) throws Exception;

    /**
     *
     * @param intId
     * @return
     * @throws Exception
     */
    CparCell selectCparCellById(Long intId) throws Exception;

    /**
     * 根据btsIntId查询小区列表
     * @param btsIntId
     * @return
     * @throws Exception
     */
    List<Cell> selectByBtsId(Long btsIntId) throws Exception;

    /**
     * 根据btsId获取c_cell列表
     * @param btsId
     * @return
     * @throws Exception
     */
    List<Ccell> selectByRelateBts(Long btsId) throws Exception;
    //根据bts_id获取c_cell列表
    List<Ccell> selectCcellByBtsId(Long btsId) throws Exception;

    /**
     * 导出小区字段
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportCellDataByMap(Map map) throws Exception;

    //小区库查询相关
    List<Map> selectSchoolLibCells(Object map) throws Exception;
    //小区库查询总数
    int countBySchoolLibCells(Object map) throws Exception;
    //场景库覆盖
    List<Map> selectSecneryLibCells(Object map) throws Exception;
    //场景库覆盖总数
    int countBySecneryLibCells(Object map) throws Exception;

    //农村库覆盖
    List<Map> selectVitoLibCells(Object map) throws Exception;
    //农村库覆盖总数
    int countByVitoLibCells(Object map) throws Exception;

    //道路库覆盖
    List<Map> selectRoadLibCells(Object map) throws Exception;
    //道路库覆盖
    int countByRoadLibCells(Object map) throws Exception;

    //隧道库覆盖
    List<Map> selectTunnelLibCells(Object map) throws Exception;
    //隧道库覆盖总数
    int countByTunnelLibCells(Object map) throws Exception;

    //天线库
    List<Map> selectCellAirLibByCellId(Long intId)throws Exception;

    //多个条件查询小区

    List<Cell> selectCellByMap(Map map)throws Exception;
    //按动态字段统计
    List<Map> selectCellGroupByColumns(Map map) throws Exception;
    //按动态字段统计总数
    int countCellGroupByColumns(Map map) throws Exception;
    //导出隧道小区
    List<Cell> selectCellAssoTunelByMap(Map<String,Object> record)throws Exception;


}
