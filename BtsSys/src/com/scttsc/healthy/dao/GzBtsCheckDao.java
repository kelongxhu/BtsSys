package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.GzBtsCheck;

import java.util.List;
import java.util.Map;

public interface GzBtsCheckDao {
    List<GzBtsCheck> selectBy(Map map) throws Exception;
}