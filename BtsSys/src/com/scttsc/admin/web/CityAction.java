package com.scttsc.admin.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.service.CityManager;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/1/28.
 */
public class CityAction extends BaseAction {
    Logger LOG = Logger.getLogger(CityAction.class);
    @Autowired
    private CityManager cityManager;
    private Long cityId;

    /**
     * 通过本地网获取区县列表json
     * @return
     */
    public String getCountrys() {
        List<City> countrys = null;
        try {
            Map<String, Object> param = new HashMap();
            param.put("pid", cityId);
            countrys = cityManager.getByMap(param);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(countrys);
        return SUCCESS;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
