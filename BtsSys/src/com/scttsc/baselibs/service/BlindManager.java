package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.WyBlind;

public interface BlindManager {
	
	List<WyBlind> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;

	int insert(WyBlind record)throws Exception ;
	
	int deleteByDeleteFlag(Object map)throws Exception;
	
	WyBlind getById(Long id);
	
	int update(WyBlind record)throws Exception;
	
	int importInsert(Map<String, Object> map)throws Exception;
	
}
