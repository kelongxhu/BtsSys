package com.scttsc.business.service.impl;

import com.scttsc.business.dao.DryStationDao;
import com.scttsc.business.model.DryStation;
import com.scttsc.business.service.DryStationManager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("dryStationManager")
@Transactional(readOnly = true)
public class DryStationManagerImpl implements DryStationManager {

    @Autowired
    private DryStationDao dryStationDao;


    public int countByExample(Map example) {
        return dryStationDao.countByExample(example);
    }


    public int deleteByExample(Map example) {
        return dryStationDao.deleteByExample(example);
    }


    public int deleteByPrimaryKey(BigDecimal id) {
        return dryStationDao.deleteByPrimaryKey(id);
    }


    public int insert(DryStation record) {
        return dryStationDao.insert(record);
    }


    public int insertSelective(DryStation record) {
        return dryStationDao.insertSelective(record);
    }


    public List<DryStation> selectByExample(Map example) {
        return dryStationDao.selectByExample(example);
    }


    public DryStation selectByPrimaryKey(BigDecimal id) {
        return dryStationDao.selectByPrimaryKey(id);
    }


    public int updateByExampleSelective(DryStation record) {
        return dryStationDao.updateByExampleSelective(record);
    }


    public int updateByExample(DryStation record) {
        return dryStationDao.updateByExample(record);
    }


    public int updateByPrimaryKeySelective(DryStation record) {
        return dryStationDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 导入干放站数据
     *
     * @param record
     * @return
     */
    public int importInsert(Map record)throws Exception{
        DryStation dryStation=new DryStation();
        BeanUtils.populate(dryStation, record);
        return dryStationDao.insert(dryStation);
    }


    public int updateByPrimaryKey(DryStation record) {
        return dryStationDao.updateByPrimaryKey(record);
    }
}
