package com.scttsc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.scttsc.baselibs.model.VitoLib;

public interface VitoLibManager {
    /**
     * 插入乡镇库
     * @param vitoLib
     * @return
     * @throws Exception
     */
	int insert(VitoLib vitoLib) throws Exception;

    /**
     * 更新乡镇库
     * @param vitoLib
     * @return
     * @throws Exception
     */
	int update(VitoLib vitoLib) throws Exception;

    /**
     * 删除乡镇库
     * @param map
     * @return
     * @throws Exception
     */
	int delete(Object map)throws Exception;

    /**
     * 获取乡镇库详情
     * @param map
     * @return
     * @throws Exception
     */
	List<VitoLib> getByConds(Object map)throws Exception;

    /**
     * 乡村库总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Object map)throws Exception;

    /**
     * 根据ID获取乡村库
     * @param id
     * @return
     * @throws Exception
     */
	VitoLib getById(Long id)throws Exception;

    /**
     * 查询乡镇库
     * @param map
     * @return
     * @throws Exception
     */
	List<VitoLib> getByMap(Object map)throws Exception;

    /**
     * 批量导入
     * @param record
     * @return
     * @throws Exception
     */
    int importInsert(Map record)throws Exception;

    /**
     * 逻辑删除
     * @param map
     * @return
     * @throws Exception
     */
    int deleteByDeleteFlag(Object map)throws Exception;
}
