package com.scttsc.healthy.dao;

import com.scttsc.healthy.model.WyDbcon;
import java.math.BigDecimal;
import java.util.List;

public interface WyDbconDao {
    WyDbcon selectByPrimaryKey(BigDecimal id);
    List<WyDbcon> selectAll()throws Exception;
}