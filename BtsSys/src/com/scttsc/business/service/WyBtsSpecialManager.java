package com.scttsc.business.service;

import com.scttsc.business.model.WyBtsSpecial;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/5/16.
 */
public interface WyBtsSpecialManager {
    /**
     * 查询特殊站点
     * @param param
     * @return
     * @throws Exception
     */
    List<WyBtsSpecial> selectByMap(Map<String, Object> param)throws Exception;

    /**
     * 特殊站点总数
     * @param param
     * @return
     * @throws Exception
     */
    int countByMap(Map<String, Object> param)throws Exception;
}
