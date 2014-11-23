package com.scttsc.baselibs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.TunnelLibDao;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.service.TunnelLibManager;

@Service("tunnelLibManager")
@Transactional(readOnly = true)
public class TunnelLibManagerImpl implements TunnelLibManager {
	@Autowired
	private TunnelLibDao tunnelLibDao;

	public int insert(TunnelLib tunnelLib) throws Exception {
		return tunnelLibDao.insert(tunnelLib);
	}

	public int update(TunnelLib tunnelLib) throws Exception {
		return tunnelLibDao.update(tunnelLib);
	}

	public int delete(Object map) throws Exception {
		return tunnelLibDao.delete(map);
	}

	public List<TunnelLib> getByConds(Object map) throws Exception {
		return tunnelLibDao.getByConds(map);
	}
	public int countByConds(Object map) throws Exception {
		return tunnelLibDao.countByConds(map);
	}

	public TunnelLib getById(Long id) throws Exception {
		return tunnelLibDao.getById(id);
	}

	public List<TunnelLib> loadAll() throws Exception {
		return tunnelLibDao.loadAll();
	}

    public int deleteByDeleteFlag(Object map) throws Exception {
        return tunnelLibDao.deleteByDeleteFlag(map);
    }
}
