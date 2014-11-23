package com.scttsc.charge.service;

import com.scttsc.charge.dto.BtsDTO;
import com.scttsc.charge.model.WyBtsChargeList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/11/10.
 */
public interface WyBtsChargeListManager {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(WyBtsChargeList record);

    WyBtsChargeList selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(WyBtsChargeList record);

    List<WyBtsChargeList> selectByBtsId(BigDecimal intId,int costType) throws Exception;

    /**
     * get bts obj by name and btsType
     * @param name
     * @param btsType
     * @return
     */
    public BtsDTO selectBtsByMap(Map<String,Object> param);

    /**
     * import charge data to DB
     * @param data
     * @return
     */
    int importChargeData(List<Map<String,Object>> data);

    List<WyBtsChargeList> selectByMap(Map<String,Object> param)throws Exception;

    int countByMap(Map<String, Object> param)throws Exception;






}
