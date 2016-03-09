package com.scttsc.business.service;

import com.scttsc.business.model.BbuManual;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.FindBackReponse;

import java.util.List;
import java.util.Map;

/**
 * bbu手工数据业务类
 */
public interface BbuManualManager {
    /**
     * 根据intid查询bbu手工数据
     * @param intId
     * @return
     * @throws Exception
     */
	BbuManual getById(Long intId)throws Exception;

    /**
     * 插入bbu手工数据
     * @param record
     * @return
     * @throws Exception
     */
    int insert(BbuManual record)throws Exception;

    /**
     * 更新bbu手工数据
     * @param record
     * @return
     * @throws Exception
     */
    int update(BbuManual record)throws Exception;

    /**
     * 导入Bbu手工数据
     * @param record
     * @throws Exception
     */
    void importInsert(Map record)throws Exception;//导入手工数据

    /**
     * 接口更新bbu手工字段
     * @param record
     * @return
     * @throws Exception
     */
    int updateByInterface(Map record)throws Exception;


    /**
     * 通过废弃的bbu名称匹配再用bbu，
     * @param intIds
     * @return
     * @throws Exception
     */
    List<MateEntry> findDelBbuData(List<Long> ids)throws Exception;

    /**
     * 将废弃数据手工数据更新为再用手工数据。
     * @param ids
     * @return
     */
    FindBackReponse findManualData(List<String> ids);
}
