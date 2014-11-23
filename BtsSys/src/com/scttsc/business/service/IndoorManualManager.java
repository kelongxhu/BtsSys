package com.scttsc.business.service;

import com.scttsc.business.model.IndoorManual;
import com.scttsc.business.vo.FindBackReponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IndoorManualManager {
    int countByExample(IndoorManual example);

    int deleteByPrimaryKey(BigDecimal intId);

    int insert(IndoorManual record);

    void insertSelective(IndoorManual record)throws Exception;

    List<IndoorManual> selectByExample(IndoorManual example);

    IndoorManual selectByPrimaryKey(Long intId);

    int updateByExampleSelective(IndoorManual record);

    int updateByExample(IndoorManual record);

    int updateByPrimaryKeySelective(IndoorManual record)throws Exception;

    int updateByPrimaryKey(IndoorManual record);

    public void importInsert(Map record) throws Exception;

   //添加
    List<IndoorManual> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    List<Map> selectExportIndoorDataByMap(Object map)throws Exception;

    List<IndoorManual> selectByCons(Object map)throws Exception;

      //未录入直放站的室分手工数据
    List<IndoorManual> selectByNoInputErect(Object map)throws Exception;
    //未录入干放站的室分手工数据
    List<IndoorManual> selectByNoInputDry(Object map)throws Exception;

    List<Map> selectIndoorDataGroupByCoulmns(Map map)throws Exception;

    int countIndoorDataGroupByColumns(Map map)throws Exception;

    int updateByInterface(Map map)throws Exception;

    FindBackReponse findManualData(List<String> ids);

}
