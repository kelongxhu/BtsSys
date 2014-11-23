package com.scttsc.business.service;

import com.scttsc.business.model.WyTransfernode;

import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-10-17
 * Time: 下午4:01
 * Email: wokzhen@vip.qq.com
 */
public interface TransferManager {

    public List<WyTransfernode> selectTransferList(Map cond) throws Exception;

    public int selectTransferCount(Map cond) throws Exception;

    public WyTransfernode selectByPrimaryKey(Long id) throws Exception;

    public void updateByPrimaryKeySelective(WyTransfernode wyTransfernode) throws Exception;

    public void insertSelective(WyTransfernode wyTransfernode) throws Exception;

    public void deleteByPks(String ids) throws Exception;

}
