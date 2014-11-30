package com.scttsc.charge.service.impl;

import com.scttsc.business.service.BbuManager;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.TunelManager;
import com.scttsc.business.util.DateConverter;
import com.scttsc.charge.dao.WyBtsChargeDao;
import com.scttsc.charge.dao.WyBtsChargeListDao;
import com.scttsc.charge.dto.BtsDto;
import com.scttsc.charge.dto.PayStatistDto;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.model.WyBtsChargeList;
import com.scttsc.charge.service.WyBtsChargeListManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by _think on 2014/11/10.
 */
@Service("wyBtsChargeListManager")
@Transactional(readOnly = true)
public class WyBtsChargeListManagerImpl implements WyBtsChargeListManager {
    Logger LOG = Logger.getLogger(WyBtsChargeListManagerImpl.class);
    @Autowired
    private WyBtsChargeListDao chargeListDao;
    @Autowired
    private WyBtsChargeDao wyBtsChargeDao;
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private BbuManager bbuManager;
    @Autowired
    private TunelManager tunelManager;

    public int deleteByPrimaryKey(BigDecimal id) {
        return chargeListDao.deleteByPrimaryKey(id);
    }

    public int insert(WyBtsChargeList record) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("intId", record.getIntId());
        BtsDto btsDTO = selectBtsByMap(param);
        if (btsDTO != null) {
            record.setBtsName(btsDTO.getName());
            record.setBscName(btsDTO.getBscName());
            record.setBtsId(btsDTO.getBtsId());
            record.setCityId(btsDTO.getCityId());
            record.setCountryId(btsDTO.getCountryId());
        }
        return chargeListDao.insert(record);
    }

    public WyBtsChargeList selectByPrimaryKey(BigDecimal id) {
        return chargeListDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WyBtsChargeList record) {
        return chargeListDao.updateByPrimaryKeySelective(record);
    }

    public List<WyBtsChargeList> selectByBtsId(BigDecimal intId, int costType) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("intId", intId);
        param.put("costType", costType);
        return chargeListDao.selectByBtsId(param);
    }

    public BtsDto selectBtsByMap(Map<String, Object> param) {
        BtsDto btsDTO = null;
        try {
            List<BtsDto> btsDTOs = chargeListDao.selectBtsByMap(param);
            if (btsDTOs != null && btsDTOs.size() > 0) {
                btsDTO = btsDTOs.get(0);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return btsDTO;
    }


    public int importChargeData(List<Map<String, Object>> data) {
        int success = 0;
        try {
            for (Map<String, Object> objectMap : data) {
                WyBtsChargeList chargeBill = new WyBtsChargeList();
                DateConverter d = new DateConverter();
                String[] datePattern = {"yyyy-MM-dd"};
                d.setPatterns(datePattern);
                ConvertUtils.register(d, Date.class);
                BeanUtils.populate(chargeBill, objectMap);
                Date payTime = chargeBill.getPayTime();
                Calendar c = Calendar.getInstance();
                c.setTime(payTime);
                WyBtsCharge charge=wyBtsChargeDao.selectByPrimaryKey(chargeBill.getIntId(), chargeBill.getCostType());
                int day = c.get(Calendar.DATE);
                if (day > charge.getPayDay()) {
                    chargeBill.setIsTimeout((short) 1);//超时
                } else {
                    chargeBill.setIsTimeout((short) 0);//不超时
                }
                success += chargeListDao.insert(chargeBill);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return success;
    }


    public List<WyBtsChargeList> selectByMap(Map<String, Object> param) throws Exception {
        return chargeListDao.selectByMap(param);
    }

    public int countByMap(Map<String, Object> param) throws Exception {
        return chargeListDao.countByMap(param);
    }

    public List<PayStatistDto> statisticsPay(Map<String, Object> param) throws Exception {
        return chargeListDao.statisticsPay(param);
    }

    public int countStatisticsPay(Map<String, Object> param) throws Exception {
        return chargeListDao.countStatisticsPay(param);
    }

    public int deleteByPrimaryKeys(List<BigDecimal> idList) throws Exception {
        return chargeListDao.deleteByPrimaryKeys(idList);
    }
}
