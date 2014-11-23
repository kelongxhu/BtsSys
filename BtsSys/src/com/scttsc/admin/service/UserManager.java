package com.scttsc.admin.service;

import com.scttsc.admin.model.User;

public interface UserManager {
	public User login(Object map)throws Exception;
	User getByPriKey(Long id)throws Exception;
    User selectByImsi(String imsi)throws Exception;
    User strongUser(User user)throws Exception;

}
