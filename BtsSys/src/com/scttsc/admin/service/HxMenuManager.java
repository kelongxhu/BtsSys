package com.scttsc.admin.service;

import com.scttsc.admin.model.HxMenu;

import java.math.BigDecimal;
import java.util.List;


public interface HxMenuManager {
    List<HxMenu> getByPid(Long pid) throws Exception;

    List<HxMenu> loadAll() throws Exception;

    HxMenu selectById(Long id)throws Exception;

    List<HxMenu> getByUserId(Long userId) throws Exception;

     List<HxMenu> getChildByUserId(Object map) throws Exception;
}
