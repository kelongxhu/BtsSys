package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.VitoLib;

public interface VitoLibManager {
	int insert(VitoLib vitoLib) throws Exception;

	int update(VitoLib vitoLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<VitoLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	VitoLib getById(Long id)throws Exception;
	
	List<VitoLib> getByMap(Object map)throws Exception;

    int importInsert(Map record)throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}
