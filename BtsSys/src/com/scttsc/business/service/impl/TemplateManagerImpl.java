package com.scttsc.business.service.impl;

import com.scttsc.business.dao.ColumnsConfigDao;
import com.scttsc.business.dao.TemplateDao;
import com.scttsc.business.model.ColumnsConfig;
import com.scttsc.business.model.Template;
import com.scttsc.business.service.TemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: 隆科
 * Date: 13-4-22
 * Time: 下午7:43
 */
@Service("templateManager")
@Transactional(readOnly = true)
public class TemplateManagerImpl implements TemplateManager {
    @Autowired
    ColumnsConfigDao columnsConfigDao;
    @Autowired
    TemplateDao templateDao;

    public ColumnsConfig selectByPrimaryKey(BigDecimal id)throws Exception {
        return columnsConfigDao.selectByPrimaryKey(id);
    }

    public List<ColumnsConfig> selectByType(BigDecimal type)throws Exception {
        return columnsConfigDao.selectByType(type);
    }

    public List<Template> selectTemplateByMap(Object map) throws Exception {
        return templateDao.selectTemplateByMap(map);
    }

    public List<ColumnsConfig> selectColumnsByConds(Object map) throws Exception {
        return columnsConfigDao.selectByConds(map);
    }

    public Template selectById(BigDecimal id) throws Exception {
        return templateDao.selectById(id);
    }

    public List<Template> selectByMap(Object map) throws Exception {
        return templateDao.selectByMap(map);
    }

    public int countByMap(Object map) throws Exception {
        return templateDao.countByMap(map);
    }

    public int insert(Template record) throws Exception {
        return templateDao.insert(record);
    }

    public int update(Template record) throws Exception {
        return templateDao.update(record);
    }
}
