package com.scttsc.charge.dao;

import com.scttsc.charge.model.Smgp;

import java.util.List;

/**
 * Created by _think on 2014/11/24.
 */
public interface SmgpDao {
    public int insertBatch(List<Smgp> smgpList)throws Exception;

    public int insert(Smgp smgp)throws Exception;
}
