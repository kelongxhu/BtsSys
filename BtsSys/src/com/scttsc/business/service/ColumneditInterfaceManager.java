package com.scttsc.business.service;

import com.scttsc.business.model.WyColumneditInterface;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-1-2
 * Time: 下午3:35
 */
public interface ColumneditInterfaceManager {
    WyColumneditInterface selectByPrimaryKey(Long id)throws Exception;

    int insert(WyColumneditInterface record)throws Exception;

    int updateByPrimaryKeySelective(WyColumneditInterface record)throws Exception;

    List<WyColumneditInterface> selectByMap(Map record) throws Exception;

    int countByMap(Map record) throws Exception;

    /**
     * 接口修改审核
     * @param ids
     * @param status，1=审核通过；2=审核不通过
     * @throws Exception
     */
    void wyColumneditInterfaceEditOpposed(String ids,int status,Long confirmUserId)throws Exception;


}
