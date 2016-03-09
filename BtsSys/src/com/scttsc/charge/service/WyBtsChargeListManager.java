package com.scttsc.charge.service;

import com.scttsc.charge.dto.BtsDto;
import com.scttsc.charge.dto.PayStatistDto;
import com.scttsc.charge.model.WyBtsChargeList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/11/10.
 */
public interface WyBtsChargeListManager {
    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * 插入费用
     * @param record
     * @return
     */
    int insert(WyBtsChargeList record);

    /**
     * 查询费用
     * @param id
     * @return
     */
    WyBtsChargeList selectByPrimaryKey(BigDecimal id);

    /**
     * 更新费用
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WyBtsChargeList record);

    /**
     * 费用类型列表
     * @param intId
     * @param costType
     * @return
     * @throws Exception
     */
    List<WyBtsChargeList> selectByBtsId(BigDecimal intId, int costType) throws Exception;

    /**
     * get bts obj by name and btsType
     *
     * @param name
     * @param btsType
     * @return
     */
    public BtsDto selectBtsByMap(Map<String, Object> param);

    /**
     * import charge data to DB
     *
     * @param data
     * @return
     */
    int insertChargeData(List<Map<String, Object>> data) throws Exception;

    /**
     * 查询费用列表
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsChargeList> selectByMap(Map<String, Object> param) throws Exception;

    /**
     * 费用列表总数
     * @param param
     * @return
     * @throws Exception
     */
    int countByMap(Map<String, Object> param) throws Exception;

    /**
     * 费用统计
     * @param param
     * @return
     * @throws Exception
     */
    List<PayStatistDto> statisticsPay(Map<String, Object> param) throws Exception;

    /**
     * 统计总数
     * @param param
     * @return
     * @throws Exception
     */
    int countStatisticsPay(Map<String, Object> param) throws Exception;


    /**
     * batch del
     * @param idList
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKeys(List<BigDecimal> idList)throws Exception;


}
