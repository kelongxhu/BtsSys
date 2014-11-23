package com.scttsc.business.dao;

import com.scttsc.business.model.Template;

import java.math.BigDecimal;
import java.util.List;

public interface TemplateDao {

    List<Template> selectTemplateByMap(Object map) throws Exception;

    Template selectById(BigDecimal id) throws Exception;

    int deleteById(BigDecimal id) throws Exception;

    int insert(Template record) throws Exception;

    int update(Template record) throws Exception;

    List<Template> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;
}