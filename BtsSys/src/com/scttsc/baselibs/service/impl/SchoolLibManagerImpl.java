package com.scttsc.baselibs.service.impl;

import com.scttsc.baselibs.dao.ConsDao;
import com.scttsc.baselibs.dao.SchoolLibDao;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.SchoolLib;
import com.scttsc.baselibs.service.SchoolLibManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("schoolLibManager")
@Transactional(readOnly = true)
public class SchoolLibManagerImpl implements SchoolLibManager {
	@Autowired
	private SchoolLibDao schoolLibDao;
	@Autowired
	private ConsDao consDao;

	public void insert(SchoolLib schoolLib) throws Exception {
		schoolLibDao.insert(schoolLib);
	}

	public void update(SchoolLib schoolLib) throws Exception {
		schoolLibDao.update(schoolLib);
	}

	public List<SchoolLib> getByConds(Map<String, Object> map) throws Exception {
		return schoolLibDao.getByConds(map);
	}

	public int countByConds(Map<String, Object> map) throws Exception {
		return schoolLibDao.countByConds(map);
	}

	/**
	 * 获取常量列表
	 */
	public List<Cons> getCons(Object map) throws Exception {
		return consDao.getByConds(map);
	}

    /**
	 * 根据ID获取校园库
	 */
	public SchoolLib getById(Long id) throws Exception {
		return schoolLibDao.getById(id);
	}

	/**
	 * 删除一条或者多条
	 */
	public void delete(Object map) throws Exception {
		schoolLibDao.delete(map);
	}

	public List<SchoolLib> loadAll() throws Exception {
		return schoolLibDao.loadAll();
	}

    public int deleteByDeleteFlag(Object map) throws Exception {
        return schoolLibDao.deleteByDeleteFlag(map);
    }
}
