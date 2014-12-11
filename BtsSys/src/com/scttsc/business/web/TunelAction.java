package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.service.RoadLibManager;
import com.scttsc.business.model.Cell;
import com.scttsc.business.model.WyTunel;
import com.scttsc.business.model.WyTunelManual;
import com.scttsc.business.service.CellManager;
import com.scttsc.business.service.TunelManager;
import com.scttsc.business.service.TunelManualManager;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by _think on 2014/9/25.
 */
public class TunelAction extends BaseAction {
    private static Logger LOG = Logger.getLogger(TunelAction.class);
    @Autowired
    private TunelManager tunelManager;
    @Autowired
    private TunelManualManager tunelManualManager;
    @Autowired
    private CityManager cityManager;
    @Autowired
    private RoadLibManager roadLibManager;
    @Autowired
    private CellManager cellManager;

    Cell wyTunel;
    Long intId;
    WyTunelManual tunelManual;

    //查詢條件
    private String countryIds;
    private String name;//名稱
    private String bscName;
    private Integer btsId;
    private Integer manualFlag;
    private Integer pn;
    private Integer ci;

    private Integer editFlag;


    private String ids;//勾选多个导出
    private Integer checkAllFlag;//选中所有

    /**
     * 导入文件
     */
    private File file;
    private String fileFileName;
    private String fileContentType;

    /**
     * 隧道录入列表页面
     * @return
     */
    public String tunel(){
        return SUCCESS;
    }


