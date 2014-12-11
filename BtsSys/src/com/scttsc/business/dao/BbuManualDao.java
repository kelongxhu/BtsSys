package com.scttsc.business.dao;

import com.scttsc.business.model.BbuManual;

import java.util.Map;

public interface BbuManualDao {

	BbuManual getById(Long intId) throws Exception;

	int insert(BbuManual record) throws Exception;

	int update(Object record) throws Exception;

    int updateByInterface(Map record)throws Exception;

    int updateByIntID(Map record)throws Exception;

    int deleteByIntId(Long intId)throws Exception;

}