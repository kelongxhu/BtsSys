package com.scttsc.business.dao;

import java.util.List;
import java.util.Map;

import com.scttsc.business.model.Bbu;

public interface BbuDao {

	int countByExample(Object example);

	List<Bbu> selectByExample(Object example);

	int updateByMap(Object record);

	Bbu getById(Long id) throws Exception;
	/**
	 * 通过条件查找BBU列表，不分页
	 * @return
	 * @throws Exception
	 */
	
	List<Bbu> selectBbuByConds(Object map)throws Exception;

    /**
     * 导出bbu数据，返回map数据结构
     * @param map
     * @return
     * @throws Exception
     */
    List<Map> selectExportBbuDataByMap(Object map)throws Exception;


    List<Map> selectBbuGroupByColumns(Object map)throws Exception;
    int countBbuGroupByColumns(Object map)throws Exception;

    List<Bbu> selectByMap(Object map)throws Exception;
}