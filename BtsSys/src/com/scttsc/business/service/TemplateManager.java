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
    /**
     * 获取字段配置
     * @param id
     * @return
     * @throws Exception
     */
    ColumnsConfig selectByPrimaryKey(BigDecimal id) throws Exception;

    /**
     * 根据类型获取字段列表
     * @param type
     * @return
     * @throws Exception
     */
    List<ColumnsConfig> selectByType(BigDecimal type) throws Exception;

    /**
     * 根据条件获取字段
     * @param map
     * @return
     * @throws Exception
     */
    List<ColumnsConfig> selectColumnsByConds(Object map) throws Exception;

    /**
     * 查询模板
     * @param map
     * @return
     * @throws Exception
     */
    List<Template> selectTemplateByMap(Object map) throws Exception;

    /**
     * 根据主键获取模板
     * @param id
     * @return
     * @throws Exception
     */
    Template selectById(BigDecimal id) throws Exception;

    /**
     * 查询模板列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Template> selectByMap(Object map) throws Exception;

    /**
     * 模板总数
     * @param map
     * @return
     * @throws Exception
     */
    int countByMap(Object map) throws Exception;

    /**
     * 插入模板
     * @param record
     * @return
     * @throws Exception
     */
    int insert(Template record) throws Exception;

    /**
     * 更新模板
     * @param record
     * @return
     * @throws Exception
     */
    int update(Template record) throws Exception;

}
