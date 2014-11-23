package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.service.TunnelLibManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TunnelLibAction extends BaseAction {
    private TunnelLibManager tunnelLibManager;

    private CityManager cityManager;

    String countryIds;// 区县集合

    private TunnelLib tunnelLib;

    int countryId;// 区县

    Long roadId;// 道路ID

    Long id;

    String ids;//ID集合

    Integer autoFlag;//自动标识

    public String tunnelLib() {
        return SUCCESS;
    }

    public String tunnelLibData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<TunnelLib> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }

            if(!Common.isEmpty(autoFlag)){
                map.put("autoFlag",autoFlag);
            }
            total = tunnelLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = tunnelLibManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 增加隧道库
     *
     * @return
     */
    public String addTunnelLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            BigDecimal countryId = tunnelLib.getCountryId();
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                tunnelLib.setCityId(new BigDecimal(city.getParentId()));// 地市
            }
            tunnelLib.setUpdateuser(user.getIntId());
            tunnelLibManager.insert(tunnelLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 编辑隧道库页面
     *
     * @return
     */
    public String tunnelLibEditPage() {
        try {
            tunnelLib = tunnelLibManager.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 隧道库信息
     *
     * @return
     */
    public String tunnelInfo() {
        try {
            tunnelLib = tunnelLibManager.getById(id);
            City city=cityManager.getById(tunnelLib.getCityId().longValue());
            City country=cityManager.getById(tunnelLib.getCountryId().longValue());
            this.getRequest().setAttribute("city",city);
            this.getRequest().setAttribute("country",country);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 编辑隧道库
     *
     * @return
     */
    public String editTunnelLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            BigDecimal countryId = tunnelLib.getCountryId();
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                tunnelLib.setCityId(new BigDecimal(city.getParentId()));// 地市
            }
            tunnelLib.setUpdateuser(user.getIntId());
            tunnelLibManager.update(tunnelLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 删除隧道库
     *
     * @return
     */
    public String delTunnelLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            tunnelLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }


    public void setTunnelLibManager(TunnelLibManager tunnelLibManager) {
        this.tunnelLibManager = tunnelLibManager;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public TunnelLib getTunnelLib() {
        return tunnelLib;
    }

    public void setTunnelLib(TunnelLib tunnelLib) {
        this.tunnelLib = tunnelLib;
    }

    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }
}
