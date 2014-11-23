package com.scttsc.baselibs.dao;

import java.util.List;

import com.scttsc.baselibs.model.WyBlind;

public interface WyBlindDao {
    int deleteByPrimaryKey(Long id);
    
    int insert(WyBlind record);

    WyBlind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyBlind record);
    
    int countByConds(Object map)throws Exception;
    
	List<WyBlind> getByConds(Object map)throws Exception;
	
	int deleteByDeleteFlag(Object map)throws Exception;
}