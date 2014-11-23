package com.scttsc.admin.web;

import com.scttsc.admin.model.HxMenu;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.HxMenuManager;
import com.scttsc.common.web.BaseAction;
import org.apache.struts2.json.annotations.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HxMenuAction extends BaseAction {
    private HxMenuManager hxMenuManager;

    List<HxMenu> authorityList;
    Long pid;

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

}