    public Map<String,Object> buildParamMap(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deleteFlag", 0);
        if (!Common.isEmpty(countryIds)) {
            // 默认权限
            map.put("countryIds", countryIds);
        } else {
            map.put("countryIds", user.getCountryIds());
        }
        //查询条件
        map.put("isIndoor",Constants.CELL_TUNEL);
        if (!Common.isEmpty(name)) {
            name = Common.decodeURL(name).trim();
            map.put("name", "%" + name + "%");
        }
        if (!Common.isEmpty(bscName)) {
            bscName = Common.decodeURL(bscName).trim();
            map.put("bscName", "%" + bscName + "%");
        }
        if (!Common.isEmpty(btsId)) {
            map.put("btsId", btsId);
        }
        if (!Common.isEmpty(ci)) {
            map.put("ci", ci);
        }
        if (!Common.isEmpty(pn)) {
            map.put("pn", pn);
        }
        if(!Common.isEmpty(manualFlag)){
            map.put("manualFlag",manualFlag);
        }
        return map;
    }
    /**
     * 待录入隧道小区数据列表
     *
     * @return
     */
    public String tunelCellData() {
        Map<String, Object> map =buildParamMap();
        int total = 0;
        List<Cell> list = null;
        try {
            total = cellManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 待录入隧道列表数据
     * @return
     */
    public String tunelData(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyTunel> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
               map.put("countryIds", user.getCountryIds());
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
                map.put("btsId", btsId);
            }

            if(!Common.isEmpty(manualFlag)){
                map.put("manualFlag",manualFlag);
            }
            //固定条件
            map.put("deleteFlag", 0);//在用
            total = tunelManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = tunelManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 隧道站点录入页面
     *
     * @return
     */
    public String tunelInput() {
        try {
            wyTunel = cellManager.selectById(intId);
            BigDecimal cityId=wyTunel.getCityId();
            BigDecimal countryId=wyTunel.getCountryId();
            if(!Common.isEmpty(cityId)){
                City city=cityManager.getById(cityId.longValue());
                wyTunel.setCity(city);
            }
            if(!Common.isEmpty(countryId)){
                City country=cityManager.getById(countryId.longValue());
                wyTunel.setCountry(country);
            }
            tunelManual=tunelManualManager.selectByPrimaryKey(intId);
            if (tunelManual != null) {
                RoadLib roadLib=roadLibManager.getById(new Long(tunelManual.getRoadId()));
                tunelManual.setRoadLib(roadLib);
                editFlag = 1;// 编辑页面
            } else {
                editFlag = 0;// 增加页面
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 增加、编辑
     * @return
     */
    public String addTunelManual(){
        try {
            User user = (User) this.getSession().getAttribute("user");
            tunelManual.setInuser(user.getIntId());
            tunelManual.setIntime(new Date());
            if (editFlag == 1) {
                tunelManualManager.updateByPrimaryKeySelective(tunelManual);// 编辑
            } else {
                tunelManualManager.insert(tunelManual);// 增加
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 隧道覆盖站点信息
     * @return
     */
    public String tunelInfo(){
        try {
            wyTunel = cellManager.selectById(intId);
            BigDecimal cityId=wyTunel.getCityId();
            BigDecimal countryId=wyTunel.getCountryId();
            if(!Common.isEmpty(cityId)){
                City city=cityManager.getById(cityId.longValue());
                wyTunel.setCity(city);
            }
            if(!Common.isEmpty(countryId)){
                City country=cityManager.getById(countryId.longValue());
                wyTunel.setCountry(country);
            }
        tunelManual=tunelManualManager.selectByPrimaryKey(intId);
            if(tunelManual!=null){
                Cons propCons = ConstantUtil.getInstance().getConsByCode("TUNELPROP", tunelManual.getProp() + "");
                tunelManual.setPropCons(propCons);
                Cons shareCons = ConstantUtil.getInstance().getConsByCode("TUNEL_SHARE", tunelManual.getShareflag() + "");
                tunelManual.setShareCons(shareCons);
                RoadLib roadLib=roadLibManager.getById(new Long(tunelManual.getRoadId()));
                tunelManual.setRoadLib(roadLib);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 导入tunel页面
     *
     * @return
     */
    public String importTunel() {
        return SUCCESS;
    }


    /**
     * 导出待录入数据
     *
     * @return
     */
    public String exportTunelInputData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyTunel> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/tunelTemplate.xls";
        String fileName = "隧道覆盖站点待录入数据.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
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
                map.put("btsId", btsId);
            }
            map.put("deleteFlag", 0);//在用
            total = tunelManager.countByMap(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = tunelManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 2;
            WyTunelManual tunelManual=null;
            for (WyTunel tunel : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex - 1));
                cList.add(StringUtil.null2String(tunel.getIntId()));
                cList.add(StringUtil.null2String(tunel.getName()));
                cList.add(StringUtil.null2String(tunel.getCity().getCityName()));
                cList.add(StringUtil.null2String(tunel.getCountry().getCityName()));
                int manualFlag = tunel.getManualFlag();
                if (manualFlag == 1) {
                    //已录入
                    tunelManual = tunelManualManager.selectByPrimaryKey(tunel.getIntId());
                    if (tunelManual != null) {
                        //设值
                        RoadLib roadLib=roadLibManager.getById(new Long(tunelManual.getRoadId()));
                        tunelManual.setRoadLib(roadLib);
                        Cons propCons = ConstantUtil.getInstance().getConsByCode("TUNELPROP", tunelManual.getProp() + "");
                        cList.add(propCons==null?"":propCons.getName());//隧道性质
                        cList.add(roadLib==null?"":roadLib.getName());//道路名称
                        cList.add(tunelManual.getTunelLength()+"");//隧道长度
                        cList.add(tunelManual.getAntennatype());//天馈类型
                        cList.add(tunelManual.getLinetype());//天线型号
                        cList.add(tunelManual.getCovertype());//覆盖类型
                        cList.add(tunelManual.getCoverrangedesc());//覆盖范围描述
                        cList.add(tunelManual.getAddress());//详细地址
                        cList.add(tunelManual.getTunelHigh()+"");//海拔高度
                        cList.add(tunelManual.getRrunum()+"");//信源设备数量
                        cList.add(tunelManual.getRruaddress()+"");//信源设备安装位置
                        cList.add(tunelManual.getRepeaternum()+"");//信源设备数量
                        cList.add(tunelManual.getRepeateraddress());//
                        cList.add(tunelManual.getDrynum() + "");//信源设备数量
                        cList.add(tunelManual.getDryaddress());//信源设备数量
                        Cons shareCons = ConstantUtil.getInstance().getConsByCode("TUNEL_SHARE", tunelManual.getShareflag() + "");
                        cList.add(shareCons==null?"":shareCons.getName());//共建共享情况
                        cList.add(tunelManual.getMchroomflag());//信源设备数量
                        cList.add(tunelManual.getMchroonarea()+"");//信源设备数量
                        cList.add(tunelManual.getMeteradress());//
                        cList.add(tunelManual.getRrunum()+"");//建设年月
                        cList.add(tunelManual.getOwner());//业主联系人
                        cList.add(tunelManual.getOwnertel());//业主电话
                        cList.add(tunelManual.getRemark());//备注
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
     * 隧道库导出
     * @return
     */
    public String tunelExport(){
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyTunel> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/tunelExportTemplate.xls";
        String fileName = "隧道覆盖站点列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }else{
                map.put("countryIds", user.getCountryIds());
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
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(ci)) {
                map.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map.put("pn", pn);
            }
            map.put("deleteFlag", 0);//在用
            list = tunelManager.selectAssoByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 2;
            for (WyTunel tunel : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(tunel.getName());
                City city=tunel.getCity();
                City country=tunel.getCountry();
                cList.add(city==null?"":city.getCityName());
                cList.add(country==null?"":country.getCityName());
                cList.add(tunel.getBscName());
                cList.add(tunel.getBtsName());
                cList.add(tunel.getBtsId()+"");
                cList.add(tunel.getServiceLevel());
                cList.add(tunel.getLatitude()+"");
                cList.add(tunel.getLongitude()+"");
                cList.add(tunel.getVendorBtstype());
                cList.add(tunel.getCircuitroomOwnership());
                cList.add(tunel.getTransOwnership());
                WyTunelManual wyTunelManual=tunel.getWyTunelManual();
                if(wyTunelManual!=null){
                    RoadLib roadLib=roadLibManager.getById(new Long(wyTunelManual.getRoadId()));
                    Cons propCons = ConstantUtil.getInstance().getConsByCode("TUNELPROP", wyTunelManual.getProp() + "");
                    cList.add(propCons==null?"":propCons.getName());
                    cList.add(roadLib==null?"":roadLib.getName());//道路名称
                    cList.add(wyTunelManual.getTunelLength()+"");//隧道长度
                    cList.add(wyTunelManual.getAntennatype());
                    cList.add(wyTunelManual.getLinetype());
                    cList.add(wyTunelManual.getCovertype());
                    cList.add(wyTunelManual.getCoverrangedesc());
                    cList.add(wyTunelManual.getAddress());
                    cList.add(wyTunelManual.getTunelHigh()+"");
                    cList.add(wyTunelManual.getRrunum()+"");
                    cList.add(wyTunelManual.getRruaddress());
                    cList.add(wyTunelManual.getRepeaternum()+"");
                    cList.add(wyTunelManual.getRepeateraddress());
                    cList.add(wyTunelManual.getDrynum()+"");
                    cList.add(wyTunelManual.getDryaddress());
                    Cons shareCons = ConstantUtil.getInstance().getConsByCode("TUNEL_SHARE", wyTunelManual.getShareflag() + "");
                    cList.add(shareCons==null?"":shareCons.getName());//共建共享情况
                    cList.add(wyTunelManual.getMchroomflag());
                    cList.add(wyTunelManual.getMchroonarea()+"");
                    cList.add(wyTunelManual.getMeteradress());
                    cList.add(wyTunelManual.getBuilddateStr());
                    cList.add(wyTunelManual.getOwner());
                    cList.add(wyTunelManual.getOwnertel());
                    cList.add(wyTunelManual.getRemark());
                }else{

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
    public String importTunelInputData() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            String fileName = DateConvert.formatDateToString(new Date(), "yyyyMMddHHmmss") + "_" + user.getName() + "_" + this.fileFileName;
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + fileName);
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
                Map obj = parseTunelObj(i - 1, row, errorList);
                if (obj != null) {
                    int status=tunelManualManager.importInsert(obj);
                    if(status== Constants.SUCECSS){
                        sucess++;
                    }
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
    public Map parseTunelObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getTunelCoulmnMap();
        try {
            int j = 0;
            int bj=0;
            for (String dataKey : coulmnMap.keySet()) {
                if(j==1){
                    bj=bj+4;
                }else{
                    bj=bj+1;
                }
                Validity validity = coulmnMap.get(dataKey);
                HSSFCell cell = row.getCell((short)bj);//Excel
                String cellValue = "";
                if (cell != null) {
                    cellValue = ExcelHelper.getValue(cell);//Excel列值
                }
                boolean validtyFlag = validity.check(cellValue);  //验证字段
                if (!validtyFlag) {
                    //未经过校验，保存该列校验失败原因,该行不在导入
                    errorList.add("第" + rowNum + "行:" + "校验失败," + validity.getMsg());
                    return null;
                }
                if ("prop".equals(dataKey) || "shareflag".equals(dataKey)) {
                    //隧道属性,共建共享
                    Map<String, String> groupCodeMap = ExcelHelper.getGroupCodeMap();
                    String groupCode = groupCodeMap.get(dataKey);
                    Cons con = ConstantUtil.getInstance().getCons(groupCode, cellValue);
                    if (con != null) {
                        map.put(dataKey, con.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行导入校验失败," + validity.getName() + ".填入未在数据库配置选项中:"+cellValue);
                        return null;
                    }
                }else if("roadId".equals(dataKey)){
                    RoadLib roadLib=roadLibManager.getByName(cellValue);
                    if(roadLib!=null){
                        map.put("roadId",roadLib.getId());
                    }else{
                        errorList.add("第" + rowNum + "行导入校验失败," + validity.getName() + ".填入未在道路库数据记录中,请核对:"+cellValue);
                        return null;
                    }
                }else {
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            if (user != null) {
                map.put("inuser", user.getIntId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }




    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
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

    public Integer getBtsId() {
        return btsId;
    }

    public void setBtsId(Integer btsId) {
        this.btsId = btsId;
    }

    public Integer getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public WyTunelManual getTunelManual() {
        return tunelManual;
    }

    public void setTunelManual(WyTunelManual tunelManual) {
        this.tunelManual = tunelManual;
    }

    public Cell getWyTunel() {
        return wyTunel;
    }

    public void setWyTunel(Cell wyTunel) {
        this.wyTunel = wyTunel;
    }

    public Integer getCheckAllFlag() {
        return checkAllFlag;
    }

    public void setCheckAllFlag(Integer checkAllFlag) {
        this.checkAllFlag = checkAllFlag;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }
}
