package com.scttsc.baselibs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.SecneryLibDao;
import com.scttsc.baselibs.model.SecneryLib;
import com.scttsc.baselibs.service.SecneryLibManager;

@Service("secneryLibManager")
@Transactional(readOnly = true)
public class SecneryLibManagerImpl implements SecneryLibManager {
	@Autowired
	SecneryLibDao sercneryLibDao;

	public int insert(SecneryLib secneryLib) throws Exception {
		return sercneryLibDao.insert(secneryLib);
	}

	public int update(SecneryLib secneryLib) throws Exception {
		return sercneryLibDao.update(secneryLib);
	}

	public int delete(Object map) throws Exception {
		return sercneryLibDao.delete(map);
	}

	public List<SecneryLib> getByConds(Object map) throws Exception {
		return sercneryLibDao.getByConds(map);
	}

	public int countByConds(Object map) throws Exception {
		return sercneryLibDao.countByConds(map);
	}

	public SecneryLib getById(Long id) throws Exception {
		return sercneryLibDao.getById(id);
	}


	public List<SecneryLib> loadAll() throws Exception {
		return sercneryLibDao.loadAll();
	}

    public int deleteByDeleteFlag(Object map) throws Exception {
        return sercneryLibDao.deleteByDeleteFlag(map);
    }
}
