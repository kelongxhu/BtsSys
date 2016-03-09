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
    /**
     * 查询传输节点
     * @param cond
     * @return
     * @throws Exception
     */
    public List<WyTransfernode> selectTransferList(Map cond) throws Exception;

    /**
     * 计数传输节点
     * @param cond
     * @return
     * @throws Exception
     */
    public int selectTransferCount(Map cond) throws Exception;

    /**
     * 根据主键获取传输节点
     * @param id
     * @return
     * @throws Exception
     */
    public WyTransfernode selectByPrimaryKey(Long id) throws Exception;

    /**
     * 更新传输节点
     * @param wyTransfernode
     * @throws Exception
     */
    public void updateByPrimaryKeySelective(WyTransfernode wyTransfernode) throws Exception;

    /**
     * 插入传输节点
     * @param wyTransfernode
     * @throws Exception
     */
    public void insertSelective(WyTransfernode wyTransfernode) throws Exception;

    /**
     * 根据主键删除传输节点
     * @param ids
     * @throws Exception
     */
    public void deleteByPks(String ids) throws Exception;

}
