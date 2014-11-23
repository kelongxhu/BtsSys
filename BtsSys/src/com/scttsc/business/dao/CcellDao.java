package com.scttsc.business.dao;

import com.scttsc.business.model.Ccell;

import java.util.List;

public interface CcellDao {
	Ccell selectById(Long intId) throws Exception;

    List<Ccell> selectByRelateBts(Long btsId)throws Exception;

    List<Ccell> selectByBtsId(Long btsId)throws Exception;
}