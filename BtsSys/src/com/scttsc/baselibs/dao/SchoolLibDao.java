package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.SchoolLib;
import com.scttsc.common.dao.MyBatisRepository;
@MyBatisRepository
public interface SchoolLibDao {
	void insert(SchoolLib schoolLib) throws Exception;

	void update(SchoolLib schoolLib) throws Exception;
	
	void delete(Object map)throws Exception;
	
	List<SchoolLib> getByConds(Object map)throws Exception;
	
	int countByConds(Object map)throws Exception;
	
	SchoolLib getById(Long id)throws Exception;
	
	List<SchoolLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}
