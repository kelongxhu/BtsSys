package com.scttsc.business.service;

import com.scttsc.business.model.WyTunel;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/9/25.
 */
public interface TunelManager {
    /**
     * 查询隧道站点
     * @param record
     * @return
     * @throws Exception
     */
    List<WyTunel> selectByMap(Map record)throws Exception;

    /**
     * 隧道总数
     * @param record
     * @return
     * @throws Exception
     */
    int countByMap(Map record)throws Exception;

    /**
     * 根据主键获取隧道站点
     * @param intId
     * @return
     */
    WyTunel selectByPrimaryKey(Long intId);

    /**
     * 更新隧道站点
     * @param record
     * @return
     * @throws Exception
     */
    int updateByMap(Map record)throws Exception;

    /**
     * 通过名字查找隧道站点
     * @param name
     * @return
     * @throws Exception
     */
    WyTunel selectByName(String name)throws Exception;

    /**
     * 查询隧道站点
     * @param record
     * @return
     * @throws Exception
     */
    List<WyTunel> selectByConds(Map record)throws Exception;

    /**
     * 按字段分组获取隧道
     * @param record
     * @return
     * @throws Exception
     */
    List<Map> selectWyTunelGroupByColumns(Map record) throws Exception;

    /**
     * 总数
     * @param record
     * @return
     * @throws Exception
     */
    int countWytunelGroupByColumns(Map record) throws Exception;

    /**
     * 隧道小区按字段分组
     * @param record
     * @return
     * @throws Exception
     */
    List<Map> selectWyTunelCellGroupByColumns(Map record)throws Exception;

    /**
     * 总数
     * @param record
     * @return
     * @throws Exception
     */
    int countWytunelCellGroupByColumns(Map record)throws Exception;

    /**
     * 导出
     * @param record
     * @return
     * @throws Exception
     */
    List<Map> selectTunelBtsExportDataByMap(Map record)throws Exception;
}
