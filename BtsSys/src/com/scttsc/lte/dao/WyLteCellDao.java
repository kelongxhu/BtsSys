package com.scttsc.lte.dao;

import com.scttsc.common.dao.MyBatisRepository;
import com.scttsc.lte.model.WyLteCell;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@MyBatisRepository
public interface WyLteCellDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyLteCell record);

    int insertSelective(WyLteCell record);

    WyLteCell selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyLteCell record);

    int updateByPrimaryKey(WyLteCell record);
    //查詢LTE小區
    List<WyLteCell> selectWyLteCell(Map<String,Object> paramMap);
   //小區數據量
    int countWyLteCell(Map<String,Object> paramMap);
}