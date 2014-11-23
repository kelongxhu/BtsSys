package com.scttsc.business.service.impl;

import com.scttsc.business.dao.WyTransfernodeDao;
import com.scttsc.business.model.WyTransfernode;
import com.scttsc.business.service.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-10-17
 * Time: 下午4:07
 * Email: wokzhen@vip.qq.com
 */
@Service("transferManager")
@Transactional(readOnly = true)
public class TransferManagerImpl implements TransferManager{

    @Autowired
    private WyTransfernodeDao transfernodeDao;

    public List<WyTransfernode> selectTransferList(Map cond) throws Exception {
        return transfernodeDao.selectTransferList(cond);
    }

    public int selectTransferCount(Map cond) throws Exception {
        return transfernodeDao.selectTransferCount(cond);
    }

    public WyTransfernode selectByPrimaryKey(Long id) throws Exception {
        return transfernodeDao.selectByPrimaryKey(id);
    }

    public void updateByPrimaryKeySelective(WyTransfernode wyTransfernode) throws Exception {
        transfernodeDao.updateByPrimaryKeySelective(wyTransfernode);
    }

    public void insertSelective(WyTransfernode wyTransfernode) throws Exception {
        transfernodeDao.insertSelective(wyTransfernode);
    }

    public void deleteByPks(String ids) throws Exception {
        transfernodeDao.deleteByPks(ids);
    }

}
