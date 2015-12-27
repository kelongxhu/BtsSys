package com.scttsc.lte.web;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import com.scttsc.lte.model.WyLteBbu;
import com.scttsc.lte.model.WyLteBts;
import com.scttsc.lte.model.WyLteCell;
import com.scttsc.lte.service.WyLteBbuManager;
import com.scttsc.lte.service.WyLteBtsManager;
import com.scttsc.lte.service.WyLteCellManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * To change this template use File | Settings | File Templates.
 */
public class WyLteAbandonAction extends BaseAction {


    @Autowired
    private WyLteBtsManager wyLteBtsManager;
    @Autowired
    private WyLteBbuManager wyLteBbuManager;
    @Autowired
    private WyLteCellManager wyLteCellManager;

    private User user;

    private Integer typeId;
    private String countryIds;
    private Integer txFlag;//是否填写4

    private Long id;
    private String deleteText;
    private Short deleteResoncode;


    private String name;

    /**
     * 废弃页面
     * @return
     */
    public String lteAbandon() {
        return SUCCESS;
    }

    /**
     * 编辑废弃原因页面
     * @return
     */
    public String abandonEdit() {
        return SUCCESS;
    }

    /**
     * 填写/编写废弃原因
     *
     * @return
     */

    public String lteAbandonEdit() {
        if (Common.isEmpty(typeId)) {
            jsonMap.put("result", 0);
            LOG.info("typeId is empty.");
            return SUCCESS;
        }
            try {
                if (typeId == 1) {
                    //bts
                    WyLteBts bts=new WyLteBts();
                    bts.setIntId(new BigDecimal(id));
                    bts.setDeleteResoncode(deleteResoncode);
                    bts.setDeleteText(deleteText);
                    wyLteBtsManager.updateByPrimaryKeySelective(bts);
                } else if (typeId == 2) {
                    //bbu
                    WyLteBbu bbu=new WyLteBbu();
                    bbu.setIntId(new BigDecimal(id));
                    bbu.setDeleteResoncode(deleteResoncode);
                    bbu.setDeleteText(deleteText);
                    wyLteBbuManager.updateByPrimaryKeySelective(bbu);
                } else if (typeId == 3) {
                    //cell
                    WyLteCell cell=new WyLteCell();
                    cell.setIntId(String.valueOf(id));
                    cell.setDeleteResoncode(deleteResoncode);
                    cell.setDeleteText(deleteText);
                    wyLteCellManager.updateByPrimaryKeySelective(cell);
                }
                jsonMap.put("result", 1);
            } catch (Exception e) {
                jsonMap.put("result", 0);
                LOG.error(e.getMessage(),e);
            }
        return SUCCESS;
    }

    /**
     * 废弃数据
     * @return
     */
    public String abandonData() {
        String result = null;
            if (!Common.isEmpty(typeId)) {
                switch (typeId.intValue()) {
                    case 1:
                        result = btsData();
                        break;
                    case 2:
                        result = bbuData();
                        break;
                    case 3:
                        result = cellData();
                        break;
                    default:
                        break;
                }
            }
        return result;
    }

    /**
     * LTE 基站废弃数据
     * @return
     */
    public String btsData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBts> list = null;
        try {
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
            total = wyLteBtsManager.countWyLteBts(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list =wyLteBtsManager.findWyLteBts(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 废弃小区数据
     * @return
     */
    public String cellData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteCell> list = null;
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
            total = wyLteCellManager.countWyLteCell(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = wyLteCellManager.selectWyLteCell(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 废弃BBU数据
     * @return
     */
    public String bbuData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBbu> list = null;
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
            total = wyLteBbuManager.countWyLteBbu(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list =wyLteBbuManager.selectWyLteBbu(map);
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
                    exportDeleteCellData();
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 导出废弃的LTE物理站点
     * @return
     */
    public String exportDeleteBtsData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBts> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/lteBtsDeleteDataTemplate.xls";
        String fileName = "废弃LTE站点.xls";
        try {
            //查询条件
            map.put("deleteFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
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
            list = wyLteBtsManager.findWyLteBts(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyLteBts bts : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bts.getName()));
                cList.add(StringUtil.null2String(bts.getCityName()));
                cList.add(StringUtil.null2String(bts.getCountryName()));
                cList.add(bts.getIsIndoor());
                cList.add(StringUtil.null2String(bts.getIsRru()));
                cList.add(StringUtil.null2String(bts.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bts.getTransOwnership()));
                cList.add(StringUtil.null2String(bts.getServiceLevel()));
                cList.add(bts.getDeleteTimeStr());
                cList.add(bts.getDeleteResonText());
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
            LOG.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * bbu废弃数据
     * @return
     */
    public String exportDeleteBbuData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteBbu> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/lteBbuDeleteDataTemplate.xls";
        String fileName = "废弃LTE的BBU站点.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
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
            list = wyLteBbuManager.selectWyLteBbu(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyLteBbu bbu : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(bbu.getName()));
                cList.add(StringUtil.null2String(bbu.getCityName()));
                cList.add(StringUtil.null2String(bbu.getCountryName()));
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
                cList.add(StringUtil.null2String(bbu.getCircuitroomOwnership()));
                cList.add(StringUtil.null2String(bbu.getTransOwnership()));
                cList.add(bbu.getDeleteTimeStr());
                cList.add(bbu.getDeleteResonText());
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
            LOG.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 导出废弃小区数据
     * @return
     */
    public String exportDeleteCellData() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyLteCell> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/lteCellDeleteDataTemplate.xls";
        String fileName = "废弃LTE小区.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            }
            //查询条件
            map.put("deleteFlag", 1);
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            list = wyLteCellManager.selectWyLteCell(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyLteCell cell : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(cell.getName()));
                cList.add(StringUtil.null2String(cell.getCityName()));
                cList.add(cell.getCountryName());
                cList.add(cell.getIsIndoor());
                cList.add(StringUtil.null2String(cell.getIsRru()));
                cList.add(cell.getDeleteTimeStr());
                cList.add(cell.getDeleteResonText());
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
            LOG.error(e.getMessage(),e);
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

    public Short getDeleteResoncode() {
        return deleteResoncode;
    }

    public void setDeleteResoncode(Short deleteResoncode) {
        this.deleteResoncode = deleteResoncode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
