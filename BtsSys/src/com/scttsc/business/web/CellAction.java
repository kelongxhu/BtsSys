package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.*;
import com.scttsc.baselibs.service.*;
import com.scttsc.business.model.*;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.BtsManualManager;
import com.scttsc.business.service.CellManager;
import com.scttsc.business.service.CellManualManager;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@SuppressWarnings("serial")
public class CellAction extends BaseAction {

    private CellManager cellManager;

    private CellManualManager cellManualManager;

    private CityManager cityManager;

    private BtsManager btsManager;

    private BtsManualManager btsManualManager;

    private ConsManager consManager;

    private SchoolLibManager schoolLibManager;
    private VitoLibManager vitoLibManager;
    private RoadLibManager roadLibManager;
    private TunnelLibManager tunnelLibManager;
    private SecneryLibManager secneryLibManager;
    @Autowired
    private AirLibManager airLibManager;

    Cell cell;// 小区表

    CellManual cellManual;// 小区手工表

    Ccell ccell;// c_cell表


    private String countryIds;// 区县集合

    Integer editFlag;// 编辑标识

    Long intId;// ID

    private File file;
    private String fileFileName;
    private String fileContentType;

    City city;//所属地市
    City country;//所属区县

    //查询条件
    private String name;
    private String bscName;
    private String btsId;
    private Integer ci;
    private Integer pn;
    String queryFlag;

    //勾选集合
    String ids;
    Integer checkAllFlag;

    private Integer manualFlag;//手工标识


    public String cell() {
        return SUCCESS;
    }

