package com.scttsc.admin.dao;


import com.scttsc.admin.model.RoleMenu;

import java.math.BigDecimal;
import java.util.List;

public interface RoleMenuDao {
    int insert(RoleMenu roleMenu) throws Exception;

    int deleteByRole(BigDecimal roleId) throws Exception;

    List<RoleMenu> selectByRole(BigDecimal roleId) throws Exception;
}