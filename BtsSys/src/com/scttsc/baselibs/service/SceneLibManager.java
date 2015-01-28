package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.WyLibScene;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/1/26.
 */
public interface SceneLibManager {
    int countByMap(Map<String, Object> param) throws Exception;

    List<WyLibScene> selectByMap(Map<String, Object> param) throws Exception;

    int deleteByPrimaryKey(Long id);

    int deleteByPrimaryKeys(Map<String,Object> param)throws Exception;

    int insert(WyLibScene record);

    WyLibScene selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyLibScene record);
}
