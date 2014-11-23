package com.scttsc.business.dao;


import com.scttsc.business.model.CBts;

import java.util.List;
import java.util.Map;

public interface CBtsDao {
    CBts selectById(Long intId) throws Exception;

    List<Map> selectBtsByConds(Map record) throws Exception;
}