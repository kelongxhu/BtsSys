package com.scttsc.baselibs.dao;

import com.scttsc.baselibs.model.WyLibVillage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WyLibVillageDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyLibVillage record);

    WyLibVillage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyLibVillage record);

    List<WyLibVillage> selectByMap(Map<String, Object> param) throws Exception;

    int countByMap(Map<String, Object> param) throws Exception;

    List<Map> selectTownByCountryId(@Param(value = "countryId") Integer countryId) throws Exception;

    List<WyLibVillage> selectByConds(Map<String, Object> param) throws Exception;

    List<WyLibVillage> selectByVillage(Map<String, Object> param) throws Exception;

}