package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.SecneryLib;
import com.scttsc.common.dao.MyBatisRepository;
@MyBatisRepository
public interface SecneryLibDao {
   
	int insert(SecneryLib secneryLib) throws Exception;

	int update(SecneryLib secneryLib) throws Exception;
	
	int delete(Object map)throws Exception;
	
	List<SecneryLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	SecneryLib getById(Long id)throws Exception;
	
	List<SecneryLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}