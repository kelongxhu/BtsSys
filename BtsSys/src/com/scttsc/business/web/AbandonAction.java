package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.SchoolLibManager;
import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.Bts;
import com.scttsc.business.model.Cell;
import com.scttsc.business.model.WyTunel;
import com.scttsc.business.service.BbuManager;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.CellManager;
import com.scttsc.business.service.TunelManager;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lishiqiang
 * Date: 13-4-2
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
public class AbandonAction extends BaseAction {

    private BtsManager btsManager;

    private SchoolLibManager schoolLibManager;

    private CellManager cellManager;

    private BbuManager bbuManager;

    @Autowired
    private TunelManager tunelManager;

    private User user;


    private Integer typeId;
    private String countryIds;
    private Integer txFlag;//是否填写

    private Long id;
    private String deleteText;
    private String deleteResoncode;


    private String name;


    public String bts() {
        return SUCCESS;
    }

    public String editPage() {
        return SUCCESS;
    }

    /**
     * 填写/编写废弃原因
     *
     * @return
     */

    public String editAbandoned() {
        if (!Common.isEmpty(typeId)) {
            Map map = new HashMap();
            if (!Common.isEmpty(id)) {
                map.put("intId", id);
            }
            if (!Common.isEmpty(deleteResoncode)) {
                map.put("deleteResoncode", deleteResoncode);
            }
            if (!Common.isEmpty(deleteText)) {
                map.put("deleteText", deleteText);
            }
            try {
                if (typeId == 1 || typeId == 3) {
                    btsManager.updateByMap(map);
                } else if (typeId == 2) {
                    bbuManager.updateByMap(map);
                } else if (typeId == 4) {
                    cellManager.updateByMap(map);
                }else if(typeId==6){
                    tunelManager.updateByMap(map);
                }
                jsonMap.put("result", 1);
            } catch (Exception e) {
                jsonMap.put("result", 0);
                e.printStackTrace();
            }
        }

        return SUCCESS;
    }

    public String edit() {
        return SUCCESS;
    }


