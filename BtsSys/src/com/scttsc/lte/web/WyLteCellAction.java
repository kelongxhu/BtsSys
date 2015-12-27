package com.scttsc.lte.web;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import com.scttsc.lte.model.WyLteCell;
import com.scttsc.lte.service.WyLteCellManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2015/12/26.
 */
public class WyLteCellAction extends BaseAction {
    private String countryIds;    //区县
    private String name;          //名称
    private Integer type;         //类型
    @Autowired
    private WyLteCellManager wyLteCellManager;
    /**
     * LTE BTS 查询页面
     * @return
     */
    public String lteCell(){
        return SUCCESS;
    }

    /**
     * LTE室内，室外，隧道站点查询
     * @return
     */
    public String cellData(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteCell> list = null;
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
            //室內，室外，隧道小區
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
            total = wyLteCellManager.countWyLteCell(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            list = wyLteCellManager.selectWyLteCell(map);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
