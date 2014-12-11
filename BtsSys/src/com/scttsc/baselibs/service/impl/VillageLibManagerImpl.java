package com.scttsc.baselibs.service.impl;

import com.scttsc.baselibs.dao.WyLibVillageDao;
import com.scttsc.baselibs.model.WyLibVillage;
import com.scttsc.baselibs.service.VillageLibManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/12/6.
 */
@Service("villageLibManager")
public class VillageLibManagerImpl implements VillageLibManager {
    @Autowired
    private WyLibVillageDao wyLibVillageDao;

    public List<WyLibVillage> selectByMap(Map<String, Object> param) throws Exception {
        return wyLibVillageDao.selectByMap(param);
    }

    public int countByMap(Map<String, Object> param) throws Exception {
        return wyLibVillageDao.countByMap(param);
    }

    public List<WyLibVillage> selectByConds(Map<String, Object> param) throws Exception {
        return wyLibVillageDao.selectByConds(param);
    }

    public List<Map> selectTownByCountryId(Integer countryId) throws Exception {
        return wyLibVillageDao.selectTownByCountryId(countryId);
    }
}
