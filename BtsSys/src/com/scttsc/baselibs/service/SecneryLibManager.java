package com.scttsc.baselibs.service;

import java.util.List;

import com.scttsc.baselibs.model.SecneryLib;
public interface SecneryLibManager {
	int insert(SecneryLib secneryLib) throws Exception;

	int update(SecneryLib secneryLib) throws Exception;

	int delete(Object map) throws Exception;

	List<SecneryLib> getByConds(Object map) throws Exception;

	int countByConds(Object map) throws Exception;

	SecneryLib getById(Long id) throws Exception;
	
	List<SecneryLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}
