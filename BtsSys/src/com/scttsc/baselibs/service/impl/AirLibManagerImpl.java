package com.scttsc.baselibs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.AirLibDao;
import com.scttsc.baselibs.model.AirLib;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.service.AirLibManager;
@Service("airLibManager")
@Transactional(readOnly = true)
public class AirLibManagerImpl implements AirLibManager {
	@Autowired
	private AirLibDao airLibDao;
	public void insert(AirLib airLib) throws Exception {
		airLibDao.insert(airLib);
	}

	public void update(AirLib airLib) throws Exception {
		airLibDao.update(airLib);
	}

	public void delete(Object map) throws Exception {
		airLibDao.delete(map);
	}

	public List<AirLib> getByConds(Object map) throws Exception {
		return airLibDao.getByConds(map);
	}

	public int countByConds(Object map) throws Exception {
		return airLibDao.countByConds(map);
	}

	public AirLib getById(Long id) throws Exception {
		return airLibDao.getById(id);
	}
	
	public List<AirLib> selectAll(Object map)throws Exception{
		return airLibDao.selectAll(map);
	}

    public int deleteByDeleteFlag(Object map) throws Exception {
        return airLibDao.deleteByDeleteFlag(map);
    }
}
