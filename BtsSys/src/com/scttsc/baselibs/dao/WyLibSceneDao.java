package com.scttsc.baselibs.dao;

import com.scttsc.baselibs.model.WyLibScene;

import java.util.List;
import java.util.Map;

public interface WyLibSceneDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyLibScene record);

    WyLibScene selectByPrimaryKey(Long id);

    List<WyLibScene> selectByPrimaryKeys(Map<String,Object> param);

    int updateByPrimaryKeySelective(WyLibScene record);

    int deleteByPrimaryKeys(Map<String,Object> param);

    int countByMap(Map<String, Object> param) throws Exception;

    List<WyLibScene> selectByMap(Map<String, Object> param) throws Exception;
}