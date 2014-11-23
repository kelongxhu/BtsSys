package com.scttsc.business.service;

import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.FindBackReponse;

import java.util.List;
import java.util.Map;

public interface BbuManager {
    int countByExample(Object example);

    List<Bbu> selectByExample(Object example);

    int updateByMap(Object record);

    Bbu getById(Long id) throws Exception;

    List<Bbu> selectBbuByConds(Object map) throws Exception;

    List<Map> selectExportBbuDataByMap(Object map) throws Exception;

    List<Map> selectBbuGroupByColumns(Object map) throws Exception;

    int countBbuGroupByColumns(Object map) throws Exception;

    /**
     * get chun bbu by name
     * @param name
     * @return
     */
    Bbu selectByName(String name)throws Exception;



}
