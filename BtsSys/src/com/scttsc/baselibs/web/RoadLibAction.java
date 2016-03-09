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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    //导入参数
    private File file;
    private String fileFileName;

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

    /**
     * 道路信息
     * @return
     */
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



    /**
     * 导出导入模板
     *
     * @return
     */
    public String exportRoadLibTemplate() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/roadTemplate.xls";
        String fileName = "道路库导入模板.xls";
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
           LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * 导入场景库页面
     * @return
     */
    public String importRoadPage(){
        return SUCCESS;
    }


    /**
     * 导入道路库
     *
     * @return
     */
    public String importRoadInputData() {
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
                Map<String, Object> obj = parseRoadLibObj(i + 1, row, errorList);
                if (obj != null) {
                    data.add(obj);
                }
            }
            sucess = roadLibManager.importInsert(data);
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
     * 解析一行道路库数据
     *
     * @param rowNum
     * @param row
     * @param errorList
     * @return
     */
    public Map<String, Object> parseRoadLibObj(int rowNum, HSSFRow row, List<String> errorList) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.geRoadCoulmnMap();
        User user = (User) this.getSession().getAttribute("user");
        try {
            int j = 0;
            Cons roadTypeCons = null;
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
                }else if ("roadProp".equals(dataKey)) {
                    //场景类型
                    roadTypeCons = ConstantUtil.getInstance().getCons("ROAD_TYPE", cellValue);
                    if (roadTypeCons != null) {
                        map.put("roadProp", roadTypeCons.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else{
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
