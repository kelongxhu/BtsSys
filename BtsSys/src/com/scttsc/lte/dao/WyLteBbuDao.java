package com.scttsc.lte.dao;

import com.scttsc.common.dao.MyBatisRepository;
import com.scttsc.lte.model.WyLteBbu;
import java.math.BigDecimal;
@MyBatisRepository
public interface WyLteBbuDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyLteBbu record);

    int insertSelective(WyLteBbu record);

    WyLteBbu selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyLteBbu record);

    int updateByPrimaryKey(WyLteBbu record);
}