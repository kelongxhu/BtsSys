package com.scttsc.baselibs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.business.util.DateConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.baselibs.dao.RoadLibDao;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.service.RoadLibManager;
@Service("roadLibManager")
@Transactional(readOnly = true)
public class RoadLibManagerImpl implements RoadLibManager {

    Logger LOG=Logger.getLogger(RoadLibManagerImpl.class);
	@Autowired
	private RoadLibDao roadLibDao;

	public int insert(RoadLib roadLib) throws Exception {
		return roadLibDao.insert(roadLib);
	}

	public int update(RoadLib roadLib) throws Exception {
		return roadLibDao.updateByPrimaryKeySelective(roadLib);
	}

	public int delete(Object map) throws Exception {
		return roadLibDao.delete(map);
	}

	public List<RoadLib> getByConds(Object map) throws Exception {
		return roadLibDao.getByConds(map);
	}

	public int countByConds(Object map) throws Exception {
		return roadLibDao.countByConds(map);
	}

	public RoadLib getById(Long id) throws Exception {
		return roadLibDao.getById(id);
	}

	public List<RoadLib> loadAll() throws Exception {
		return roadLibDao.loadAll();
	}

    public int deleteByDeleteFlag(Object map) throws Exception {
        return roadLibDao.deleteByDeleteFlag(map);
    }

    public RoadLib getByName(String name) throws Exception {
        List<RoadLib> roadLibs=roadLibDao.getByName(name);
        RoadLib roadLib=null;
        if(roadLibs!=null&&roadLibs.size()>0){
            roadLib=roadLibs.get(0);
        }
        return roadLib;
    }

    public int importInsert(List<Map<String, Object>> data) {
        int result = 0;
        try {
            DateConverter d = new DateConverter();
            String[] datePattern = {"yyyy-MM-dd", "yyyy-MM"};
            d.setPatterns(datePattern);
            ConvertUtils.register(d, Date.class);
            for (Map<String, Object> roadMap : data) {
                RoadLib roadLib = new RoadLib();
                BeanUtils.populate(roadLib, roadMap);
                roadLib.setIntime(new Date());
                roadLib.setDeleteFlag(0);
                result +=  roadLibDao.insert(roadLib);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
