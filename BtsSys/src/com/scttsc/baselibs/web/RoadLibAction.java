package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.RoadLibManager;
import com.scttsc.baselibs.service.TunnelLibManager;
import com.scttsc.baselibs.util.BuildTree;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class RoadLibAction extends BaseAction {

    Logger LOG = Logger.getLogger(RoadLibAction.class);

    private RoadLibManager roadLibManager;

    private TunnelLibManager tunnelLibManager;

    private ConsManager consManager;

    private CityManager cityManager;

    RoadLib roadLib;

    Long id;// ID

    String ids;//ID集合

    BigDecimal cityId;//本地网

    String roadProps;//属性集合,查询参数

    String roadJson;

    /**
     * 道路库管理页面
     *
     * @return
     */
    public String roadLib() {
        return SUCCESS;
    }

    /**
     * 道路库列表页面
     *
     * @return
     */
    public String roadLibData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<RoadLib> list = null;
        try {
            if (!Common.isEmpty(roadProps)) {
                map.put("roadProps", roadProps);
            }
            if(!Common.isEmpty(cityId)){
                map.put("cityId",cityId);
            }
            total = roadLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = roadLibManager.getByConds(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 增加道路库信息
     *
     * @return
     */
    public String addRoadLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            if (roadLib.getId() == null) {
                roadLib.setIntime(new Date());
                roadLib.setDeleteFlag(0);
                roadLibManager.insert(roadLib);
            } else {
                roadLib.setUpdatetime(new Date());
                roadLibManager.update(roadLib);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    public String roadInfo() {
        try {
            roadLib = roadLibManager.getById(id);
            City city = cityManager.getById(roadLib.getCityId().longValue());
            Map map = new HashMap();
            map.put("groupCode", "ROADPROP");
            map.put("code", roadLib.getRoadProp());
            Cons roadPropCons = consManager.getByMap(map);
            this.getRequest().setAttribute("city", city);
            this.getRequest().setAttribute("roadPropCons", roadPropCons);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 编辑道路库页面
     *
     * @return
     */
    public String addRoadLibPage() {
        try {
            roadLib = roadLibManager.getById(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 删除道路信息
     *
     * @return
     */
    public String delRoadLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            roadLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 本地网道路树
     *
     * @return
     */
    public String roadTree() {
        List<RoadLib> list;
        try {
            list = roadLibManager.loadAll();
            TreeNode root = BuildTree.buildTreeNode(list);
            JSONArray jsonObject = JSONArray.fromObject(root);
            roadJson = jsonObject.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 道路隧道树
     *
     * @return
     */
    public String roadTunnelTree() {
        List<RoadLib> list;
        List<TunnelLib> list2;
        try {
            list = roadLibManager.loadAll();
            list2 = tunnelLibManager.loadAll();
            TreeNode root = BuildTree.buildTreeNodeByRoad(list, list2);
            JSONArray jsonObject = JSONArray.fromObject(root);
            roadJson = jsonObject.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    public void setRoadLibManager(RoadLibManager roadLibManager) {
        this.roadLibManager = roadLibManager;
    }

    public RoadLib getRoadLib() {
        return roadLib;
    }

    public void setRoadLib(RoadLib roadLib) {
        this.roadLib = roadLib;
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

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public String getRoadProps() {
        return roadProps;
    }

    public void setRoadProps(String roadProps) {
        this.roadProps = roadProps;
    }

    public String getRoadJson() {
        return roadJson;
    }

    public void setRoadJson(String roadJson) {
        this.roadJson = roadJson;
    }

    public void setTunnelLibManager(TunnelLibManager tunnelLibManager) {
        this.tunnelLibManager = tunnelLibManager;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }
}
