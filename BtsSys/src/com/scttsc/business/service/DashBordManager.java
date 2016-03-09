package com.scttsc.business.service;

import com.scttsc.business.vo.DataCityItem;
import com.scttsc.business.vo.DataCityItemNoManual;
import com.scttsc.business.vo.DataCitySpecial;

import java.util.List;

/**
 * Created by _think on 2014/12/2.
 */
public interface DashBordManager {
    /**
     * 按城市分组统计
     * @return
     */
    List<DataCityItem> selectGroupbyCity();

    /**
     * 按城市分组统计未录入数量
     * @return
     */
    List<DataCityItemNoManual> statNoManualGroupByCity();

    /**
     * 按城市分组统计特殊站点数
     * @return
     */
    List<DataCitySpecial> statSpecialGroupByCity();
}
