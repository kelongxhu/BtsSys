package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.Cons;

public interface ConsManager {
    /**
     * 获取常量信息
     * @param map
     * @return
     * @throws Exception
     */
	Cons getByMap(Object map)throws Exception;
}
