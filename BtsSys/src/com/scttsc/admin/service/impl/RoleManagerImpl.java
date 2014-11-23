package com.scttsc.admin.service.impl;

import com.scttsc.admin.dao.RoleDao;
import com.scttsc.admin.dao.RoleMenuDao;
import com.scttsc.admin.model.Role;
import com.scttsc.admin.model.RoleMenu;
import com.scttsc.admin.service.RoleManager;
import com.scttsc.common.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-4-28
 * Time: 上午11:14
 */
@Service("roleManager")
@Transactional(readOnly = true)
public class RoleManagerImpl implements RoleManager {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    public List<Role> selectByMap(Object map) throws Exception {
        return roleDao.selectByMap(map);
    }

    public int countByMap(Object map) throws Exception {
        return roleDao.countByMap(map);
    }

    public int insertRoleMenu(RoleMenu roleMenu) throws Exception {
        return roleMenuDao.insert(roleMenu);
    }

    public int deleteByRole(BigDecimal roleId) throws Exception {
        return roleMenuDao.deleteByRole(roleId);
    }

    public List<RoleMenu> selectByRole(BigDecimal roleId) throws Exception {
        return roleMenuDao.selectByRole(roleId);
    }

    public Role selectById(BigDecimal intId) throws Exception {
        return roleDao.selectById(intId);
    }

    /**
     * 为角色分配菜单
     *
     * @param menuIds
     * @throws Exception
     */
    public void applyMenu(String menuIds, BigDecimal roleId) throws Exception {
        deleteByRole(roleId);//先删除
        if (!Common.isEmpty(menuIds)) {
            String[] menuIdArr = menuIds.split(";");
            for (String menuId : menuIdArr) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(new BigDecimal(menuId));
                insertRoleMenu(roleMenu);
            }
        }
    }
}
