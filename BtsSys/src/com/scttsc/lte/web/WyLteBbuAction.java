package com.scttsc.lte.web;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import com.scttsc.lte.model.WyLteBbu;
import com.scttsc.lte.service.WyLteBbuManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/22.
 */
public class WyLteBbuAction extends BaseAction {

    private String countryIds;//
    private String name;//名称
    @Autowired
    private WyLteBbuManager wyLteBbuManager;
    /**
     * LTE室内，室外，隧道站点查询
     * @return
     */
    public String bbuData(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBbu> list = null;
        try {
            map.put("deleteFlag",0);
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            //固定条件
            total = wyLteBbuManager.countWyLteBbu(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            list = wyLteBbuManager.selectWyLteBbu(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
