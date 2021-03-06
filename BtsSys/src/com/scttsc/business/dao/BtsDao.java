package com.scttsc.business.dao;

import com.scttsc.business.model.Bts;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface BtsDao {
    List<Bts> getByConds(Object map) throws Exception;

    int countByConds(Object map) throws Exception;

    Bts getById(Long id) throws Exception;

    void updateByMap(Object map) throws Exception;

    List<Bts> selectByMap(Object map) throws Exception;

    List<Map> selectExportDataByMap(Object map) throws Exception;

    //统计室外覆盖站点
    List<Map> selectBtsGroupByColumns(Object record) throws Exception;

    int countBtsGroupByColumns(Object record) throws Exception;

    //统计室分站点
    List<Map> selectWyIndoorBtsGroupByColumns(Map<String,Object> param)throws Exception;

    int countWyIndoorBtsGroupByColumns(Map<String,Object> param)throws Exception;

    List<Map> selectWyBtsByMap(Map map) throws Exception;

    List<Map> selectBtsCountGroupByCity(@Param(value="isIndoor") String isIndoor,@Param(value = "manualFlag") Integer manualFlag) throws Exception;
}