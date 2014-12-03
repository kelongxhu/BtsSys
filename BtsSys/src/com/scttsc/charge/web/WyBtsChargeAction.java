package com.scttsc.charge.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import com.scttsc.admin.model.User;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.service.WyBtsChargeManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.util.DateConvert;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StoreUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;

/**
 * Created by _think on 2014/11/10.
 */
public class WyBtsChargeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(WyBtsChargeAction.class);
	
	private static String GROUP_CODE = "CHARGE_REMIND_CYCLE";
	
	//参数
	private BigDecimal intId;
	private String countryIds;
    private Integer btsType;
    private Integer costType;
    
    //高级检索参数
    private String btsName;
    private String bscName;
    private Integer btsId;
    
    private String ids;
    private String fileName;
    private String fileFileName;
    
    private File file;
    
    private WyBtsCharge wyBtsCharge; 
    
    @Autowired
    private WyBtsChargeManager wyBtsChargeManager;
	
	/**
	 * 费用设置首页
	 * @return
	 * @throws Exception
	 */
	public String chargeSetting() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 查询数据
	 * @return
	 * @throws Exception
	 */
    public String query() throws Exception {
        int total = 0;
        List<WyBtsCharge> list = null;
        Map<String, Object> paramMap = buildParamMap();
        try {
            total = wyBtsChargeManager.selectWyBtsChargeSettingCountByMap(paramMap, btsType);
            if (total < pagesize) {
                page = 1;
            }
            paramMap.put("start", (page - 1) * pagesize + 1);
            paramMap.put("pagesize", pagesize);
            list = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }
    
    /**
     * 费用设置
     * @return
     * @throws Exception
     */
    public String setting()throws Exception {
    	try {
			Map<String, Object> paramMap = buildParamMap();
			List<WyBtsCharge> list = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
			getRequest().setAttribute("WyBtsCharge", list.get(0));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }
    
    public String doChargeSetting() throws Exception{
    	try {
    		copyAttachment(wyBtsCharge.getCostType(), wyBtsCharge.getIntId());
    		
    		initChargeProperties(wyBtsCharge);
    		wyBtsChargeManager.inserOrUpdateChargeSetting(wyBtsCharge);
    		jsonMap.put("result", 1);
		} catch (Exception e) {
			jsonMap.put("result", 0);
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }

	private void initChargeProperties(WyBtsCharge charge) {
		User user = (User) this.getSession().getAttribute("user");
		short aheadDay = (short)ConstantUtil.getInstance().getCons(GROUP_CODE, String.valueOf(charge.getCostType())).getCode();
		
		charge.setInUser(new BigDecimal(user.getIntId()));
		charge.setAheadDay(aheadDay);
		if(3 != charge.getCostType()){
			charge.setPayType((short)1);
		}
	}
    
    public String deleteChargeSetting() throws Exception{
    	try {
    		if(!StringUtil.isEmpty(ids)){
    			//delete record
    			wyBtsChargeManager.deleteChargeSetting(ids, costType);
    			//delete attachment
    			deleteAttachment(ids, costType);
    		}
			
    		jsonMap.put("result", 1);
		} catch (Exception e) {
			jsonMap.put("result", 0);
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }
    
    public String btsChargeInfo() throws Exception {
    	try {
    		Map<String, Object> paramMap = buildParamMap();
    		List<WyBtsCharge> chargeList = wyBtsChargeManager.selectBtsChargeSettingListByMap(paramMap);
    		List<WyBtsCharge> btsList = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
			getRequest().setAttribute("bts", btsList.get(0));
    		getRequest().setAttribute("btsChargeList", chargeList);    	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }
    
    /**
     * 下载附件
     * @return
     * @throws Exception
     */
    public String downloadAttachment() throws Exception {
    	OutputStream outputStream = null;
    	HttpServletResponse resp = getResponse();
    	ServletOutputStream servletOutputStream = resp.getOutputStream();
        try {
        	String fileType = getRequest().getParameter("fileType")==null?"contract":getRequest().getParameter("fileType").trim();
        	String attachmentName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
        	String path = getAttachmentPath(intId, fileType) + File.separator + attachmentName;
        	File file = new File(path);
        	InputStream fis = new BufferedInputStream(new FileInputStream(path));
        	byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            
            // 设置Response
            resp.reset();
            // 不读缓存
            resp.setHeader("Cache-Control", "no-store");
            resp.setHeader("Pragrma", "no-cache");
            resp.setHeader("Content-Length", "" + file.length());
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(attachmentName.getBytes("GBK"), "iso-8859-1"));
            resp.setDateHeader("Expires", 0);
            resp.setContentType("application/octet-stream");

            outputStream = new BufferedOutputStream(servletOutputStream);
            outputStream.write(buffer);
            outputStream.flush();
            servletOutputStream.flush();
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        }finally{
        	if(servletOutputStream != null){
        		servletOutputStream.close();
        	}
        	if(outputStream != null){
        		outputStream.close();
        	}
        }
        return null;
    }
    
    /**
     * 导出bts数据
     * @return
     * @throws Exception
     */
	public String exportBtsData() throws Exception{
        List<WyBtsCharge> wyBtsCharges = null;
        ServletOutputStream servletOutputStream = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template/" + (costType == 3? "btsPowerChargeTemplate.xls" : "btsChargeTemplate.xls");
        String fileName = "基站费用待录入";
        fileName = fileName + (costType == 1?"_房租.xls":costType == 2?"_物业.xls":"_电费.xls");
    	try {
    		Map<String, Object> paramMap = buildParamMap();
    		wyBtsCharges = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
    		POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(templatePath));
            HSSFWorkbook workBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(workBook);
            HSSFSheet sheet = ExcelUtil.getSheet(workBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 2;
            for (WyBtsCharge charge : wyBtsCharges) {
            	if(StringUtil.isEmpty(charge.getRemindUser())){
            		List<String> columnList = new ArrayList<String>();
            		columnList.add(StringUtil.null2String(rowIndex - 1));
            		columnList.add(StringUtil.null2String(charge.getIntId()));
            		columnList.add(StringUtil.null2String(charge.getBtsType()));
            		columnList.add(StringUtil.null2String(charge.getCostType()));
            		columnList.add(StringUtil.null2String(charge.getBtsName()));
            		columnList.add(StringUtil.null2String(charge.getCityName()));
            		columnList.add(StringUtil.null2String(charge.getCountryName()));
            		columnList.add(StringUtil.null2String(charge.getBscName()));
            		columnList.add(StringUtil.null2String(charge.getBtsId()));
            		columnList.add(StringUtil.null2String(charge.getCostTypeStr()));
            		// 创建行 //创建第rowIndex行
            		HSSFRow row = sheet.createRow(rowIndex - 1);
            		for (int j = 0; j < columnList.size(); j++) {
            			// 创建第i个单元格
            			HSSFCell cell = row.createCell(j);
            			cell.setCellStyle(style);
            			cell.setCellValue(columnList.get(j));
            		}
            		rowIndex++;
            	}
            }
            sheet.setColumnHidden(1, true);
            sheet.setColumnHidden(2, true);
            sheet.setColumnHidden(3, true);
            
            HttpServletResponse resp = getResponse();
            servletOutputStream = resp.getOutputStream();
            // 设置Response
            resp.reset();
            // 不读缓存
            resp.setHeader("Cache-Control", "no-store");
            resp.setHeader("Pragrma", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType("application/msexcel");
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "iso-8859-1"));
            sheet.setGridsPrinted(true);
            workBook.write(servletOutputStream);

            // 清空流
            servletOutputStream.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			if(servletOutputStream != null){
				servletOutputStream.close();
			}
		}
    	return null;
    }
    
    /**
     * @return
     * @throws Exception
     */
    public String importCharges() throws Exception {
    	return SUCCESS;
    }
    
    public String importChargeData() throws Exception {
        try {
            User user = (User) this.getSession().getAttribute("user");
            String fileName = DateConvert.formatDateToString(new Date(), "yyyyMMddHHmmss") + "_" + user.getName() + "_" + fileFileName;
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + fileName);
            FileUtils.copyFile(file, desFile);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(desFile.getPath())); // 得到excel对象
            HSSFSheet sheet = workbook.getSheetAt(0); // 得到第一个sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 得到行数
            HSSFRow row = null;
            List<String> errorList = new ArrayList<String>();
            // 从第二行开始取数
            List<Map<String, String>> chargeMapList = new ArrayList<Map<String,String>>();
            for (int i = 1; i < rows; i++) {
                row = sheet.getRow(i);
            	Map<String, String> chargeMap = parseChargeMap(i+1, 9, row, errorList);
            	if(null != chargeMap){
        			//提醒天数
        			short aheadDay = (short)ConstantUtil.getInstance().getCons(GROUP_CODE, chargeMap.get("costType")).getCode();
        			if(chargeMap.containsKey("balance")&&StringUtil.isEmpty(chargeMap.get("balance"))){
        				chargeMap.remove("balance");
        			}
        			chargeMap.put("aheadDay", aheadDay+"");
        			chargeMap.put("inUser", user.getIntId()+"");
            		chargeMapList.add(chargeMap);
            	}
            }
            
            wyBtsChargeManager.insertChargeSetting(chargeMapList);
            jsonMap.put("result", 1);
            jsonMap.put("sucess", chargeMapList.size());
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            jsonMap.put("result", 0);
        }
    	return SUCCESS;
    }
    
	private Map<String, String> parseChargeMap(int rowNum, int cloumnStart, HSSFRow row, List<String> errorList) throws Exception {
    	Map<String, String> map = parseBaseMap(row);
        Map<String, Validity> coulmnMap = "3".equals(map.get("costType"))?ExcelHelper.getBtsPowerChargeCoulmnMap():ExcelHelper.getBtsChargeCoulmnMap();
        try {
            int j = cloumnStart;
            for (String dataKey : coulmnMap.keySet()) {
                Validity validity = coulmnMap.get(dataKey);
                HSSFCell cell = row.getCell(++j);//Excel
                String cellValue = cell != null?cellValue = ExcelHelper.getValue(cell, validity.getDateFormater()):"";
                if (!validity.check(cellValue)) {
                    //未经过校验，保存该列校验失败原因,该行不在导入
                    errorList.add("第" + rowNum + "行:" + "校验失败," + validity.getMsg());
                    return null;
                }
                if("payType".equals(dataKey)){
                	map.put(dataKey, String.valueOf(validity.getIndex()+1));
                }else{
                	map.put(dataKey, cellValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add("第" + rowNum + "行:" + "程序解析异常...");
            return null;
        }
    	
    	return map;
    }
    
    /**
     * 
     * @param rowNum
     * @param row
     * @return
     * @throws Exception
     */
	private Map<String, String> parseBaseMap(HSSFRow row) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			HSSFCell cell = null;
			String[] properties = new String[] { "intId", "btsType", "costType" };
			for (int i = 0; i < properties.length; i++) {
				cell = row.getCell(i + 1);
				map.put(properties[i], ExcelHelper.getValue(cell));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}
    
    /**
     * 获取附件路径
     * @param costType
     * @param intId
     * @return
     * @throws Exception
     */
    private String getAttachmentPath(BigDecimal intId, String fileType) throws Exception{
    	StringBuffer sb = new StringBuffer();
    	String path = getRequest().getSession().getServletContext().getRealPath(Constants.CHARGE_FILE);
    	sb.append(path).append(File.separator).append(fileType)
    				   .append(File.separator).append(intId);
    	return sb.toString();
    }
    
    /**
     * 
     * @param fileName
     * @throws Exception
     */
    private void deleteAttachment(String ids, int costType) throws Exception{
    	for(String id : ids.split(",")){
        	String path = getAttachmentPath(new BigDecimal(id), "contract");
        	StoreUtil.deleteDir(path);
    	}
    }
	
    /**
     * 拷贝附件
     * @throws Exception
     */
    private void copyAttachment(int costType, BigDecimal intId) throws Exception{
    	//将文件从临时目录copy到正式目录...copy 成功清除临时目录文件...
        String contractFile = wyBtsCharge.getContractFile();
        if (!StringUtils.isEmpty(contractFile)) {
            String descPath = getAttachmentPath(intId, "contract");
            StoreUtil.copyFile(contractFile, descPath);
        }
    }
    
    /**
     * 组装查询条件
     * @return
     */
    private Map<String, Object> buildParamMap() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(countryIds)) {
            param.put("countryIds", countryIds);
        } else {
            param.put("countryIds", user.getCountryIds());
        }
        if (!StringUtil.isEmpty(btsName)) {
            param.put("btsName", Common.decodeURL(btsName).trim());
        }
        if (!StringUtil.isEmpty(bscName)) {
            param.put("bscName", Common.decodeURL(bscName).trim());
        }
        if (!StringUtil.isEmpty(btsId)) {
            param.put("btsId", btsId);
        }
        if(!StringUtil.isEmpty(btsType)){
        	param.put("btsType", btsType);
        }
        if(!StringUtil.isEmpty(intId)){
        	param.put("intId", intId);
        	param.remove("countryIds");
        }
        if(!StringUtil.isEmpty(costType)){
        	param.put("costType", costType);
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

	public BigDecimal getIntId() {
		return intId;
	}

	public void setIntId(BigDecimal intId) {
		this.intId = intId;
	}
	
	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	public void setWyBtsChargeManager(WyBtsChargeManager wyBtsChargeManager) {
		this.wyBtsChargeManager = wyBtsChargeManager;
	}

	public WyBtsCharge getWyBtsCharge() {
		return wyBtsCharge;
	}

	public void setWyBtsCharge(WyBtsCharge wyBtsCharge) {
		this.wyBtsCharge = wyBtsCharge;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
}