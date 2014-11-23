package com.scttsc.business.service;

import com.scttsc.business.model.BbuManual;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.vo.FindBackReponse;

import java.util.List;
import java.util.Map;

public interface BbuManualManager {
	BbuManual getById(Long intId)throws Exception;
	   
    int insert(BbuManual record)throws Exception;
   
    int update(BbuManual record)throws Exception;
    
    void importInsert(Map record)throws Exception;//导入手工数据

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
