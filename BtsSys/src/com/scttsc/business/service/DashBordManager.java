package com.scttsc.business.service;

import com.scttsc.business.vo.DataCityItem;
import com.scttsc.business.vo.DataCityItemNoManual;

import java.util.List;

/**
 * Created by _think on 2014/12/2.
 */
public interface DashBordManager {
    List<DataCityItem> selectGroupbyCity();

    List<DataCityItemNoManual> statNoManualGroupByCity();
}
