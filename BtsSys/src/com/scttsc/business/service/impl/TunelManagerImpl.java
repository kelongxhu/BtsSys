package com.scttsc.business.service.impl;

import com.scttsc.business.dao.WyTunelDao;
import com.scttsc.business.model.WyTunel;
import com.scttsc.business.service.TunelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/9/25.
 */
@Service("tunelManager")
@Transactional(readOnly = true)
public class TunelManagerImpl implements TunelManager {
    @Autowired
    private WyTunelDao wyTunelDao;

    public List<WyTunel> selectByMap(Map record) throws Exception {
        return wyTunelDao.selectByMap(record);
    }

    public int countByMap(Map record) throws Exception {
        return wyTunelDao.countByMap(record);
    }

    public WyTunel selectByPrimaryKey(Long intId) {
        return wyTunelDao.selectByPrimaryKey(intId);
    }

    public int updateByMap(Map record) throws Exception {
        return wyTunelDao.updateByMap(record);
    }

    public WyTunel selectByName(String name) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        param.put("deleteFlag", 0);
        List<WyTunel> tunelList = wyTunelDao.selectByConds(param);
        if (tunelList != null && tunelList.size() > 0) {
            return tunelList.get(0);
        }
        return null;
    }


    public List<WyTunel> selectByConds(Map record) throws Exception {
        return wyTunelDao.selectByConds(record);
    }

    public List<Map> selectWyTunelGroupByColumns(Map record) throws Exception {
        return wyTunelDao.selectWyTunelGroupByColumns(record);
    }

    public int countWytunelGroupByColumns(Map record) throws Exception {
        return wyTunelDao.countWytunelGroupByColumns(record);
    }

    public List<Map> selectWyTunelCellGroupByColumns(Map record) throws Exception {
        return wyTunelDao.selectWyTunelCellGroupByColumns(record);
    }

    public int countWytunelCellGroupByColumns(Map record) throws Exception {
        return wyTunelDao.countWytunelCellGroupByColumns(record);
    }
}
