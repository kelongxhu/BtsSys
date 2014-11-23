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
	CellManual selectById(Long intId)throws Exception;
	   
    int insert(CellManual record)throws Exception;
   
    int update(CellManual record)throws Exception;
    
    void insertImport(Map record,Map<String,Map> libMap)throws Exception ;//导入手工数据
    
    void saveCellManual(CellManual record)throws Exception;
    
    void updateCellManual(CellManual record)throws Exception;//编辑小区手工数据
    
    List<CellLib> selectCellLibByMap(Object map)throws Exception;
    
    List<CellAirLib> selectCellAirLibByCellId(Long cellId)throws Exception;
    
    int deleteCellLibByMap(Object map)throws Exception;
    
    int deleteCellAirLibByMap(Object map)throws Exception;

    int updateByInterface(Map map)throws Exception;

    //根据废弃小区名称找到再用小区，进行数据匹配
    List<MateEntry> findDelCellData(List<Long> ids)throws Exception;
    //找回小区数据
    FindBackReponse findManualData(List<String> ids);

}
