package com.scttsc.business.service;

import com.scttsc.business.model.Bbu;

import java.util.List;
import java.util.Map;

/**
 * bbu业务manager
 */
public interface BbuManager {
    /**
     * 总数
     * @param example
     * @return
     */
    int countByExample(Object example);

    /**
     * 查询bbu列表
     * @param example
     * @return
     */
    List<Bbu> selectByExample(Object example);

    /**
     * 更新bbu根据条件
     * @param record
     * @return
     */
    int updateByMap(Object record);

    /**
     * 根据主键获取bbu
     * @param id
     * @return
     * @throws Exception
     */
    Bbu getById(Long id) throws Exception;

    /**
     * 根据条件查询bbu
     * @param map
     * @return
     * @throws Exception
     */
    List<Bbu> selectBbuByConds(Object map) throws Exception;

    /**
     * 导出bbu
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportBbuDataByMap(Object map) throws Exception;

    /**
     * 按动态字段进行分组
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectBbuGroupByColumns(Object map) throws Exception;

    /**
     * 字段分组总数
     * @param map
     * @return
     * @throws Exception
     */
    int countBbuGroupByColumns(Object map) throws Exception;

    /**
     * get chun bbu by name
     * @param name
     * @return
     */
    Bbu selectByName(String name)throws Exception;

    /**
     * 根据条件查询bbu
     * @param map
     * @return
     * @throws Exception
     */
    List<Bbu> selectByMap(Object map) throws Exception;



}
