package com.scttsc.business.service;

import com.scttsc.business.model.Bts;
import com.scttsc.business.model.CBts;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.FindBackReponse;

import java.util.List;
import java.util.Map;

public interface BtsManager {
    /**
     * 查询bts
     * @param map
     * @return
     * @throws Exception
     */
    List<Bts> getByConds(Object map) throws Exception;

    /**
     * 总数bts
     * @param map
     * @return
     * @throws Exception
     */
    int countByConds(Object map) throws Exception;

    /**
     * 根据主键查询bts
     * @param id
     * @return
     * @throws Exception
     */
    Bts getById(Long id) throws Exception;

    /**
     * 更新bts
     * @param map
     * @throws Exception
     */
    void updateByMap(Object map) throws Exception;

    /**
     * 查询bts
     * @param map
     * @return
     * @throws Exception
     */
    List<Bts> selectByMap(Object map) throws Exception;

    /**
     * 根据int_id获取c_bts数据
     * @param intId
     * @return
     * @throws Exception
     */
    CBts selectCBtsById(Long intId) throws Exception;

    /**
     * 导出bts
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportDataByMap(Object map) throws Exception;

    /**
     * 按字段统计bts列表
     * @param record
     * @return
     * @throws Exception
     */
    List<Map> selectBtsGroupByColumns(Object record) throws Exception; //按字段统计列表

    /**
     * 按字段统计bts列表总数
     * @param record
     * @return
     * @throws Exception
     */
    int countBtsGroupByColumns(Object record) throws Exception;

    //按字段统计室分
    List<Map> selectWyIndoorBtsGroupByColumns(Map<String,Object> param)throws Exception;

    /**
     * 按字段统计室分总数
     * @param param
     * @return
     * @throws Exception
     */
    int countWyIndoorBtsGroupByColumns(Map<String,Object> param)throws Exception;

    /**
     * 查询bts列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectWyBtsByMap(Map map) throws Exception;

    /**
     * 通过废弃数据intid找到对应再用的intid进行匹配
     *
     * @param intIds
     * @return
     * @throws Exception
     */
    List<MateEntry> findDelBtsData(List<Long> ids) throws Exception;

    /**
     * 将废弃数据手工数据更新为再用手工数据。
     *
     * @param ids
     * @return
     */
    FindBackReponse findManualData(List<String> ids);

    /**
     * get bts by name
     *
     * @param name
     * @return
     * @throws Exception
     */
    Bts selectByName(String name) throws Exception;


}
