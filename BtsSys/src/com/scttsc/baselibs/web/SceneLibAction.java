package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.SceneLibManager;
import com.scttsc.baselibs.util.BuildTree;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
public class SceneLibAction extends BaseAction {
    Logger LOG = Logger.getLogger(SceneLibAction.class);
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
    String cityIds;
    String jsonData;
    String sceneTypes;
    String name;

    //导入参数
    private File file;
    private String fileFileName;

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
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
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
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 场景库增加编辑页面
     *
     * @return
     */
    public String pageSceneLibAdd() {
        if (!Common.isEmpty(id)) {
            wyLibScene = sceneLibManager.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }

    /**
     * 增加景区库
     */
    public String addSceneLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            if (wyLibScene.getId() == null) {
                wyLibScene.setInTime(new Date());
                wyLibScene.setInUser(user.getIntId());
                wyLibScene.setDeleteFlag(0);
                sceneLibManager.insert(wyLibScene);
            } else {
                wyLibScene.setUpdateTime(new Date());
                sceneLibManager.updateByPrimaryKeySelective(wyLibScene);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
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
            if (!Common.isEmpty(wyLibScene)) {
                City city = cityManager.getById(wyLibScene.getCityId().longValue());
                wyLibScene.setCity(city);
                City country = cityManager.getById(wyLibScene.getCountryId().longValue());
                wyLibScene.setCountry(country);
                Cons sceneType = ConstantUtil.getInstance().getConsByCode("SCENE_TYPE", wyLibScene.getSceneType() + "");
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("groupCode", "SCENE_LEVEL");
                param.put("pId", wyLibScene.getSceneType());
                param.put("code", wyLibScene.getSceneLevel());
                Cons sceneLevel = consManager.getByMap(param);
                wyLibScene.setSceneTypeName(sceneType.getName());
                wyLibScene.setSceneLevelName(sceneLevel.getName());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 删除景区库
     *
     * @return
     */
    public String delSceneLibs() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            sceneLibManager.deleteByPrimaryKeys(map);
            jsonMap.put("result", 1);//删除成功
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);//删除失败
        }
        return SUCCESS;
    }


    /**
     * 热点类型
     *
     * @return
     */
    public String hotTree() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<WyLibScene> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            }
            if (!Common.isEmpty(sceneTypes)) {
                map.put("sceneTypes", sceneTypes);
            }
            list = sceneLibManager.selectByMap(map);
            TreeNode root = BuildTree.buildTreeNodeBySceneLib(list);
            JSONArray jsonObject = JSONArray.fromObject(root);
            jsonData = jsonObject.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * @return
     */
    public String sceneLibs() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        List<WyLibScene> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
            }
            if (!Common.isEmpty(sceneTypes)) {
                map.put("sceneTypes", sceneTypes);
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", name);
            }
            list = sceneLibManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        return SUCCESS;
    }

    /**
     * 选择场景库对话框
     *
     * @return
     */
    public String seeneDialog() {
        return SUCCESS;
    }

    /**
     * 导出导入模板
     *
     * @return
     */
    public String exportSceneLibTemplate() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/sceneLibTemplate.xls";
        String fileName = "场景库导入模板.xls";
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);
            HttpServletResponse resp = getResponse();
            // 设置Response
            resp.reset();
            // 不读缓存
            resp.setHeader("Cache-Control", "no-store");
            resp.setHeader("Pragrma", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType("application/msexcel");
            resp.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("GBK"), "iso-8859-1"));
            demoSheet.setGridsPrinted(true);
            demoWorkBook.write(resp.getOutputStream());

            // 清空流
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入场景库页面
     * @return
     */
    public String importScenePage(){
        return SUCCESS;
    }


    /**
     * 导入场景库
     *
     * @return
     */
    public String importSceneInputData() {
        int sucess = 0;
        List<String> errorList = new ArrayList<String>();
        try {
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + this.fileFileName);
            FileUtils.copyFile(file, desFile);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(desFile.getPath())); // 得到excel对象
            HSSFSheet sheet = workbook.getSheetAt(0); // 得到第一个sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 得到行数
            HSSFRow row = null;
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            for (int i =1 ; i < rows; i++) { // 从第二行开始取数
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map<String, Object> obj = parseSceneLibObj(i + 1, row, errorList);
                if (obj != null) {
                    data.add(obj);
                }
            }
            sucess = sceneLibManager.importInsert(data);
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
            jsonMap.put("result", 0);
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 解析一行场景库数据
     *
     * @param rowNum
     * @param row
     * @param errorList
     * @return
     */
    public Map<String, Object> parseSceneLibObj(int rowNum, HSSFRow row, List<String> errorList) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getSceneCoulmnMap();
        User user = (User) this.getSession().getAttribute("user");
        try {
            int j = 0;
            Cons sceneTypeCons = null;
            for (String dataKey : coulmnMap.keySet()) {
                Validity validity = coulmnMap.get(dataKey);
                HSSFCell cell = row.getCell((short) (j));//Excel
                String cellValue = "";
                if (cell != null) {
                    cellValue = StringUtil.null2String(ExcelHelper.getValue(cell));//Excel列值
                }
                boolean validtyFlag = validity.check(cellValue);  //验证字段
                if (!validtyFlag) {
                    //未经过校验，保存该列校验失败原因,该行不在导入
                    errorList.add("第" + rowNum + "行:" + "校验失败," + validity.getMsg());
                    return null;
                }
                //为空字段，验证通过，继续数据
                if (StringUtils.isEmpty(cellValue)) {
                    j++;
                    continue;
                }
                //特殊字段校验处理
                if ("cityId".equals(dataKey)) {
                    City city = ConstantUtil.getInstance().getCity(cellValue);
                    if (city != null) {
                        map.put(dataKey, city.getId());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else if ("countryId".equals(dataKey)) {
                    City city = ConstantUtil.getInstance().getCountry(cellValue);
                    if (city != null) {
                        map.put(dataKey, city.getId());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else if ("sceneType".equals(dataKey)) {
                    //场景类型
                    sceneTypeCons = ConstantUtil.getInstance().getCons("SCENE_TYPE", cellValue);
                    if (sceneTypeCons != null) {
                        map.put("sceneType", sceneTypeCons.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else if ("sceneLevel".equals(dataKey)) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("groupCode", "SCENE_LEVEL");
                    param.put("pId", sceneTypeCons.getCode());
                    param.put("name", cellValue);
                    Cons sceneLevel = consManager.getByMap(param);
                    if (sceneLevel != null) {
                        map.put("sceneLevel", sceneLevel.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else {
                    //其它
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("inUser", user.getIntId());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
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

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getSceneTypes() {
        return sceneTypes;
    }

    public void setSceneTypes(String sceneTypes) {
        this.sceneTypes = sceneTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
