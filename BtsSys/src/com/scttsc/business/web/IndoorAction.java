package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.impl.ConsManagerImpl;
import com.scttsc.business.model.*;
import com.scttsc.business.service.*;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("serial")
public class IndoorAction extends BaseAction {
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private CellManager cellManager;
    @Autowired
    private ConsManagerImpl consManager;
    @Autowired
    private ErectStationManager erectStationManager;
    @Autowired
    private IndoorManualManager indoorManualManager;
    @Autowired
    private DryStationManager dryStationManager;

    IndoorManual indoorManual;

    private Integer addFlag;//手工增加或者关联增加

    private Long intId;//室分站点ID

    private CityManager cityManager;


    private File file;
    private String fileFileName;
    private String fileContentType;

    private Long userId;


    String name; //基站名称
    String bscName; //基站bsc名称
    String btsId;   //网管编号


    private Integer manualFlag;//手工标识

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    String countryIds;// 区县集合

    String ids;  //ID集合

    Integer checkAllFlag;

    public String indoor() {
        return SUCCESS;
    }

    public String indoorDetail() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (intId != null) {
            map.put("intId", intId);
        }
        Bts bts = null;
        try {
            if (intId != null) {
                List<Bts> list = btsManager.getByConds(map);
                if (list != null && list.size() > 0) {
                    bts = list.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Ccell ccell = null;
        try {
            if (bts != null) {
                List<Ccell> list = cellManager.selectCcellByBtsId(Long.parseLong(bts.getIntId() + ""));
                if (list != null && list.size() > 0) {
                    ccell = list.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        IndoorManual indoorManual = null;
        try {
            if (bts != null) {
                indoorManual = indoorManualManager.selectByPrimaryKey(bts.getIntId().longValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (indoorManual != null) {
            if (indoorManual.getProp1() != null) {
                Map maps = new HashMap();
                maps.put("name", indoorManual.getProp1());
                maps.put("groupCode", "NATURE1");
                try {
                    Cons cons = consManager.getByMap(maps);
                    if (cons != null) {
                        this.getRequest().setAttribute("prop1", cons.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (indoorManual.getProp2() != null) {
                Map maps = new HashMap();
                maps.put("name", indoorManual.getProp2());
                maps.put("groupCode", "NATURE2");
                try {
                    Cons cons = consManager.getByMap(maps);
                    if (cons != null) {
                        this.getRequest().setAttribute("prop2", cons.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Map maps = new HashMap();
        if (!Common.isEmpty(intId)) {
            maps.put("wybtsintid", intId);
        }
        List<ErectStation> erectStationList = erectStationManager.selectByExample(maps);
        List<DryStation> dryStationList = dryStationManager.selectByExample(maps);

        this.getRequest().setAttribute("erectStationList", erectStationList);
        this.getRequest().setAttribute("dryStationList", dryStationList);

        this.getSession().setAttribute("bts", bts);

        this.getRequest().setAttribute("ccell", ccell);
        this.getRequest().setAttribute("bts", bts);
        this.getRequest().setAttribute("indoorManual", indoorManual);

        return SUCCESS;
    }

    public String indoorImp() {
        return SUCCESS;
    }

    /**
     * 室分站点
     *
     * @return
     */
    public String indoorData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<IndoorManual> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                map.put("countryIds", user.getCountryIds());
            }
            map.put("deleteFlag", 0);
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }

            if(!Common.isEmpty(manualFlag)){
                map.put("manualFlag",manualFlag);
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
     * 室分手工数据录入
     * <p/>
     * 选中一条数据，则关联增加
     * 未选中则手工增加页面y
     */
    public String indoorInput() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!Common.isEmpty(intId)) {
                map.put("intId", intId);
                indoorManual = indoorManualManager.selectByPrimaryKey(intId);   //编辑
                if (indoorManual == null) {
                    //增加
                    //室内分布小区
                    indoorManual = new IndoorManual();
                    Cell cell=cellManager.selectById(intId);
                    //所属物理站点
                    Bts bts = null;
                    if(cell!=null){
                        bts=btsManager.getById(cell.getWyBtsIntId());
                        indoorManual.setIntId(intId.toString());
                        indoorManual.setName(cell.getName());
                        indoorManual.setCityId(cell.getCityId());
                        indoorManual.setCountryId(cell.getCountryId());
                        indoorManual.setCellSeq(new BigDecimal(cell.getCellId()));
                        indoorManual.setPn(new BigDecimal(cell.getPn()));
                        indoorManual.setCi(new BigDecimal(cell.getCi()));
                        indoorManual.setBscName(cell.getBscName());
                    }
                    //初始化附带值
                    if(bts!=null){
                        indoorManual.setLongitude(bts.getLongitude());
                        indoorManual.setLatitude(bts.getLatitude());
                        indoorManual.setVendorBtstype(bts.getVendorBtstype());
                        indoorManual.setBtsName(bts.getBtsName());
                    }
                } else {
                    //编辑
                }
                addFlag = 0;//关联增加
            } else {
                indoorManual = new IndoorManual();
                indoorManual.setName("无");
                addFlag = 1;//手工增加
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }

        if (indoorManual != null) {
            if (indoorManual.getProp1() != null) {
                Map maps = new HashMap();
                maps.put("name", indoorManual.getProp1());
                maps.put("groupCode", "NATURE1");
                try {
                    Cons cons = consManager.getByMap(maps);
                    if (cons != null) {
                        this.getRequest().setAttribute("prop1", cons.getCode());
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }

            if (indoorManual.getProp2() != null) {
                Map maps = new HashMap();
                maps.put("name", indoorManual.getProp2());
                maps.put("groupCode", "NATURE2");
                try {
                    Cons cons = consManager.getByMap(maps);
                    if (cons != null) {
                        this.getRequest().setAttribute("prop2", cons.getCode());
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return SUCCESS;
    }


    /**
     * 增加编辑室分手工数据
     *
     * @return
     */

    public String indoorManualDate() {
        User user = (User) this.getSession().getAttribute("user");
        indoorManual.setUpdateuser(user.getIntId());
        indoorManual.setUpdatetime(new Date());
        if ("1".equals(indoorManual.getWlanflag())) {
            indoorManual.setWlanflag("是");
        } else {
            indoorManual.setWlanflag("否");
        }
        try {
            //处理本地网
            //
            City city=cityManager.getById(indoorManual.getCountryId().longValue());
            if(city!=null){
                indoorManual.setCityId(new BigDecimal(city.getParentId()));
            }
            //手动增加自动生成intId
            if (Common.isEmpty(indoorManual.getIntId()) && indoorManual.getAddFlag() == 1) {
                StringBuffer s = new StringBuffer();
                s.append(indoorManual.getName());
                s.append(indoorManual.getBscName());
                s.append(indoorManual.getBtsName());
                int id = s.toString().hashCode();
                indoorManual.setIntId(id+"");
            }
            IndoorManual indoorManual1 = indoorManualManager.selectByPrimaryKey(new Long(indoorManual.getIntId()));
            if (indoorManual1 == null) {
                //插入
                indoorManualManager.insert(indoorManual);
            } else {
                //更新
                indoorManualManager.updateByPrimaryKeySelective(indoorManual);
            }
            jsonMap.put("intId", indoorManual.getIntId());
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }

        return SUCCESS;
    }

    /**
     * 导出模版
     *
     * @return
     */
    public String exportIndoorInputData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        List<IndoorManual> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/indoorManal.xls";
        String fileName = "室分未录入数据.xls";
        try {

            if (checkAllFlag == null || checkAllFlag == 0) {
                if (!Common.isEmpty(ids)) {
                    map.put("ids", ids); //
                } else {
                    map.put("manualFlag", 0);// 未录入的数据
                }
            }

            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
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
            list = indoorManualManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (IndoorManual indoorManual1 : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(indoorManual1.getIntId()));
                cList.add(StringUtil.null2String(indoorManual1.getName()));
                cList.add(StringUtil.null2String(indoorManual1.getCity().getCityName()));
                cList.add(StringUtil.null2String(indoorManual1.getCountry().getCityName()));
                cList.add(StringUtil.null2String(indoorManual1.getLongitude()));
                cList.add(StringUtil.null2String(indoorManual1.getLatitude()));
                cList.add(StringUtil.null2String(indoorManual1.getVendorBtstype()));
                cList.add(StringUtil.null2String(indoorManual1.getBscName()));
                cList.add(StringUtil.null2String(indoorManual1.getBtsName()));
                cList.add(StringUtil.null2String(indoorManual1.getCellSeq()));
                cList.add(StringUtil.null2String(indoorManual1.getPn()));
                cList.add(StringUtil.null2String(indoorManual1.getCi()));
                int manualFlag = indoorManual1.getManualFlag().intValue();
                if (manualFlag != 0) {
                    //补充已经手工填写的数据
                    IndoorManual indoorManual2 = indoorManualManager.selectByPrimaryKey(new Long(indoorManual1.getIntId()));
                    if (indoorManual2 != null) {
                        cList.add(StringUtil.null2String(indoorManual2.getTown()));
                        cList.add(StringUtil.null2String(indoorManual2.getVillage()));
                        cList.add(StringUtil.null2String(indoorManual2.getProp1()));
                        cList.add(StringUtil.null2String(indoorManual2.getProp2()));
                        cList.add(StringUtil.null2String(indoorManual2.getGrade()));
                        cList.add(StringUtil.null2String(indoorManual2.getCoverage()));
                        cList.add(StringUtil.null2String(indoorManual2.getBuildingnum()));
                        cList.add(StringUtil.null2String(indoorManual2.getAddress()));
                        cList.add(StringUtil.null2String(indoorManual2.getDevicenum()));
                        cList.add(StringUtil.null2String(indoorManual2.getMonitorflag()));
                        cList.add(StringUtil.null2String(indoorManual2.getMonitornumber()));
                        cList.add(StringUtil.null2String(indoorManual2.getDeviceaddress()));
                        cList.add(StringUtil.null2String(indoorManual2.getRepeaternum()));
                        cList.add(StringUtil.null2String(indoorManual2.getDrynum()));
                        cList.add(StringUtil.null2String(indoorManual2.getDistributedesc()));
                        cList.add(StringUtil.null2String(indoorManual2.getDistributefac()));
                        cList.add(StringUtil.null2String(indoorManual2.getMonitorflag()));
                        cList.add(StringUtil.null2String(indoorManual2.getMotorarea()));
                        cList.add(StringUtil.null2String(indoorManual2.getMotorright()));
                        cList.add(StringUtil.null2String(indoorManual2.getMeteraddress()));
                        cList.add(StringUtil.null2String(indoorManual2.getConstructiondate()));
                        cList.add(StringUtil.null2String(indoorManual2.getWlanflag()));
                        cList.add(StringUtil.null2String(indoorManual2.getWlanshare()));
                        cList.add(StringUtil.null2String(indoorManual2.getWlancoverage()));
                        cList.add(StringUtil.null2String(indoorManual2.getOwnername()));
                        cList.add(StringUtil.null2String(indoorManual2.getOwnerphone()));
                        cList.add(StringUtil.null2String(indoorManual2.getMonitordevicenum()));
                        cList.add(StringUtil.null2String(indoorManual2.getMonitordeviceids()));
                        cList.add(StringUtil.null2String(indoorManual2.getRemark()));
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
     * 直放站导入模板
     *
     * @return
     */
    public String exportErectTemplate() {
        User user = (User) getSession().getAttribute("user");
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/Erect.xls";
        String fileName = "直放站导入模板.xls";
        List<IndoorManual> list = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);

            //查询条件
            if (checkAllFlag == null || checkAllFlag == 0) {
                if (!Common.isEmpty(ids)) {
                    map.put("ids", ids); //
                }
            }
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
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


            list = indoorManualManager.selectByNoInputErect(map);
            if (list != null) {
                int rowIndex = 1;
                for (IndoorManual indoorManual1 : list) {
                    BigDecimal erectNu = indoorManual1.getRepeaternum();
                    int num = (erectNu == null) ? 0 : erectNu.intValue();
                    for (int i = 0; i < num; i++) {
                        //列
                        List<String> cList = new ArrayList<String>();
                        cList.add(indoorManual1.getName());
                        cList.add((i + 1) + "");
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
                }
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
     * 直放站导入模板
     *
     * @return
     */
    public String exportDryTemplate() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/dry.xls";
        String fileName = "干放站导入模板.xls";
        List<IndoorManual> list = null;
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);

            //查询条件
            //查询条件
            if (checkAllFlag == null || checkAllFlag == 0) {
                if (!Common.isEmpty(ids)) {
                    map.put("ids", ids); //
                }
            }
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
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


            list = indoorManualManager.selectByNoInputDry(map);
            if (list != null) {
                int rowIndex = 1;
                for (IndoorManual indoorManual1 : list) {
                    BigDecimal dryNu = indoorManual1.getDrynum();
                    int num = (dryNu == null) ? 0 : dryNu.intValue();
                    for (int i = 0; i < num; i++) {
                        //列
                        List<String> cList = new ArrayList<String>();
                        cList.add(indoorManual1.getName());
                        cList.add((i + 1) + "");
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
                }
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
    public String importIndoorInputData() {
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
            int update = 0;
            List<String> errorList = new ArrayList<String>();
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseIndoorObj(i, row, errorList);
                if (obj != null) {
                    indoorManualManager.importInsert(obj);
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
    public Map parseIndoorObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getIndoorCoulmnMap();
        Bts bts = null;
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
            map.put("updatetime", new Date());
            map.put("updateuser", user.getIntId());
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

    public IndoorManual getIndoorManual() {
        return indoorManual;
    }

    public void setIndoorManual(IndoorManual indoorManual) {
        this.indoorManual = indoorManual;
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

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
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
