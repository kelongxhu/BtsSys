package com.scttsc.baselibs.service.impl;

import com.scttsc.baselibs.dao.WyLibSceneDao;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.baselibs.service.SceneLibManager;
import com.scttsc.business.util.DateConverter;
import com.scttsc.charge.model.WyBtsCharge;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/1/26.
 */
@Service("sceneLibManager")
public class SceneLibManagerImpl implements SceneLibManager {

    Logger LOG = Logger.getLogger(SceneLibManagerImpl.class);
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

    public int deleteByPrimaryKeys(Map<String, Object> param) throws Exception {
        return wyLibSceneDao.deleteByPrimaryKeys(param);
    }

    public List<WyLibScene> selectByPrimaryKeys(String ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return wyLibSceneDao.selectByPrimaryKeys(param);
    }

    public int importInsert(List<Map<String, Object>> data) {
        int result = 0;
        try {
            DateConverter d = new DateConverter();
            String[] datePattern = {"yyyy-MM-dd", "yyyy-MM"};
            d.setPatterns(datePattern);
            ConvertUtils.register(d, Date.class);
            for (Map<String, Object> sceneMap : data) {
                WyLibScene wyLibScene = new WyLibScene();
                BeanUtils.populate(wyLibScene, sceneMap);
                wyLibScene.setInTime(new Date());
                wyLibScene.setDeleteFlag(0);
                result += inserOrUpdateSceneLib(wyLibScene);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    int inserOrUpdateSceneLib(WyLibScene wyLibScene) {
        int result = 0;
        try {
            result = wyLibSceneDao.insert(wyLibScene);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return result;
    }
}
