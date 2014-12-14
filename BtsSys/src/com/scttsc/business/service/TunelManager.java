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
    int updateByMap(Map record)throws Exception;
    WyTunel selectByName(String name)throws Exception;
    List<WyTunel> selectByConds(Map record)throws Exception;
    List<Map> selectWyTunelGroupByColumns(Map record) throws Exception;
    int countWytunelGroupByColumns(Map record) throws Exception;
    List<Map> selectWyTunelCellGroupByColumns(Map record)throws Exception;
    int countWytunelCellGroupByColumns(Map record)throws Exception;
}
