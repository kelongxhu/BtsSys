package com.scttsc.business.service.impl;

import com.scttsc.business.dao.*;
import com.scttsc.business.model.Ccell;
import com.scttsc.business.model.Cell;
import com.scttsc.business.model.CparCell;
import com.scttsc.business.service.CellManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("cellManager")
@Transactional(readOnly = true)
public class CellManagerImpl implements CellManager {

    @Autowired
    private CellDao cellDao;
    @Autowired
    private CcellDao ccellDao;
    @Autowired
    private CparCellDao cparCellDao;
    @Autowired
    private CellLibDao cellLibDao;
    @Autowired
    private CellAirLibDao cellAirLibDao;


    public List<Cell> selectByMap(Object map) throws Exception {
        return cellDao.selectByMap(map);
    }

    public int countCarrierByCell(Object map) throws Exception {
        return cellDao.countCarrierByCell(map);
    }

    public int countByMap(Object map) throws Exception {
        return cellDao.countByMap(map);
    }


    public int updateByMap(Object map) throws Exception {
        return cellDao.updateByMap(map);
    }

    public Cell selectById(Long id) throws Exception {
        return cellDao.selectById(id);
    }

    public Ccell selectCcellById(Long intId) throws Exception {
        return ccellDao.selectById(intId);
    }

    /**
     * 查找c_cell的手工表c_tzx_par_cell_do
     *
     * @param intId
     * @return
     * @throws Exception
     */
    public CparCell selectCparCellById(Long intId) throws Exception {
        return cparCellDao.selectById(intId);
    }

    /**
     * 通过物理站点ID，查询旗下下去列表
     *
     * @param btsIntId
     * @return
     * @throws Exception
     */
    public List<Cell> selectByBtsId(Long btsIntId) throws Exception {
        return cellDao.selectByBtsId(btsIntId);
    }

    /**
     * 通过c_bts的int_id查找c_cell关联的小区
     *
     * @param btsId
     * @return
     * @throws Exception
     */

    public List<Ccell> selectByRelateBts(Long btsId) throws Exception {
        return ccellDao.selectByRelateBts(btsId);
    }

    public List<Ccell> selectCcellByBtsId(Long btsId) throws Exception {
        return ccellDao.selectByBtsId(btsId);
    }

    /**
     * excel导出小区
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectExportCellDataByMap(Map map) throws Exception {
        return cellDao.selectExportCellDataByMap(map);
    }


    public List<Map> selectSchoolLibCells(Object map) throws Exception {
        return cellLibDao.selectSchoolLibCells(map);
    }

    public int countBySchoolLibCells(Object map) throws Exception {
        return cellLibDao.countBySchoolLibCells(map);
    }

    public List<Map> selectSecneryLibCells(Object map) throws Exception {
        return cellLibDao.selectSecneryLibCells(map);
    }

    public int countBySecneryLibCells(Object map) throws Exception {
        return cellLibDao.countBySecneryLibCells(map);
    }

    public List<Map> selectVitoLibCells(Object map) throws Exception {
        return cellLibDao.selectVitoLibCells(map);
    }

    public int countByVitoLibCells(Object map) throws Exception {
        return cellLibDao.countByVitoLibCells(map);
    }

    public List<Map> selectRoadLibCells(Object map) throws Exception {
        return cellLibDao.selectRoadLibCells(map);
    }

    public int countByRoadLibCells(Object map) throws Exception {
        return cellLibDao.countByRoadLibCells(map);
    }

    public List<Map> selectTunnelLibCells(Object map) throws Exception {
        return cellLibDao.selectTunnelLibCells(map);
    }

    public int countByTunnelLibCells(Object map) throws Exception {
        return cellLibDao.countByTunnelLibCells(map);
    }

    /**
     * 多个条件查询小区
     * @param map
     * @return
     * @throws Exception
     */
    public List<Cell> selectCellByMap(Map map) throws Exception {
        return cellDao.selectCellByMap(map);
    }

    /**
     * 按照字段动态统计小区个数
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectCellGroupByColumns(Map map) throws Exception {
        return cellDao.selectCellGroupByColumns(map);
    }

    public int countCellGroupByColumns(Map map) throws Exception {
        return cellDao.countCellGroupByColumns(map);
    }

    public List<Cell> selectCellAssoTunelByMap(Map<String, Object> record) throws Exception {
        return cellDao.selectCellAssoTunelByMap(record);
    }

    public List<Map> selectCellAirLibByCellId(Long intId) throws Exception {
        return cellAirLibDao.selectCellAirLibByCellId(intId);
    }

}
