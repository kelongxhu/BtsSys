package com.scttsc.lte.service.impl;

import com.scttsc.lte.dao.WyLteBbuDao;
import com.scttsc.lte.model.WyLteBbu;
import com.scttsc.lte.service.WyLteBbuManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/23.
 */
@Service("wyLteBbuManager")
public class WyLteBbuManagerImpl implements WyLteBbuManager {
    @Resource
    private WyLteBbuDao wyLteBbuDao;
    public List<WyLteBbu> selectWyLteBbu(Map<String, Object> paramMap) {
        return wyLteBbuDao.selectWyLteBbu(paramMap);
    }

    public int countWyLteBbu(Map<String, Object> paramMap) {
        return wyLteBbuDao.countWyLteBbu(paramMap);
    }

    public int updateByPrimaryKeySelective(WyLteBbu record) {
        return wyLteBbuDao.updateByPrimaryKeySelective(record);
    }
}
