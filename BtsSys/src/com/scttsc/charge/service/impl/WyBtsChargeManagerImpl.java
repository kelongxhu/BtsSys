package com.scttsc.charge.service.impl;

import com.scttsc.charge.dao.WyBtsChargeDao;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.service.WyBtsChargeManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/11/10.
 */
@Service("wyBtsChargeManager")
public class WyBtsChargeManagerImpl implements WyBtsChargeManager {
    Logger LOG = Logger.getLogger(WyBtsChargeManagerImpl.class);
    @Autowired
    private WyBtsChargeDao wyBtsChargeDao;

    public List<WyBtsCharge> selectWyBtsChargeListByMap(Map<String, Object> param, int btsType) throws Exception {
        List<WyBtsCharge> wyBtsCharges = null;
        switch (btsType) {
            case 1:
                //室内
                param.put("btsType", btsType);
                wyBtsCharges = wyBtsChargeDao.selectWyBtsChargeListByMap(param);
                break;
            case 2:
                wyBtsCharges = wyBtsChargeDao.selectWyBbuChargeListByMap(param);
                break;
            case 3:
                param.put("btsType", btsType);
                wyBtsCharges = wyBtsChargeDao.selectWyBtsChargeListByMap(param);
                break;
            case 6:
                wyBtsCharges = wyBtsChargeDao.selectWyTunelChargeListByMap(param);
                break;
            default:
                break;
        }
        return wyBtsCharges;
    }

    public int selectWyBtsChargeCountByMap(Map<String, Object> param, int btsType) throws Exception {
        int total = 0;
        switch (btsType) {
            case 1:
                param.put("btsType", btsType);
                total = wyBtsChargeDao.selectWyBtsChargeCountByMap(param);
                break;
            case 2:
                total = wyBtsChargeDao.selectWyBbuChargeCountByMap(param);
                break;
            case 3:
                param.put("btsType", btsType);
                total = wyBtsChargeDao.selectWyBtsChargeCountByMap(param);
                break;
            case 6:
                total = wyBtsChargeDao.selectWyTunelChargeCountByMap(param);
                break;
        }
        return total;
    }

    public WyBtsCharge selectByPrimaryKey(BigDecimal intId, int costType, int btsType) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("intId", intId);
        param.put("costType", costType);
        WyBtsCharge wyBtsCharge = null;
        try {
            switch (btsType) {
                case 1:
                    wyBtsCharge = wyBtsChargeDao.selectChargeWithBtsByAis(param);
                    break;
                case 2:
                    wyBtsCharge = wyBtsChargeDao.selectChargeWithBbuByAis(param);
                    break;
                case 3:
                    wyBtsCharge = wyBtsChargeDao.selectChargeWithBtsByAis(param);
                    break;
                case 6:
                    wyBtsCharge = wyBtsChargeDao.selectChargeWithTunelByAis(param);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return wyBtsCharge;
    }
}
