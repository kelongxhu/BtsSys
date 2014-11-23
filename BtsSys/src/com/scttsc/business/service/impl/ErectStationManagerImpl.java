package com.scttsc.business.service.impl;

import com.scttsc.business.dao.ErectStationDao;
import com.scttsc.business.model.ErectStation;
import com.scttsc.business.service.ErectStationManager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("erectStationManager")
@Transactional(readOnly = true)
public class ErectStationManagerImpl implements ErectStationManager {

    @Autowired
    private ErectStationDao erectStationDao;


    public int countByExample(Map example) {
        try {
            int i = erectStationDao.countByExample(example);
            return i;
        } catch (Exception e) {
            return 0;
        }
    }


    public int deleteByExample(Map example) {
        return erectStationDao.deleteByExample(example);
    }


    public int deleteByPrimaryKey(BigDecimal id) {
        return erectStationDao.deleteByPrimaryKey(id);
    }


    public int insert(ErectStation record) {
        return erectStationDao.insert(record);
    }


    public int importInsert(Map record) throws Exception {
        ErectStation erectStation = new ErectStation();
        BeanUtils.populate(erectStation, record);
        return erectStationDao.insert(erectStation);
    }


    public int insertSelective(ErectStation record) {
        return erectStationDao.insertSelective(record);
    }


    public List<ErectStation> selectByExample(Map example) {
        return erectStationDao.selectByExample(example);
    }


    public ErectStation selectByPrimaryKey(BigDecimal id) {
        return erectStationDao.selectByPrimaryKey(id);
    }


    public int updateByExampleSelective(ErectStation record) {
        return erectStationDao.updateByExampleSelective(record);
    }


    public int updateByExample(ErectStation record) {
        return erectStationDao.updateByExample(record);
    }


    public int updateByPrimaryKeySelective(ErectStation record) {
        return erectStationDao.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(ErectStation record) {
        return erectStationDao.updateByPrimaryKey(record);
    }
}
