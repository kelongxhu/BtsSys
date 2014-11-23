package com.scttsc.business.service;

import com.scttsc.business.model.WrongName;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午4:40
 */
public interface WrongNameManager {
     List<WrongName> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;
}
