package com.scttsc.baselibs.service;

import java.util.List;

import com.scttsc.baselibs.model.TunnelLib;

public interface TunnelLibManager {
	int insert(TunnelLib tunnelLib) throws Exception;

	int update(TunnelLib tunnelLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<TunnelLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	TunnelLib getById(Long id)throws Exception;
	
	List<TunnelLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}
