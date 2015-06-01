package com.scttsc.business.dao;

import com.scttsc.business.model.WyBtsSpecial;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WyBtsSpecialDao {
    int deleteByPrimaryKey(BigDecimal intId);

    int insert(WyBtsSpecial record);

    int insertSelective(WyBtsSpecial record);

    WyBtsSpecial selectByPrimaryKey(BigDecimal intId);

    int updateByPrimaryKeySelective(WyBtsSpecial record);

    int updateByPrimaryKey(WyBtsSpecial record);

    List<WyBtsSpecial> selectByMap(Map<String, Object> param);

    int countByMap(Map<String, Object> param);

    List<Map> selectSpcialGroupByState(@Param("type") Integer type);
}