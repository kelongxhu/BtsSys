package com.scttsc.business.service;

import com.scttsc.business.model.ErectStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ErectStationManager {
    /**
     * 直放总数
     * @param example
     * @return
     */
    int countByExample(Map example);

    /**
     * 按条件删除直放
     * @param example
     * @return
     */
    int deleteByExample(Map example);

    /**
     * 按主键删除直放
     * @param id
     * @return
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * 插入直放
     * @param record
     * @return
     */
    int insert(ErectStation record);

    /**
     * 导入直放
     * @param record
     * @return
     * @throws Exception
     */
    int importInsert(Map record)throws Exception;

    /**
     * 插入直放
     * @param record
     * @return
     */
    int insertSelective(ErectStation record);

    /**
     * 查询直放
     * @param example
     * @return
     */
    List<ErectStation> selectByExample(Map example);

    /**
     * 按主键查询直放
     * @param id
     * @return
     */
    ErectStation selectByPrimaryKey(BigDecimal id);

    /**
     * 按条件更新
     * @param record
     * @return
     */
    int updateByExampleSelective(ErectStation record);

    /**
     * 按条件更新
     * @param record
     * @return
     */
    int updateByExample(ErectStation record);

    /**
     * 按主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ErectStation record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(ErectStation record);
    
}
