package com.scttsc.business.dao;

import com.scttsc.business.model.WyTunel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WyTunelDao {
    int deleteByPrimaryKey(Long intId);

    int insert(WyTunel record);

    int insertSelective(WyTunel record);

    WyTunel selectByPrimaryKey(Long intId);

    int updateByPrimaryKeySelective(WyTunel record);

    int updateByPrimaryKey(WyTunel record);

    List<WyTunel> selectByMap(Map record)throws Exception;
    int countByMap(Map record)throws Exception;

    List<WyTunel> selectAssoByMap(Map record)throws Exception;

    List<WyTunel> selectByConds(Map record)throws Exception;

    int updateByMap(Map record)throws Exception;

    List<Map> selectWyTunelGroupByColumns(Map record)throws Exception;

    int countWytunelGroupByColumns(Map record)throws Exception;
}