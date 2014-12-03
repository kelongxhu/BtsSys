package com.scttsc.business.dao;

import com.scttsc.business.model.Cell;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CellDao {

    /**
     * 根据条件查询小区列表
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<Cell> selectByMap(Object map) throws Exception;

    /**
     * 根据物理站点ID查询小区列表
     *
     * @param map
     * @throws Exception
     * @return:不分页
     */
    List<Cell> selectByBtsId(Long btsIntId) throws Exception;

    /**
     * 通过小区查询小区小的载频数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    int countCarrierByCell(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    int updateByMap(Object map) throws Exception;

    Cell selectById(Long id) throws Exception;

    /**
     * 灵和查询导出字段
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportCellDataByMap(Map map) throws Exception;

    /**
     * 查询小区根据多个条件
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<Cell> selectCellByMap(Map map) throws Exception;

    List<Map> selectCellGroupByColumns(Map map) throws Exception;

    int countCellGroupByColumns(Map map) throws Exception;

    /**
     * 按本地网统计小区
     *
     * @param isIndoor
     * @return
     * @throws Exception
     */
    List<Map> selectCellCountGroupByCity(@Param(value="isIndoor") String isIndoor,@Param(value="manualFlag") Integer manualFlag) throws Exception;

}