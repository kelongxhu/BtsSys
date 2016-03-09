package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.RoadLib;

public interface RoadLibManager {
    /**
     * 插入道路库
     * @param roadLib
     * @return
     * @throws Exception
     */
	int insert(RoadLib roadLib) throws Exception;

    /**
     * 更新道路库
     * @param roadLib
     * @return
     * @throws Exception
     */
	int update(RoadLib roadLib) throws Exception;

    /**
     * 删除道路库
     * @param map
     * @return
     * @throws Exception
     */
	int delete(Object map)throws Exception;

    /**
     * 查询道路库列表
     * @param map
     * @return
     * @throws Exception
     */
	List<RoadLib> getByConds(Object map)throws Exception;

    /**
     * 查询道路库总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Object map)throws Exception;

    /**
     * 根据主键ID获取道路库详情
     * @param id
     * @return
     * @throws Exception
     */
	RoadLib getById(Long id)throws Exception;

    /**
     * 根据名称获取道路库信息
     * @param name
     * @return
     * @throws Exception
     */
    RoadLib getByName(String name)throws Exception;

    /**
     * 获取所有道路库
     * @return
     * @throws Exception
     */
	List<RoadLib> loadAll()throws Exception;

    /**
     * 逻辑删除道路库
     * @param map
     * @return
     * @throws Exception
     */
    int deleteByDeleteFlag(Object map)throws Exception;
    /**
     * 批量导入场景库
     * @param data
     * @return
     */
    int importInsert(List<Map<String, Object>> data);
}
