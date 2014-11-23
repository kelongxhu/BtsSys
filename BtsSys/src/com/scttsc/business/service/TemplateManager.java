package com.scttsc.business.service;

import com.scttsc.business.model.ColumnsConfig;
import com.scttsc.business.model.Template;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-4-22
 * Time: 下午7:42
 */
public interface TemplateManager {
    ColumnsConfig selectByPrimaryKey(BigDecimal id) throws Exception;

    List<ColumnsConfig> selectByType(BigDecimal type) throws Exception;

    List<ColumnsConfig> selectColumnsByConds(Object map) throws Exception;

    List<Template> selectTemplateByMap(Object map) throws Exception;

    Template selectById(BigDecimal id) throws Exception;

    List<Template> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

     int insert(Template record) throws Exception;

    int update(Template record) throws Exception;

}
