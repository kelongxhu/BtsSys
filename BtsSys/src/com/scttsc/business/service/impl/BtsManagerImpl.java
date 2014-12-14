package com.scttsc.business.service.impl;

import com.scttsc.business.dao.BtsDao;
import com.scttsc.business.dao.BtsManualDao;
import com.scttsc.business.dao.CBtsDao;
import com.scttsc.business.model.Bts;
import com.scttsc.business.model.BtsManual;
import com.scttsc.business.model.CBts;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.vo.FindBackReponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("btsManager")
@Transactional(readOnly = true)
public class BtsManagerImpl implements BtsManager {

    public static Logger LOG = Logger.getLogger(BtsManagerImpl.class);

    @Autowired
    private BtsDao btsDao;
    @Autowired
    private CBtsDao cBtsDao;
    @Autowired
    private BtsManualDao btsManualDao;


    public List<Bts> getByConds(Object map) throws Exception {
        return btsDao.getByConds(map);
    }

    public int countByConds(Object map) throws Exception {
        return btsDao.countByConds(map);
    }

    public Bts getById(Long id) throws Exception {
        return btsDao.getById(id);
    }

    public void updateByMap(Object map) throws Exception {
        btsDao.updateByMap(map);
    }

    public List<Bts> selectByMap(Object map) throws Exception {
        return btsDao.selectByMap(map);
    }

    public CBts selectCBtsById(Long intId) throws Exception {
        return cBtsDao.selectById(intId);
    }

    /**
     * 查询所有待导出字段
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectExportDataByMap(Object map) throws Exception {
        return btsDao.selectExportDataByMap(map);
    }

    /**
     * 按照动态字段统计基站数量
     *
     * @param columnsList ：统计字段
     * @return
     * @throws Exception
     */

    public List<Map> selectBtsGroupByColumns(Object record) throws Exception {
        return btsDao.selectBtsGroupByColumns(record);
    }

    /**
     * 通过Map，bscName和btsId查找基站
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectWyBtsByMap(Map map) throws Exception {
        return btsDao.selectWyBtsByMap(map);
    }

    /**
     * 通过字段名统计数量
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int countBtsGroupByColumns(Object record) throws Exception {
        return btsDao.countBtsGroupByColumns(record);
    }


    public List<Map> selectWyIndoorBtsGroupByColumns(Map<String, Object> param) throws Exception {
        return btsDao.selectWyIndoorBtsGroupByColumns(param);
    }

    public int countWyIndoorBtsGroupByColumns(Map<String, Object> param) throws Exception {
        return btsDao.countWyIndoorBtsGroupByColumns(param);
    }

    public List<MateEntry> findDelBtsData(List<Long> ids) throws Exception {
        List<MateEntry> mateEntries = new ArrayList<MateEntry>();
        for (Long delIntId : ids) {
            Bts btsDel = btsDao.getById(delIntId);
            if (btsDel == null) {
                continue;
            }
            Map map = new HashMap();
            map.put("wybtsName", btsDel.getName());
            map.put("deleteFlag", 0);
            List<Bts> btsList = btsDao.selectByMap(map);//在用bts
            Bts bts = null;
            if (btsList != null && btsList.size() > 0) {
                bts = btsList.get(0);
            }
            //匹配上，且未录入
            if (bts != null && bts.getManualFlag().intValue() == 0) {
                MateEntry mateEntry = new MateEntry();
                mateEntry.setDel_IntId(btsDel.getIntId().toString());
                mateEntry.setDel_BscName(btsDel.getBscName());
                mateEntry.setDel_Name(btsDel.getName());
                mateEntry.setDel_BtsId(btsDel.getBtsId().intValue());
                mateEntry.setDel_ManualFlag(btsDel.getManualFlag().intValue());
                mateEntry.setIntId(bts.getIntId().toString());
                mateEntry.setBscName(bts.getBscName());
                mateEntry.setName(bts.getName());
                mateEntry.setBtsId(bts.getBtsId().intValue());
                mateEntry.setManualFlag(bts.getManualFlag().intValue());
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
                BtsManual btsManual = btsManualDao.getById(new Long(pair[0]));//废弃数据的手工数据
                BtsManual btsManualNow = btsManualDao.getById(new Long(pair[1]));
                //找回原则：废弃存在手工数据，当前再用不存在手工数据
                if (btsManual != null && btsManualNow == null) {
                    //将废弃数据手工数据关联到现在的数据INT_ID
                    Map map = new HashMap();
                    map.put("newIntId", new Long(pair[1]));
                    map.put("intId", btsManual.getIntId());
                    btsManualDao.updateByIntID(map);
                    //将现在的数据INT_ID状态更新为已录入
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("manualFlag", 1);
                    map2.put("intId", new Long(pair[1]));
                    btsDao.updateByMap(map2);
                    //将废弃数据INT_ID状态更新未未录入
                    Map<String, Object> map3 = new HashMap<String, Object>();
                    map3.put("manualFlag", 0);
                    map3.put("intId", new Long(pair[0]));
                    btsDao.updateByMap(map3);
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

    public Bts selectByName(String name) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("wybtsName", name);
        param.put("deleteFlag", 0);
        List<Bts> btsList = btsDao.selectByMap(param);
        if (btsList != null && btsList.size() > 0) {
            return btsList.get(0);
        }
        return null;
    }
}
