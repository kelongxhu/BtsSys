package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyGradeperiodcfg;

public interface WyGradeperiodcfgDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyGradeperiodcfg record);

    int insertSelective(WyGradeperiodcfg record);

    WyGradeperiodcfg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyGradeperiodcfg record);

    int updateByPrimaryKey(WyGradeperiodcfg record);
}