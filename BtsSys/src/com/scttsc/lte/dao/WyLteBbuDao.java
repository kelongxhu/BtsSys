package com.scttsc.lte.dao;

import com.scttsc.common.dao.MyBatisRepository;
import com.scttsc.lte.model.WyLteBbu;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface WyLteBbuDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyLteBbu record);

    int insertSelective(WyLteBbu record);

    WyLteBbu selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyLteBbu record);

    int updateByPrimaryKey(WyLteBbu record);

    List<WyLteBbu> selectWyLteBbu(Map<String, Object> paramMap);

    int countWyLteBbu(Map<String,Object> paramMap);
}