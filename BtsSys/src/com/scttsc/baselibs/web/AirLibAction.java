package com.scttsc.baselibs.web;

import com.scttsc.admin.model.User;
import com.scttsc.baselibs.model.AirLib;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.AirLibManager;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirLibAction extends BaseAction {
    private AirLibManager airLibManager;

    private ConsManager consManager;

    AirLib airLib;
    Long id;
    String ids;// ID集合

    String airFacs;// 厂家集合


    String airTreeJsonString;

    /**
     * 天线库管理页面
     *
     * @return
     */
    public String airLib() {
        return SUCCESS;
    }

    /**
     * 天线库列表页面
     *
     * @return
     */
    public String airLibData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<AirLib> list = null;
        try {
            if (!Common.isEmpty(airFacs)) {
                map.put("airFacs", airFacs);
            }
            total = airLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = airLibManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 增加天线库信息
     *
     * @return
     */
    public String addAirLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            airLib.setUserId(user.getIntId().longValue());
            airLibManager.insert(airLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 天线信息
     * @return
     */
    public String airInfo() {
        try {
            airLib = airLibManager.getById(id);
            Map map1 = new HashMap();
            map1.put("groupCode", "AIRFAC");
            map1.put("code", airLib.getAirFac());
            Cons airFacCons = consManager.getByMap(map1);
            Map map2 = new HashMap();
            map2.put("groupCode", "AIRTYPE");
            map2.put("code", airLib.getAirType());
            Cons airTypeCons = consManager.getByMap(map2);
            Map map3 = new HashMap();
            map3.put("groupCode", "JHTYPE");
            map3.put("code", airLib.getJhType());
            Cons jhTypeCons = consManager.getByMap(map3);

            this.getRequest().setAttribute("airFacCons",airFacCons);
            this.getRequest().setAttribute("airTypeCons",airTypeCons);
            this.getRequest().setAttribute("jhTypeCons",jhTypeCons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 农村库编辑页面
     *
     * @return
     */
    public String airEditPage() {
        try {
            airLib = airLibManager.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除天线库
     *
     * @return
     */
    public String delAirLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            airLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 编辑天线库
     *
     * @return
     */
    public String editAirLib() {

        try {
            User user = (User) this.getSession().getAttribute("user");
            airLib.setUserId(user.getIntId().longValue());
            airLibManager.update(airLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }

        return SUCCESS;
    }

    // 天线树
    public String airTree() {
        try {
            List<AirLib> airs = airLibManager.selectAll(null);
            TreeNode root = new TreeNode("天线库", "0", "-1");
            for (AirLib airLib : airs) {
                TreeNode node = new TreeNode(airLib.getAirModel(), airLib.getId() + "", "0");
                root.addChild(node);
            }
            JSONArray jsonObject = JSONArray.fromObject(root);
            airTreeJsonString = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public void setAirLibManager(AirLibManager airLibManager) {
        this.airLibManager = airLibManager;
    }

    public AirLib getAirLib() {
        return airLib;
    }

    public void setAirLib(AirLib airLib) {
        this.airLib = airLib;
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

    public String getAirFacs() {
        return airFacs;
    }

    public void setAirFacs(String airFacs) {
        this.airFacs = airFacs;
    }

    public String getAirTreeJsonString() {
        return airTreeJsonString;
    }

    public void setAirTreeJsonString(String airTreeJsonString) {
        this.airTreeJsonString = airTreeJsonString;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }
}
