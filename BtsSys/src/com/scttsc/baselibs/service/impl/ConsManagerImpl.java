package com.scttsc.baselibs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.ConsDao;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.ConsManager;
@Service("consManager")
@Transactional(readOnly = true)
public class ConsManagerImpl implements ConsManager {
	@Autowired
	private ConsDao consDao;
	public Cons getByMap(Object map) throws Exception {
		return consDao.getByMap(map);
	}
	
}
