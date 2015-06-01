package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.WyLibVillage;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/12/6.
 */
public interface VillageLibManager {
    List<WyLibVillage> selectByMap(Map<String, Object> param) throws Exception;

    int countByMap(Map<String, Object> param) throws Exception;

    List<WyLibVillage> selectByConds(Map<String, Object> param) throws Exception;

    List<Map> selectTownByCountryId(Integer countryId) throws Exception;

    WyLibVillage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyLibVillage record);

    WyLibVillage selectByVillage(Map<String, Object> param) throws Exception;

}
