package com.scttsc.charge.dao;

import com.scttsc.charge.dto.BtsDTO;
import com.scttsc.charge.model.WyBtsChargeList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WyBtsChargeListDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(WyBtsChargeList record);

    WyBtsChargeList selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(WyBtsChargeList record);

    List<WyBtsChargeList> selectByBtsId(Map<String, Object> param) throws Exception;

    List<WyBtsChargeList> selectByMap(Map<String,Object> param)throws Exception;

    int countByMap(Map<String, Object> param)throws Exception;

    List<BtsDTO> selectBtsByMap(Map<String,Object> param)throws Exception;
}