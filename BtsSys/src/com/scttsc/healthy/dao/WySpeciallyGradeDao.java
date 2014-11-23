package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WySpeciallyGrade;

public interface WySpeciallyGradeDao {
    int insert(WySpeciallyGrade record);

    int insertSelective(WySpeciallyGrade record);

    int delete(Long specialId)throws Exception;

}