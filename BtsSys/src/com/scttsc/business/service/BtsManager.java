package com.scttsc.business.service;

import com.scttsc.business.model.Bts;
import com.scttsc.business.model.CBts;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.FindBackReponse;

import java.util.List;
import java.util.Map;

public interface BtsManager {
    List<Bts> getByConds(Object map) throws Exception;

    int countByConds(Object map) throws Exception;

    Bts getById(Long id) throws Exception;

    void updateByMap(Object map) throws Exception;

    List<Bts> selectByMap(Object map) throws Exception;

    CBts selectCBtsById(Long intId) throws Exception;

    List<Map> selectExportDataByMap(Object map) throws Exception;

    List<Map> selectBtsGroupByColumns(Object record) throws Exception; //按字段统计列表

    int countBtsGroupByColumns(Object record) throws Exception;

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
