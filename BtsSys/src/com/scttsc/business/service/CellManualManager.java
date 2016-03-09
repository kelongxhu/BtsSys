package com.scttsc.business.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.model.VitoLib;
import com.scttsc.business.model.CellAirLib;
import com.scttsc.business.model.CellLib;
import com.scttsc.business.model.CellManual;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.CellMateEntry;
import com.scttsc.business.vo.FindBackReponse;

public interface CellManualManager {
    /**
     * intId获取小区手工数据
     * @param intId
     * @return
     * @throws Exception
     */
	CellManual selectById(Long intId)throws Exception;

    /**
     * 插入手工数据
     * @param record
     * @return
     * @throws Exception
     */
    int insert(CellManual record)throws Exception;

    /**
     * 更新手工数据
     * @param record
     * @return
     * @throws Exception
     */
    int update(CellManual record)throws Exception;

    /**
     * 导入手工数据
     * @param record
     * @param libMap
     * @throws Exception
     */
    void insertImport(Map record,Map<String,Map> libMap)throws Exception ;//导入手工数据

    /**
     * 补全小区手工数据
     * @param record
     * @throws Exception
     */
    void saveCellManual(CellManual record)throws Exception;

    /**
     * 编辑小区手工数据
     * @param record
     * @throws Exception
     */
    void updateCellManual(CellManual record)throws Exception;//编辑小区手工数据

    /**
     * 获取小区关联的库
     * @param map
     * @return
     * @throws Exception
     */
    List<CellLib> selectCellLibByMap(Object map)throws Exception;

    /**
     * 获取小区天线库列表
     * @param cellId
     * @return
     * @throws Exception
     */
    List<CellAirLib> selectCellAirLibByCellId(Long cellId)throws Exception;

    /**
     * 删除小区库关系
     * @param map
     * @return
     * @throws Exception
     */
    int deleteCellLibByMap(Object map)throws Exception;

    /**
     * 删除天线库
      * @param map
     * @return
     * @throws Exception
     */
    int deleteCellAirLibByMap(Object map)throws Exception;

    /**
     * 接口更新小区手工数据
     * @param map
     * @return
     * @throws Exception
     */
    int updateByInterface(Map map)throws Exception;

    //根据废弃小区名称找到再用小区，进行数据匹配
    List<MateEntry> findDelCellData(List<Long> ids)throws Exception;
    //找回小区数据
    FindBackReponse findManualData(List<String> ids);

    /**
     * 通过物理站点ID，获取CELL_ID的小区手工数据
     * @param wybtsId
     * @return
     * @throws Exception
     */
    CellManual getBtsMinCell(Long wybtsId);

}
