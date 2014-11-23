package com.scttsc.healthy.service;

import com.scttsc.healthy.model.WyGrade;

import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-8-28
 * Time: 下午2:52
 * Email: wokzhen@vip.qq.com
 */
public interface GradeManager {

    public int selectGradeCount(Map cond) throws Exception;

    public List<WyGrade> selectGradeList(Map cond) throws Exception;

    WyGrade selectByPrimaryKey(Long intId)throws Exception;

    int updateByPrimaryKeySelective(WyGrade record)throws Exception;

    int insert(WyGrade record)throws Exception;

    public WyGrade getAllInfoById(Long id) throws Exception;

    public List<Map> countBtsGradeNumByCityIds(String cityIds) throws Exception;

    public List<Map> getSortedBtsAvgGradeWithCity() throws Exception;

}
