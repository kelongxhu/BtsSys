package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.SchoolLib;

import java.util.List;
import java.util.Map;

public interface SchoolLibManager {
	void insert(SchoolLib schoolLib) throws Exception;

	void update(SchoolLib schoolLib) throws Exception;
	
	List<SchoolLib> getByConds(Map<String,Object> map)throws Exception;
	
	int countByConds(Map<String,Object> map)throws Exception;
	
	List<Cons> getCons(Object map)throws Exception;

	SchoolLib getById(Long id)throws Exception;
	
	void delete(Object map)throws Exception;
	
	List<SchoolLib> loadAll()throws Exception;

    int deleteByDeleteFlag(Object map)throws Exception;
}
