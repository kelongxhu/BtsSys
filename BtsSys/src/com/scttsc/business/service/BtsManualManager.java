package com.scttsc.business.service;

import com.scttsc.business.model.BtsManual;

import java.util.Map;

public interface BtsManualManager {
	BtsManual getById(Long intId)throws Exception;
	   
    int insert(BtsManual record)throws Exception;
   
    int update(BtsManual record)throws Exception;
    
    void importInsert(Map record)throws Exception;//导入手工数据


    int updateByInterface(Map record)throws Exception;
    
  
}
