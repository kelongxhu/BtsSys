package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyGradeDetail;

public interface WyGradeDetailDao {
    int insert(WyGradeDetail record) throws Exception;

    int deleteByBtsIntId(Long btsIntId) throws Exception;

}