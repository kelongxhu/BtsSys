package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.VitoLib;
import com.scttsc.common.dao.MyBatisRepository;

@MyBatisRepository
public interface VitoLibDao {
	int insert(VitoLib vitoLib) throws Exception;

	int update(VitoLib vitoLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<VitoLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	VitoLib getById(Long id)throws Exception;
	
	List<VitoLib> getByMap(Object map)throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}