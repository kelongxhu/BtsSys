package com.scttsc.charge.dao;

import com.scttsc.charge.model.WyBtsCharge;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WyBtsChargeDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyBtsCharge record);

    int updateByPrimaryKeySelective(WyBtsCharge record);

    //bts覆盖站点费用详情
    WyBtsCharge selectChargeWithBtsByAis(Map<String, Object> param);

    //纯bbu覆盖站点费用详情
    WyBtsCharge selectChargeWithBbuByAis(Map<String, Object> param);

    //隧道覆盖站点费用详情
    WyBtsCharge selectChargeWithTunelByAis(Map<String, Object> param);

    /**
     * 室外覆盖站点的房屋费用设置列表
     *
     * @param param
     * @return
     */
    List<WyBtsCharge> selectWyBtsChargeListByMap(Map<String, Object> param) throws Exception;

    /**
     * 室外覆盖站点费用数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    int selectWyBtsChargeCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 纯bbu费用列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectWyBbuChargeListByMap(Map<String, Object> param) throws Exception;


    /**
     * 纯bbu费用列表数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    int selectWyBbuChargeCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 隧道费用列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectWyTunelChargeListByMap(Map<String, Object> param) throws Exception;

    int selectWyTunelChargeCountByMap(Map<String, Object> param) throws Exception;

    /**
     * WyBts费用设置列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectWyBtsChargeSettingByMap(Map<String, Object> param) throws Exception;

    /**
     * WyBts费用设置列表数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    int selectWyBtsChargeSettingCountByMap(Map<String, Object> param) throws Exception;

    /**
     * WyBbu费用设置列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectWyBbuChargeSettingByMap(Map<String, Object> param) throws Exception;

    /**
     * WyBbu费用设置列表数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    int selectWyBbuChargeSettingCountByMap(Map<String, Object> param) throws Exception;

    /**
     * WyTunel费用设置列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsCharge> selectWyTunelChargeSettingByMap(Map<String, Object> param) throws Exception;

    /**
     * WyTunel费用设置列表数量
     *
     * @param param
     * @return
     * @throws Exception
     */
    int selectWyTunelChargeSettingCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 更新费用设置信息
     *
     * @param param
     * @return
     * @throws Exception
     */
    int updateByMap(Map<String, Object> param) throws Exception;
    
    /**
     * 根据int_id,costType查询bts费用信息
     * @param param
     * @throws Exception
     */
    WyBtsCharge selectBtsChargeSettingByMap(Map<String,Object> param)throws Exception;
}