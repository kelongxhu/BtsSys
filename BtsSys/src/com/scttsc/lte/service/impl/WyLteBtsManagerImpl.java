package com.scttsc.lte.service.impl;

import com.scttsc.lte.dao.WyLteBtsDao;
import com.scttsc.lte.model.WyLteBts;
import com.scttsc.lte.service.WyLteBtsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/22.
 */
@Service("wyLteBtsManager")
public class WyLteBtsManagerImpl implements WyLteBtsManager {
    @Autowired
    private WyLteBtsDao wyLteBtsDao;

    public List<WyLteBts> findWyLteBts(Map<String, Object> paramMap) {
        return wyLteBtsDao.findWyLteBts(paramMap);
    }

    public int countWyLteBts(Map<String, Object> param) {
        return wyLteBtsDao.countWyLteBts(param);
    }


    public int updateByPrimaryKeySelective(WyLteBts wyLteBts) {
        return wyLteBtsDao.updateByPrimaryKeySelective(wyLteBts);
    }
}
