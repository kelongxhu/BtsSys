package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.RoadLib;

public interface RoadLibManager {
	int insert(RoadLib roadLib) throws Exception;

	int update(RoadLib roadLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<RoadLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	RoadLib getById(Long id)throws Exception;

    RoadLib getByName(String name)throws Exception;
	
	List<RoadLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
    /**
     * 批量导入场景库
     * @param data
     * @return
     */
    int importInsert(List<Map<String, Object>> data);
}
