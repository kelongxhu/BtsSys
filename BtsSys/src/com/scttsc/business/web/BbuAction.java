package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.WyLibVillage;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.VillageLibManager;
import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.BbuManual;
import com.scttsc.business.service.BbuManager;
import com.scttsc.business.service.BbuManualManager;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@SuppressWarnings("serial")
public class BbuAction extends BaseAction {

    Logger LOG=Logger.getLogger(BbuAction.class);

    private BbuManager bbuManager;

    private BbuManualManager bbuManualManager;

    private CityManager cityManager;

    private ConsManager consManager;

    private String countryIds;

    private Long intId;// ID

    BbuManual bbuManual;

    Bbu bbu;

    Integer editFlag;// 编辑标识

    // 增加编辑

    private File file;
    private String fileFileName;
    private String fileContentType;

    List<Bbu> bbuList;//共站BBU
    City city;//本地网
    City country;//区县


    //查询条件
    private String name;
    private String bscName;
    private String btsId;
    String queryFlag;


    String ids;

    Integer checkAllFlag;
    private Integer manualFlag;//是否录入标志
    @Autowired
    private VillageLibManager villageLibManager;


    /**
     * BBU列表页面
     *
     * @return
     */
    public String bbu() {
        return SUCCESS;
    }

    /**
     * BBU查询
     *
     * @return
     */
    public String bbuData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bbu> list = null;
        try {
            //固定条件
            map.put("deleteFlag", 0);// 再用
            map.put("isShare", 0);
            map.put("bbuType", "1,2");//纯BBU数据
            if (!Common.isEmpty(countryIds)) {
                // 查询条件
                map.put("countryIds", countryIds);
            } else {
                // 默认数据权限
                if (Common.isEmpty(queryFlag)) {
                    //用户权限
                    map.put("countryIds", user.getCountryIds());
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                btsId = Common.decodeURL(btsId).trim();
                map.put("btsId", btsId);
            }
            if(!Common.isEmpty(manualFlag)){
                map.put("manualFlag",manualFlag);
            }
            total = bbuManager.countByExample(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = bbuManager.selectByExample(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * BBU增加编辑页面
     *
     * @return
     */
    public String bbuInput() {
        try {
            bbu = bbuManager.getById(intId);
            City city = cityManager.getById(bbu.getCityId().longValue());
            City country = cityManager.getById(bbu.getCountyId().longValue());
            bbu.setCity(city);
            bbu.setCountry(country);
            bbuManual = bbuManualManager.getById(intId);
            if (bbuManual != null) {
                Map<String,Object> param=new HashMap<String, Object>();
                String town=bbuManual.getTown();
                String viliage=bbuManual.getVillage();
                if(!StringUtils.isEmpty(town)&&!StringUtils.isEmpty(viliage)){
                    String[] v=viliage.split(",");
                    if(v!=null&&v.length>0){
                        viliage=v[0];
                    }
                    param.put("town",town);
                    param.put("village",viliage);
                    WyLibVillage wyLibVillage=villageLibManager.selectByVillage(param);
                    if(wyLibVillage!=null){
                        bbuManual.setWyLibVillage(wyLibVillage);
                    }
                }
                editFlag = 1;// 编辑页面
            } else {
                editFlag = 0;// 增加页面
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return SUCCESS;
    }

    /**
     * BBU显示信息页面
     *
     * @return
     */
    public String bbuInfo() {
        try {
            bbu = bbuManager.getById(intId);
            bbuManual = bbuManualManager.getById(intId);
            int bbuNum = 0;
            if (bbu != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bbuType", 2);//共站BBU
                map.put("deleteFlag", 0);
                map.put("relateWyBts", bbu.getIntId());
                bbuList = bbuManager.selectBbuByConds(map);
                bbuNum = bbuList.size();
                city = cityManager.getById(bbu.getCityId().longValue());
                country = cityManager.getById(bbu.getCountyId().longValue());
            }
            if (bbuManual != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("groupCode", "MRSTRUT");
                map.put("code", bbuManual.getMrStrut());
                Cons struts = consManager.getByMap(map);
                bbuManual.setMrStrutCons(struts);
            }


            this.getRequest().setAttribute("bbuNum", bbuNum + 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return SUCCESS;
    }

    /**
     * 增加站点手工数据
     *
     * @return
     */
    public String addBbuManual() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            bbuManual.setUpdateuser(user.getIntId());
            bbuManual.setUpdatetime(new Date());
            if (editFlag == 1) {
                bbuManualManager.update(bbuManual);// 编辑
            } else {
                bbuManualManager.insert(bbuManual);// 增加
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 导出待录入数据
     *
     * @return
     */
    public String exportBbuInputData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bbu> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/bbuTemplate.xls";
        String fileName = "BBU待录入数据.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            map.put("deleteFlag", 0);// 再用
            map.put("bbuType", "1,2");//纯BBU数据
            map.put("isShare", 0);


            if (checkAllFlag == null || checkAllFlag == 0) {
                if (!Common.isEmpty(ids)) {
                    map.put("ids", ids); //
                } else {
                    map.put("manualFlag", 0);// 未录入的数据
                }
            }

            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                btsId = Common.decodeURL(btsId).trim();
                map.put("btsId", btsId);
            }
            total = bbuManager.countByExample(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = bbuManager.selectByExample(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 2;
            for (Bbu bbu : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex - 1));
                cList.add(StringUtil.null2String(bbu.getIntId()));
                cList.add(StringUtil.null2String(bbu.getName()));
                cList.add(StringUtil.null2String(bbu.getBscName()));
                cList.add(StringUtil.null2String(bbu.getBtsId()));
                int manualFlag = bbu.getManualFlag().intValue();
                if (manualFlag == 1) {
                    BbuManual bbuManual1 = bbuManualManager.getById(bbu.getIntId().longValue());
                    if (bbuManual1 != null) {
                        cList.add(StringUtil.null2String(bbuManual1.getLongitude()));
                        cList.add(StringUtil.null2String(bbuManual1.getLatitude()));
                        cList.add(StringUtil.null2String(bbuManual1.getTown()));
                        cList.add(StringUtil.null2String(bbuManual1.getVillage()));
                        cList.add(StringUtil.null2String(bbuManual1.getSharFlag()));
                        cList.add(StringUtil.null2String(bbuManual1.getSharName()));//共享方名称
                        cList.add(StringUtil.null2String(bbuManual1.getAddress()));
                        cList.add(StringUtil.null2String(bbuManual1.getOpenTime()));
                        cList.add(StringUtil.null2String(bbuManual1.getTranModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getTranFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getTranUpsitename())); //传输TOP上游站点
                        cList.add(StringUtil.null2String(bbuManual1.getTranDownsitename()));//传输下游站点
                        cList.add(StringUtil.null2String(bbuManual1.getTranNetprotect()));
                        cList.add(StringUtil.null2String(bbuManual1.getTranIsnode()));
                        cList.add(StringUtil.null2String(bbuManual1.getTranSitenum()));
                        cList.add(StringUtil.null2String(bbuManual1.getSourModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getSourFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getSourModuleModel()));//整流模块型号
                        cList.add(StringUtil.null2String(bbuManual1.getSourModuleNum()));
                        cList.add(StringUtil.null2String(bbuManual1.getSourCapa())); //满架容量（A）
                        cList.add(StringUtil.null2String(bbuManual1.getBoxModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getBoxFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellCapa()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellNum()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellPower()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellDuar()));
                        cList.add(StringUtil.null2String(bbuManual1.getCellUstime()));
                        cList.add(StringUtil.null2String(bbuManual1.getAcModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getAcFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getAcNum()));
                        cList.add(StringUtil.null2String(bbuManual1.getDhModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getDhFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getXfModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getXfFac()));   //
                        Cons mrstrutCons = ConstantUtil.getInstance().getConsByCode("MRSTRUT", bbuManual1.getMrStrut() + "");
                        cList.add(StringUtil.null2String(mrstrutCons == null ? "" : mrstrutCons.getName()));
                        cList.add(StringUtil.null2String(bbuManual1.getMrLength()));
                        cList.add(StringUtil.null2String(bbuManual1.getMrWidth()));
                        cList.add(StringUtil.null2String(bbuManual1.getMrHigh()));
                        cList.add(StringUtil.null2String(bbuManual1.getMrOwner()));
                        cList.add(StringUtil.null2String(bbuManual1.getWdType()));
                        cList.add(StringUtil.null2String(bbuManual1.getWdModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getWdFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getWdGds()));
                        cList.add(StringUtil.null2String(bbuManual1.getOeType()));
                        cList.add(StringUtil.null2String(bbuManual1.getOeModel()));
                        cList.add(StringUtil.null2String(bbuManual1.getOePower()));
                        cList.add(StringUtil.null2String(bbuManual1.getOeFac()));
                        cList.add(StringUtil.null2String(bbuManual1.getRemark()));
                    }
                }
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    cell.setCellValue(cList.get(j));
                }
                rowIndex++;
            }
            demoSheet.setColumnHidden(1, true);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入基站手工数据
     *
     * @return
     */
    public String importBbuInputData() {
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
                Map obj = parseBbuObj(i - 1, row, errorList);
                if (obj != null) {
                    bbuManualManager.importInsert(obj);
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
    public Map parseBbuObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getBbuCoulmnMap();
        try {
            Bbu wyBbu=null;
            String townDb=null;
            int j = 0;
            for (String dataKey : coulmnMap.keySet()) {
                Validity validity = coulmnMap.get(dataKey);
                HSSFCell cell = row.getCell((short) (j + 1));//Excel
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
                if("intId".equals(dataKey)){
                  wyBbu=bbuManager.getById(new Long(cellValue));
                  if(wyBbu==null){
                      errorList.add("第"+rowNum+"行,找不到对应bbu站点信息，"+cellValue);
                      return null;
                  }
                    map.put(dataKey, cellValue);
                }else if ("mrStrut".equals(dataKey)) {
                    //机房结构
                    Map<String, String> groupCodeMap = ExcelHelper.getGroupCodeMap();
                    String groupCode = groupCodeMap.get(dataKey);
                    Cons con = ConstantUtil.getInstance().getCons(groupCode, cellValue);
                    if (con != null) {
                        map.put(dataKey, con.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行导入校验失败:" + validity.getName() + ":填入未在数据库配置选项中");
                        return null;
                    }
                }else if ("town".equals(dataKey)) {
                    String townKey =cellValue;
                    townDb = ConstantUtil.getInstance().getTown(townKey);
                    if (townDb == null) {
                        errorList.add("第" + rowNum + "行乡镇列校验失败。" + cellValue + "未在乡镇库中，请核查。");
                        return null;
                    }
                    map.put(dataKey, cellValue.replace(";", ","));
                } else if("village".equals(dataKey)){
                    //校验农村库
                    if(!Common.isEmpty(cellValue)){
                        String[] villageArr = cellValue.split(";");
                        if (villageArr != null && villageArr.length > 0) {
                            for (String village : villageArr) {
                                String villageKey=village;
                                String villageDb = ConstantUtil.getInstance().getVillage(villageKey);
                                if (villageDb == null) {
                                    errorList.add("第" + rowNum + "行农村列校验失败，" + cellValue + "中，" + village + "未在乡镇库中，请核查。");
                                    return null;
                                }
                            }
                            map.put(dataKey, cellValue.replace(";", ","));
                        }
                    }
                } else {
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("installPosCode", 1);
            map.put("updatetime", new Date());
            if (user != null) {
                map.put("updateuser", user.getIntId());
            }
        } catch (Exception e) {
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            LOG.error(e.getMessage(),e);
            return null;
        }
        return map;
    }

    public void setBbuManager(BbuManager bbuManager) {
        this.bbuManager = bbuManager;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public BbuManual getBbuManual() {
        return bbuManual;
    }

    public void setBbuManual(BbuManual bbuManual) {
        this.bbuManual = bbuManual;
    }

    public Bbu getBbu() {
        return bbu;
    }

    public void setBbu(Bbu bbu) {
        this.bbu = bbu;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setBbuManualManager(BbuManualManager bbuManualManager) {
        this.bbuManualManager = bbuManualManager;
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

    public List<Bbu> getBbuList() {
        return bbuList;
    }

    public void setBbuList(List<Bbu> bbuList) {
        this.bbuList = bbuList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCountry() {
        return country;
    }

    public void setCountry(City country) {
        this.country = country;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName;
    }

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getCheckAllFlag() {
        return checkAllFlag;
    }

    public void setCheckAllFlag(Integer checkAllFlag) {
        this.checkAllFlag = checkAllFlag;
    }

    public Integer getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }
}
