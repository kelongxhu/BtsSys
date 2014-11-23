package com.scttsc.charge.dao;

import com.scttsc.charge.model.WySmsLog;
import java.math.BigDecimal;

public interface WySmsLogDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(WySmsLog record);

    WySmsLog selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(WySmsLog record);
}