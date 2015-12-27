package com.scttsc.lte.service.impl;

import com.scttsc.lte.dao.WyLteCellDao;
import com.scttsc.lte.model.WyLteCell;
import com.scttsc.lte.service.WyLteCellManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/26.
 */
@Service("wyLteCellManager")
public class WyLteCellManagerImpl implements WyLteCellManager {
    @Autowired
    private WyLteCellDao wyLteCellDao;
    public List<WyLteCell> selectWyLteCell(Map<String, Object> paramMap) {
        return wyLteCellDao.selectWyLteCell(paramMap);
    }
    public int countWyLteCell(Map<String, Object> paramMap) {
        return wyLteCellDao.countWyLteCell(paramMap);
    }

    public int updateByPrimaryKeySelective(WyLteCell record) {
        return wyLteCellDao.updateByPrimaryKeySelective(record);
    }
}
