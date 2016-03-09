package com.scttsc.baselibs.service;

import java.util.List;

import com.scttsc.baselibs.model.TunnelLib;

public interface TunnelLibManager {
    /**
     * 查詢隧道庫
     * @param tunnelLib
     * @return
     * @throws Exception
     */
	int insert(TunnelLib tunnelLib) throws Exception;

    /**
     * 更新隧道库
     * @param tunnelLib
     * @return
     * @throws Exception
     */
	int update(TunnelLib tunnelLib) throws Exception;

    /**
     * 删除隧道库
     * @param map
     * @return
     * @throws Exception
     */
	int delete(Object map)throws Exception;

    /**
     * 分页查询隧道库
     * @param map
     * @return
     * @throws Exception
     */
	List<TunnelLib> getByConds(Object map)throws Exception;

    /**
     * 获取隧道库总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Object map)throws Exception;

    /**
     * 根据ID获取隧道库
     * @param id
     * @return
     * @throws Exception
     */
	TunnelLib getById(Long id)throws Exception;

    /**
     * 加载所有隧道库
     * @return
     * @throws Exception
     */
	List<TunnelLib> loadAll()throws Exception;

    /**
     * 逻辑删除隧道库
     * @param map
     * @return
     * @throws Exception
     */
    int deleteByDeleteFlag(Object map)throws Exception;
}
