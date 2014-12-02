package com.scttsc.charge.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.scttsc.charge.model.WyBtsCharge;

/**
 * Created by _think on 2014/11/10.
 */
public interface WyBtsChargeManager {
    /**
     * 室外覆盖站点的房屋费用设置列表
     *
     * @param param
     * @return
     */
    List<WyBtsCharge> selectWyBtsChargeListByMap(Map<String, Object> param, int btsType) throws Exception;

    int selectWyBtsChargeCountByMap(Map<String, Object> param, int btsType) throws Exception;

    WyBtsCharge selectByPrimaryKey(BigDecimal intId, int costType, int btsType) throws Exception;

    List<WyBtsCharge> selectWyBtsChargeSettingByMap(final Map<String, Object> param, int btsType) throws Exception;

    int selectWyBtsChargeSettingCountByMap(final Map<String, Object> param, int btsType) throws Exception;

    /**
     * sendMsg
     *
     * @param smgp
     * @return
     * @throws Exception
     */
    int sendMsg(WyBtsCharge wyBtsCharge) throws Exception;

    /**
     * update btsCharge propery by map
     * @param param
     * @return
     * @throws Exception
     */
    public int updateByMap(Map<String,Object> param)throws Exception;

    void inserOrUpdateChargeSetting(WyBtsCharge wyBtsCharge)throws Exception;
    
    /**
     * 
     * @param ids
     * @param costType
     * @throws Exception
     */
    void deleteChargeSetting(String ids, int costType)throws Exception;
    
    /**
     * 
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectBtsChargeSettingListByMap(Map<String,Object> param)throws Exception;
    
    /**
     * 批量插入费用设置数据
     * @param chargeMapList
     * @throws Exception
     */
    void insertChargeSetting(List<Map<String, String>> chargeMapList) throws Exception;
    
}
