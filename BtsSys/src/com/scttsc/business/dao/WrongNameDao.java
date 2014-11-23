package com.scttsc.business.dao;

import com.scttsc.business.model.WrongName;

import java.util.List;

public interface WrongNameDao {
    List<WrongName> selectByMap(Object map) throws Exception;

    int countByMap(Object map) throws Exception;
}