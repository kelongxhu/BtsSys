package com.scttsc.business.dao;

import com.scttsc.business.model.WyLog;

public interface WyLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyLog record);

    WyLog selectByPrimaryKey(Long id);

    int updateByPrimaryKey(WyLog record);
}