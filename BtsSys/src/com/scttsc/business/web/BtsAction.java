package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.*;
import com.scttsc.baselibs.service.*;
import com.scttsc.business.model.*;
import com.scttsc.business.service.*;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.clientinterface.service.InterfaceManager;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
public class BtsAction extends BaseAction {
    private BtsManager btsManager;
    private BtsManualManager btsManualManager;
    private CityManager cityManager;
    private BbuManager bbuManager;
    private CellManualManager cellManualManager;
    private CellManager cellManager;
    private SchoolLibManager schoolLibManager;
    private VitoLibManager vitoLibManager;
    private RoadLibManager roadLibManager;
    private TunnelLibManager tunnelLibManager;
    private SecneryLibManager secneryLibManager;
    private ConsManager consManager;
    @Autowired
    private TransferManager transferManager;

    String countryIds;// 本地网集合

    Bts bts;

    Long intId;// ID

    BtsManual btsManual;// 站点手工数据

    CBts cbts;//c_bts表对应数据


    Integer editFlag;// 编辑标识

    private File file;
    private String fileFileName;
    private String fileContentType;

    //基站查询条件
    String name; //基站名称
    String bscName; //基站bsc名称
    String btsId;   //网管编号
    String queryFlag;//查询标识
    private Integer manualFlag;//是否录入标志


    //ids
    String ids;
    Integer checkAllFlag;//导出全选标识

    public String bts() {
        return SUCCESS;
    }


    /**
     * 物理站点列表查询,非室分站点
     * <p/>
     * 包括数据录入列表，数据查询
     *
     * @return
     */
    public String btsData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限，queryFlag=all,代表取消用户数据权限，显示全部
                if (Common.isEmpty(queryFlag)) {
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
            //固定条件
            map.put("isIndoor", "否");//物理站点
            map.put("deleteFlag", 0);//在用
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
        return SUCCESS;
    }

    /**
     * 室内分布站点
     * @return
     */
    public String indoorBtsData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限，queryFlag=all,代表取消用户数据权限，显示全部
                if (Common.isEmpty(queryFlag)) {
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
            //固定条件
            map.put("isIndoor", "是");//物理站点
            map.put("deleteFlag", 0);//在用
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
        return SUCCESS;
    }


