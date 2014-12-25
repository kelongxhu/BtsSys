package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.baselibs.model.AirLib;
import com.scttsc.business.model.CellAirLib;
import com.scttsc.business.model.CellManual;
import com.scttsc.business.model.ColumnsConfig;
import com.scttsc.business.model.Template;
import com.scttsc.business.service.*;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: Administrator
 * Date: 13-4-23
 * Time: 上午10:37
 */
public class TemplateAction extends BaseAction {
    Logger LOG =Logger.getLogger(TemplateAction.class);

    private TemplateManager templateManager;
    private BtsManager btsManager;
    private BbuManager bbuManager;
    private CellManager cellManager;
    private IndoorManualManager indoorManualManager;
    private BigDecimal id;//模板ID
    private BigDecimal typeId;//查询分类
    private String ids;//字段ID集合
    private String countryIds;//区县集合
    private String name;
    private String bscName;
    private String btsId;
    private Integer ci;
    private Integer pn;

    private Template template;

    private Integer editFlag;//编辑操作标识

    private Integer useFlag;//数据类型

    @Autowired
    private CellManualManager cellManualManager;//小区手工数据
    @Autowired
    private TunelManager tunelManager;

    private Integer enAble;


    /**
     * 模板列表页面
     *
     * @return
     */
    public String template() {
        return SUCCESS;
    }

