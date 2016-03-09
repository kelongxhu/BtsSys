package com.scttsc.admin.service;

import com.scttsc.admin.model.HxMenu;

import java.math.BigDecimal;
import java.util.List;


public interface HxMenuManager {
    /**
     * 获取子菜单列表
     * @param pid
     * @return
     * @throws Exception
     */
    List<HxMenu> getByPid(Long pid) throws Exception;

    /**
     * 获取菜单列表
     * @return
     * @throws Exception
     */
    List<HxMenu> loadAll() throws Exception;

    /**
     * 获取菜单信息
     * @param id
     * @return
     * @throws Exception
     */
    HxMenu selectById(Long id)throws Exception;

    /**
     * 用户的菜单列表
     * @param userId
     * @return
     * @throws Exception
     */
    List<HxMenu> getByUserId(Long userId) throws Exception;

    /**
     * 获取用户角色的菜单
     * @param map
     * @return
     * @throws Exception
     */
     List<HxMenu> getChildByUserId(Object map) throws Exception;
}
