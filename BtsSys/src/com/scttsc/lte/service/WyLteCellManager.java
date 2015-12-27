package com.scttsc.lte.service;

import com.scttsc.lte.model.WyLteCell;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/26.
 */
public interface WyLteCellManager {
    //查詢LTE小區
    List<WyLteCell> selectWyLteCell(Map<String,Object> paramMap);
    //小區數據量
    int countWyLteCell(Map<String,Object> paramMap);
    //编辑
    int updateByPrimaryKeySelective(WyLteCell record);
}