    /**
     * 模板列表数据
     *
     * @return
     */
    public String templateData() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<Template> list = null;
        try {
            if (!Common.isEmpty(typeId)) {
                map.put("typeId", typeId);
            }
            if (!Common.isEmpty(useFlag)) {
                map.put("useFlag", useFlag);
            }
            map.put("userId", user.getIntId());
            total = templateManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = templateManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 模板定制或者编辑
     *
     * @return
     */
    public String templateDefine() {
        try {
            if (!Common.isEmpty(id)) {
                template = templateManager.selectById(id);
                String columnsConfigName = "";
                Map map = new HashMap();
                String columnsConfigs = template.getColumnsConfigs();
                if (columnsConfigs != null) {
                    map.put("ids", columnsConfigs.replaceAll(";", ","));
                    List<ColumnsConfig> configs = templateManager.selectColumnsByConds(map);
                    for (ColumnsConfig config : configs) {
                        columnsConfigName += config.getCnName() + ";";
                    }
                }
                columnsConfigName.substring(0, columnsConfigName.length() - 1);
                this.getRequest().setAttribute("columnsConfigName", columnsConfigName);
            }
            editFlag = (template == null ? 0 : 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return SUCCESS;
    }


    /**
     * 字段选择页面
     *
     * @return
     */
    public String columnConfig() {
        name = Common.decodeURL(name).trim();
        bscName = Common.decodeURL(bscName).trim();
        return SUCCESS;
    }

    /**
     * 所有字段列表
     *
     * @return
     */
    public String columnsList() {
        List<ColumnsConfig> configs = null;
        Map map=new HashMap();
        try {
            if(!Common.isEmpty(enAble)){
                map.put("enable",enAble);
            }
            if (!Common.isEmpty(typeId)) {
                map.put("type",typeId);
                configs = templateManager.selectColumnsByConds(map);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(configs);
        return SUCCESS;
    }

    /**
     * 用户模板列表
     *
     * @return
     */
    public String templateList() {
        User user = (User) this.getSession().getAttribute("user");
        List<Template> templateList = null;
        try {
            Map map = new HashMap();
            map.put("userId", user.getIntId());
            if (!Common.isEmpty(useFlag)) {
                map.put("useFlag", useFlag);
            }
            if (!Common.isEmpty(typeId)) {
                map.put("typeId", typeId);
            }
            templateList = templateManager.selectTemplateByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(templateList);
        return SUCCESS;
    }

    /**
     * 定制模板
     *
     * @return
     */
    public String addTemplate() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            template.setUserId(user.getIntId());
            template.setUpdateTime(new Date());
            if (editFlag == 1) {
                templateManager.update(template);
            } else {
                templateManager.insert(template);
            }

            jsonMap.put("result", 1);//操作成功
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            jsonMap.put("result", 0);//操作失败
        }
        return SUCCESS;
    }

    /**
     * 自定义模板导出
     *
     * @return
     */
    public String defineCoulmnsExport() {
        List<ColumnsConfig> configList = null;
        try {
            Map map = new HashMap();
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            configList = templateManager.selectColumnsByConds(map);
            coulmnsExport(configList);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 按照字段导出
     *
     * @param configList
     */
    public void coulmnsExport(List<ColumnsConfig> configList) {
        String fileName = "";
        String sheetName = "";
        List<String> headers = new ArrayList<String>();
        //组装表头
        for (int i = 0; i < configList.size(); i++) {
            ColumnsConfig columnsConfig = (ColumnsConfig) configList.get(i);
            String enName=columnsConfig.getEnName();//字段名称
            if("AIRLIB".equals(enName)){
                //如果勾选了天线库
                headers.add("天线型号");
                headers.add("天线方位角");
                headers.add("挂高");
                headers.add("电子倾角");
                headers.add("机械倾角");
                headers.add("总倾角");
            }else{
                headers.add(columnsConfig.getCnName());
            }
        }
        //组装数据
        List datas = null;
        if (!Common.isEmpty(typeId)) {
            //根据类型组装数据
            switch (typeId.intValue()) {
                case 1:
                    //bts
                    fileName = "室外覆盖站点数据.xls";
                    sheetName = "室外覆盖站点信息";
                    datas = setBtsData(configList);
                    break;
                case 2:
                    fileName = "bbu.xls";
                    sheetName = "bbu信息";
                    datas = setBbuData(configList);
                    break;
                case 3:
                    fileName = "室内分布小区数据.xls";
                    sheetName = "室内分布小区信息";
                    datas = setIndoorData(configList);
                    break;
                case 4:
                    fileName = "室外覆盖小区信息.xls";
                    sheetName = "室外覆盖小区信息";
                    datas = setCellData(configList);
                    break;
                case 8:
                    fileName="隧道覆盖站点信息.xls";
                    sheetName = "隧道覆盖站点信息";
                    datas = setTunelData(configList);
                    break;

            }
            //根据表头、数据导出
            exportExcel(fileName, sheetName, headers, datas);
        }
    }

    /**
     * 组装bts导出数据
     *
     * @param configList
     * @return
     */
    public List setBtsData(List<ColumnsConfig> configList) {
        User user = (User) this.getSession().getAttribute("user");
        List<Map> dataList = null;
        List datas = new LinkedList();
        try {
            //查询条件
            Map map2 = new HashMap();
            if (!Common.isEmpty(countryIds)) {
                map2.put("countryIds", countryIds);
            } else {
                map2.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map2.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map2.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map2.put("btsId", btsId);
            }
            dataList = btsManager.selectExportDataByMap(map2);
            for (Map data : dataList) {
                List cellObj = new LinkedList();//定义存储1行的数据值
                for (ColumnsConfig columnsConfig : configList) {
                    //定义导出的字段
                    String enName = columnsConfig.getEnName();
                    if("CELL_COVERAREA".equals(enName)){
                        //扇区覆盖类型
                        CellManual cellManual=cellManualManager.getBtsMinCell(StringUtil.null2Long0(data.get("INT_ID")));
                        cellObj.add(cellManual==null?"":cellManual.getCoverarea());
                    }else{
                        Object obj = data.get(columnsConfig.getEnName());//获取值
                        cellObj.add(obj);
                    }
                }
                datas.add(cellObj);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return datas;
    }

    public List setBbuData(List<ColumnsConfig> configList) {
        User user = (User) this.getSession().getAttribute("user");
        List<Map> dataList = null;
        List datas = new LinkedList();
        try {
            //查询条件
            Map map2 = new HashMap();
            if (!Common.isEmpty(countryIds)) {
                map2.put("countryIds", countryIds);
            } else {
                map2.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map2.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map2.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map2.put("btsId", btsId);
            }
            dataList = bbuManager.selectExportBbuDataByMap(map2);
            for (Map data : dataList) {
                List cellObj = new LinkedList();//定义存储1行的数据值
                for (ColumnsConfig columnsConfig : configList) {
                    //定义导出的字段
                    String enName=columnsConfig.getEnName();
                    Object obj = data.get(columnsConfig.getEnName());//获取值
                    cellObj.add(obj);
                }
                datas.add(cellObj);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return datas;
    }

    /**
     * @param configList
     * @return
     */
    public List setCellData(List<ColumnsConfig> configList) {
        User user = (User) this.getSession().getAttribute("user");
        List<Map> dataList = null;
        List datas = new LinkedList();
        try {
            //查询条件
            Map map2 = new HashMap();
            if (!Common.isEmpty(countryIds)) {
                map2.put("countryIds", countryIds);
            } else {
                map2.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map2.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map2.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map2.put("btsId", btsId);
            }
            if (!Common.isEmpty(ci)) {
                map2.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map2.put("pn", pn);
            }
            dataList = cellManager.selectExportCellDataByMap(map2);
            for (Map data : dataList) {
                List cellObj = new LinkedList();//定义存储1行的数据值
                Long intId=StringUtil.null2Long0(data.get("INT_ID"));
                for (ColumnsConfig columnsConfig : configList) {
                    //定义导出的字段
                    String enName=columnsConfig.getEnName();
                    if("AIRLIB".equals(enName)){
                        if(!Common.isEmpty(intId)){
                            List<CellAirLib> cellAirLibs=cellManualManager.selectCellAirLibByCellId(intId);
                            StringBuilder airModel=new StringBuilder();
                            StringBuilder azimuth=new StringBuilder();
                            StringBuilder hanghigh=new StringBuilder();
                            StringBuilder electrondip=new StringBuilder();
                            StringBuilder enginedip=new StringBuilder();
                            StringBuilder totaldip=new StringBuilder();
                            if(cellAirLibs!=null){
                                for(CellAirLib cellAirLib:cellAirLibs){
                                    AirLib airlib=cellAirLib.getAirLib();
                                    if(airlib!=null){
                                        airModel.append(airlib.getAirModel()+";");
                                    }
                                    azimuth.append(cellAirLib.getAzimuth()+";");
                                    hanghigh.append(cellAirLib.getHanghigh()+";");
                                    electrondip.append(cellAirLib.getElectrondip()+";");
                                    enginedip.append(cellAirLib.getEnginedip()+";");
                                    totaldip.append(cellAirLib.getTotaldip()+";");
                                }
                                if(airModel.length()>0){
                                    airModel=airModel.delete(airModel.length() - 1, airModel.length());
                                }
                                if(azimuth.length()>0){
                                    azimuth=azimuth.delete(azimuth.length()-1,azimuth.length());
                                }
                                if(hanghigh.length()>0){
                                    hanghigh=hanghigh.delete(hanghigh.length() - 1, hanghigh.length());
                                }
                                if(electrondip.length()>0){
                                    electrondip=electrondip.delete(electrondip.length() - 1, electrondip.length());
                                }
                                if(enginedip.length()>0){
                                    enginedip=enginedip.delete(enginedip.length() - 1, enginedip.length());
                                }
                                if(totaldip.length()>0){
                                    totaldip=totaldip.delete(totaldip.length() - 1, totaldip.length());
                                }
                            }
                            cellObj.add(airModel.toString());
                            cellObj.add(azimuth.toString());
                            cellObj.add(hanghigh.toString());
                            cellObj.add(electrondip.toString());
                            cellObj.add(enginedip.toString());
                            cellObj.add(totaldip.toString());
                        }
                    } else{
                        Object obj = data.get(columnsConfig.getEnName());//获取值
                        cellObj.add(obj);
                    }
                }
                datas.add(cellObj);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return datas;
    }

    /**
     * 组装室分数据
     *
     * @param configList
     * @return
     */
    public List setIndoorData(List<ColumnsConfig> configList) {
        User user = (User) this.getSession().getAttribute("user");
        List<Map> dataList = null;
        List datas = new LinkedList();
        try {
            //查询条件
            Map map2 = new HashMap();
            if (!Common.isEmpty(countryIds)) {
                map2.put("countryIds", countryIds);
            } else {
                map2.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map2.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map2.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map2.put("btsId", btsId);
            }
            if (!Common.isEmpty(ci)) {
                map2.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map2.put("pn", pn);
            }
            dataList = indoorManualManager.selectExportIndoorDataByMap(map2);
            for (Map data : dataList) {
                List cellObj = new LinkedList();//定义存储1行的数据值
                for (ColumnsConfig columnsConfig : configList) {
                    //定义导出的字段
                    Object obj = data.get(columnsConfig.getEnName());//获取值
                    cellObj.add(obj);
                }
                datas.add(cellObj);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return datas;
    }




    /**
     * 组装隧道覆盖站点
     *
     * @param configList
     * @return
     */
    public List setTunelData(List<ColumnsConfig> configList) {
        User user = (User) this.getSession().getAttribute("user");
        List<Map> dataList = null;
        List datas = new LinkedList();
        try {
            //查询条件
            Map map2 = new HashMap();
            if (!Common.isEmpty(countryIds)) {
                map2.put("countryIds", countryIds);
            } else {
                map2.put("countryIds", user.getCountryIds());
            }
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map2.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map2.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map2.put("btsId", btsId);
            }
            dataList = tunelManager.selectTunelBtsExportDataByMap(map2);
            for (Map data : dataList) {
                List cellObj = new LinkedList();//定义存储1行的数据值
                for (ColumnsConfig columnsConfig : configList) {
                    //定义导出的字段
                    Object obj = data.get(columnsConfig.getEnName());//获取值
                    cellObj.add(obj);
                }
                datas.add(cellObj);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return datas;
    }


    public void exportExcel(String fileName, String sheetName, List<String> headers, List<List> datas) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 25);
            HSSFRow row = sheet.createRow(0);
            row.setHeightInPoints(30);
            HSSFCellStyle headStyle = ExcelUtil.setHeaderStype(workbook);
            HSSFCellStyle style = ExcelUtil.setStyle(workbook);
            //填充表头
            for (short i = 0; i < headers.size(); i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headStyle);
                HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
                cell.setCellValue(text);
            }
            //填充数据
            int rowIndex = 1;
            for (List data : datas) {
                row = sheet.createRow((short) rowIndex);
                for (short j = 0; j < data.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell((short) j);
                    cell.setCellStyle(style);
                    cell.setCellValue(StringUtil.null2String(data.get(j)));
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
            sheet.setGridsPrinted(true);
            workbook.write(resp.getOutputStream());

            // 清空流
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (IOException e) {
            LOG.error(e.getMessage(),e);
        }
    }

    /**
     * 按模板导出
     *
     * @return
     */
    public String templateExport() {
        List<ColumnsConfig> configList = null;
        try {
            template = templateManager.selectById(id);
            Map map = new HashMap();
            String columnsConfigs = template.getColumnsConfigs();
            if (columnsConfigs != null) {
                map.put("ids", columnsConfigs.replaceAll(";", ","));
                configList = templateManager.selectColumnsByConds(map);
                coulmnsExport(configList);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return SUCCESS;
    }

    /**
     * 通过模板查询对应的字段
     *
     * @return
     */
    public String columnsByTemplate() {
        List<ColumnsConfig> configList = null;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        try {
            template = templateManager.selectById(id);
            Map map = new HashMap();
            String columnsConfigs = template.getColumnsConfigs();
            if (columnsConfigs != null) {
                map.put("ids", columnsConfigs.replaceAll(";", ","));
                configList = templateManager.selectColumnsByConds(map);
                if (configList != null) {
                    for (ColumnsConfig columnsConfig : configList) {
                        sb1.append(columnsConfig.getId() + ",");
                        sb2.append(columnsConfig.getEnName() + ",");
                        sb3.append(columnsConfig.getCnName() + ",");
                    }
                }
                if (sb1.length() > 0) {
                    sb1 = sb1.delete(sb1.length() - 1, sb1.length());
                    sb2 = sb2.delete(sb2.length() - 1, sb2.length());
                    sb3 = sb3.delete(sb3.length() - 1, sb3.length());
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        jsonMap.put("ids",sb1.toString());
        jsonMap.put("enName",sb2.toString());
        jsonMap.put("cnName",sb3.toString());
        return SUCCESS;
    }


    /**
     * 选择字段弹出页面
     *
     * @return
     */
    public String columns() {
        return SUCCESS;
    }

    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    public BigDecimal getTypeId() {
        return typeId;
    }

    public void setTypeId(BigDecimal typeId) {
        this.typeId = typeId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setBtsManager(BtsManager btsManager) {
        this.btsManager = btsManager;
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

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public void setBbuManager(BbuManager bbuManager) {
        this.bbuManager = bbuManager;
    }

    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }

    public void setIndoorManualManager(IndoorManualManager indoorManualManager) {
        this.indoorManualManager = indoorManualManager;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    public Integer getEnAble() {
        return enAble;
    }

    public void setEnAble(Integer enAble) {
        this.enAble = enAble;
    }
}
