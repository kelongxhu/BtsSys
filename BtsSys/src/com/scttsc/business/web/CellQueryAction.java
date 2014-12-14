package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.Cell;
import com.scttsc.business.service.CellManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by _think on 2014/12/13.
 */
public class CellQueryAction extends BaseAction {

    Logger LOG=Logger.getLogger(CellQueryAction.class);

    @Autowired
    private CellManager cellManager;

    //查询条件
    private String isIndoor;
    private String name;
    private String bscName;
    private String btsId;
    private Integer ci;
    private Integer pn;

    private String countryIds;

    /**
     * 小区查询页面
     *
     * @return
     */
    public String cellQuery() {
        return SUCCESS;
    }

    /**
     * 小区查询数据
     * @return
     */
    public String cellQueryData(){
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<Cell> list = null;
        try {
            map.put("deleteFlag", 0);
            if (!Common.isEmpty(countryIds)) {
                // 默认权限
                map.put("countryIds", countryIds);
            } else {
                    //用户权限树
                    map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(ci)) {
                map.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map.put("pn", pn);
            }
            if(!Common.isEmpty(isIndoor)){
                isIndoor = Common.decodeURL(isIndoor).trim();
                map.put("isIndoor",isIndoor);
            }
            total = cellManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    public String getIsIndoor() {
        return isIndoor;
    }

    public void setIsIndoor(String isIndoor) {
        this.isIndoor = isIndoor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName;
    }

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }
}
