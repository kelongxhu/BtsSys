package com.scttsc.healthy.dao;

import com.scttsc.business.model.Bts;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;

import java.util.List;
import java.util.Map;

public interface WySpeciallyBtsDao {
    int insert(WySpeciallyBts record);

    int deleteAll()throws Exception;

    int specialCountByCorrection(Map map);

    List<WySpeciallycfg> specialListByCorrection(Map map);

    int specialBtsCountByCorrection(Map map);

    List<WySpeciallyBts> specialBtsListByCorrection(Map map);
}