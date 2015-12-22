package com.scttsc.lte.web;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import com.scttsc.lte.model.WyLteBts;
import com.scttsc.lte.service.WyLteBtsManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/22.
 */
public class WyLteBtsAction extends BaseAction {

    private String countryIds;//
    private String name;//名称
    private Integer type;//类型
    @Autowired
    private WyLteBtsManager wyLteBtsManager;
    /**
     * LTE BTS 查询页面
     * @return
     */
    public String lteBts(){
        return SUCCESS;
    }

    /**
     * LTE室内，室外，隧道站点查询
     * @return
     */
    public String btsData(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBts> list = null;
        try {
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
            if(!Common.isEmpty(type)){
                if (type==1){
                    map.put("isIndoor","是");
                }else if(type==2){
                    map.put("isIndoor","否");
                }else if(type==3){
                    map.put("isIndoor","隧");
                }
            }
            //固定条件
            total = wyLteBtsManager.countWyLteBts(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            list = wyLteBtsManager.findWyLteBts(map);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
