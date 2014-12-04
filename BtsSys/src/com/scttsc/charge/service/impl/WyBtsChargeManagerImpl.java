package com.scttsc.charge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.scttsc.business.util.DateConverter;
import com.scttsc.charge.dao.SmgpDao;
import com.scttsc.charge.dao.WyBtsChargeDao;
import com.scttsc.charge.dao.WySmsLogDao;
import com.scttsc.charge.model.Smgp;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.model.WySmsLog;
import com.scttsc.charge.service.WyBtsChargeManager;
import com.scttsc.common.util.DateUtils;

/**
 * Created by _think on 2014/11/10.
 */
@Service("wyBtsChargeManager")
public class WyBtsChargeManagerImpl implements WyBtsChargeManager {
    Logger LOG = Logger.getLogger(WyBtsChargeManagerImpl.class);
    @Autowired
    private WyBtsChargeDao wyBtsChargeDao;
    @Autowired
    private SmgpDao smgpDao;
    @Autowired
    private WySmsLogDao wySmsLogDao;

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

    public List<WyBtsCharge> selectWyBtsChargeSettingByMap(
            final Map<String, Object> param, int btsType) throws Exception {
        List<WyBtsCharge> wyBtsChargeList = null;
        try {
			switch (btsType) {
			    case 1:
			        param.put("isInDoor", "否");
			        wyBtsChargeList = wyBtsChargeDao.selectWyBtsChargeSettingByMap(param);
			        break;
			    case 2:
			        wyBtsChargeList = wyBtsChargeDao.selectWyBbuChargeSettingByMap(param);
			        break;
			    case 3:
			        param.put("isInDoor", "是");
			        wyBtsChargeList = wyBtsChargeDao.selectWyBtsChargeSettingByMap(param);
			        break;
			    case 6:
			        wyBtsChargeList = wyBtsChargeDao.selectWyTunelChargeSettingByMap(param);
			        break;
			    default:
			        break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new Exception(e);
		}
        return wyBtsChargeList;
    }

	public int selectWyBtsChargeSettingCountByMap(
			final Map<String, Object> param, int btsType) throws Exception {
		int count = 0;
		try {
			switch (btsType) {
			case 1:
				param.put("isInDoor", "否");
				count = wyBtsChargeDao.selectWyBtsChargeSettingCountByMap(param);
				break;
			case 2:
				count = wyBtsChargeDao.selectWyBbuChargeSettingCountByMap(param);
				break;
			case 3:
				param.put("isInDoor", "是");
				count = wyBtsChargeDao.selectWyBtsChargeSettingCountByMap(param);
				break;
			case 6:
				count = wyBtsChargeDao.selectWyTunelChargeSettingCountByMap(param);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new Exception(e);
		}
		return count;
	}

    public int updateByMap(Map<String, Object> param) throws Exception {
        return wyBtsChargeDao.updateByMap(param);
    }

    public int sendMsg(WyBtsCharge wyBtsCharge) throws Exception {
        String tel = wyBtsCharge.getRemindTel();
        String remindUser = wyBtsCharge.getRemindUser();
        if (tel == null || "".equals(tel)) {
            return 0;
        }
        StringBuilder msgContent = new StringBuilder();
        msgContent.append(remindUser + ",您好.");
        msgContent.append(wyBtsCharge.getBtsName()+ wyBtsCharge.getCostTypeStr() + "缴费时间是," +
                DateUtils.formatDateTime(wyBtsCharge.getNextPayTime(), "yyyy-MM-dd"));
        msgContent.append(",请提前缴费。谢谢");
        Smgp smgp = new Smgp();
        smgp.setServiceId("wyzb");
        smgp.setDesttermId(wyBtsCharge.getRemindTel());
        smgp.setMsgContent(msgContent.toString());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("intId", wyBtsCharge.getIntId());
        paramMap.put("costType", wyBtsCharge.getCostType());
        paramMap.put("isRemind", 1);
        //记录提醒日志
        WySmsLog wySmsLog=new WySmsLog();
        wySmsLog.setIntId(wyBtsCharge.getIntId());
        wySmsLog.setBtsType(wyBtsCharge.getBtsType());
        wySmsLog.setCostType(wyBtsCharge.getCostType());
        wySmsLog.setRemindUser(wyBtsCharge.getRemindUser());
        wySmsLog.setRemindTel(wyBtsCharge.getRemindTel());
        wySmsLog.setContent(msgContent.toString());
        wySmsLog.setStatus((short)0);
        wySmsLog.setInTime(new Date());
        return wyBtsChargeDao.updateByMap(paramMap) + smgpDao.insert(smgp)+wySmsLogDao.insert(wySmsLog);
    }

	public void inserOrUpdateChargeSetting(WyBtsCharge wyBtsCharge) throws Exception {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("intId", wyBtsCharge.getIntId());
			param.put("costType", wyBtsCharge.getCostType());
			List<WyBtsCharge> wyBtsChargeList = selectBtsChargeSettingListByMap(param);
			wyBtsCharge.setInTime(new Date());
			if(CollectionUtils.isEmpty(wyBtsChargeList)){
				wyBtsChargeDao.insert(wyBtsCharge);
			} else {
				wyBtsChargeDao.updateByPrimaryKeySelective(wyBtsCharge);
			}
	}

	public void deleteChargeSetting(String ids, int costType) throws Exception {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			List<BigDecimal> idList = new ArrayList<BigDecimal>();
			String[] idsArr = ids.split(",");
            for (String id : idsArr) {
                idList.add(new BigDecimal(id));
            }
			param.put("ids", idList);
			param.put("costType", costType);
			wyBtsChargeDao.deleteChargeSetting(param);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public List<WyBtsCharge> selectBtsChargeSettingListByMap(
			Map<String, Object> param) throws Exception {
		return wyBtsChargeDao.selectBtsChargeSettingListByMap(param);
	}

	public void insertChargeSetting(List<Map<String, String>> chargeMapList)
			throws Exception {
		DateConverter d = new DateConverter();
		String[] datePattern = {"yyyy-MM-dd", "yyyy-MM"};
		d.setPatterns(datePattern);
		ConvertUtils.register(d, Date.class);
		for(Map<String, String> chargeMap : chargeMapList){
			WyBtsCharge charge = new WyBtsCharge();
			BeanUtils.populate(charge, chargeMap);
			inserOrUpdateChargeSetting(charge);
		}
		
	}
}
