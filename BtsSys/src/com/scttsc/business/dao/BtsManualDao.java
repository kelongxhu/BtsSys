package com.scttsc.business.dao;

import com.scttsc.business.model.BtsManual;

import java.util.Map;

public interface BtsManualDao {

	BtsManual getById(Long intId)throws Exception;
   
    int insert(BtsManual record)throws Exception;

    int updateByMap(BtsManual record)throws Exception;

    int updateByInterface(Map record)throws Exception;

    int updateByIntID(Map record)throws Exception;
}