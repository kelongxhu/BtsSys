package com.scttsc.business.service.impl;

import com.scttsc.business.dao.WyBtsSpecialDao;
import com.scttsc.business.model.WyBtsSpecial;
import com.scttsc.business.service.WyBtsSpecialManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/5/16.
 */
@Service("wyBtsSpecialManager")
@Transactional(readOnly = true)
public class WyBtsSpecialManagerImpl implements WyBtsSpecialManager {
    @Autowired
    private WyBtsSpecialDao wyBtsSpecialDao;
    public List<WyBtsSpecial> selectByMap(Map<String, Object> param)throws Exception {
        return wyBtsSpecialDao.selectByMap(param);
    }

    public int countByMap(Map<String, Object> param)throws Exception {
        return wyBtsSpecialDao.countByMap(param);
    }
}
