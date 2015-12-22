package com.scttsc.lte.service;

import com.scttsc.lte.model.WyLteBts;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/22.
 */
public interface WyLteBtsManager {
    /**
     * 查询Lte Bts
     * @param paramMap
     * @return
     */
    List<WyLteBts> findWyLteBts(Map<String,Object> paramMap);

    /**
     * 查询Lte Bts总数
     * @param param
     * @return
     */
    int countWyLteBts(Map<String,Object> param);
}
