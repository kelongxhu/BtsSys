package com.scttsc.baselibs.service;

import java.util.List;

import com.scttsc.baselibs.model.AirLib;
import com.scttsc.baselibs.model.TunnelLib;

public interface AirLibManager {
    /**
     * 新建天线库
     * @param airLib
     * @throws Exception
     */
	void insert(AirLib airLib) throws Exception;

    /**
     * 更新天线库
     * @param airLib
     * @throws Exception
     */
	void update(AirLib airLib) throws Exception;

    /**
     * 删除天线库
     * @param map
     * @throws Exception
     */
	void delete(Object map)throws Exception;

    /**
     * 查询天线库
     * @param map
     * @return
     * @throws Exception
     */
	List<AirLib> getByConds(Object map)throws Exception;

    /**
     * 查询天线库总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Object map)throws Exception;

    /**
     * 天线库详情
     * @param id
     * @return
     * @throws Exception
     */
	AirLib getById(Long id)throws Exception;

    /**
     * 获取所有天线库列表
     * @param map
     * @return
     * @throws Exception
     */
	List<AirLib> selectAll(Object map)throws Exception;

    /**
     * 假删除天线库
     * @param map
     * @return
     * @throws Exception
     */
    int deleteByDeleteFlag(Object map)throws Exception;
	
	
}
