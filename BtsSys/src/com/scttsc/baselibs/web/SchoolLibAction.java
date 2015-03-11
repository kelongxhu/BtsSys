package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.CityTreeNode;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.SchoolLib;
import com.scttsc.baselibs.model.SecneryLib;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.SchoolLibManager;
import com.scttsc.baselibs.service.SecneryLibManager;
import com.scttsc.baselibs.util.BuildTree;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class SchoolLibAction extends BaseAction {
    private SchoolLibManager schoolLibManager;
    private SecneryLibManager secneryLibManager;
    private CityManager cityManager;
    private ConsManager consManager;

    private SchoolLib schoolLib;

    String groupCode;

    private String cityJson;

    Long id;// ID
    String ids;// ID集合
    String cityIds;//地市集合

    private File file;//导入的楼宇分布图
    private String fileFileName;
    private String fileContentType;

    Integer editFlag;

    public void setSchoolLibManager(SchoolLibManager schoolLibManager) {
        this.schoolLibManager = schoolLibManager;
    }

    public String schoolLib() {
        return SUCCESS;
    }

    /**
     * 初始化树节点
     *
     * @return:用户权限树
     */
    public String initCityTree() {
        User user = (User) this.getSession().getAttribute("user");
        City city = user.getCity();
        CityTreeNode root = ConstantUtil.buildCityTree(city.getId() + "");
        JSONArray jsonObject = JSONArray.fromObject(root);
        cityJson = jsonObject.toString();
        return SUCCESS;
    }

    /**
     * 初始化区县树节点
     *
     * @return:用户权限树
     */
    public String initCountryTree() {
        User user = (User) this.getSession().getAttribute("user");
        City city = user.getCity();
        CityTreeNode root = ConstantUtil.buildCountryTree(city.getId() + "");
        root.setChildExpand("false");
        JSONArray jsonObject = JSONArray.fromObject(root);
        cityJson = jsonObject.toString();
        return SUCCESS;
    }

    /**
     * 初始化全部区县的树
     *
     * @return
     */
    public String initAllCountryTree() {
        CityTreeNode root = ConstantUtil.buildCountryTree("10001");
        root.setChildExpand("false");
        JSONArray jsonObject = JSONArray.fromObject(root);
        cityJson = jsonObject.toString();
        return SUCCESS;
    }

    /**
     * 初始化全部本地网树
     *
     * @return
     */
    public String initAllCityTree() {
        CityTreeNode root = ConstantUtil.buildCityTree("10001");
        JSONArray jsonObject = JSONArray.fromObject(root);
        cityJson = jsonObject.toString();
        return SUCCESS;
    }

    /**
     * 校园库列表页面
     *
     * @return
     */
    public String schoolLibData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<SchoolLib> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                //默认权限
                map.put("cityIds", user.getCityIds());
            }
            total = schoolLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = schoolLibManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 根据常量组获取常量列表
     *
     * @return
     */
    public String cons() {
        String pid = this.getRequest().getParameter("pid");
        Map<String, Object> map = new HashMap<String, Object>();
        List<Cons> consList = null;
        try {
            if (!Common.isEmpty(groupCode)) {
                map.put("groupCode", groupCode);
            }
            if (!Common.isEmpty(pid)) {
                map.put("pId", pid);
            }
            consList = schoolLibManager.getCons(map);
            setJsonMapRows(consList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 内存中拿变量
     * @return
     */
    public String cons2() {
        List<Cons> consList = null;
        if (!Common.isEmpty(groupCode)) {
            consList = ConstantUtil.getInstance().getConListByGroupCode(groupCode);
        }
        setJsonMapRows(consList);
        return SUCCESS;
    }


    /**
     * 增加校园库
     *
     * @return
     */

    public String addSchoolLib() {
        try {
            //待增加填写人
            User user = (User) this.getSession().getAttribute("user");
            schoolLib.setUserId(user.getIntId().longValue());
            if (editFlag == 1) {
                schoolLibManager.update(schoolLib);
            } else {
                schoolLibManager.insert(schoolLib);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 上传楼宇分布图
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String upImage() {
        Map m = new HashMap();
        JSONObject json = null;
        try {
            File desFile = new File(getRequest().getSession().getServletContext().getRealPath("/uploadFiles")
                    + "/" + this.fileFileName);
            FileUtils.copyFile(file, desFile);
            m.put("address", "/uploadFiles/" + this.fileFileName);
            m.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", 0);
        }
        json = JSONObject.fromObject(m);
        jsonResponse(json.toString());
        return null;
    }

    /**
     * 校园库编辑页面
     *
     * @return
     */
    public String addPage() {
        try {
            if (!Common.isEmpty(id)) {
                schoolLib = schoolLibManager.getById(id);
                editFlag = 1;//编辑
            } else {
                editFlag = 0;//增加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String schoolInfo() {
        try {
            schoolLib = schoolLibManager.getById(id);
            City city = cityManager.getById(schoolLib.getCityId());
            Map map1 = new HashMap();
            map1.put("groupCode", "SCHOOL_LEVEL");
            map1.put("code", schoolLib.getSchoolLevel());
            Cons schoolLevelCons = consManager.getByMap(map1);
            Map map2 = new HashMap();
            map2.put("groupCode", "SCHOOL_TYPE");
            map2.put("code", schoolLib.getSchoolType());
            Cons schoolTypeCons = consManager.getByMap(map2);
            Map map4 = new HashMap();
            map4.put("groupCode", "STRUCTURAL_TYPE");
            map4.put("code", schoolLib.getStructuralType());
            Cons structuralTypeCons = consManager.getByMap(map4);
            Map map5 = new HashMap();
            map5.put("groupCode", "WIFI_TYPE");
            map5.put("code", schoolLib.getWifiType());
            Cons wifiTypeCons = consManager.getByMap(map5);
            this.getRequest().setAttribute("city", city);
            this.getRequest().setAttribute("schoolLevelCons", schoolLevelCons);
            this.getRequest().setAttribute("schoolTypeCons", schoolTypeCons);
            this.getRequest().setAttribute("structuralTypeCons", structuralTypeCons);
            this.getRequest().setAttribute("wifiTypeCons", wifiTypeCons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除校园库
     *
     * @return
     */
    public String delSchoolLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            schoolLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }


    public String getCityJson() {
        return cityJson;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public SchoolLib getSchoolLib() {
        return schoolLib;
    }

    public void setSchoolLib(SchoolLib schoolLib) {
        this.schoolLib = schoolLib;
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

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public void setSecneryLibManager(SecneryLibManager secneryLibManager) {
        this.secneryLibManager = secneryLibManager;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public ConsManager getConsManager() {
        return consManager;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }
}
