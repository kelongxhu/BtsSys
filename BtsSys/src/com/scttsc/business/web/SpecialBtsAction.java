package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.business.model.WyBtsSpecial;
import com.scttsc.business.service.WyBtsSpecialManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.FileRealPath;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特殊状态站点查询
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午4:52
 */
public class SpecialBtsAction extends BaseAction {

    Logger LOG=Logger.getLogger(SpecialBtsAction.class);
    @Autowired
    private WyBtsSpecialManager wyBtsSpecialManager;

    private Integer type;

    private Integer netType;//网络类型

    private String cityIds;//本地网

    private String countryIds;//本地网

    private Integer state;

    public String special() {
        return SUCCESS;
    }

    public String specialeData() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<WyBtsSpecial> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
            }
            if (!Common.isEmpty(type)) {
                map.put("type", type);
            }
            if(!Common.isEmpty(netType)){
                map.put("netType",netType);
            }
            if (!Common.isEmpty(state)) {
                map.put("state", state);
            }
            total = wyBtsSpecialManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = wyBtsSpecialManager.selectByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    public String specialBtsExport() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<WyBtsSpecial> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/specialBtsTemplate.xls";
        String fileName = "特殊站点列表.xls";
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
            if(!Common.isEmpty(netType)){
                map.put("netType",netType);
            }
            if(!Common.isEmpty(state)){
                map.put("state",state);
            }
            list = wyBtsSpecialManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyBtsSpecial wyBtsSpecial : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                City city = wyBtsSpecial.getCity();
                String cityName = "";
                if (city != null) {
                    cityName = city.getCityName();
                }
                cList.add(StringUtil.null2String(cityName));
                cList.add(StringUtil.null2String(wyBtsSpecial.getName()));
                cList.add(StringUtil.null2String(wyBtsSpecial.getNetTypeStr()));
                cList.add(StringUtil.null2String(wyBtsSpecial.getBscName()));
                cList.add(StringUtil.null2String(wyBtsSpecial.getTypeStr()));
                cList.add(StringUtil.null2String(wyBtsSpecial.getStateStr()));
                cList.add(StringUtil.null2String(wyBtsSpecial.getIntimeStr()));
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNetType() {
        return netType;
    }

    public void setNetType(Integer netType) {
        this.netType = netType;
    }
}
