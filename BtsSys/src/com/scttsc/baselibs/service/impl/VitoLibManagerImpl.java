package com.scttsc.baselibs.service.impl;

import com.scttsc.baselibs.dao.VitoLibDao;
import com.scttsc.baselibs.model.VitoLib;
import com.scttsc.baselibs.service.VitoLibManager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("vitoLibManager")
@Transactional(readOnly = true)
public class VitoLibManagerImpl implements VitoLibManager {
    @Autowired
    private VitoLibDao vitoLibDao;

    public int insert(VitoLib vitoLib) throws Exception {
        return vitoLibDao.insert(vitoLib);
    }

    public int update(VitoLib vitoLib) throws Exception {
        return vitoLibDao.update(vitoLib);
    }

    public int delete(Object map) throws Exception {
        return vitoLibDao.delete(map);
    }

    public List<VitoLib> getByConds(Object map) throws Exception {
        return vitoLibDao.getByConds(map);
    }

    public int countByConds(Object map) throws Exception {
        return vitoLibDao.countByConds(map);
    }


    public VitoLib getById(Long id) throws Exception {
        return vitoLibDao.getById(id);
    }

    public List<VitoLib> getByMap(Object map) throws Exception {
        return vitoLibDao.getByMap(map);
    }

    /**
     * 导入数据对象
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int importInsert(Map record) throws Exception {
        VitoLib vitoLib = new VitoLib();
        BeanUtils.populate(vitoLib, record);
        Map map=new HashMap();
        map.put("name",vitoLib.getName());
        List<VitoLib> vitoLibs=vitoLibDao.getByMap(map);
        VitoLib old=null;
        if(vitoLibs!=null&&vitoLibs.size()>0){
            old=vitoLibs.get(0);
        }
        if(old==null){
            vitoLibDao.insert(vitoLib);
        }else{
            vitoLib.setId(old.getId());
            vitoLibDao.update(vitoLib);
        }
        return 0;
    }

    public int deleteByDeleteFlag(Object map) throws Exception {
        return vitoLibDao.deleteByDeleteFlag(map);
    }
}
