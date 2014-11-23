package com.scttsc.baselibs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.WyBlindDao;
import com.scttsc.baselibs.model.WyBlind;
import com.scttsc.baselibs.service.BlindManager;

@Service("blindManager")
@Transactional(readOnly = true)
public class BlindManagerImpl implements BlindManager {
	
	@Autowired
	private WyBlindDao wyBlindDao;

	public int countByConds(Object map) throws Exception {
		return wyBlindDao.countByConds(map);
	}

	public List<WyBlind> getByConds(Object map) throws Exception {
		return wyBlindDao.getByConds(map);
	}

	public int insert(WyBlind record) throws Exception {
		return wyBlindDao.insert(record);
	}

	public int deleteByDeleteFlag(Object map) throws Exception {
		return wyBlindDao.deleteByDeleteFlag(map);
	}

	public WyBlind getById(Long id) {
		return wyBlindDao.selectByPrimaryKey(id);
	}

	public int update(WyBlind record) throws Exception {
		return wyBlindDao.updateByPrimaryKeySelective(record);
	}

	public int importInsert(Map<String, Object> map) throws Exception {
		WyBlind wyBlind = new WyBlind();
		BeanUtils.populate(wyBlind, map);
		
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", wyBlind.getName());
        List<WyBlind> wyBlindList = wyBlindDao.getByConds(paramMap);
        WyBlind oldBlind = wyBlindList.size()>0?wyBlindList.get(0):null;
        if(oldBlind == null){
        	wyBlindDao.insert(wyBlind);
        }else{
        	wyBlind.setId(oldBlind.getId());
        	wyBlindDao.updateByPrimaryKeySelective(wyBlind);
        }
		return 0;
	}
	
}
