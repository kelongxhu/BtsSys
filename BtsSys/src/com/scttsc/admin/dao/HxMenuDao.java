package com.scttsc.admin.dao;

import com.scttsc.admin.model.HxMenu;
import com.scttsc.common.dao.BaseDao;
import com.scttsc.common.dao.MyBatisRepository;

import java.math.BigDecimal;
import java.util.List;

@MyBatisRepository
public interface HxMenuDao extends BaseDao<HxMenu, Long> {
    List<HxMenu> getByPid(Long pid) throws Exception;

    List<HxMenu> loadAll() throws Exception;

    HxMenu selectById(Long id) throws Exception;

    List<HxMenu> getByUserId(Long userId) throws Exception;

    List<HxMenu> getChildByUserId(Object map) throws Exception;
}
