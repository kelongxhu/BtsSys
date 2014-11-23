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
    List<Role> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    int insertRoleMenu(RoleMenu roleMenu) throws Exception;

    int deleteByRole(BigDecimal roleId) throws Exception;

    List<RoleMenu> selectByRole(BigDecimal roleId) throws Exception;

    Role selectById(BigDecimal intId) throws Exception;

    /**
     * 为角色分配菜单
     * @param menuIds
     * @throws Exception
     */
    void applyMenu(String menuIds, BigDecimal roleId)throws Exception;
}
