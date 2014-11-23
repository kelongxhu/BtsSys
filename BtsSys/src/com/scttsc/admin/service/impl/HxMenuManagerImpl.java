package com.scttsc.admin.service.impl;

import com.scttsc.admin.dao.HxMenuDao;
import com.scttsc.admin.model.HxMenu;
import com.scttsc.admin.service.HxMenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("hxMenuManager")
@Transactional(readOnly = true)
public class HxMenuManagerImpl implements
		HxMenuManager {
	@Autowired
	private HxMenuDao hxMenuDao;


	public List<HxMenu> getByPid(Long pid) throws Exception {
		return hxMenuDao.getByPid(pid);
	}

    public List<HxMenu> loadAll() throws Exception {
        return hxMenuDao.loadAll();
    }

    public HxMenu selectById(Long id) throws Exception {
        return hxMenuDao.selectById(id);
    }

    public List<HxMenu> getByUserId(Long userId) throws Exception {
        return hxMenuDao.getByUserId(userId);
    }

    public List<HxMenu> getChildByUserId(Object map) throws Exception {
        return hxMenuDao.getChildByUserId(map);
    }
}
