package com.scttsc.business.web;

import com.scttsc.business.service.DashBordManager;
import com.scttsc.common.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by _think on 2014/12/3.
 */
public class DashBordAction extends BaseAction {
    @Autowired
    private DashBordManager dashBordManager;

    /**
     * 按本地网统计各种类型基站小区的数量
     *
     * @return
     */
    public String statByCity() {
        jsonMap.put("dataCitys", dashBordManager.selectGroupbyCity());
        return SUCCESS;
    }

    /**
     * 按本地网统计各种类型基站小区未录入数
     *
     * @return
     */
    public String statNoManualByCity() {
        jsonMap.put("noManualCitys", dashBordManager.statNoManualGroupByCity());
        return SUCCESS;
    }
}
