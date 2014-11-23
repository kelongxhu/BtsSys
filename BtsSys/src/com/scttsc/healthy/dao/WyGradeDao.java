package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyGrade;

import java.util.List;
import java.util.Map;

public interface WyGradeDao {
    int insert(WyGrade record);

    int insertSelective(WyGrade record);

    int selectGradeCount(Map cond);

    List<WyGrade> selectGradeList(Map cond);

    WyGrade selectByPrimaryKey(Long intId);

    int updateByPrimaryKeySelective(WyGrade record);

    int updateByPrimaryKey(WyGrade record);

    WyGrade getAllInfoById(Long id);

    List<Map> countBtsGradeNumByCityIds(String cityIds);

    List<Map> getSortedBtsAvgGradeWithCity();

}