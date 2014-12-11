package com.scttsc.business.service.impl;

import com.scttsc.admin.dao.CityDao;
import com.scttsc.business.dao.BtsDao;
import com.scttsc.business.dao.IndoorManualDao;
import com.scttsc.business.model.BbuManual;
import com.scttsc.business.model.IndoorManual;
import com.scttsc.business.service.IndoorManualManager;
import com.scttsc.business.service.WyLogManager;
import com.scttsc.business.vo.FindBackReponse;
import com.scttsc.common.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("indoorManualManager")
@Transactional(readOnly = true)
public class IndoorManualManagerImpl implements IndoorManualManager {

    public static Logger LOG = Logger.getLogger(BbuManualManagerImpl.class);

    @Autowired
    private IndoorManualDao indoorManualDao;

    @Autowired
    private BtsDao btsDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private WyLogManager wyLogManager;


    public int countByExample(IndoorManual example) {
        return indoorManualDao.countByExample(example);
    }


    public int deleteByPrimaryKey(BigDecimal intId) {
        return indoorManualDao.deleteByPrimaryKey(intId);
    }

    /**
     * 插入室分手工数据
     *
     * @param record
     * @return
     */
    public void insert(IndoorManual record) throws Exception {
        if (0 == record.getAddFlag()) {
            //关联增加，将手工标识更新为1
            Map map = new HashMap();
            map.put("intId", record.getIntId());
            map.put("manualFlag", 1);
            btsDao.updateByMap(map);
        }
        indoorManualDao.insert(record);

        //切入日志
        wyLogManager.insertLog(record);
    }


    public List<IndoorManual> selectByExample(IndoorManual example) {
        return indoorManualDao.selectByExample(example);
    }


    public IndoorManual selectByPrimaryKey(Long intId) {
        return indoorManualDao.selectByPrimaryKey(intId);
    }

    /**
     * 更新室分手工数据
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(IndoorManual record) throws Exception{
        wyLogManager.updateLog(record);
        return indoorManualDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 导入BBU数据
     */
    public void importInsert(Map record) throws Exception {
        String intId = StringUtil.null2String(record.get("intId"));
        if (intId == null || "".equals(intId)) {
            String code = StringUtil.null2String(record.get("name")) + StringUtil.null2String(record.get("bscName")) + StringUtil.null2String(record.get("btsName"));
            intId = code.hashCode() + "";
            record.put("addFlag", 1L);
        } else {
            record.put("addFlag", 0L);
        }
        if (intId != null) {
            IndoorManual indoorManual=new IndoorManual();
            BeanUtils.populate(indoorManual, record);
            IndoorManual obj = selectByPrimaryKey(new Long(indoorManual.getIntId()));
            if (obj == null) {
                //插入
                insert(indoorManual);
            } else {
                //覆盖更新
                updateByPrimaryKeySelective(indoorManual);
            }
        }

    }

    /**
     * @param map
     * @return
     * @throws Exception
     * @author longke
     */
    public List<IndoorManual> selectByMap(Object map) throws Exception {
        return indoorManualDao.selectByMap(map);
    }

    /**
     * @param map
     * @return
     * @throws Exception
     * @author longke
     */
    public int countByMap(Object map) throws Exception {
        return indoorManualDao.countByMap(map);
    }

    /**
     * 组装按字段导出查询的全部字段
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectExportIndoorDataByMap(Object map) throws Exception {
        return indoorManualDao.selectExportIndoorDataByMap(map);
    }


    public List<IndoorManual> selectByCons(Object map) throws Exception {
        return indoorManualDao.selectByCons(map);
    }

    /**
     * 查询未录入直放站的手工数据
     * @return
     * @throws Exception
     */
    public List<IndoorManual> selectByNoInputErect(Object map) throws Exception {
        return indoorManualDao.selectByNoInputErect(map);
    }

    /**
     * 查询未录入干放站的手工数据
     * @return
     * @throws Exception
     */
    public List<IndoorManual> selectByNoInputDry(Object map) throws Exception {
        return indoorManualDao.selectByNoInputDry(map);
    }

    /**
     * 动态自定义统计字段统计
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> selectIndoorDataGroupByCoulmns(Map map) throws Exception {
        return indoorManualDao.selectIndoorDataGroupByCoulmns(map);
    }

    public int countIndoorDataGroupByColumns(Map map) throws Exception {
        return indoorManualDao.countIndoorDataGroupByColumns(map);
    }

    /**
     * 接口修改室分手工数据
     * @param map
     * @return
     * @throws Exception
     */
    public int updateByInterface(Map map) throws Exception {
        return indoorManualDao.updateByInterface(map);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public FindBackReponse findManualData(List<String> ids) {
        FindBackReponse reponse = new FindBackReponse();
        int sucess = 0;
        try {
            for (String intIdObj : ids) {
                String[] pair = intIdObj.split(",");
                IndoorManual indoorManual = indoorManualDao.selectByPrimaryKey(new Long(pair[0]));//废弃数据的手工数据
                //找回原则：废弃存在手工数据，当前再用不存在手工数据
                if (indoorManual != null) {
                    Long intId= new Long(pair[1]);//再用intId
                    IndoorManual indoorManualNow=indoorManualDao.selectByPrimaryKey(intId);
                    if(indoorManualNow!=null){
                        indoorManualDao.deleteByPrimaryKey(new BigDecimal(pair[1]));
                    }
                    //将废弃数据手工数据关联到现在的数据INT_ID
                    Map map = new HashMap();
                    map.put("newIntId", new Long(pair[1]));
                    map.put("intId", indoorManual.getIntId());
                    indoorManualDao.updateByIntID(map);
                    //如果现在有数据，删除现在的，避免删除失败
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
}
