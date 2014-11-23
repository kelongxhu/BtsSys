package com.scttsc.common.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.common.dao.BaseDao;
import com.scttsc.common.service.BaseManager;

public class BaseManagerImpl<T, PK extends Serializable>
		implements BaseManager<T, PK> {
	public BaseDao<T, PK> baseDao;
	
	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public T get(PK id) throws Exception{
		return baseDao.get(id);
	}

	public List<T> loadAll() throws Exception{
		return baseDao.loadAll();
	}

	public void save(T t) throws Exception{
		baseDao.insert(t);
	}

	public void update(T t)throws Exception {
		baseDao.update(t);
	}
	
	public void deleteByKey(PK id) throws Exception{
		baseDao.deleteByKey(id);
	}
	
	public void delete(T t)throws Exception{
		baseDao.delete(t);
	}
	
	public List<T> selectByMap(Object map)throws Exception{
		return baseDao.selectByMap(map);
	}

	public int countByMap(Object map) throws Exception{
		return baseDao.countByMap(map);
	}

}
