package com.scttsc.business.service.impl;

import com.scttsc.business.dao.WrongNameDao;
import com.scttsc.business.model.WrongName;
import com.scttsc.business.service.WrongNameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午4:41
 */
@Service("wrongNameManager")
@Transactional(readOnly = true)
public class WrongNameManagerImpl implements WrongNameManager {
    @Autowired
    private WrongNameDao wrongNameDao;

    public List<WrongName> selectByMap(Object map) throws Exception {
        return wrongNameDao.selectByMap(map);
    }

    public int countByMap(Object map) throws Exception {
        return wrongNameDao.countByMap(map);
    }
}
