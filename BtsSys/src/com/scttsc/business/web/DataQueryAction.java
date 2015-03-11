package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.service.ConsManager;
import com.scttsc.baselibs.service.RoadLibManager;
import com.scttsc.business.model.IndoorManual;
import com.scttsc.business.service.CellManager;
import com.scttsc.business.service.IndoorManualManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-19
 * Time: 上午11:00
 * 基础数据查询
 */
public class DataQueryAction extends BaseAction {

    private IndoorManualManager indoorManualManager;

    private CellManager cellManager;

    private ConsManager consManager;

    private RoadLibManager roadLibManager;

    //查询条件
    private String countryIds;
    private String name;
    private String bscName;
    private String btsId;
    private Integer ci;
    private Integer pn;
    private String libName;//信息库名称


    private Integer typeId;//基本库类型


    public String dataQuery() {
        return SUCCESS;
    }

    /**
     * 室分数据查询
     *
     * @return
     */

    public String indoorQueryData() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<IndoorManual> list = null;
        try {
            map.put("deleteFlag", 0);
            if (!Common.isEmpty(countryIds)) {
                // 查询权限
                map.put("countryIds", countryIds);
            } else {
                //用户数据权限
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
            if (!Common.isEmpty(ci)) {
                map.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map.put("pn", pn);
            }
            total = indoorManualManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = indoorManualManager.selectByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 小区信息库查询
     *
     * @return
     */
    public String libQuery() {
        return SUCCESS;
    }

    /**
     * 校园库覆盖查询
     *
     * @return
     */
    public String schoolQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
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
            total = cellManager.countBySchoolLibCells(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectSchoolLibCells(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 场景库覆盖查询
     *
     * @return
     */
    public String secneryQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            total = cellManager.countBySecneryLibCells(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectSecneryLibCells(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 农村库覆盖查询
     *
     * @return
     */
    public String vitoQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
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
            total = cellManager.countByVitoLibCells(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectVitoLibCells(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 道路覆盖查询
     *
     * @return
     */
    public String roadQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            total = cellManager.countByRoadLibCells(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectRoadLibCells(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 隧道库覆盖查询
     *
     * @return
     */
    public String tunnelQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            total = cellManager.countByTunnelLibCells(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectTunnelLibCells(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    public String libExport() {
        if (!Common.isEmpty(typeId)) {
            switch (typeId.intValue()) {
                case 3:
                    exportroadLib();
                    break;
                case 6:
                    exporttunnelLib();
                    break;
                case 7:
                    exportSenceLib();
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 导出校园覆盖小区信息
     *
     * @return
     */
    public String exportSchoolLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/schoolLibTemplate.xls";
        String fileName = "校园覆盖小区列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
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
            total = cellManager.countBySchoolLibCells(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = cellManager.selectSchoolLibCells(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex - 1));
                cList.add(StringUtil.null2String(m.get("CITYNAME")));
                cList.add(StringUtil.null2String(m.get("LIBNAME")));
                cList.add(StringUtil.null2String(m.get("ADDRESS")));
                cList.add(StringUtil.null2String(m.get("SCHOOL_LEVEL")));
                cList.add(StringUtil.null2String(m.get("SCHOOL_TYPE")));
                cList.add(StringUtil.null2String(m.get("COVERAGE_TYPE")));
                cList.add(StringUtil.null2String(m.get("STRUCTURAL_TYPE")));
                cList.add(StringUtil.null2String(m.get("WIFI_TYPE")));
                cList.add(StringUtil.null2String(m.get("C_USERS")));
                cList.add(StringUtil.null2String(m.get("DO_USERS")));
                cList.add(StringUtil.null2String(m.get("C_USERS_PLAN")));
                cList.add(StringUtil.null2String(m.get("DO_USERS_PLAN")));
                cList.add(StringUtil.null2String(m.get("MEAL_TYPE")));
                cList.add(StringUtil.null2String(m.get("BUSINESS_TYPE")));
                cList.add(StringUtil.null2String(m.get("CELLNAME")));
                cList.add(StringUtil.null2String(m.get("BSC_NAME")));
                cList.add(StringUtil.null2String(m.get("CI")));
                cList.add(StringUtil.null2String(m.get("PN")));
                cList.add(StringUtil.null2String(m.get("BTSID")));
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    switch (j) {
                        case 4:
                            //重要等级
                            Map a = new HashMap();
                            a.put("groupCode", "SCHOOL_LEVEL");
                            a.put("code", cList.get(j));
                            Cons cons = consManager.getByMap(a);
                            if (cons != null) {
                                cell.setCellValue(cons.getName());
                            }
                            break;
                        case 5:
                            //校园类型
                            Map b = new HashMap();
                            b.put("groupCode", "SCHOOL_TYPE");
                            b.put("code", cList.get(j));
                            Cons cons2 = consManager.getByMap(b);
                            if (cons2 != null) {
                                cell.setCellValue(cons2.getName());
                            }
                            break;
                        case 7:
                            //校园结构
                            Map c = new HashMap();
                            c.put("groupCode", "STRUCTURAL_TYPE");
                            c.put("code", cList.get(j));
                            Cons cons3 = consManager.getByMap(c);
                            if (cons3 != null) {
                                cell.setCellValue(cons3.getName());
                            }
                            break;
                        case 8:
                            //WIFI覆盖
                            Map d = new HashMap();
                            d.put("groupCode", "WIFI_TYPE");
                            d.put("code", cList.get(j));
                            Cons cons4 = consManager.getByMap(d);
                            if (cons4 != null) {
                                cell.setCellValue(cons4.getName());
                            }
                            break;
                        default:
                            cell.setCellValue(cList.get(j));
                            break;

                    }
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
     * 场景库小区
     *
     * @return
     */
    public String exportSenceLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/secneryLibTemplate.xls";
        String fileName = "场景库覆盖小区列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            list = cellManager.selectSecneryLibCells(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(m.get("CITYNAME")));
                cList.add(StringUtil.null2String(m.get("COUNTRYNAME")));
                cList.add(StringUtil.null2String(m.get("LIBNAME")));
                cList.add(StringUtil.null2String(m.get("CELLNAME")));
                cList.add(StringUtil.null2String(m.get("BSC_NAME")));
                cList.add(StringUtil.null2String(m.get("CI")));
                cList.add(StringUtil.null2String(m.get("PN")));
                cList.add(StringUtil.null2String(m.get("BTSID")));
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
     * 导出农村库覆盖
     *
     * @return
     */
    public String exportvitoLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/votoLibTemplate.xls";
        String fileName = "农村乡镇库覆盖小区列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
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
            total = cellManager.countByVitoLibCells(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = cellManager.selectVitoLibCells(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(m.get("CITYNAME")));
                cList.add(StringUtil.null2String(m.get("COUNTRYNAME")));
                cList.add(StringUtil.null2String(m.get("LIBNAME")));
                cList.add(StringUtil.null2String(m.get("GPS_LONG")));
                cList.add(StringUtil.null2String(m.get("GPS_LAT")));
                cList.add(StringUtil.null2String(m.get("CMDA1X_COVGRATE")));
                cList.add(StringUtil.null2String(m.get("MOBGSM_COVGRATE")));
                cList.add(StringUtil.null2String(m.get("COVG_DIF_COVGRATE")));
                cList.add(StringUtil.null2String(m.get("UNICGSM_COVGRATE")));
                cList.add(StringUtil.null2String(m.get("EVDO_COVGRATE")));
                cList.add(StringUtil.null2String(m.get("CDMA1X_MOS")));
                cList.add(StringUtil.null2String(m.get("MOBGSM_MOS")));
                cList.add(StringUtil.null2String(m.get("UNICGSM_MOS")));
                cList.add(StringUtil.null2String(m.get("CELLNAME")));
                cList.add(StringUtil.null2String(m.get("BSC_NAME")));
                cList.add(StringUtil.null2String(m.get("CI")));
                cList.add(StringUtil.null2String(m.get("PN")));
                cList.add(StringUtil.null2String(m.get("BTSID")));
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    switch (j) {
                        default:
                            cell.setCellValue(cList.get(j));
                            break;

                    }
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
     * 导出道路覆盖小区
     *
     * @return
     */
    public String exportroadLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/roadLibTemplate.xls";
        String fileName = "道路覆盖小区列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            list = cellManager.selectRoadLibCells(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(m.get("CITYNAME")));
                cList.add(StringUtil.null2String(m.get("LIBNAME")));
                cList.add(StringUtil.null2String(m.get("ROAD_PROP")));
                cList.add(StringUtil.null2String(m.get("ROAD_NO")));
                cList.add(StringUtil.null2String(m.get("DOMESTIC_START")));
                cList.add(StringUtil.null2String(m.get("DOMESTIC_END")));
                cList.add(StringUtil.null2String(m.get("MILEAGE")));
                cList.add(StringUtil.null2String(m.get("CELLNAME")));
                cList.add(StringUtil.null2String(m.get("BSC_NAME")));
                cList.add(StringUtil.null2String(m.get("CI")));
                cList.add(StringUtil.null2String(m.get("PN")));
                cList.add(StringUtil.null2String(m.get("BTSID")));
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    switch (j) {
                        case 3:
                            //线路属性
                            Map a = new HashMap();
                            a.put("groupCode", "ROADPROP");
                            a.put("code", cList.get(j));
                            Cons cons = consManager.getByMap(a);
                            if (cons != null) {
                                cell.setCellValue(cons.getName());
                            }
                            break;
                        default:
                            cell.setCellValue(cList.get(j));
                            break;

                    }
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

    public String exporttunnelLib() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Map> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/tunnelLibTemplate.xls";
        String fileName = "隧道覆盖小区列表.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            if (!Common.isEmpty(libName)) {
                libName = Common.decodeURL(libName).trim();
                map.put("libName", "%" + libName + "%");
            }
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
            list = cellManager.selectTunnelLibCells(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(m.get("CITYNAME")));
                cList.add(StringUtil.null2String(m.get("LIBNAME")));
                cList.add(StringUtil.null2String(m.get("ROAD_ID")));
                cList.add(StringUtil.null2String(m.get("LENGTH")));
                cList.add(StringUtil.null2String(m.get("DIRECTION")));
                cList.add(StringUtil.null2String(m.get("LONGITUDE")));
                cList.add(StringUtil.null2String(m.get("LATITUDE")));
                cList.add(StringUtil.null2String(m.get("HEIGHT")));
                cList.add(StringUtil.null2String(m.get("COVGTYPE")));
                cList.add(StringUtil.null2String(m.get("INSTALLADRESS")));
                cList.add(StringUtil.null2String(m.get("RIGHTS")));
                cList.add(StringUtil.null2String(m.get("TOGBS")));
                cList.add(StringUtil.null2String(m.get("TOGNAME")));
                cList.add(StringUtil.null2String(m.get("OPENTIME")));
                cList.add(StringUtil.null2String(m.get("ADDRESS")));
                cList.add(StringUtil.null2String(m.get("CELLNAME")));
                cList.add(StringUtil.null2String(m.get("BSC_NAME")));
                cList.add(StringUtil.null2String(m.get("CI")));
                cList.add(StringUtil.null2String(m.get("PN")));
                cList.add(StringUtil.null2String(m.get("BTSID")));
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    switch (j) {
                        case 3:
                            //所属道路
                            Long roadId = StringUtil.null2Long0(cList.get(j));
                            RoadLib roadLib = null;
                            if (roadId != null) {
                                roadLib = roadLibManager.getById(roadId);
                            }
                            if (roadLib != null) {
                                cell.setCellValue(roadLib.getName());
                            }
                            break;
                        default:
                            cell.setCellValue(cList.get(j));
                            break;

                    }
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


    public void setIndoorManualManager(IndoorManualManager indoorManualManager) {
        this.indoorManualManager = indoorManualManager;
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

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setRoadLibManager(RoadLibManager roadLibManager) {
        this.roadLibManager = roadLibManager;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}
