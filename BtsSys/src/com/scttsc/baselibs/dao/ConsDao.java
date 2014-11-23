package com.scttsc.baselibs.dao;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.common.dao.MyBatisRepository;

import java.util.List;
@MyBatisRepository
public interface ConsDao {
	List<Cons> getByConds(Object map)throws Exception;
    Cons getByMap(Object map) throws Exception;
}
