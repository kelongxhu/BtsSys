package com.scttsc.admin.web;

import com.scttsc.admin.model.HxMenu;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.HxMenuManager;
import com.scttsc.charge.util.JsonParser;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HxMenuAction extends BaseAction {

    Logger LOG = Logger.getLogger(HxMenuAction.class);
    private HxMenuManager hxMenuManager;

    List<HxMenu> authorityList;
    Long pid;

    private String menuJson;

    public void setHxMenuManager(HxMenuManager hxMenuManager) {
        this.hxMenuManager = hxMenuManager;
    }

    /**
     * 获取一级菜单
     *
     * @return
     */
    public String menu() {
        try {
            authorityList = hxMenuManager.getByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @JSON(serialize = false)
    public String subMenu() {
        try {
            authorityList = hxMenuManager.getByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String index() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            authorityList = hxMenuManager.getByUserId(user.getIntId());
            for (HxMenu menu : authorityList) {
                Map map = new HashMap();
                map.put("userId", user.getIntId());
                map.put("pid", menu.getId());
                List<HxMenu> children = hxMenuManager.getChildByUserId(map);
                menu.setChildren(children);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String loadMenuTree() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            Map<String, Object> map = new HashMap<String, Object>();
            Long userId = user.getIntId();
            map.put("pid", pid);
            map.put("userId", userId);
            List<HxMenu> menuList = hxMenuManager.getChildByUserId(map);
            if (menuList != null && menuList.size() > 0) {
                for (HxMenu menu : menuList) {
                    buildMenuTree(menu, userId);
                }
            }
            menuJson = JsonParser.toString(menuList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
//        JSONArray jsonObject = JSONArray.fromObject(root);
//        menuJson = jsonObject.toString();
        return SUCCESS;
    }


    private void buildMenuTree(HxMenu menuTreeNode, Long userId) {
        List<HxMenu> menuList = null;
        try {
            Long pid = menuTreeNode.getId();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("pid", pid);
            map.put("userId", userId);
            menuList = hxMenuManager.getChildByUserId(map);
            if (menuList != null && menuList.size() > 0) {
                for (HxMenu menu : menuList) {
                    buildMenuTree(menu, userId);
                }
                menuTreeNode.setChildren(menuList);
            } else {
                return;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


    public List<HxMenu> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<HxMenu> authorityList) {
        this.authorityList = authorityList;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPid() {
        return pid;
    }

    public String getMenuJson() {
        return menuJson;
    }

    public void setMenuJson(String menuJson) {
        this.menuJson = menuJson;
    }
}
