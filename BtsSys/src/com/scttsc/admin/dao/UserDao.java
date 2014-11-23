package com.scttsc.admin.dao;

import com.scttsc.admin.model.User;
import com.scttsc.common.dao.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface UserDao {
	User login(Object map) throws Exception;

	User getByPriKey(Long id) throws Exception;

    List<User> selectByImsi(String imsi)throws Exception;
}