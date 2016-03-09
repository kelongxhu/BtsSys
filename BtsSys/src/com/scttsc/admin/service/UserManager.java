package com.scttsc.admin.service;

import com.scttsc.admin.model.User;

public interface UserManager {
    /**
     * 登陸
     * @param map
     * @return
     * @throws Exception
     */
	public User login(Object map)throws Exception;

    /**
     * 获取用户信息
     * @param id
     * @return
     * @throws Exception
     */
	User getByPriKey(Long id)throws Exception;

    /**
     * 根据IMSI获取用户信息
     * @param imsi
     * @return
     * @throws Exception
     */
    User selectByImsi(String imsi)throws Exception;

    /**
     * 填充用户信息
     * @param user
     * @return
     * @throws Exception
     */
    User strongUser(User user)throws Exception;

}