    /**
     * 小区数据查询
     *
     * @return
     */
    public String cellData() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<Cell> list = null;
        try {
            map.put("deleteFlag", 0);
            if (!Common.isEmpty(countryIds)) {
                // 默认权限
                map.put("countryIds", countryIds);
            } else {
                // 默认数据权限
                if (Common.isEmpty(queryFlag)) {
                    //用户权限树
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

    /**
     * 录入小区手工信息
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String cellInput() {
        try {
            cell = cellManager.selectById(intId);
            ccell = cellManager.selectCcellById(intId);
            cellManual = cellManualManager.selectById(intId);
            //处理功分
            int isGf = Common.string2Int(cell.getIsGf());
            if (isGf == 0) {
                isGf = 1;
            }
            if (cellManual == null) {
                editFlag = 0;// 增加
            } else {
                //农村乡镇库
                Map map = new HashMap();
                map.put("cellId", intId);
                map.put("libType", 2);
                List<CellLib> countryLibs = cellManualManager.selectCellLibByMap(map);
                String countryLib = "";
                for (CellLib coutry : countryLibs) {
                    countryLib += coutry.getLibId() + ";";
                }
                if (!Common.isEmpty(countryLib)) {
                    countryLib = countryLib.substring(0, countryLib.length() - 1);
                    cellManual.setCountryLib(countryLib);
                }
                //覆盖道路库
                String roadLib = "";
                Map map2 = new HashMap();
                map2.put("cellId", intId);
                map2.put("libType", 3);
                List<CellLib> roadLibs = cellManualManager.selectCellLibByMap(map2);
                for (CellLib road : roadLibs) {
                    roadLib += "-2_" + road.getLibId() + ";";
                }
                //隧道库
                Map map3 = new HashMap();
                map3.put("cellId", intId);
                map3.put("libType", 6);
                List<CellLib> tunnelLibs = cellManualManager.selectCellLibByMap(map3);
                for (CellLib tunnel : tunnelLibs) {
                    roadLib += "-3_" + tunnel.getLibId() + ";";
                }
                if (!Common.isEmpty(roadLib)) {
                    roadLib = roadLib.substring(0, roadLib.length() - 1);
                    cellManual.setRoadLib(roadLib);
                }
                //校园库
                String hotLib = "";
                Map map4 = new HashMap();
                map4.put("cellId", intId);
                map4.put("libType", 1);
                List<CellLib> schoolLibs = cellManualManager.selectCellLibByMap(map4);
                for (CellLib school : schoolLibs) {
                    hotLib += "-2_" + school.getLibId() + ";";
                }
                //风景库
                Map map5 = new HashMap();
                map5.put("cellId", intId);
                map5.put("libType", 5);
                List<CellLib> secneryLibs = cellManualManager.selectCellLibByMap(map5);
                for (CellLib secnery : secneryLibs) {
                    hotLib += "-3_" + secnery.getLibId() + ";";
                }
                if (!Common.isEmpty(hotLib)) {
                    hotLib = hotLib.substring(0, hotLib.length() - 1);
                    cellManual.setHotLib(hotLib);
                }
                //天线库
                List<CellAirLib> cellAirLibs = cellManualManager.selectCellAirLibByCellId(intId);
                cellManual.setAirLibs(cellAirLibs);
                editFlag = 1;// 编辑
            }
            this.getRequest().setAttribute("isGf", isGf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 小区信息显示
     *
     * @return
     */
    public String cellInfo() {
        try {
            cell = cellManager.selectById(intId);//wy_cell
            cellManual = cellManualManager.selectById(intId);//小区手工表
            ccell = cellManager.selectCcellById(intId);//c_cell
            city = cityManager.getById(cell.getCityId().longValue());
            country = cityManager.getById(cell.getCountryId().longValue());
            Long btsId = cell.getWyBtsIntId();
            if (btsId != null) {
                Bts bts = btsManager.getById(btsId);
                BtsManual btsManual = btsManualManager.getById(btsId);
                if (btsManual != null) {
                    Map consMap = new HashMap();
                    consMap.put("groupCode", "TOWERTYPE");
                    consMap.put("code", btsManual.getTowerType());
                    Cons towerCons = consManager.getByMap(consMap); //通过code获取塔桅类型
                    this.getRequest().setAttribute("towerCons", towerCons);
                    this.getRequest().setAttribute("btsManual", btsManual);
                }
                this.getRequest().setAttribute("bts", bts);
            }
            //c_cell的手工表
            CparCell cparCell = cellManager.selectCparCellById(intId);
            this.getRequest().setAttribute("cparCell", cparCell);
            addCoverInfo(cellManual);//处理覆盖信息
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    /**
     * 处理覆盖信息
     *
     * @param cellManual
     */
    public void addCoverInfo(CellManual cellManual) {
        try {
            //覆盖信息
            Map map1 = new HashMap();
            //农村乡镇库
            map1.put("cellId", cellManual.getIntId());
            List<CellLib> cellLibs = cellManualManager.selectCellLibByMap(map1);
            //校园库
            List<SchoolLib> schoolLibs = new ArrayList<SchoolLib>();
            List<VitoLib> vitoLibs = new ArrayList<VitoLib>();//农村库
            List<RoadLib> roadLibs = new ArrayList<RoadLib>();//道路库
            List<TunnelLib> tunnelLibs = new ArrayList<TunnelLib>();//隧道库
            List<SecneryLib> secneryLibs = new ArrayList<SecneryLib>();//风景库
            for (CellLib cellLib : cellLibs) {
                int libType = cellLib.getLibType();
                switch (libType) {
                    case 1:
                        SchoolLib schoolLib = schoolLibManager.getById(cellLib.getLibId().longValue());
                        schoolLibs.add(schoolLib);
                        break;
                    case 2:
                        //农村库
                        VitoLib vitoLib = vitoLibManager.getById(cellLib.getLibId().longValue());
                        vitoLibs.add(vitoLib);
                        break;
                    case 3:
                        //道路库
                        RoadLib roadLib = roadLibManager.getById(cellLib.getLibId().longValue());
                        roadLibs.add(roadLib);
                        break;
                    case 5:
                        //风景库
                        SecneryLib secneryLib = secneryLibManager.getById(cellLib.getLibId().longValue());
                        secneryLibs.add(secneryLib);
                        break;
                    case 6:
                        //隧道库
                        TunnelLib tunnelLib = tunnelLibManager.getById(cellLib.getLibId().longValue());
                        tunnelLibs.add(tunnelLib);
                        break;

                }
            }
            cellManual.setSchoolLibs(schoolLibs);
            cellManual.setVitoLibs(vitoLibs);
            cellManual.setRoadLibs(roadLibs);
            cellManual.setSecneryLibs(secneryLibs);
            cellManual.setTunnelLibs(tunnelLibs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加小区手工数据
     *
     * @return
     */
    public String addCellManual() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            cellManual.setUpdatetime(new Date());
            cellManual.setUpdateuser(user.getIntId());
            if (editFlag == 0) {
                cellManualManager.saveCellManual(cellManual);//增加
            } else {
                //编辑
                cellManualManager.updateCellManual(cellManual);
            }
            jsonMap.put("result", 1);// 录入成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);// 录入失败
        }
        return SUCCESS;
    }


    /**
     * 导出待录入数据
     *
     * @return
     */
    public String exportCellInputData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Cell> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/cellTemplate.xls";
        String fileName = "Cell待录入数据.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            map.put("deleteFlag", 0);// 再用

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
            for (Cell c : list) {
                Ccell ccell = cellManager.selectCcellById(new Long(c.getIntId()));
                if (ccell == null) {
                    continue;
                }
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(c.getIntId()));
                cList.add(StringUtil.null2String(c.getName()));
                cList.add(StringUtil.null2String(ccell.getBscName()));
                cList.add(StringUtil.null2String(ccell.getCi()));//CI
                String gF = c.getIsGf();
                if ("0".equals(gF)) {
                    cList.add("否");
                } else {
                    cList.add("是");
                }
                cList.add(StringUtil.null2String(c.getIsGf()));//功分编号

                int manualFlag = c.getManualFlag().intValue();
                if (manualFlag == 1) {
                    //小区手工数据录入
                    CellManual cellManual1 = cellManualManager.selectById(new Long(c.getIntId()));
                    if (cellManual1 != null) {
                        addCoverInfo(cellManual1);
                        cList.add(StringUtil.null2String(cellManual1.getIndoornum()));//所带室内分布数量
                        List<CellAirLib> airLibs = cellManual1.getAirLibs();
                        StringBuilder airModelSb = new StringBuilder();
                        StringBuilder azimuthSb = new StringBuilder();
                        StringBuilder hanghighSb = new StringBuilder();
                        StringBuilder electrondipSb = new StringBuilder();
                        StringBuilder enginedipSb = new StringBuilder();
                        if (airLibs != null && airLibs.size() > 0) {
                            for (CellAirLib airLib : airLibs) {
                                AirLib air = airLib.getAirLib();
                                if (air != null) {
                                    airModelSb.append(air.getAirModel() + ";");
                                    azimuthSb.append(airLib.getAzimuth() + ";");
                                    hanghighSb.append(airLib.getHanghigh() + ";");
                                    electrondipSb.append(airLib.getElectrondip() + ";");
                                    enginedipSb.append(airLib.getEnginedip() + ";");
                                }
                            }
                        }
                        cList.add(airModelSb.length() > 0 ? airModelSb.substring(0, airModelSb.length() - 1).toString() : "");
                        cList.add(azimuthSb.length() > 0 ? azimuthSb.substring(0, azimuthSb.length() - 1).toString() : "");
                        cList.add(hanghighSb.length() > 0 ? hanghighSb.substring(0, hanghighSb.length() - 1).toString() : "");
                        cList.add(electrondipSb.length() > 0 ? electrondipSb.substring(0, electrondipSb.length() - 1).toString() : "");
                        cList.add(enginedipSb.length() > 0 ? enginedipSb.substring(0, enginedipSb.length() - 1).toString() : "");
                        cList.add(StringUtil.null2String(cellManual1.getBorderflag()));
                        cList.add(StringUtil.null2String(cellManual1.getDofrqnum()));
                        cList.add(StringUtil.null2String(cellManual1.getCoverarea())); //扇区覆盖区域类型

                        //农村乡镇名称
                        List<VitoLib> vitoLibs = cellManual1.getVitoLibs();
                        StringBuilder vitoLibSb = new StringBuilder();
                        if (vitoLibs != null) {
                            for (VitoLib vitoLib : vitoLibs) {
                                vitoLibSb.append(vitoLib.getName() + ";");
                            }
                        }
                        cList.add(vitoLibSb.length() > 0 ? vitoLibSb.substring(0, vitoLibSb.length() - 1).toString() : "");//农村乡镇名称
                        cList.add(StringUtil.null2String(cellManual1.getCoverroad()));

                        //道路名称
                        List<RoadLib> roadLibs = cellManual1.getRoadLibs();//覆盖道路库
                        List<TunnelLib> tunnelLibs = cellManual1.getTunnelLibs();//覆盖隧道库
                        StringBuilder roadSb = new StringBuilder();
                        if (roadLibs != null) {
                            for (RoadLib roadLib : roadLibs) {
                                roadSb.append(roadLib.getName() + ";");
                            }
                        }
                        if (tunnelLibs != null) {
                            for (TunnelLib tunnelLib : tunnelLibs) {
                                roadSb.append(tunnelLib.getName() + ";");
                            }
                        }
                        cList.add(roadSb.length() > 0 ? roadSb.substring(0, roadSb.length() - 1).toString() : "");//道路名称
                        cList.add(StringUtil.null2String(cellManual1.getCoverhot()));

                        List<SchoolLib> schoolLibs = cellManual1.getSchoolLibs();
                        List<SecneryLib> secneryLibs = cellManual1.getSecneryLibs();
                        StringBuilder hotSb = new StringBuilder();
                        if (schoolLibs != null) {
                            for (SchoolLib schoolLib : schoolLibs) {
                                hotSb.append(schoolLib.getName() + ";");
                            }
                        }
                        if (secneryLibs != null) {
                            for (SecneryLib secneryLib : secneryLibs) {
                                hotSb.append(secneryLib.getSceName() + ";");
                            }
                        }
                        cList.add(hotSb.length() > 0 ? hotSb.substring(0, hotSb.length() - 1).toString() : "");//热点名称
                        cList.add(StringUtil.null2String(cellManual1.getBoundarycellflag()));
                        cList.add(StringUtil.null2String(cellManual1.getBorderprovince()));
                        cList.add(StringUtil.null2String(cellManual1.getBordercity()));//接壤城市
                    }
                }


                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (short j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell hssfCell = row.createCell((short) j);
                    hssfCell.setCellStyle(style);
                    hssfCell.setCellValue(cList.get(j));
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


    public Map<String, Map> getLibMap() {
        Map<String, Map> libMap = new HashMap<String, Map>();
        try {
            //农村库统一查询放内存，加快导入速度
            List<VitoLib> vitoLibs = vitoLibManager.getByMap(null);
            Map<String, VitoLib> vitoLibMap = new HashMap<String, VitoLib>();
            for (VitoLib vitoLib : vitoLibs) {
                vitoLibMap.put(vitoLib.getName(), vitoLib);
            }
            libMap.put(Constants.VitoLib + "", vitoLibMap);
            List<RoadLib> roadLibs = roadLibManager.loadAll();
            Map<String, RoadLib> roadLibMap = new HashMap<String, RoadLib>();
            for (RoadLib road : roadLibs) {
                roadLibMap.put(road.getName(), road);
            }
            libMap.put(Constants.RoadLib + "", roadLibMap);
            List<TunnelLib> tunnelLibs = tunnelLibManager.loadAll();
            Map<String, TunnelLib> tunnelLibMap = new HashMap<String, TunnelLib>();
            for (TunnelLib tunnelLib : tunnelLibs) {
                tunnelLibMap.put(tunnelLib.getName(), tunnelLib);
            }
            libMap.put(Constants.TunnelLib + "", tunnelLibMap);
            List<SchoolLib> schoolLibs = schoolLibManager.loadAll();
            Map<String, SchoolLib> schoolLibMap = new HashMap<String, SchoolLib>();
            for (SchoolLib schoolLib : schoolLibs) {
                schoolLibMap.put(schoolLib.getName(), schoolLib);
            }
            libMap.put(Constants.SchoolLib + "", schoolLibMap);
            List<SecneryLib> secneryLibs = secneryLibManager.loadAll();
            Map<String, SecneryLib> secneryLibMap = new HashMap<String, SecneryLib>();
            for (SecneryLib secneryLib : secneryLibs) {
                secneryLibMap.put(secneryLib.getSceName(), secneryLib);
            }
            libMap.put(Constants.SecneryLib + "", secneryLibMap);
            List<AirLib> airLibs = airLibManager.selectAll(null);
            Map<String, AirLib> airLibMap = new HashMap<String, AirLib>();
            for (AirLib airLib : airLibs) {
                airLibMap.put(airLib.getAirModel(), airLib);
            }
            libMap.put(Constants.AirLib + "", airLibMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libMap;
    }


    /**
     * 导入小区手工数据
     *
     * @return
     */
    public String importCellInputData() {
        try {

            Map<String, Map> libMap = getLibMap();

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
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseCellObj(i, row, errorList);
                if (obj != null) {
                    try {
                        cellManualManager.insertImport(obj, libMap);
                        sucess++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorList.add("第" + i + "行增加数据失败，" + e.getMessage());
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
     * 解析一行Excel对象
     *
     * @param row
     * @return
     */
    public Map parseCellObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getCellCoulmnMap();
        try {
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
                map.put(dataKey, cellValue);
                j++;
            }
            map.put("updatetime", new Date());
            if (user != null) {
                map.put("updateuser", user.getIntId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }


    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public CellManual getCellManual() {
        return cellManual;
    }

    public void setCellManual(CellManual cellManual) {
        this.cellManual = cellManual;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public void setCellManualManager(CellManualManager cellManualManager) {
        this.cellManualManager = cellManualManager;
    }

    public Ccell getCcell() {
        return ccell;
    }

    public void setCcell(Ccell ccell) {
        this.ccell = ccell;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public void setTunnelLibManager(TunnelLibManager tunnelLibManager) {
        this.tunnelLibManager = tunnelLibManager;
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

    public CityManager getCityManager() {
        return cityManager;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
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

    public void setBtsManager(BtsManager btsManager) {
        this.btsManager = btsManager;
    }

    public void setBtsManualManager(BtsManualManager btsManualManager) {
        this.btsManualManager = btsManualManager;
    }

    public void setConsManager(ConsManager consManager) {
        this.consManager = consManager;
    }

    public void setSchoolLibManager(SchoolLibManager schoolLibManager) {
        this.schoolLibManager = schoolLibManager;
    }

    public void setVitoLibManager(VitoLibManager vitoLibManager) {
        this.vitoLibManager = vitoLibManager;
    }

    public void setRoadLibManager(RoadLibManager roadLibManager) {
        this.roadLibManager = roadLibManager;
    }

    public void setSecneryLibManager(SecneryLibManager secneryLibManager) {
        this.secneryLibManager = secneryLibManager;
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