    /**
     * 物理站点录入页面
     *
     * @return
     */
    public String btsInput() {
        try {
            bts = btsManager.getById(intId);
            City city = cityManager.getById(bts.getCityId().longValue());
            City country = cityManager.getById(bts.getCountyId().longValue());
            bts.setCity(city);
            bts.setCountry(country);
            btsManual = btsManualManager.getById(intId);
            String tranUpsitename;
            String tranDownsitename;
            if (btsManual != null) {
                editFlag = 1;// 编辑页面
            } else {
                editFlag = 0;// 增加页面
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 物理站点信息显示
     *
     * @return
     */
    public String btsInfo() {
        try {
            bts = btsManager.getById(intId);
            btsManual = btsManualManager.getById(intId);
            if (bts != null) {
                City city = cityManager.getById(bts.getCityId().longValue());
                City country = cityManager.getById(bts.getCountyId().longValue());
                bts.setCity(city);
                bts.setCountry(country);
                //wy_bbu中查找共站bbu
                Map map = new HashMap();
                map.put("relateWyBts", bts.getIntId());
                map.put("deleteFlag", 0);//在用
                List<Bbu> bbus = bbuManager.selectBbuByConds(map);
                bts.setBbus(bbus);
                //cons
                //基站覆盖信息
                CellManual cellManual = getBtsMinCell(bts.getIntId().longValue());
                this.getRequest().setAttribute("cellManual", cellManual);
            }

            if (btsManual != null) {
                Map map2 = new HashMap();
                map2.put("groupCode", "BTSINSTALL");
                map2.put("code", btsManual.getInstallPos());
                Cons installPosCons = consManager.getByMap(map2);

                Map map3 = new HashMap();
                map3.put("groupCode", "TOWERTYPE");
                map3.put("code", btsManual.getTowerType());
                Cons towerTypeCons = consManager.getByMap(map3);

                Map map4 = new HashMap();
                map4.put("groupCode", "MRSTRUT");
                map4.put("code", btsManual.getMrStrut());
                Cons mrStrutCons = consManager.getByMap(map4);

                btsManual.setInstallPosCons(installPosCons);
                btsManual.setTowerTypeCons(towerTypeCons);
                btsManual.setMrStrutCons(mrStrutCons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    private CellManual getBtsMinCell(Long wybtsId) {
        CellManual cellManual = null;
        try {
            //计算小区覆盖情况
            List<Cell> cellList = cellManager.selectByBtsId(wybtsId);
            if (bts != null) {
                bts.setCells(cellList);
            }
            Cell minCcell = null;
            if (cellList != null) {
                int cellId = 0;
                int minCellId = 0;
                for (int i = 0; i < cellList.size(); i++) {
                    Cell cell = cellList.get(i);
                    cellId = cell.getCellId();
                    if (i == 0) {
                        minCellId = cellId;
                        minCcell = cell;
                    } else {
                        if (cellId < minCellId) {
                            minCellId = cellId;
                            minCcell = cell;
                        }
                    }
                }
            }
            if (minCcell != null) {
                //以最小cellId显示基站覆盖信息
                cellManual = cellManualManager.selectById(new Long(minCcell.getIntId()));//小区手工表
                //覆盖信息
                Map map1 = new HashMap();
                //农村乡镇库
                map1.put("cellId", minCcell.getIntId());
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellManual;
    }

    /**
     * 增加站点手工数据
     *
     * @return
     */
    public String addBtsManual() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            btsManual.setUpdateuser(user.getIntId());
            btsManual.setUpdatetime(new Date());
            if (editFlag == 1) {
                btsManualManager.update(btsManual);// 编辑
            } else {
                btsManualManager.insert(btsManual);// 增加
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 导入bts页面
     *
     * @return
     */
    public String importbts() {
        return SUCCESS;
    }

    /**
     * 导出待录入数据
     *
     * @return
     */
    public String exportInputData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/btsTemplate.xls";
        String fileName = "基站待录入数据.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            map.put("isIndoor", "否");


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
            map.put("deleteFlag", 0);//在用
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
            int rowIndex = 2;
            for (Bts bts : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex - 1));
                cList.add(StringUtil.null2String(bts.getIntId()));
                cList.add(StringUtil.null2String(bts.getName()));
                cList.add(StringUtil.null2String(bts.getBscName()));
                cList.add(StringUtil.null2String(bts.getBtsId()));
                int manualFlag = bts.getManualFlag().intValue();
                if (manualFlag == 1) {
                    //已录入
                    btsManual = btsManualManager.getById(bts.getIntId().longValue());
                    if (btsManual != null) {
                        Cons btsinstallCons = ConstantUtil.getInstance().getConsByCode("BTSINSTALL", btsManual.getInstallPos() + "");
                        cList.add(StringUtil.null2String(btsinstallCons == null ? "" : btsinstallCons.getName()));
                        cList.add(StringUtil.null2String(btsManual.getSharFlag()));
                        cList.add(StringUtil.null2String(btsManual.getSharName()));//共享方名称
                        cList.add(StringUtil.null2String(btsManual.getAddress()));
                        cList.add(StringUtil.null2String(btsManual.getOpenTime()));
                        cList.add(StringUtil.null2String(btsManual.getTranModel()));
                        cList.add(StringUtil.null2String(btsManual.getTranFac()));
                        cList.add(StringUtil.null2String(btsManual.getTranUpsitenameStr())); //传输TOP上游站点
                        cList.add(StringUtil.null2String(btsManual.getTranDownsitenameStr()));//传输下游站点
                        cList.add(StringUtil.null2String(btsManual.getTranNetprotect()));
                        cList.add(StringUtil.null2String(btsManual.getTranIsnode()));
                        cList.add(StringUtil.null2String(btsManual.getTranSitenum()));
                        cList.add(StringUtil.null2String(btsManual.getSourModel()));
                        cList.add(StringUtil.null2String(btsManual.getSourFac()));
                        cList.add(StringUtil.null2String(btsManual.getSourModuleModel()));//整流模块型号
                        cList.add(StringUtil.null2String(btsManual.getSourModuleNum()));
                        cList.add(StringUtil.null2String(btsManual.getSourCapa())); //满架容量（A）
                        cList.add(StringUtil.null2String(btsManual.getBoxModel()));
                        cList.add(StringUtil.null2String(btsManual.getBoxFac()));
                        cList.add(StringUtil.null2String(btsManual.getCellModel()));
                        cList.add(StringUtil.null2String(btsManual.getCellFac()));
                        cList.add(StringUtil.null2String(btsManual.getCellCapa()));
                        cList.add(StringUtil.null2String(btsManual.getCellNum()));
                        cList.add(StringUtil.null2String(btsManual.getCellPower()));
                        cList.add(StringUtil.null2String(btsManual.getCellDuar()));
                        cList.add(StringUtil.null2String(btsManual.getCellUstime()));
                        Cons towerTypeCons = ConstantUtil.getInstance().getConsByCode("TOWERTYPE", btsManual.getTowerType() + "");
                        cList.add(StringUtil.null2String(towerTypeCons == null ? "" : towerTypeCons.getName()));
                        cList.add(StringUtil.null2String(btsManual.getTowerHigh()));
                        cList.add(StringUtil.null2String(btsManual.getAcModel()));
                        cList.add(StringUtil.null2String(btsManual.getAcFac()));
                        cList.add(StringUtil.null2String(btsManual.getAcNum()));
                        cList.add(StringUtil.null2String(btsManual.getDhModel()));
                        cList.add(StringUtil.null2String(btsManual.getDhFac()));
                        cList.add(StringUtil.null2String(btsManual.getXfModel()));
                        cList.add(StringUtil.null2String(btsManual.getXfFac()));   //
                        Cons mrstrutCons = ConstantUtil.getInstance().getConsByCode("MRSTRUT", btsManual.getMrStrut() + "");
                        cList.add(StringUtil.null2String(mrstrutCons == null ? "" : mrstrutCons.getName()));
                        cList.add(StringUtil.null2String(btsManual.getMrLength()));
                        cList.add(StringUtil.null2String(btsManual.getMrWidth()));
                        cList.add(StringUtil.null2String(btsManual.getMrHigh()));
                        cList.add(StringUtil.null2String(btsManual.getMrOwner()));
                        cList.add(StringUtil.null2String(btsManual.getWdType()));
                        cList.add(StringUtil.null2String(btsManual.getWdModel()));
                        cList.add(StringUtil.null2String(btsManual.getWdFac()));
                        cList.add(StringUtil.null2String(btsManual.getWdGds()));
                        cList.add(StringUtil.null2String(btsManual.getOeType()));
                        cList.add(StringUtil.null2String(btsManual.getOeModel()));
                        cList.add(StringUtil.null2String(btsManual.getOePower()));
                        cList.add(StringUtil.null2String(btsManual.getOeFac()));
                        cList.add(StringUtil.null2String(btsManual.getRemark()));
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
    public String importInputData() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            Map transConds = new HashMap();
            List<WyTransfernode> transferList = transferManager.selectTransferList(transConds);
            Map<String, WyTransfernode> transMap = new HashMap<String, WyTransfernode>();
            if (transferList != null) {
                for (WyTransfernode wyTransfernode : transferList) {
                    transMap.put(wyTransfernode.getName(), wyTransfernode);
                }
            }
            Map btsCons = new HashMap();
            btsCons.put("isIndoor", "否");//物理站点
            btsCons.put("deleteFlag", 0);//在用
            List<Bts> btsList = btsManager.selectByMap(btsCons);
            Map<String, Bts> btsMap = new HashMap<String, Bts>();
            if (btsList != null) {
                for (Bts bts : btsList) {
                    btsMap.put(bts.getName(), bts);
                }
            }
            Map bbuCons = new HashMap();
            bbuCons.put("deleteFlag", 0);// 再用
            bbuCons.put("isShare", 0);
            bbuCons.put("bbuType", "1,2");//纯BBU数据
            Map<String, Bbu> bbuMap = new HashMap<String, Bbu>();
            List<Bbu> bbuList = bbuManager.selectByExample(bbuCons);
            if (bbuList != null) {
                for (Bbu bbu : bbuList) {
                    bbuMap.put(bbu.getName(), bbu);
                }
            }

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
                Map obj = parseBtsObj(i - 1, row, errorList, transMap, btsMap, bbuMap);
                if (obj != null) {
                    btsManualManager.importInsert(obj);
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
    public Map parseBtsObj(int rowNum, HSSFRow row, List errorList, Map<String, WyTransfernode> transMap, Map<String, Bts> btsMap, Map<String, Bbu> bbuMap) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getBtsCoulmnMap();
        try {
            int j = 0;
            for (String dataKey : coulmnMap.keySet()) {
                Validity validity = coulmnMap.get(dataKey);
                HSSFCell cell = row.getCell((short) (j + 1));//Excel
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

                if ("installPos".equals(dataKey) || "towerType".equals(dataKey) || "mrStrut".equals(dataKey)) {
                    //安装位置，塔跪类型，机房结构
                    Map<String, String> groupCodeMap = ExcelHelper.getGroupCodeMap();
                    String groupCode = groupCodeMap.get(dataKey);
                    Cons con = ConstantUtil.getInstance().getCons(groupCode, cellValue);
                    if (con != null) {
                        map.put(dataKey, con.getCode());
                    } else {
                        errorList.add("第" + rowNum + "行导入校验失败:" + validity.getName() + ":填入未在数据库配置选项中,"+cellValue);
                        return null;
                    }
                } else if ("tranUpsitename".equals(dataKey)||"tranDownsitename".equals(dataKey)) {
                    Bts bts = btsMap.get(cellValue);
                    WyTransfernode wyTransfernode = transMap.get(cellValue);
                    Bbu bbu = bbuMap.get(cellValue);
                    if (bts != null) {
                        map.put(dataKey, "1_" + bts.getName());
                    }
                    if (wyTransfernode != null) {
                        map.put(dataKey, "2_" + wyTransfernode.getName());
                    }
                    if (bbu != null) {
                        map.put(dataKey, "3_" + bbu.getName());
                    }
                }  else {
                    map.put(dataKey, cellValue);
                }
                j++;
            }
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


    /**
     * 导入文件
     *
     * @return
     */
    public String importFile() {
        File desFile = new File(getRequest().getSession()
                .getServletContext().getRealPath("/uploadFiles")
                + "/" + this.fileFileName);
        try {
            FileUtils.copyFile(file, desFile);
            jsonMap.put("result", 1);
            jsonMap.put("address", "/uploadFiles/" + desFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }


    public void setBtsManager(BtsManager btsManager) {
        this.btsManager = btsManager;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public Bts getBts() {
        return bts;
    }

    public void setBts(Bts bts) {
        this.bts = bts;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public void setBtsManualManager(BtsManualManager btsManualManager) {
        this.btsManualManager = btsManualManager;
    }

    public BtsManual getBtsManual() {
        return btsManual;
    }

    public void setBtsManual(BtsManual btsManual) {
        this.btsManual = btsManual;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
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

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    public void setBbuManager(BbuManager bbuManager) {
        this.bbuManager = bbuManager;
    }

    public CBts getCbts() {
        return cbts;
    }

    public void setCbts(CBts cbts) {
        this.cbts = cbts;
    }

    public void setCellManualManager(CellManualManager cellManualManager) {
        this.cellManualManager = cellManualManager;
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

    public void setTunnelLibManager(TunnelLibManager tunnelLibManager) {
        this.tunnelLibManager = tunnelLibManager;
    }

    public void setSecneryLibManager(SecneryLibManager secneryLibManager) {
        this.secneryLibManager = secneryLibManager;
    }

    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
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
