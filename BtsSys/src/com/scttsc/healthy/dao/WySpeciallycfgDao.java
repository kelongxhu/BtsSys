package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WySpeciallycfg;

import java.util.List;
import java.util.Map;

public interface WySpeciallycfgDao {
    int deleteByPrimaryKey(Long id);

    int insert(WySpeciallycfg record);

    int insertSelective(WySpeciallycfg record);

    WySpeciallycfg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WySpeciallycfg record);

    int updateByPrimaryKey(WySpeciallycfg record);

    List<WySpeciallycfg> selectSpeciallyList(Map record)throws Exception;

    int selectSpeciallyCount(Map record)throws Exception;

    List<WySpeciallycfg> selectAll()throws Exception;
}