    public String data() {
        String result = null;
        try {
            if (!Common.isEmpty(typeId)) {
                switch (typeId.intValue()) {
                    case 1:
                        result = btsData();
                        break;
                    case 2:
                        result = bbuData();
                        break;
                    case 3:
                        result = indoorData();
                        break;
                    case 4:
                        result = cellData();
                        break;
                    case 6:
                       result=tunelAbandonData();
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String btsData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            map.put("deleteFlag", 1);
            map.put("isIndoor", "否");
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = btsManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = btsManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    public String cellData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Cell> list = null;
        try {
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                map.put("countryIds", user.getCountryIds());
            }
            //是否填写
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
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
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    public String bbuData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bbu> list = null;
        try {
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                 map.put("countryIds", user.getCountryIds());
            }
            //是否填写
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
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
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 废弃室分站点
     *
     * @return
     */
    public String indoorData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            map.put("deleteFlag", 1);
            map.put("isIndoor", "是");
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = btsManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = btsManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 废弃隧道数据
     * @return
     */
    public String tunelAbandonData(){
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
            //是否填写
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            //固定条件
            map.put("deleteFlag", 1);//在用
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
     * 导出废弃数据
     *
     * @return
     */
    public String exportDeleteData() {
        if (!Common.isEmpty(typeId)) {
            switch (typeId.intValue()) {
                case 1:
                    exportDeleteBtsData();
                    break;
                case 2:
                    exportDeleteBbuData();
                    break;
                case 3:
                    exportDeleteIndoorData();
                    break;
                case 4:
                    exportDeleteCellData();
                    break;
                case 6:
                    exportDeleteTunelData();
                    break;
                default:
                    break;
            }
        }
        return null;
    }


    public String exportDeleteBtsData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/btsDeleteDataTemplate.xls";
        String fileName = "废弃物理站点.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            map.put("isIndoor", "否");
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                //  map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = btsManager.countByConds(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = btsManager.getByConds(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Bts bts : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bts.getName()));
                cList.add(StringUtil.null2String(bts.getCityName()));
                City country = bts.getCountry();
                String countryName = "";
                if (country != null) {
                    countryName = country.getCityName();
                }
                cList.add(countryName);
                cList.add(bts.getIsIndoor());
                cList.add(StringUtil.null2String(bts.getIsRru()));
                cList.add(StringUtil.null2String(bts.getBtsName()));
                cList.add(StringUtil.null2String(bts.getBscName()));
                cList.add(StringUtil.null2String(bts.getBtsId()));
                cList.add(StringUtil.null2String(bts.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bts.getTransOwnership()));
                cList.add(StringUtil.null2String(bts.getServiceLevel()));
                cList.add(StringUtil.null2String(bts.getVendorBtstype()));
                cList.add(bts.getDeleteTimeStr());
                Long deleteResoncode = bts.getDeleteResoncode();
                String txFlagText = "";
                if (deleteResoncode == 0) {
                    txFlagText = "未填写";
                } else {
                    txFlagText = "已填写";
                }
                cList.add(txFlagText);
                Cons cons = bts.getCons();
                String deleteCodeText = "";
                if (cons != null) {
                    deleteCodeText = cons.getName();
                }
                cList.add(deleteCodeText);
                cList.add(bts.getDeleteText());
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


    public String exportDeleteBbuData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bbu> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/bbuDeleteDataTemplate.xls";
        String fileName = "废弃BBU站点.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                //  map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
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
            int rowIndex = 1;
            for (Bbu bbu : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bbu.getName()));
                cList.add(StringUtil.null2String(bbu.getCityName()));
                City country = bbu.getCountry();
                String countryName = "";
                if (country != null) {
                    countryName = country.getCityName();
                }
                cList.add(countryName);

                String bbuTypeText = "";
                int bbuType = bbu.getBbuType().intValue();
                if (bbuType == 1) {
                    bbuTypeText = "纯BBU";
                } else if (bbuType == 2) {
                    bbuTypeText = "纯BBU共站BBU";
                } else if (bbuType == 3) {
                    bbuTypeText = "基站共站BBU";
                }
                cList.add(bbuTypeText);
                cList.add(StringUtil.null2String(bbu.getBtsName()));
                cList.add(StringUtil.null2String(bbu.getBscName()));
                cList.add(StringUtil.null2String(bbu.getBtsId()));
                cList.add(StringUtil.null2String(bbu.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bbu.getTransOwnership()));
                cList.add(StringUtil.null2String(bbu.getVendorBtstype()));
                cList.add(bbu.getDeleteTimeStr());
                Long deleteResoncode = bbu.getDeleteResoncode();
                String txFlagText = "";
                if (deleteResoncode == 0) {
                    txFlagText = "未填写";
                } else {
                    txFlagText = "已填写";
                }
                cList.add(txFlagText);
                Cons cons = bbu.getCons();
                String deleteCodeText = "";
                if (cons != null) {
                    deleteCodeText = cons.getName();
                }
                cList.add(deleteCodeText);
                cList.add(bbu.getDeleteText());
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


    public String exportDeleteIndoorData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/indoorDeleteDataTemplate.xls";
        String fileName = "废弃室分站点.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            map.put("isIndoor", "是");
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                //  map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = btsManager.countByConds(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = btsManager.getByConds(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Bts bts : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bts.getName()));
                cList.add(StringUtil.null2String(bts.getCityName()));
                City country = bts.getCountry();
                String countryName = "";
                if (country != null) {
                    countryName = country.getCityName();
                }
                cList.add(countryName);
                cList.add(bts.getIsIndoor());
                cList.add(StringUtil.null2String(bts.getIsRru()));
                cList.add(StringUtil.null2String(bts.getBtsName()));
                cList.add(StringUtil.null2String(bts.getBscName()));
                cList.add(StringUtil.null2String(bts.getBtsId()));
                cList.add(StringUtil.null2String(bts.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bts.getTransOwnership()));
                cList.add(StringUtil.null2String(bts.getServiceLevel()));
                cList.add(StringUtil.null2String(bts.getVendorBtstype()));
                cList.add(bts.getDeleteTimeStr());
                Long deleteResoncode = bts.getDeleteResoncode();
                String txFlagText = "";
                if (deleteResoncode == 0) {
                    txFlagText = "未填写";
                } else {
                    txFlagText = "已填写";
                }
                cList.add(txFlagText);
                Cons cons = bts.getCons();
                String deleteCodeText = "";
                if (cons != null) {
                    deleteCodeText = cons.getName();
                }
                cList.add(deleteCodeText);
                cList.add(bts.getDeleteText());
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

    public String exportDeleteCellData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Cell> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/cellDeleteDataTemplate.xls";
        String fileName = "废弃小区.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                //  map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = cellManager.countByMap(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = cellManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Cell cell : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(cell.getName()));
                cList.add(StringUtil.null2String(cell.getCityName()));
                City country = cell.getCountry();
                String countryName = "";
                if (country != null) {
                    countryName = country.getCityName();
                }
                cList.add(countryName);
                cList.add(cell.getIsIndoor());
                cList.add(StringUtil.null2String(cell.getIsRru()));
                cList.add(cell.getDeleteTimeStr());
                Long deleteResoncode = cell.getDeleteResoncode();
                String txFlagText = "";
                if (deleteResoncode == 0) {
                    txFlagText = "未填写";
                } else {
                    txFlagText = "已填写";
                }
                cList.add(txFlagText);
                Cons cons = cell.getCons();
                String deleteCodeText = "";
                if (cons != null) {
                    deleteCodeText = cons.getName();
                }
                cList.add(deleteCodeText);
                cList.add(cell.getDeleteText());
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell hfcell = row.createCell((short) j);
                    hfcell.setCellStyle(style);
                    switch (j) {
                        default:
                            hfcell.setCellValue(cList.get(j));
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
     * 导出废弃隧道
     * @return
     */
    public String exportDeleteTunelData(){
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyTunel> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/tunelDeleteDataTemplate.xls";
        String fileName = "废弃隧道站点_"+ DateUtils.formatDateTime(new Date(), "yyyyMMddHHmm")+".xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                  map.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(txFlag)) {
                if (0 == txFlag) {
                    map.put("ntxFlag", txFlag);
                } else {
                    map.put("ytxFlag", txFlag);
                }
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
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
            int rowIndex = 1;
            for (WyTunel bts : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bts.getName()));
                cList.add(StringUtil.null2String(bts.getCity().getCityName()));
                City country = bts.getCountry();
                String countryName = "";
                if (country != null) {
                    countryName = country.getCityName();
                }
                cList.add(countryName);
                cList.add(StringUtil.null2String(bts.getIsRru()));
                cList.add(StringUtil.null2String(bts.getBtsName()));
                cList.add(StringUtil.null2String(bts.getBscName()));
                cList.add(StringUtil.null2String(bts.getBtsId()));
                cList.add(StringUtil.null2String(bts.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bts.getTransOwnership()));
                cList.add(StringUtil.null2String(bts.getServiceLevel()));
                cList.add(StringUtil.null2String(bts.getVendorBtstype()));
                cList.add(bts.getDeleteTimeStr());
                Integer deleteResoncode = bts.getDeleteResoncode();
                String txFlagText = "";
                if (deleteResoncode == 0) {
                    txFlagText = "未填写";
                } else {
                    txFlagText = "已填写";
                }
                cList.add(txFlagText);
                Cons cons = bts.getReasonCons();
                String deleteCodeText = "";
                if (cons != null) {
                    deleteCodeText = cons.getName();
                }
                cList.add(deleteCodeText);
                cList.add(bts.getDeleteText());
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


    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public Integer getTxFlag() {
        return txFlag;
    }

    public void setTxFlag(Integer txFlag) {
        this.txFlag = txFlag;
    }

    public void setBbuManager(BbuManager bbuManager) {
        this.bbuManager = bbuManager;
    }


    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }

    public void setBtsManager(BtsManager btsManager) {
        this.btsManager = btsManager;
    }

    public void setSchoolLibManager(SchoolLibManager schoolLibManager) {
        this.schoolLibManager = schoolLibManager;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(String deleteText) {
        this.deleteText = deleteText;
    }

    public String getDeleteResoncode() {
        return deleteResoncode;
    }

    public void setDeleteResoncode(String deleteResoncode) {
        this.deleteResoncode = deleteResoncode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
