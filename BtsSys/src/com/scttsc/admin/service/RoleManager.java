package com.scttsc.admin.service;

import com.scttsc.admin.model.Role;
import com.scttsc.admin.model.RoleMenu;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-4-28
 * Time: 上午11:06
 */
public interface RoleManager {
    /**
     * 查询角色列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Role> selectByMap(Object map) throws Exception;

    /**
     * 角色总数
     * @param map
     * @return
     * @throws Exception
     */
    int countByMap(Object map) throws Exception;

    /**
     * 插入角色菜单关联表
     * @param roleMenu
     * @return
     * @throws Exception
     */
    int insertRoleMenu(RoleMenu roleMenu) throws Exception;

    /**
     * 删除角色
     * @param roleId
     * @return
     * @throws Exception
     */
    int deleteByRole(BigDecimal roleId) throws Exception;

    /**
     * 根据角色ID获取角色的菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    List<RoleMenu> selectByRole(BigDecimal roleId) throws Exception;

    /**
     * 获取角色信息
     * @param intId
     * @return
     * @throws Exception
     */
    Role selectById(BigDecimal intId) throws Exception;

    /**
     * 为角色分配菜单
     * @param menuIds
     * @throws Exception
     */
    void applyMenu(String menuIds, BigDecimal roleId)throws Exception;
}
