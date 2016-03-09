package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.WyLibScene;

import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/1/26.
 */
public interface SceneLibManager {
    /**
     * 场景库总数
     * @param param
     * @return
     * @throws Exception
     */
    int countByMap(Map<String, Object> param) throws Exception;

    /**
     * 分页查询场景库列表
     * @param param
     * @return
     * @throws Exception
     */
    List<WyLibScene> selectByMap(Map<String, Object> param) throws Exception;

    /**
     * 根據ID刪除場景庫
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 删除多条场景库
     * @param param
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKeys(Map<String, Object> param) throws Exception;

    /**
     * 插入场景库
     * @param record
     * @return
     */
    int insert(WyLibScene record);

    /**
     * 根据主键查询场景库
     * @param id
     * @return
     */
    WyLibScene selectByPrimaryKey(Long id);

    /**
     * 更新场景库
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WyLibScene record);

    /**
     * 根据ID集合获取多条场景库
     * @param ids
     * @return
     */
    List<WyLibScene> selectByPrimaryKeys(String ids);

    /**
     * 批量导入场景库
     * @param data
     * @return
     */
    int importInsert(List<Map<String, Object>> data);
}
