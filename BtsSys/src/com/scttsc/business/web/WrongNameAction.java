package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.business.model.WrongName;
import com.scttsc.business.service.WrongNameManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午4:52
 */
public class WrongNameAction extends BaseAction {
    private Logger logger = Logger.getLogger(WrongNameAction.class);
    private WrongNameManager wrongNameManager;

    private Integer type;

    private String cityIds;//本地网

    private String countryIds;//本地网

    private Integer netId;//网络类型

    public String wrongName() {
        return SUCCESS;
    }

    /**
     * 错误数据
     * @return
     */
    public String wrongNameData() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<WrongName> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
            }
            if (!Common.isEmpty(type)) {
                map.put("type", type);
            }
            if(!Common.isEmpty(netId)){
                map.put("netType",netId);
            }
            total = wrongNameManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = wrongNameManager.selectByMap(map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 错误命名数据导出
     * @return
     */
    public String wrongNameExport() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        List<WrongName> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/wrongNameTemplate.xls";
        String fileName = "错误小区命名列表.xls";
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
            }
            //查询条件
            if (!Common.isEmpty(type)) {
                map.put("type", type);
            }
            if(!Common.isEmpty(netId)){
                map.put("netType",netId);
            }
            list = wrongNameManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WrongName wrongName : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                City city = wrongName.getCity();
                String cityName = "";
                if (city != null) {
                    cityName = city.getCityName();
                }
                cList.add(StringUtil.null2String(cityName));
                cList.add(StringUtil.null2String(wrongName.getCellName()));
                cList.add(StringUtil.null2String(wrongName.getWrongMsg()));
                int netType=wrongName.getNetType();
                String netTypeName="";
                if(netType==1){
                    netTypeName="CDMA";
                }else{
                    netTypeName="LTE";
                }
                cList.add(netTypeName);
                BigDecimal type = wrongName.getType();
                String typeName = "";
                if (type != null) {
                    int value = type.intValue();
                    if (value == 1) {
                        typeName = "错误小区";
                    } else if (value == 2) {
                        typeName = "错误BBU";
                    }
                }
                cList.add(StringUtil.null2String(typeName));
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


    public void setWrongNameManager(WrongNameManager wrongNameManager) {
        this.wrongNameManager = wrongNameManager;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public Integer getNetId() {
        return netId;
    }
    public void setNetId(Integer netId) {
        this.netId = netId;
    }
}
