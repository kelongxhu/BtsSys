package com.scttsc.baselibs.dao;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.AirLib;
import com.scttsc.common.dao.MyBatisRepository;
@MyBatisRepository
public interface AirLibDao {
	void insert(AirLib airLib) throws Exception;

	void update(AirLib airLib) throws Exception;
	
	void delete(Object map)throws Exception;
	
	List<AirLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	AirLib getById(Long id)throws Exception;
	
	List<AirLib> selectAll(Object map)throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;

    List<Map> selectCellAirLibByCellId(Long id)throws Exception;
}
