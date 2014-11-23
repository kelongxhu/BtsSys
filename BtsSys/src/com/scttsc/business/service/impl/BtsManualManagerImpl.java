package com.scttsc.business.service.impl;

import com.scttsc.business.dao.BtsDao;
import com.scttsc.business.dao.BtsManualDao;
import com.scttsc.business.dao.CellDao;
import com.scttsc.business.model.BtsManual;
import com.scttsc.business.model.Cell;
import com.scttsc.business.service.BtsManualManager;
import com.scttsc.business.service.WyLogManager;
import com.scttsc.business.util.DateConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("btsManualManager")
@Transactional(readOnly = true)
public class BtsManualManagerImpl implements BtsManualManager {

    @Autowired
    private BtsManualDao btsManualDao;
    @Autowired
    private BtsDao btsDao;
    @Autowired
    private CellDao cellDao;
    @Autowired
    private WyLogManager wyLogManager;

    public BtsManual getById(Long intId) throws Exception {
        return btsManualDao.getById(intId);
    }

    /**
     * 录入手工数据
     */
    public int insert(BtsManual record) throws Exception {

        BtsManual obj = fullObj(record);  //填充字段
        int j = btsManualDao.insert(record);
        // 手工标识置为1
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("manualFlag", 1);
        map.put("intId", record.getIntId());
        btsDao.updateByMap(map);

        //切入日志
        wyLogManager.insertLog(record);

        return j;
    }

    /**
     * 填充合成字段
     *
     * @param record
     * @return
     */
    public BtsManual fullObj(BtsManual record) {
        StringBuilder sb1x = null;
        StringBuilder sbdo = null;
        try {
            List<Cell> cells = cellDao.selectByBtsId(record.getIntId());
            sb1x = new StringBuilder("S");
            sbdo = new StringBuilder("S");
            for (Cell cell : cells) {
                Map<String, Object> seach1xMap = new HashMap<String, Object>();
                seach1xMap.put("carType", "1X");
                seach1xMap.put("cellId", cell.getIntId());
                int car1xNum = cellDao.countCarrierByCell(seach1xMap);
                Map<String, Object> seachdoMap = new HashMap<String, Object>();
                seachdoMap.put("carType", "DO");
                seachdoMap.put("cellId", cell.getIntId());
                int cardoNum = cellDao.countCarrierByCell(seachdoMap);
                sb1x.append(car1xNum);
                sbdo.append(cardoNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        record.setConf1x(sb1x.toString());
        record.setConfDo(sbdo.toString());
        return record;
    }

    /**
     * 导入物理站点手工数据步骤
     *
     * @param record
     * @throws Exception
     */
    public void importInsert(Map record) throws Exception {
        BtsManual btsManual = new BtsManual();
        DateConverter d = new DateConverter();
        String[] datePattern = {"yyyy-MM"};
        d.setPatterns(datePattern);
        ConvertUtils.register(d, java.util.Date.class);
        BeanUtils.populate(btsManual, record);
        btsManual.setUpdatetime(new Date());
        BtsManual obj = getById(btsManual.getIntId().longValue());
        if (obj == null) {
            //插入
            insert(btsManual);
        } else {
            //覆盖更新
            update(btsManual);
        }
    }

    /**
     * 更新物理站点手工数据
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int update(BtsManual record) throws Exception {
        //切入日志
        wyLogManager.updateLog(record);
        //更新操作
        btsManualDao.updateByMap(record);
        return 0;
    }

    /**
     * 手机接口更改基站属性
     * @param record
     * @return
     * @throws Exception
     */
    public int updateByInterface(Map record) throws Exception {
        return btsManualDao.updateByInterface(record);
    }
}
