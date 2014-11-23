package com.scttsc.business.dao;

import com.scttsc.business.model.WyTransfernode;

import java.util.List;
import java.util.Map;

public interface WyTransfernodeDao {
    int deleteByPrimaryKey(Long id);

    int deleteByPks(String ids);

    int insert(WyTransfernode record);

    int insertSelective(WyTransfernode record);

    WyTransfernode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WyTransfernode record);

    int updateByPrimaryKey(WyTransfernode record);

    public List<WyTransfernode> selectTransferList(Map cond) throws Exception;

    public int selectTransferCount(Map cond) throws Exception;
}