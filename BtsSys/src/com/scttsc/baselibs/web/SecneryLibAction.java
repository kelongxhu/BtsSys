package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.SecneryLib;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.SecneryLibManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SecneryLibAction extends BaseAction {
    private SecneryLibManager secneryLibManager;
    private CityManager cityManager;
    private ConsManager consManager;


    SecneryLib secneryLib;

    Long id;// ID
    String ids;// ID集合
    String countryIds;//区县集合，列表查询

    /**
     * 景区库管理页面
     *
     * @return
     */
    public String secneryLib() {
        return SUCCESS;
    }

    /**
     * 景区库列表页面
     *
     * @return
     */
    public String secneryLibData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<SecneryLib> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            total = secneryLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = secneryLibManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 增加景区库
     */
    public String addSecneryLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            BigDecimal countryId = secneryLib.getCountryId();
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                secneryLib.setCityId(new BigDecimal(city.getParentId()));// 地市
            }
            secneryLibManager.insert(secneryLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }

        return SUCCESS;
    }

    /**
     * 编辑景区库页面
     *
     * @return
     */
    public String secneryEditPage() {
        try {
            secneryLib = secneryLibManager.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 景区库信息
     *
     * @return
     */
    public String secneryInfo() {
        try {
            secneryLib = secneryLibManager.getById(id);
            City city = cityManager.getById(secneryLib.getCityId().longValue());
            City country = cityManager.getById(secneryLib.getCountryId().longValue());
            Map map=new HashMap();
            map.put("groupCode", "SECNERYTYPE");
            map.put("code", secneryLib.getSceLevel());
            Cons secLevelCons=consManager.getByMap(map);
            this.getRequest().setAttribute("city", city);
            this.getRequest().setAttribute("country", country);
            this.getRequest().setAttribute("secLevelCons",secLevelCons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 编辑景区库
     *
     * @return
     */
    public String editSecneryLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            BigDecimal countryId = secneryLib.getCountryId();
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                secneryLib.setCityId(new BigDecimal(city.getParentId()));// 地市
            }
            secneryLibManager.update(secneryLib);
            jsonMap.put("result", 1);// 编辑成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);// 编辑失败
        }
        return SUCCESS;
    }

    /**
     * 删除景区库
     *
     * @return
     */
    public String delSecneryLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            secneryLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);//删除成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);//删除失败
        }
        return SUCCESS;
    }

    public void setSecneryLibManager(SecneryLibManager secneryLibManager) {
        this.secneryLibManager = secneryLibManager;
    }

    public SecneryLib getSecneryLib() {
        return secneryLib;
    }

    public void setSecneryLib(SecneryLib secneryLib) {
        this.secneryLib = secneryLib;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
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

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }
}
