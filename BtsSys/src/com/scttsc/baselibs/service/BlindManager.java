package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.WyBlind;

public interface BlindManager {
    /**
     * 查询盲点
     * @param map
     * @return
     * @throws Exception
     */
	List<WyBlind> getByConds(Object map)throws Exception;

    /**
     * 盲点总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Object map)throws Exception;

    /**
     *插入盲点库
     * @param record
     * @return
     * @throws Exception
     */
	int insert(WyBlind record)throws Exception ;

    /**
     * 逻辑删除盲点库
     * @param map
     * @return
     * @throws Exception
     */
	int deleteByDeleteFlag(Object map)throws Exception;

    /**
     * 获取盲点库
     * @param id
     * @return
     */
	WyBlind getById(Long id);

    /**
     * 更新盲点库
     * @param record
     * @return
     * @throws Exception
     */
	int update(WyBlind record)throws Exception;

    /**
     * 导入盲点库
     * @param map
     * @return
     * @throws Exception
     */
	int importInsert(Map<String, Object> map)throws Exception;
	
}
