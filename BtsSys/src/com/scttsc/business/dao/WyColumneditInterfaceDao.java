package com.scttsc.business.dao;

import com.scttsc.business.model.WyColumneditInterface;

import java.util.List;
import java.util.Map;

public interface WyColumneditInterfaceDao {
    int deleteByPrimaryKey(Long id);

    int insert(WyColumneditInterface record);

    int insertSelective(WyColumneditInterface record);

    WyColumneditInterface selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyColumneditInterface record);

    int updateByPrimaryKey(WyColumneditInterface record);

    List<WyColumneditInterface> selectByMap(Map record) throws Exception;

    int countByMap(Map record) throws Exception;
}