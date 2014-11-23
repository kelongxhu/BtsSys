package com.scttsc.charge.web;

import com.scttsc.admin.model.User;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.business.model.Bts;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.charge.dto.BtsDTO;
import com.scttsc.charge.dto.FileDTO;
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

import javax.mail.Store;
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


    @Autowired
    private WyBtsChargeManager wyBtsChargeManager;

    @Autowired
    private WyBtsChargeListManager wyBtsChargeListManager;

    //缴费清单
    private WyBtsChargeList chargeBill;

    //费用
    private BigDecimal intId;

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
            charge = wyBtsChargeManager.selectByPrimaryKey(intId, costType, btsType);//
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
        User user = (User) this.getSession().getAttribute("user");
        String fileName = DateConvert.formatDateToString(new Date(), "yyyyMMddHHmmss") + "_" + this.fileFileName;
        File desFile = new File(StoreUtil.storeTmpDir() + "/" + fileName);//存儲路徑
        FileDTO fileDTO = new FileDTO();
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
            if (!StringUtil.isEmpty(chargeBill)) {
                //将文件从临时目录copy到正式目录...copy 成功清除临时目录文件...
                String proofFile = chargeBill.getProofFile();
                if (!StringUtils.isEmpty(proofFile)) {
                    String path = Constants.PROOF_FILE + proofFile;
                    String descPath = getRequest().getSession()
                            .getServletContext().getRealPath(path);
                    StoreUtil.copyFile(proofFile, descPath);
                    chargeBill.setProofFile(path);
                }
                //是否延时,周期为15号，如果>15则判断延时
                Date payTime = chargeBill.getPayTime();
                Calendar c = Calendar.getInstance();
                c.setTime(payTime);
                int day = c.get(Calendar.DATE);
                if (day > chargeBill.getPayCycle()) {
                    chargeBill.setIsTimeout((short) 1);//超时
                } else {
                    chargeBill.setIsTimeout((short) 0);//不超时
                }
                //缴费方式，默认为人工
                if(Common.isEmpty(chargeBill.getPayType())){
                    chargeBill.setPayType((short)1);
                }
                chargeBill.setInTime(new Date());
                chargeBill.setInUser(user.getIntId().intValue());
                wyBtsChargeListManager.insert(chargeBill);
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
        List<WyBtsChargeList> chargeList = null;
        WyBtsCharge charge = null;
        try {
            charge = wyBtsChargeManager.selectByPrimaryKey(intId, costType, btsType);//
            chargeList = wyBtsChargeListManager.selectByBtsId(intId, costType);
            this.getRequest().setAttribute("charge", charge);
            this.getRequest().setAttribute("chargeList", chargeList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
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
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);
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
        } catch (IOException e) {
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
            HSSFCell cell = null;
            HSSFRow row = sheet.getRow(1);//首行
            int lastNum = row.getLastCellNum();
            boolean flag = false;
            switch (costType) {
                case 1:
                    if (lastNum != 5) {
                        flag = true;
                    }
                    break;
                case 2:
                    if (lastNum != 5) {
                        flag = true;
                    }
                    break;
                case 3:
                    if (lastNum != 8) {
                        flag = true;
                    }
                    break;
            }
            if (flag) {
                jsonMap.put("result", 2);//导入对应费用类型模板
                return SUCCESS;
            }
            List<String> errorList = new ArrayList<String>();
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseRoomChargeObj(i - 1, row, errorList, btsType,costType);
                if (obj != null) {
                    data.add(obj);
                }
            }
            int sucess = wyBtsChargeListManager.importChargeData(data);
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
    public Map<String, Object> parseRoomChargeObj(int rowNum, HSSFRow row, List<String> errorList, int btsType,int costType) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap =null;
        User user = (User) this.getSession().getAttribute("user");
        try {
            switch (costType) {
                case 1:
                    coulmnMap=ExcelHelper.getRoomChargeCoulmnMap();
                    break;
                case 2:
                    coulmnMap=ExcelHelper.getRoomChargeCoulmnMap();
                    break;
                case 3:
                    coulmnMap=ExcelHelper.getPowerChargeCoulmnMap();
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
                if ("btsName".equals(dataKey)) {
                    Map<String,Object> param=new HashMap<String, Object>();
                    param.put("btsName",cellValue);
                    param.put("cellValue",btsType);
                    BtsDTO btsDTO = wyBtsChargeListManager.selectBtsByMap(param);
                    if (btsDTO != null) {
                        map.put("intId", btsDTO.getIntId());
                        map.put("btsType", btsDTO.getBtsType());
                        map.put("btsName",btsDTO.getName());
                        map.put("btsId",btsDTO.getBtsId());
                        map.put("cityId",btsDTO.getCityId());
                        map.put("countryId",btsDTO.getCountryId());
                        map.put("costType",costType);
                    } else {
                        errorList.add("第" + rowNum + "行:" + validity.getName() + "在" + Constants.BTS_TYPE[btsType - 1] + "基站类型中未查询到该基站");
                        return null;
                    }
                }else if("payType".equals(dataKey)){
                    //付费方式
                   map.put("payType",validity.getIndex()+1);
                } else {
                    //其它
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            //其他属性
            map.put("inUser", user.getIntId());
            map.put("inTime", new Date());
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
        return map;
    }


    /**
     * 缴费查询
     * @return
     */
    public String payQuery(){
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
     * @return
     */
    public String payExport(){
        User user = (User) getSession().getAttribute("user");
        int total = 0;
        List<WyBtsChargeList> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/roomPayDataTemplate.xls";
        String fileName = "基站缴费清单数据.xls";
        try {
            Map<String, Object> map =buildParamMap();
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
                cList.add(chargeList.getBtsId()+"");
                cList.add(chargeList.getCostTypeStr());
                cList.add(chargeList.getMoney()+"");
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
        if(!StringUtil.isEmpty(btsType)){
            param.put("btsType",btsType);
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
}
