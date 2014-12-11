package com.scttsc.business.dao;

import com.scttsc.business.model.IndoorManual;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IndoorManualDao {

    int countByExample(IndoorManual example);

    int deleteByPrimaryKey(BigDecimal intId);

    int insert(IndoorManual record);

    List<IndoorManual> selectByExample(IndoorManual example);

    IndoorManual selectByPrimaryKey(Long intId);

    int updateByPrimaryKeySelective(IndoorManual record);

    //室分查询
    List<IndoorManual> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    List<Map> selectExportIndoorDataByMap(Object map) throws Exception;

    //通过条件查询室分数据
    List<IndoorManual> selectByCons(Object map) throws Exception;

    //未录入直放站的室分手工数据
    List<IndoorManual> selectByNoInputErect(Object map) throws Exception;

    //未录入干放站的室分手工数据
    List<IndoorManual> selectByNoInputDry(Object map) throws Exception;

    List<Map> selectIndoorDataGroupByCoulmns(Map map) throws Exception;

    int countIndoorDataGroupByColumns(Map map) throws Exception;

    int updateByInterface(Map map) throws Exception;

    //更新intID
    int updateByIntID(Map map) throws Exception;

}