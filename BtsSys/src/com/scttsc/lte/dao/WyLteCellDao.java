package com.scttsc.lte.dao;

import com.scttsc.lte.model.WyLteCell;
import java.math.BigDecimal;

public interface WyLteCellDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyLteCell record);

    int insertSelective(WyLteCell record);

    WyLteCell selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyLteCell record);

    int updateByPrimaryKey(WyLteCell record);
}