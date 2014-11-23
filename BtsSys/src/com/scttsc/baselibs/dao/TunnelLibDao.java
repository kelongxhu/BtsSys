package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.common.dao.MyBatisRepository;
@MyBatisRepository
public interface TunnelLibDao {
	int insert(TunnelLib tunnelLib) throws Exception;

	int update(TunnelLib tunnelLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<TunnelLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	TunnelLib getById(Long id)throws Exception;
	
	List<TunnelLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}