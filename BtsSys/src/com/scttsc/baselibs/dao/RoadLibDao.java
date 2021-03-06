package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.RoadLib;


public interface RoadLibDao {
	int insert(RoadLib roadLib) throws Exception;

	int updateByPrimaryKeySelective(RoadLib roadLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<RoadLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	RoadLib getById(Long id)throws Exception;

    List<RoadLib> getByName(String name)throws Exception;
	
	List<RoadLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}