package com.scttsc.baselibs.web;

import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.SceneLibManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SceneLibAction extends BaseAction {
    Logger LOG=Logger.getLogger(SceneLibAction.class);
    @Autowired
    private SceneLibManager sceneLibManager;
    @Autowired
    private CityManager cityManager;
    @Autowired
    private ConsManager consManager;


    WyLibScene wyLibScene;

    Long id;// ID
    String ids;// ID集合
    String countryIds;//区县集合，列表查询

    /**
     * 场景库管理页面
     *
     * @return
     */
    public String sceneLib() {
        return SUCCESS;
    }

    /**
     * 景区库列表页面
     *
     * @return
     */
    public String sceneLibData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLibScene> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            total = sceneLibManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = sceneLibManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 场景库增加编辑页面
     * @return
     */
    public String pageSceneLibAdd(){
        if(!Common.isEmpty(id)){
            wyLibScene=sceneLibManager.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }

    /**
     * 增加景区库
     */
    public String addSceneLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            wyLibScene.setInTime(new Date());
            wyLibScene.setInUser(user.getIntId());
            wyLibScene.setDeleteFlag(0);
            sceneLibManager.insert(wyLibScene);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
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
            wyLibScene = sceneLibManager.selectByPrimaryKey(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
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
            wyLibScene = sceneLibManager.selectByPrimaryKey(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
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
            sceneLibManager.updateByPrimaryKeySelective(wyLibScene);
            jsonMap.put("result", 1);// 编辑成功
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
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
            sceneLibManager.deleteByPrimaryKeys(map);
            jsonMap.put("result", 1);//删除成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);//删除失败
        }
        return SUCCESS;
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

    public WyLibScene getWyLibScene() {
        return wyLibScene;
    }

    public void setWyLibScene(WyLibScene wyLibScene) {
        this.wyLibScene = wyLibScene;
    }
}
