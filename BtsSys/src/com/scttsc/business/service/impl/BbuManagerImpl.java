package com.scttsc.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scttsc.business.model.Bts;
import com.scttsc.business.model.MateEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.business.dao.BbuDao;
import com.scttsc.business.model.Bbu;
import com.scttsc.business.service.BbuManager;

@Service("bbuManager")
@Transactional(readOnly = true)
public class BbuManagerImpl implements BbuManager {

    @Autowired
    private BbuDao bbuDao;


    public int countByExample(Object example) {
        return bbuDao.countByExample(example);
    }


    public List<Bbu> selectByExample(Object example) {
        return bbuDao.selectByExample(example);
    }


    public Bbu getById(Long id) throws Exception {
        return bbuDao.getById(id);
    }

    public List<Bbu> selectByMap(Object map) throws Exception {
        return bbuDao.selectByMap(map);
    }

    public int updateByMap(Object record) {
        return bbuDao.updateByMap(record);
    }


    public List<Bbu> selectBbuByConds(Object map) throws Exception {
        return bbuDao.selectBbuByConds(map);
    }

    public List<Map> selectExportBbuDataByMap(Object map) throws Exception {
        return bbuDao.selectExportBbuDataByMap(map);
    }

    /**
     * 按动态字段统计
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectBbuGroupByColumns(Object map) throws Exception {
        return bbuDao.selectBbuGroupByColumns(map);
    }

    public int countBbuGroupByColumns(Object map) throws Exception {
        return bbuDao.countBbuGroupByColumns(map);
    }


    public Bbu selectByName(String name) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        param.put("deleteFlag", 0);
        param.put("isShare", 0);
        param.put("bbuType", "1,2");//纯BBU数据
        List<Bbu> bbuList = bbuDao.selectByMap(param);
        if (bbuList != null && bbuList.size() > 0) {
            return bbuList.get(0);
        }
        return null;
    }
}
