package com.scttsc.healthy.service;

import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-9-4
 * Time: 下午3:14
 */
public interface SpeciallyManager {
    List<WySpeciallycfg> selectSpeciallyList(Map record) throws Exception;

    int selectSpeciallyCount(Map record) throws Exception;

    WySpeciallycfg selectByPrimaryKey(Long id)throws Exception;

    int insert(WySpeciallycfg record)throws Exception;

    int updateByPrimaryKeySelective(WySpeciallycfg record)throws Exception;

    int deleteByPrimaryKey(Long id)throws Exception;

    List<WySpeciallycfg> selectAll()throws Exception;

    int insertSpBts(List<WySpeciallyBts> spBtss)throws Exception;
}
