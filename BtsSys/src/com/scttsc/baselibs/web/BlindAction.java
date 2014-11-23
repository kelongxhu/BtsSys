package com.scttsc.baselibs.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.business.model.Bts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.SimpleDateFormat;
import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.VitoLib;
import com.scttsc.baselibs.model.WyBlind;
import com.scttsc.baselibs.service.BlindManager;
import com.scttsc.baselibs.service.VitoLibManager;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.business.util.Validity;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;

public class BlindAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WyBlind wyBlind;
	
	private String countryIds;// 区县集合
	
	private String ids;//ID集合
	
	private Long id;
	
	//导入参数
	private File file;
	private String fileFileName;
	
	@Autowired
	private BlindManager blindManager;
	
	@Autowired
	private CityManager cityManager;
	
	@Autowired
	private VitoLibManager vitoLibManager;
	
	public String blind()throws Exception {
		return SUCCESS;
	}
	
	public String blindImport()throws Exception {
		return SUCCESS;
	}
	
    public String blindData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyBlind> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            total = blindManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = blindManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }
    
    public String addBlind(){
    	try {
    		BigDecimal countryId = null;// 区县ID
    		User user = (User) this.getSession().getAttribute("user");
    		if (!Common.isEmpty(wyBlind)) {
                countryId = wyBlind.getCountyId();
            }
    		if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                wyBlind.setCityId(new BigDecimal(city.getParentId()));
            }
        	wyBlind.setInUser(new BigDecimal(user.getIntId()));
        	wyBlind.setInTime(new Date());
        	blindManager.insert(wyBlind);
        	jsonMap.put("result", 1);
		} catch (Exception e) {
			 e.printStackTrace();
	         jsonMap.put("result", 0);
		}
    	
    	return SUCCESS;
    }
    
    public String deleteBlind() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            blindManager.deleteByDeleteFlag(map);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
    	return SUCCESS;
    }
    
    public String blindInfo() {
    	try {
    		wyBlind = blindManager.getById(id);
            City city=cityManager.getById(wyBlind.getCityId().longValue());
            City country=cityManager.getById(wyBlind.getCountyId().longValue());
            VitoLib vitoLib = vitoLibManager.getById(wyBlind.getVitoId().longValue());
            this.getRequest().setAttribute("city",city);
            this.getRequest().setAttribute("country",country);
            this.getRequest().setAttribute("vitoLib",vitoLib);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return SUCCESS;
    }
    
    public String blindEdit() {
    	try {
    		wyBlind = blindManager.getById(id);
    		City country=cityManager.getById(wyBlind.getCountyId().longValue());
    		this.getRequest().setAttribute("country",country);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return SUCCESS;
    }
    
    public String editBlind() {
    	try {
    		BigDecimal countryId = null;// 区县ID
    		if (!Common.isEmpty(wyBlind)) {
                countryId = wyBlind.getCountyId();
            }
    		if (!Common.isEmpty(countryId)) {
                City city = cityManager.getById(countryId.longValue());
                wyBlind.setCityId(new BigDecimal(city.getParentId()));
            }
    		blindManager.update(wyBlind);
        	jsonMap.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
	        jsonMap.put("result", 0);			
		}
    	
    	return SUCCESS;
    }
    
    public String exportBlindTemplate() {
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/blindTemplate.xls";
        String fileName = "盲点库导入模板.xls";
        try {
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
            e.printStackTrace();
        }
        return null;
    }
    
    public String importBlindInputData() {
        int sucess = 0;
        List<String> errorList = new ArrayList<String>();
        try {
            File desFile = new File(getRequest().getSession()
                    .getServletContext().getRealPath("/uploadFiles")
                    + "/" + this.fileFileName);
            FileUtils.copyFile(file, desFile);
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(desFile.getPath())); // 得到excel对象
            HSSFSheet sheet = workbook.getSheetAt(0); // 得到第一个sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 得到行数
            HSSFRow row = null;
            for (int i = 2; i < rows; i++){ // 从第二行开始取数
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map<String, Object> obj = parseBlindObj(i+1, row, errorList);
                if (obj != null) {
                    blindManager.importInsert(obj);
                    sucess++;
                }
            }
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList", errorList);
        }
        return SUCCESS;
    }
    
    public Map<String, Object> parseBlindObj(int rowNum, HSSFRow row, List<String> errorList) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getBlindCoulmnMap();
        User user = (User) this.getSession().getAttribute("user");
        try {
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
                if(StringUtils.isEmpty(cellValue)){
                    j++;
                    continue;
                }
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
                } else if("vitoId".equals(dataKey)){
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("name",cellValue);
                        List<VitoLib> vitoLibList = vitoLibManager.getByMap(paramMap);
                        if(vitoLibList!=null&&vitoLibList.size()>0){
                            VitoLib vitoLib=vitoLibList.get(0);
                            map.put(dataKey,vitoLib.getId());
                        }else{
                            errorList.add("第" + rowNum + "行:" + validity.getName() + "未在农村库中找到对应数据。");
                            return null;
                        }
                } else if("isPlan".equals(dataKey)||"status".equals(dataKey)
                		||"blindType1".equals(dataKey)||"blindType2".equals(dataKey)
                		||"is1x".equals(dataKey)||"isLte".equals(dataKey)
                		||"isDo".equals(dataKey)||"gridType".equals(dataKey)
                		||"solType".equals(dataKey)||"assistType".equals(dataKey)){
                	map.put(dataKey, validity.getIndex());
                }else if("planSolutionTime".equals(dataKey)||"realSolutionTime".equals(dataKey)){
                	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                	map.put(dataKey, format.parse(cellValue));
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
     * 导出盲点库
     *
     * @return
     */
    public String exportBlindData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyBlind> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/blindDataTemplate.xls";
        String fileName = "盲点库数据.xls";
        try {
            if (!Common.isEmpty(countryIds)) {
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            total = blindManager.countByConds(map);
            map.put("start", 0);
            map.put("pagesize", (total + 1));
            list = blindManager.getByConds(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyBlind wyBlind1 : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(wyBlind1.getCityName()));
                cList.add(StringUtil.null2String(wyBlind1.getCountryName()));
                cList.add(StringUtil.null2String(wyBlind1.getName()));
                cList.add(StringUtil.null2String(wyBlind1.getVitoName()));
                cList.add(StringUtil.null2String(wyBlind1.getLongitude()));
                cList.add(StringUtil.null2String(wyBlind1.getLatitude()));
                cList.add(StringUtil.null2String(wyBlind1.getAddress()));
                cList.add(StringUtil.null2String(wyBlind1.getLandMark()));//标识性建筑
                //是否有规划站
                Short isPlan=wyBlind1.getIsPlan();
                cList.add(isPlan==null?"":ExcelHelper.ISHAS[isPlan]);
                //盲点状态
                cList.add(ExcelHelper.STATUS[wyBlind1.getStatus()]);
                cList.add(ExcelHelper.GRIDTYPE[wyBlind1.getGridType()]);//网咯类型
                cList.add(ExcelHelper.BLINDTYPE1[wyBlind1.getBlindType1()]);//网咯类型
                //盲点类型2
                Short bindType2=wyBlind1.getBlindType2();
                cList.add((bindType2==null)?"":ExcelHelper.BLINDTYPE2[bindType2]);
                cList.add(ExcelHelper.ISNO[wyBlind1.getIs1x()]);
                cList.add(ExcelHelper.ISNO[wyBlind1.getIsDo()]);
                cList.add(ExcelHelper.ISNO[wyBlind1.getIsLte()]);
                cList.add(StringUtil.null2String(wyBlind1.getCausesBy()));
                cList.add(ExcelHelper.SOLTYPE[wyBlind1.getSolType()]);
                Short assistType=wyBlind1.getAssistType();
                cList.add((assistType==null)?"":ExcelHelper.ASSISTTYPE[assistType]);
                cList.add(StringUtil.null2String(wyBlind1.getPlanSolutionTmStr()));
                cList.add(StringUtil.null2String(wyBlind1.getRealSolutionTmStr()));
                cList.add(StringUtil.null2String(wyBlind1.getRemarks()));

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
    
	public String getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(String countryIds) {
		this.countryIds = countryIds;
	}
	
	public void setBlindManager(BlindManager blindManager) {
		this.blindManager = blindManager;
	}
	
	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}

	public void setVitoLibManager(VitoLibManager vitoLibManager) {
		this.vitoLibManager = vitoLibManager;
	}

	public WyBlind getWyBlind() {
		return wyBlind;
	}

	public void setWyBlind(WyBlind wyBlind) {
		this.wyBlind = wyBlind;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
	
}