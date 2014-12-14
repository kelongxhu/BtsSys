package com.scttsc.business.service;

import com.scttsc.business.model.MateEntry;
import com.scttsc.business.model.WyTunelManual;
import com.scttsc.business.vo.FindBackReponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/9/27.
 */
public interface TunelManualManager {
    WyTunelManual selectByPrimaryKey(Long intId);

    /**
     * 錄入手工數據
     * @param record
     * @return
     * @throws Exception
     */
    int insert(WyTunelManual record)throws Exception;

    int updateByPrimaryKeySelective(WyTunelManual record)throws Exception;

    /**
     * 导入隧道数据到隧道库
     * @param record
     * @return
     * @throws Exception
     */
    int importInsert(Map record)throws Exception;


    /**
     * 通过废弃小区的name匹配对应再用小区
     * @param ids
     * @return
     * @throws Exception
     */
    List<MateEntry> findDelTunelData(List<Long> ids) throws Exception;


    FindBackReponse findManualData(List<String> ids);
}
