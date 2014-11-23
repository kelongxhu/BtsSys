package com.scttsc.admin.web;

import com.scttsc.admin.model.HxMenu;
import com.scttsc.admin.model.Role;
import com.scttsc.admin.model.RoleMenu;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.HxMenuManager;
import com.scttsc.admin.service.RoleManager;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.model.TreeNodeHelper;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-28
 * Time: 上午11:19
 */
public class RoleAction extends BaseAction {
    private RoleManager roleManager;
    private HxMenuManager hxMenuManager;
    private Long pid;
    private String name;
    private String json;
    private BigDecimal roleId;
    private Role role;
    private String menuIds;//菜单ID集合


    private Long id;//菜单ID

    public String role() {
        return SUCCESS;
    }

    /**
     * 角色列表
     *
     * @return
     */
    public String roleData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Role> list = null;
        try {
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name);
                map.put("name", "%" + name + "%");
            }
            total = roleManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = roleManager.selectByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    public String roleMenu() {
        User user = (User) this.getSession().getAttribute("user");
        List<RoleMenu> roleMenus = null;
        try {
            role = roleManager.selectById(roleId);
            roleMenus = roleManager.selectByRole(roleId);
            menuIds = "";
            String menuText = "";
            for (RoleMenu roleMenu : roleMenus) {
                menuIds += roleMenu.getMenuId() + ";";
                HxMenu menu = hxMenuManager.selectById(roleMenu.getMenuId().longValue());
                if(menu!=null){
                    menuText += menu.getName() + ";";
                }
            }
            if (menuIds.length() > 0) {
                menuIds = menuIds.substring(0, menuIds.length() - 1);
                menuText = menuText.substring(0, menuText.length() - 1);
            }
            this.getRequest().setAttribute("menuText", menuText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 菜单分配
     *
     * @return
     */
    public String menuApply() {
        try {
            roleManager.applyMenu(menuIds, roleId);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 菜单树
     *
     * @return
     */
    public String menuTreeData() {
        List<HxMenu> menuList = null;
        List<RoleMenu> roleMenus = null;
        try {
            menuList = hxMenuManager.loadAll();
            roleMenus = roleManager.selectByRole(roleId);
            List<TreeNode> nodes = new ArrayList<TreeNode>();
            TreeNode rootNode = new TreeNode("权限菜单", "0", "-1");
            for (HxMenu menu : menuList) {
                TreeNode menuNode = new TreeNode(menu.getName(), menu.getId() + "", menu.getPid() + "");
                for (RoleMenu roleMenu : roleMenus) {
                    if (menu.getId() == roleMenu.getMenuId().longValue()) {
                        menuNode.setIschecked(true);
                        break;
                    }
                }
                nodes.add(menuNode);
            }
            nodes.add(rootNode);
            TreeNodeHelper helper = new TreeNodeHelper(nodes, "0");
            TreeNode root = helper.getRoot();
            JSONArray jsonObject = JSONArray.fromObject(root);
            json = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * Ztree异步生成菜单树
     *
     * @return
     */
    public String roleMenuTreeData() {
//        List<HxMenu> menuList = null;
//        List<TreeNode> array=new ArrayList<TreeNode>();
//        try {
//            if(!Common.isEmpty(id)){
//                menuList=hxMenuManager.getByPid(id);
//                for(HxMenu menu:menuList){
//                    TreeNode menuNode = new TreeNode(menu.getName(), menu.getId() + "", menu.getPid() + "");
//                    menuNode.setisParent("true");
//                    menuNode.setNocheck(true);
//                    array.add(menuNode);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        jsonMap.put("rows", array);
        List<HxMenu> menuList = null;
        List<RoleMenu> roleMenus = null;
        try {
            menuList = hxMenuManager.loadAll();
            roleMenus = roleManager.selectByRole(roleId);
            List<TreeNode> nodes = new ArrayList<TreeNode>();
            TreeNode rootNode = new TreeNode("权限菜单", "0", "-1");
            for (HxMenu menu : menuList) {
                TreeNode menuNode = new TreeNode(menu.getName(), menu.getId() + "", menu.getPid() + "");
                for (RoleMenu roleMenu : roleMenus) {
                    if (menu.getId() == roleMenu.getMenuId().longValue()) {
                        menuNode.setChecked(true);
                        break;
                    }
                }
                nodes.add(menuNode);
            }
            nodes.add(rootNode);
            TreeNodeHelper helper = new TreeNodeHelper(nodes, "0");
            TreeNode root = helper.getRoot();
            JSONArray jsonObject = JSONArray.fromObject(root);
            json = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 菜单分配权限
     *
     * @return
     */
    public String roleMenu2() {
        try {
            role = roleManager.selectById(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHxMenuManager(HxMenuManager hxMenuManager) {
        this.hxMenuManager = hxMenuManager;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
