package com.scttsc.baselibs.service.impl;

import com.scttsc.baselibs.dao.WyLibSceneDao;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.baselibs.service.SceneLibManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/1/26.
 */
@Service("sceneLibManager")
public class SceneLibManagerImpl implements SceneLibManager {
    @Autowired
    private WyLibSceneDao wyLibSceneDao;

    public int countByMap(Map<String, Object> param) throws Exception {
        return wyLibSceneDao.countByMap(param);
    }

    public List<WyLibScene> selectByMap(Map<String, Object> param) throws Exception {
        return wyLibSceneDao.selectByMap(param);
    }

    public int deleteByPrimaryKey(Long id) {
        return wyLibSceneDao.deleteByPrimaryKey(id);
    }

    public int insert(WyLibScene record) {
        return wyLibSceneDao.insert(record);
    }

    public WyLibScene selectByPrimaryKey(Long id) {
        return wyLibSceneDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WyLibScene record) {
        return wyLibSceneDao.updateByPrimaryKeySelective(record);
    }

    public int deleteByPrimaryKeys(Map<String,Object> param) throws Exception {
        return wyLibSceneDao.deleteByPrimaryKeys(param);
    }
}
