package com.scttsc.charge.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.charge.dto.FileDto;
import com.scttsc.charge.dto.PayStatistDto;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.model.WyBtsChargeList;
import com.scttsc.charge.service.WyBtsChargeListManager;
import com.scttsc.charge.service.WyBtsChargeManager;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by _think on 2014/11/10.
 */
public class WyBtsChargeListAction extends BaseAction {

    Logger LOG = Logger.getLogger(WyBtsChargeListAction.class);

    private String countryIds;
    private String btsName;
    private String bscName;
    private Integer btsId;
    private Integer btsType;
    private Integer costType;
    private String startTime;
    private String endTime;
    private String contractEndtime;//合同到期
    private BigDecimal money;//缴费金额


    @Autowired
    private WyBtsChargeManager wyBtsChargeManager;

    @Autowired
    private WyBtsChargeListManager wyBtsChargeListManager;

    //缴费清单
    private WyBtsChargeList chargeBill;

    //费用
    private BigDecimal intId;

    private String ids;//缴费ID
    private BigDecimal id;//缴费ID

    //文件上传
    private File file;
    private String fileFileName;
    private String fileContentType;

    /**
     * index of charge Pay
     *
     * @return
     */
    public String pay() {
        return SUCCESS;
    }

    /**
     * charge list of bts
     *
     * @return
     */
    public String wyBtsChargeList() {
        int total = 0;
        List<WyBtsCharge> list = null;
        Map<String, Object> paramMap = buildParamMap();
        try {
            total = wyBtsChargeManager.selectWyBtsChargeCountByMap(paramMap, btsType);
            if (total < pagesize) {
                page = 1;
            }
            paramMap.put("start", (page - 1) * pagesize + 1);
            paramMap.put("pagesize", pagesize);
            list = wyBtsChargeManager.selectWyBtsChargeListByMap(paramMap, btsType);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * charge pay of jsp
     *
     * @return
     */
    public String payAddPage() {
        //基站类型，INT_ID,COST_TYPE
        WyBtsCharge charge = null;
        try {
            if (id != null) {
                chargeBill = wyBtsChargeListManager.selectByPrimaryKey(id);
                charge = wyBtsChargeManager.selectByPrimaryKey(chargeBill.getIntId(), chargeBill.getCostType(), chargeBill.getBtsType());
            } else {
                charge = wyBtsChargeManager.selectByPrimaryKey(intId, costType, btsType);
            }
            this.getRequest().setAttribute("charge", charge);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * upload file
     *
     * @return
     */
    public String uploadFile() {
        String fileName = DateConvert.formatDateToString(new Date(), "yyyyMMddHHmmss") + "_" + this.fileFileName;
        File desFile = new File(StoreUtil.storeTmpDir() + "/" + fileName);//存儲路徑
        FileDto fileDTO = new FileDto();
        try {
            FileUtils.copyFile(file, desFile);
            fileDTO.setUuid(fileName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            fileDTO.fail();
        }
        jsonMap.put("fileDTO", fileDTO);
        return SUCCESS;
    }

    /**
     * charge pay Add
     *
     * @return
     */
    public String payAdd() {
        User user = (User) this.getSession().getAttribute("user");
        try {
            if (StringUtil.isEmpty(chargeBill)) {
                jsonMap.put("result", 0);
                return SUCCESS;
            }
            //将文件从临时目录copy到正式目录...copy 成功清除临时目录文件...
            String proofFile = chargeBill.getProofFile();
            if (!StringUtils.isEmpty(proofFile) && !proofFile.contains(Constants.CHARGE_FILE)) {
                //新上傳文件，如果是更新则路径为/store_file/
                String path = Constants.CHARGE_FILE + File.separator + chargeBill.getIntId();
                String descPath = getRequest().getSession()
                        .getServletContext().getRealPath(path);
                StoreUtil.copyFile(proofFile, descPath);
                //chargeBill.setProofFile(path);
            } else {
                chargeBill.setProofFile(null);//忽略编辑字段
            }
            //是否延时,周期为15号，如果>15则判断延时
            Date payTime = chargeBill.getPayTime();
            Calendar c = Calendar.getInstance();
            c.setTime(payTime);
            int day = c.get(Calendar.DATE);
            if (day > chargeBill.getPayDay()) {
                chargeBill.setIsTimeout((short) 1);//超时
            } else {
                chargeBill.setIsTimeout((short) 0);//不超时
            }
            //缴费方式，默认为人工
            if (Common.isEmpty(chargeBill.getPayType())) {
                chargeBill.setPayType((short) 1);
            }
            if (chargeBill.getId() == null) {
                chargeBill.setInTime(new Date());
                chargeBill.setInUser(user.getIntId().intValue());
                wyBtsChargeListManager.insert(chargeBill);
            } else {
                wyBtsChargeListManager.updateByPrimaryKeySelective(chargeBill);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 删除缴费费用
     *
     * @return
     */
    public String payDel() {
        try {
            List<BigDecimal> idList = new ArrayList<BigDecimal>();
            if (!Common.isEmpty(ids)) {
                String[] idsArr = ids.split(",");
                for (String id : idsArr) {
                    idList.add(new BigDecimal(id));
                }
                wyBtsChargeListManager.deleteByPrimaryKeys(idList);
                jsonMap.put("result", 1);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }


    /**
     * pay detail list
     *
     * @return
     */
    public String payDetail() {
        WyBtsCharge charge = null;
        try {
            charge = wyBtsChargeManager.selectByPrimaryKey(intId, costType, btsType);//
            this.getRequest().setAttribute("charge", charge);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 通过基站intId,costType获取缴费清单
     *
     * @return
     */
    public String payDetailList() {
        List<WyBtsChargeList> chargeList = null;
        try {
            chargeList = wyBtsChargeListManager.selectByBtsId(intId, costType);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(chargeList);
        return SUCCESS;
    }


    /**
     * export the excel tempate for import
     *
     * @return
     */
    public String exportTemplateExcel() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template";
        String fileName = "基站缴费导入模板";
        try {
            switch (costType) {
                case 1:
                    templatePath += "/roomchargeTemplate.xls";
                    fileName += "_房租.xls";
                    break;
                case 2:
                    templatePath += "/roomchargeTemplate.xls";
                    fileName += "_物管.xls";
                    break;
                case 3:
                    templatePath += "/powerchargeTemplate.xls";
                    fileName += "_电费.xls";
                    break;
            }
            Map<String, Object> paramMap = buildParamMap();
            List<WyBtsCharge> list = wyBtsChargeManager.selectWyBtsChargeListByMap(paramMap, btsType);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyBtsCharge btsCharge : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(btsCharge.getBtsName());//基站名称
                cList.add(btsCharge.getIntId() + "");//基站名称
                cList.add(btsCharge.getBtsType() + "");
                cList.add(btsCharge.getCityName());
                cList.add(btsCharge.getCountryName());
                cList.add(btsCharge.getBscName());
                cList.add(btsCharge.getBtsId() + "");
                cList.add(btsCharge.getCostTypeStr());//费用类型
                // 创建行 //创建第rowIndex行
                HSSFRow row = demoSheet.createRow((short) rowIndex);
                for (int j = 0; j < cList.size(); j++) {
                    // 创建第i个单元格
                    HSSFCell cell = row.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellValue(cList.get(j));
                }
                rowIndex++;
            }
            demoSheet.setColumnHidden(1, true);
            demoSheet.setColumnHidden(2, true);
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
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * page of import
     *
     * @return
     */
    public String importPage() {
        return SUCCESS;
    }


    /**
     * import Charge pay Data
     *
     * @return
     */
    public String importPayInputData() {
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
            HSSFRow row = sheet.getRow(0);//首行
            List<String> errorList = new ArrayList<String>();
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseRoomChargeObj(i - 1, row, errorList);
                if (obj != null) {
                    data.add(obj);
                }
            }
            int sucess = wyBtsChargeListManager.insertChargeData(data);
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }


    /**
     * parge row Data
     *
     * @param rowNum
     * @param row
     * @param errorList
     * @param btsType
     * @return
     */
    public Map<String, Object> parseRoomChargeObj(int rowNum, HSSFRow row, List<String> errorList) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = null;
        User user = (User) this.getSession().getAttribute("user");
        try {
            HSSFCell costTypeCell = ExcelUtil.getCell(row, 7);
            String costTypeText = ExcelHelper.getValue(costTypeCell);
            if ("房租".equals(costTypeText)) {
                costType = 1;
            } else if ("物业".equals(costTypeText)) {
                costType = 2;
            } else if ("电费".equals(costTypeText)) {
                costType = 3;
            }
            if (Common.isEmpty(costType)) {
                errorList.add("第" + rowNum + "行,校验失败,费用类型请验证是否是房租、物业、电费");
                return null;
            }
            switch (costType) {
                case 1:
                    coulmnMap = ExcelHelper.getRoomChargeCoulmnMap();
                    break;
                case 2:
                    coulmnMap = ExcelHelper.getRoomChargeCoulmnMap();
                    break;
                case 3:
                    coulmnMap = ExcelHelper.getPowerChargeCoulmnMap();
                    break;
            }
            int j = 0;
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
                } else if ("countyId".equals(dataKey)) {
                    City city = ConstantUtil.getInstance().getCountry(cellValue);
                    if (city != null) {
                        map.put(dataKey, city.getId());
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "未找到对应数据。");
                        return null;
                    }
                } else if ("payType".equals(dataKey) || "costType".equals(dataKey)) {
                    //付费方式
                    map.put("payType", validity.getIndex() + 1);
                } else {
                    //其它
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("costType", costType);
            map.put("inUser", user.getIntId());
            map.put("inTime", new Date());
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }


    /**
     * 缴费查询
     *
     * @return
     */
    public String payQuery() {
        return SUCCESS;
    }


    /**
     * charge list of bts
     *
     * @return
     */
    public String chargeBillList() {
        int total = 0;
        List<WyBtsChargeList> list = null;
        Map<String, Object> paramMap = buildParamMap();
        try {
            total = wyBtsChargeListManager.countByMap(paramMap);
            if (total < pagesize) {
                page = 1;
            }
            paramMap.put("start", (page - 1) * pagesize + 1);
            paramMap.put("pagesize", pagesize);
            list = wyBtsChargeListManager.selectByMap(paramMap);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 缴费
     *
     * @return
     */
    public String payExport() {
        User user = (User) getSession().getAttribute("user");
        int total = 0;
        List<WyBtsChargeList> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template";
        String fileName = "基站缴费清单数据";
        switch (costType) {
            case 1:
                templatePath += "/roomPayDataTemplate.xls";
                fileName += "_房租.xls";
                break;
            case 2:
                templatePath += "/roomPayDataTemplate.xls";
                fileName += "_物业.xls";
                break;
            case 3:
                templatePath += "/powerPayDataTemplate.xls";
                fileName += "_电费.xls";
                break;
        }
        try {
            Map<String, Object> map = buildParamMap();
            total = wyBtsChargeListManager.countByMap(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = wyBtsChargeListManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 2;
            for (WyBtsChargeList chargeList : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex - 1));
                cList.add(chargeList.getBtsName());//基站名称
                cList.add(chargeList.getCityName());
                cList.add(chargeList.getCountryName());
                cList.add(chargeList.getBscName());
                cList.add(chargeList.getBtsId() + "");
                cList.add(chargeList.getCostTypeStr());
                cList.add(chargeList.getMoney() + "");
                cList.add(chargeList.getPayTimeStr());
                cList.add(chargeList.getPayUser());//缴费人员
                cList.add(chargeList.getRemark());//备注
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
//            demoSheet.setColumnHidden(1, true);
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
     * 缴费统计页面
     *
     * @return
     */
    public String payStat() {
        return SUCCESS;
    }

    /**
     * 缴费统计
     *
     * @return
     */
    public String statPay() {
        int total = 0;
        List<PayStatistDto> list = null;
        Map<String, Object> paramMap = buildParamMap();
        try {
            total = wyBtsChargeListManager.countStatisticsPay(paramMap);
            if (total < pagesize) {
                page = 1;
            }
            paramMap.put("start", (page - 1) * pagesize + 1);
            paramMap.put("pagesize", pagesize);
            list = wyBtsChargeListManager.statisticsPay(paramMap);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 根据查询条件导出费用设置基站列表
     *
     * @return
     */
    public String exportChargeSetting() {
        List<WyBtsCharge> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template";
        String fileName = "基站费用设置数据";
        switch (costType) {
            case 1:
                templatePath += "/roomchargeDataTemplate.xls";
                fileName += "_房租.xls";
                break;
            case 2:
                templatePath += "/roomchargeDataTemplate.xls";
                fileName += "_物业.xls";
                break;
            case 3:
                templatePath += "/powerchargeDataTemplate.xls";
                fileName += "_电费.xls";
                break;
        }
        try {
            Map<String, Object> paramMap = buildParamMap();
            list = wyBtsChargeManager.selectWyBtsChargeListByMap(paramMap, btsType);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyBtsCharge charge : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(charge.getBtsName());//基站名称
                cList.add(charge.getCityName());
                cList.add(charge.getCountryName());
                cList.add(charge.getBscName());
                cList.add(charge.getBtsId() + "");
                cList.add(charge.getCostTypeStr());
                if (costType == 1 || costType == 2) {
                    cList.add(charge.getContractStarttimeStr());
                    cList.add(charge.getContractEndtimeStr());
                }
                cList.add(StringUtil.null2String(charge.getMoney()));
                cList.add(StringUtil.null2String(charge.getPayCycle()));//缴费周期
                cList.add(StringUtil.null2String(charge.getPayDay()));//缴费日期
                cList.add(StringUtil.null2String(charge.getAheadDay()));//提前日期
                cList.add(charge.getRemindUser());//备注
                cList.add(charge.getRemindTel());//备注
                cList.add(charge.getRemark());//备注
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
//            demoSheet.setColumnHidden(1, true);
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
     * build query condition
     *
     * @return
     */
    public Map<String, Object> buildParamMap() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(countryIds)) {
            param.put("countryIds", countryIds);
        } else {
            param.put("countryIds", user.getCountryIds());
        }
        if (!StringUtil.isEmpty(btsName)) {
            param.put("btsName", btsName);
        }
        if (!StringUtil.isEmpty(bscName)) {
            param.put("bscName", bscName);
        }
        if (!StringUtil.isEmpty(btsId)) {
            param.put("btsId", btsId);
        }
        if (!StringUtil.isEmpty(costType)) {
            param.put("costType", costType);//费用类型
        }
        if (!StringUtil.isEmpty(btsType)) {
            param.put("btsType", btsType);
        }
        if (!StringUtil.isEmpty(startTime)) {
            param.put("startTime", startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            param.put("endTime", endTime);
        }
        if (!StringUtil.isEmpty(contractEndtime)) {
            param.put("contractEndtime", contractEndtime);
        }
        if (!StringUtil.isEmpty(money)) {
            param.put("money", money);
        }
        return param;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
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

    public Integer getBtsType() {
        return btsType;
    }

    public void setBtsType(Integer btsType) {
        this.btsType = btsType;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public WyBtsChargeList getChargeBill() {
        return chargeBill;
    }

    public void setChargeBill(WyBtsChargeList chargeBill) {
        this.chargeBill = chargeBill;
    }

    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getContractEndtime() {
        return contractEndtime;
    }

    public void setContractEndtime(String contractEndtime) {
        this.contractEndtime = contractEndtime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
