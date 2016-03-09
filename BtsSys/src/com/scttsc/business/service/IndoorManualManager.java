package com.scttsc.business.service;

import com.scttsc.business.model.IndoorManual;
import com.scttsc.business.vo.FindBackReponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IndoorManualManager {
    /**
     * 室分总数
     * @param example
     * @return
     */
    int countByExample(IndoorManual example);

    /**
     * 删除室分手工数据
     * @param intId
     * @return
     */
    int deleteByPrimaryKey(BigDecimal intId);

    /**
     * 插入室分手工数据
     * @param record
     * @throws Exception
     */
    public void insert(IndoorManual record) throws Exception;

    /**
     * 查询室分列表
     * @param example
     * @return
     */
    List<IndoorManual> selectByExample(IndoorManual example);

    /**
     * 根据主键查询室分手工数据
     * @param intId
     * @return
     */
    IndoorManual selectByPrimaryKey(Long intId);

    /**
     * 更新室分数据
     * @param record
     * @return
     * @throws Exception
     */
    int updateByPrimaryKeySelective(IndoorManual record)throws Exception;

    /**
     * 导入室分数据
     * @param record
     * @throws Exception
     */
    public void importInsert(Map record) throws Exception;

    /**
     * 查询室分数据
     * @param map
     * @return
     * @throws Exception
     */
    List<IndoorManual> selectByMap(Object map) throws Exception;

    /**
     * 室分数据总数
     * @param map
     * @return
     * @throws Exception
     */
    int countByMap(Object map) throws Exception;

    /**
     * 导出室分
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportIndoorDataByMap(Object map)throws Exception;

    /**
     * 查询室分手工数据
     * @param map
     * @return
     * @throws Exception
     */
    List<IndoorManual> selectByCons(Object map)throws Exception;

      //未录入直放站的室分手工数据
    List<IndoorManual> selectByNoInputErect(Object map)throws Exception;
    //未录入干放站的室分手工数据
    List<IndoorManual> selectByNoInputDry(Object map)throws Exception;

    /**
     * 按字段分组查询
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectIndoorDataGroupByCoulmns(Map map)throws Exception;

    /**
     * 按字段分组总数
     * @param map
     * @return
     * @throws Exception
     */
    int countIndoorDataGroupByColumns(Map map)throws Exception;

    /**
     * 更新接口
     * @param map
     * @return
     * @throws Exception
     */
    int updateByInterface(Map map)throws Exception;

    /**
     * 找回录入数据
     * @param ids
     * @return
     */
    FindBackReponse findManualData(List<String> ids);

}
