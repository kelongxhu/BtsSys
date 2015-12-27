package com.scttsc.lte.service;

import com.scttsc.lte.model.WyLteBbu;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/23.
 */
public interface WyLteBbuManager {
    /**
     * 查询bbu
     * @param paramMap
     * @return
     */
    List<WyLteBbu> selectWyLteBbu(Map<String,Object> paramMap);

    /**
     * bbu数量
     * @param paramMap
     * @return
     */
    int countWyLteBbu(Map<String,Object> paramMap);

    /**
     * 编辑
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WyLteBbu record);
}
