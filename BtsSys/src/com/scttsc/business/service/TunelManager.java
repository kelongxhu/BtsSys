package com.scttsc.business.service;

import com.scttsc.business.model.WyTunel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/9/25.
 */
public interface TunelManager {
    List<WyTunel> selectByMap(Map record)throws Exception;
    int countByMap(Map record)throws Exception;
    WyTunel selectByPrimaryKey(Long intId);
    List<WyTunel> selectAssoByMap(Map record)throws Exception;
    int updateByMap(Map record)throws Exception;
    WyTunel selectByName(String name)throws Exception;
}
