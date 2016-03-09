package com.scttsc.business.service;

import com.scttsc.business.model.DryStation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DryStationManager {
    /**
     * 总数
     * @param example
     * @return
     */
    int countByExample(Map example);

    /**
     * 导入一行干放站
     * @param record
     * @return
     * @throws Exception
     */
    int importInsert(Map record)throws Exception;

    /**
     * 按条件删除
     * @param example
     * @return
     */
    int deleteByExample(Map example);

    /**
     * 按主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(DryStation record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(DryStation record);

    /**
     * 查询干放
     * @param example
     * @return
     */
    List<DryStation> selectByExample(Map example);

    /**
     * 按主键查询干放
     * @param id
     * @return
     */
    DryStation selectByPrimaryKey(BigDecimal id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByExampleSelective(DryStation record);

    /**
     * 按条件更新
     * @param record
     * @return
     */
    int updateByExample(DryStation record);

    /**
     * 按主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DryStation record);

    /**
     * 按主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(DryStation record);
    
}
