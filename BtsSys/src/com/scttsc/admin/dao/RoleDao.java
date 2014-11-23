package com.scttsc.admin.dao;

import com.scttsc.admin.model.Role;

import java.math.BigDecimal;
import java.util.List;

public interface RoleDao {
    List<Role> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;

    Role selectById(BigDecimal intId)throws Exception;
}