package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.WyLibVillage;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/12/6.
 */
public interface VillageLibManager {
    /**
     * 分页查询乡镇库
     * @param param
     * @return
     * @throws Exception
     */
    List<WyLibVillage> selectByMap(Map<String, Object> param) throws Exception;

    /**
     * 获取乡镇库总数
     * @param param
     * @return
     * @throws Exception
     */
    int countByMap(Map<String, Object> param) throws Exception;

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyLibVillage> selectByConds(Map<String, Object> param) throws Exception;

    /**
     * 根据区县ID获取乡镇列表
     * @param countryId
     * @return
     * @throws Exception
     */
    List<Map> selectTownByCountryId(Integer countryId) throws Exception;

    /**
     * 根据主键获取乡镇
     * @param id
     * @return
     */
    WyLibVillage selectByPrimaryKey(Long id);

    /**
     * 更新乡镇信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WyLibVillage record);

    /**
     * 根据镇名或者乡村名获取乡镇库
     * @param param
     * @return
     * @throws Exception
     */
    WyLibVillage selectByVillage(Map<String, Object> param) throws Exception;

}
