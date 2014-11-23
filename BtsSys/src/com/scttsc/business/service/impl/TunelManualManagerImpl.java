package com.scttsc.business.service.impl;

import com.scttsc.business.dao.WyTunelDao;
import com.scttsc.business.dao.WyTunelManualDao;
import com.scttsc.business.model.*;
import com.scttsc.business.service.TunelManualManager;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.DateConverter;
import com.scttsc.business.vo.FindBackReponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by _think on 2014/9/27.
 */
@Service("tunelManualManager")
@Transactional(readOnly = true)
public class TunelManualManagerImpl implements TunelManualManager {

    private static Logger LOG = Logger.getLogger(TunelManualManagerImpl.class);
    @Autowired
    private WyTunelManualDao wyTunelManualDao;

    @Autowired
    private WyTunelDao wyTunelDao;

    public WyTunelManual selectByPrimaryKey(Long intId) {
        return wyTunelManualDao.selectByPrimaryKey(intId);
    }

    public int insert(WyTunelManual record) throws Exception {
        //插入手工数据，更新wy_tunel为手工录入状态
        wyTunelManualDao.insert(record);
        WyTunel wyTunel=new WyTunel();
        wyTunel.setIntId(record.getIntId());
        wyTunel.setManualFlag(1);
        wyTunelDao.updateByPrimaryKeySelective(wyTunel);
        return Constants.SUCECSS;
    }

    public int updateByPrimaryKeySelective(WyTunelManual record) throws Exception {
        return wyTunelManualDao.updateByPrimaryKeySelective(record);
    }

    public int importInsert(Map record) throws Exception {
        int status=Constants.FAIL;
        try {
            WyTunelManual wyTunelManual = new WyTunelManual();
            DateConverter d = new DateConverter();
            String[] datePattern = {"yyyy-MM"};
            d.setPatterns(datePattern);
            ConvertUtils.register(d, Date.class);
            BeanUtils.populate(wyTunelManual, record);
            wyTunelManual.setIntime(new Date());
            WyTunelManual manualObj = selectByPrimaryKey(wyTunelManual.getIntId());
            if (manualObj == null) {
                //插入
                insert(wyTunelManual);
            } else {
                //覆盖更新
                updateByPrimaryKeySelective(wyTunelManual);
            }
            status=Constants.SUCECSS;
        } catch (Exception e) {
            LOG.error(e.getMessage()+"-"+"导入隧道库手工数据失败!",e);
        }
        return status;
    }

    public List<MateEntry> findDelTunelData(List<Long> ids) throws Exception {
        List<MateEntry> mateEntries = new ArrayList<MateEntry>();
        for (Long delIntId : ids) {
            WyTunel tunelDel = wyTunelDao.selectByPrimaryKey(delIntId);
            if (tunelDel == null) {
                continue;
            }
            //根据名称查找再用数据
            Map map = new HashMap();
            map.put("name", tunelDel.getName());
            map.put("deleteFlag", 0);
            List<WyTunel> tunelList = wyTunelDao.selectByConds(map);//在用bts
            WyTunel wyTunel = null;
            if (tunelList != null && tunelList.size() > 0) {
                wyTunel = tunelList.get(0);
            }
            //再用的未录入
            if (wyTunel != null&&wyTunel.getManualFlag()==0) {
                MateEntry mateEntry = new MateEntry();
                mateEntry.setDel_IntId(tunelDel.getIntId().toString());
                mateEntry.setDel_BscName(tunelDel.getBscName());
                mateEntry.setDel_Name(tunelDel.getName());
                mateEntry.setDel_BtsId(tunelDel.getBtsId());
                mateEntry.setIntId(wyTunel.getIntId().toString());
                mateEntry.setBscName(wyTunel.getBscName());
                mateEntry.setName(wyTunel.getName());
                mateEntry.setBtsId(wyTunel.getBtsId());
                mateEntries.add(mateEntry);
            }
        }
        return mateEntries;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindBackReponse findManualData(List<String> ids) {
        FindBackReponse reponse = new FindBackReponse();
        int sucess = 0;
        try {
            for (String intIdObj : ids) {
                String[] pair = intIdObj.split(",");
                WyTunelManual wyTunelManual = wyTunelManualDao.selectByPrimaryKey(new Long(pair[0]));//废弃数据的手工数据
                //找回原则：废弃存在手工数据，当前再用不存在手工数据
                if (wyTunelManual != null) {
                    Long intId= new Long(pair[1]);//再用intId
                    WyTunelManual wyTunelManual1=wyTunelManualDao.selectByPrimaryKey(intId);
                    if(wyTunelManual1!=null){
                        wyTunelManualDao.deleteByPrimaryKey(intId);
                    }
                    //将废弃数据手工数据关联到现在的数据INT_ID
                    Map map = new HashMap();
                    map.put("newIntId", new Long(pair[1]));
                    map.put("intId", wyTunelManual.getIntId());
                    wyTunelManualDao.updateByIntID(map);
                    //如果现在有数据，删除现在的，避免删除失败
                    //将现在的数据INT_ID状态更新为已录入
                    WyTunel wyTunel=new WyTunel();
                    wyTunel.setIntId(new Long(pair[1]));
                    wyTunel.setManualFlag(1);
                    wyTunelDao.updateByPrimaryKeySelective(wyTunel);
                    //将废弃数据INT_ID状态更新未未录入
                    WyTunel wyTunel2=new WyTunel();
                    wyTunel.setIntId(new Long(pair[0]));
                    wyTunel.setManualFlag(0);
                    wyTunelDao.updateByPrimaryKeySelective(wyTunel2);
                    sucess++;
                }
            }
            reponse.setResult("Y");
            reponse.setSucessNum(sucess);
        } catch (Exception e) {
            reponse.setSucessNum(sucess);
            reponse.setResult("N");
            LOG.error(e.getMessage(), e);
        }
        return reponse;
    }

    public List<Map> selectWyTunelGroupByColumns(Map record) throws Exception {
        return wyTunelDao.selectWyTunelGroupByColumns(record);
    }

    public int countWytunelGroupByColumns(Map record) throws Exception {
        return wyTunelDao.countWytunelGroupByColumns(record);
    }
}
