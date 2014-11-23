package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.VitoLib;
import com.scttsc.baselibs.service.VitoLibManager;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.model.TreeNode;
import com.scttsc.common.model.TreeNodeHelper;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
public class VitoLibAction extends BaseAction {
    private VitoLibManager vitoLibManager;

    private CityManager cityManager;

    Long countryId;// 区县ID

    String townJson;

    VitoLib vitoLib;

    int vitoId;

    Long id;// ID

    String ids;// ID集合

    String vitoType;// 库类型（乡镇或者农村）

    String countryIds;// 乡村集合


    City city;// 所属区县

    //导入参数
    private File file;
    private String fileFileName;
    private String fileContentType;


    /**
     * 景区库管理页面
     *
     * @return
     */
    public String vitoLib() {
        return SUCCESS;
    }

    /**
     * 景区库列表页面
     *
     * @return
     */
    public String vitoLibData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<VitoLib> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryId", countryIds);
            } else {
                map.put("countryId", user.getCountryIds());
            }
            if (!Common.isEmpty(vitoType)) {
                if ("0".equals(vitoType)) {
                    map.put("town", 0);// 农村库
                } else {
                    map.put("country", 1);
                }
            }
            total = vitoLibManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = vitoLibManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 乡镇树
     *
     * @return
     */
    public String townTree() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!Common.isEmpty(countryId)) {
            map.put("countryId", countryId);
        }
        map.put("parentId", 0);
        List<VitoLib> vitoLibs = null;
        try {
            vitoLibs = vitoLibManager.getByMap(map);
            TreeNode root = new TreeNode("乡镇库(ROOT)", "0", "-1");
            if (vitoLibs != null) {
                for (VitoLib vitoLib : vitoLibs) {
                    root.addChild(new TreeNode(vitoLib.getName(), vitoLib
                            .getId() + "", "0"));
                }
            }
            JSONArray jsonObject = JSONArray.fromObject(root);
            townJson = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 乡镇、农村树型结构
     *
     * @return
     */
    public String vitoTree() {
        List<VitoLib> vitoLibs = null;
        Map map=new HashMap();
        try {
            if(!Common.isEmpty(countryId)){
                map.put("countryId",countryId);
            }
            vitoLibs = vitoLibManager.getByMap(map);
            TreeNode root = new TreeNode("请选择", "0", "-1");
            List<TreeNode> list = new ArrayList<TreeNode>();
            for (VitoLib vitoLib : vitoLibs) {
                TreeNode node = new TreeNode(vitoLib.getName(), vitoLib.getId() + "", vitoLib.getParentId() + "");
                list.add(node);
            }
            list.add(root);

            TreeNodeHelper helper = new TreeNodeHelper(list);

            root = helper.getRoot();

            JSONArray jsonObject = JSONArray.fromObject(root);
            townJson = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加乡村库
     *
     * @return
     */
    public String addVitoLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            if (!Common.isEmpty(vitoLib)) {
                countryId = vitoLib.getCountryId();
            }
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId);
                vitoLib.setCityId(city.getParentId());// 地市
            }
            vitoLib.setUpdateuser(user.getIntId().longValue());
            vitoLib.setUpdatetime(new Date());
            vitoLibManager.insert(vitoLib);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 乡镇库信息
     *
     * @return
     */
    public String vitoInfo() {
        try {
            vitoLib = vitoLibManager.getById(id);
            if (!Common.isEmpty(vitoLib)) {
                city = cityManager.getById(vitoLib.getCityId());//所属本地网
                City country = cityManager.getById(vitoLib.getCountryId());//区县
                this.getSession().setAttribute("country", country);
                if (vitoLib.getParentId() != 0) {
                    VitoLib parent = vitoLibManager.getById(vitoLib.getParentId());
                    vitoLib.setParent(parent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 编辑乡村库页面
     *
     * @return
     */
    public String vitoLibEditPage() {
        try {
            vitoLib = vitoLibManager.getById(id);
            if (!Common.isEmpty(vitoLib)) {
                city = cityManager.getById(vitoLib.getCountryId());
            }
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
    public String editVitoLib() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            if (!Common.isEmpty(vitoLib)) {
                countryId = vitoLib.getCountryId();
            }
            if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId);
                vitoLib.setCityId(city.getParentId());// 地市
            }
            vitoLib.setUpdateuser(user.getIntId().longValue());
            vitoLib.setUpdatetime(new Date());
            vitoLibManager.update(vitoLib);
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
    public String delVitoLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            vitoLibManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);// 删除成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);// 删除失败
        }
        return SUCCESS;
    }

    public String importTownInputData() {
        try {
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + this.fileFileName);
            FileUtils.copyFile(file, desFile);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    desFile.getPath())); // 得到excel对象
            HSSFSheet sheet = workbook.getSheetAt(0); // 得到第一个sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 得到行数
            HSSFCell cell = null;
            HSSFRow row = null;
            int sucess = 0;
            List<String> errorList = new ArrayList<String>();
            for (int i = 2; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map obj = parseTownObj(i+1, row, errorList);
                if (obj != null) {
                    vitoLibManager.importInsert(obj);
                    sucess++;
                }
            }
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 解析Excel行
     *
     * @param row
     * @return
     */
    public Map parseTownObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getTownCoulmnMap();
        try {
            int j = 0;
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
                } else {
                    //其它
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("updatetime", new Date());
            map.put("updateuser", user.getIntId());
            map.put("parentId", 0);
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }


    public String importCountryInputData() {
        try {
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + this.fileFileName);
            FileUtils.copyFile(file, desFile);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    desFile.getPath())); // 得到excel对象
            HSSFSheet sheet = workbook.getSheetAt(0); // 得到第一个sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 得到行数
            HSSFCell cell = null;
            HSSFRow row = null;
            int sucess = 0;
            List<String> errorList = new ArrayList<String>();
            //定义乡镇库Map,方便导入找到对应乡镇库，增加导入效率
            Map t = new HashMap();
            t.put("parentId", 0);
            List<VitoLib> townLibs = vitoLibManager.getByMap(t);
            Map<String, VitoLib> townMap = new HashMap<String, VitoLib>();
            if (townLibs != null) {
                for (VitoLib townLib : townLibs) {
                    townMap.put(townLib.getName(), townLib);
                }
            }
            for (int i = 2; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map obj = parseCountryObj(i+1, row, errorList, townMap);
                if (obj != null) {
                    vitoLibManager.importInsert(obj);
                    sucess++;
                }
            }
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 解析Excel行
     *
     * @param row
     * @return
     */
    public Map parseCountryObj(int rowNum, HSSFRow row, List errorList, Map<String, VitoLib> townMap) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getCountryCoulmnMap();
        try {
            int j = 0;
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
                } else if ("parentId".equals(dataKey)) {
                    VitoLib vitoLib = townMap.get(cellValue);
                    if (vitoLib != null) {
                        map.put(dataKey, vitoLib.getId());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应所属乡镇...");
                        return null;
                    }
                } else {
                    //其它
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("updatetime", new Date());
            map.put("updateuser", user.getIntId());
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }


    /**
     * 乡镇库导入模板
     *
     * @return
     */
    public String exportTownTemplate() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/townTemplate.xls";
        String fileName = "乡镇库导入模板.xls";
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
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
     * 农村库导入模板
     *
     * @return
     */
    public String exportCountryTemplate() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/countryTemplate.xls";
        String fileName = "农村库导入模板.xls";
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
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


    public void setVitoLibManager(VitoLibManager vitoLibManager) {
        this.vitoLibManager = vitoLibManager;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getTownJson() {
        return townJson;
    }

    public void setTownJson(String townJson) {
        this.townJson = townJson;
    }

    public VitoLib getVitoLib() {
        return vitoLib;
    }

    public void setVitoLib(VitoLib vitoLib) {
        this.vitoLib = vitoLib;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public int getVitoId() {
        return vitoId;
    }

    public void setVitoId(int vitoId) {
        this.vitoId = vitoId;
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

    public String getVitoType() {
        return vitoType;
    }

    public void setVitoType(String vitoType) {
        this.vitoType = vitoType;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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


}
