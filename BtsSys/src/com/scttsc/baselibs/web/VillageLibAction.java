package com.scttsc.baselibs.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.WyBlind;
import com.scttsc.baselibs.model.WyLibVillage;
import com.scttsc.baselibs.service.VillageLibManager;
import com.scttsc.business.util.ExcelHelper;
import com.scttsc.common.util.*;
import com.scttsc.common.web.BaseAction;
import org.apache.commons.lang.StringUtils;
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
 * Created by _think on 2014/12/6.
 */
public class VillageLibAction extends BaseAction {

    Logger LOG = Logger.getLogger(VillageLibAction.class);
    @Autowired
    private VillageLibManager villageLibManager;
    @Autowired
    private CityManager cityManager;

    private String countryIds;

    private String town;

    private String village;

    private Integer countryId;

    private WyLibVillage wyLibVillage;

    private Long id;



    public String village() {
        return SUCCESS;
    }

    /**
     * 乡镇农村库
     *
     * @return
     */
    public String villageData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> param = buildMap();
        int total = 0;
        List<WyLibVillage> list = null;
        try {
            total = villageLibManager.countByMap(param);
            if (total < pagesize) {
                page = 1;
            }
            param.put("start", (page - 1) * pagesize + 1);
            param.put("pagesize", pagesize);
            list = villageLibManager.selectByMap(param);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 获取城镇列表
     * @return
     */
    public String getTownList() {
        try {
            List<Map> townList = villageLibManager.selectTownByCountryId(countryId);
            setJsonMapRows(townList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 根据区县、镇获取镇或者农村
     *
     * @return
     */
    public String getVillageLib() {
        Map<String, Object> param = new HashMap<String, Object>();
        if (!Common.isEmpty(countryId)) {
            param.put("countryId", countryId);
        }
        if (!Common.isEmpty(town)) {
            town = Common.decodeURL(town).trim();
            param.put("town", town);
        }
        try {
            List<WyLibVillage> villageList = villageLibManager.selectByConds(param);
            setJsonMapRows(villageList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 构建查询条件
     * @return
     */
    public Map<String, Object> buildMap() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(countryIds)) {
            param.put("countryIds", countryIds);
        } else {
            param.put("countryIds", user.getCountryIds());
        }
        if (!StringUtils.isEmpty(town)) {
            town = Common.decodeURL(town).trim();
            param.put("town", town);
        }
        if (!StringUtils.isEmpty(village)) {
            village = Common.decodeURL(village).trim();
            param.put("village", village);
        }
        return param;
    }


    /**
     * 编辑乡镇农村页面
     * @return
     */
    public String editVillagePage(){
        try {
            wyLibVillage=villageLibManager.selectByPrimaryKey(id);
            if(!Common.isEmpty(wyLibVillage)){
                City city=cityManager.getById(wyLibVillage.getCityId().longValue());
                wyLibVillage.setCity(city);
                City country=cityManager.getById(wyLibVillage.getCountryId().longValue());
                wyLibVillage.setCountry(country);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return SUCCESS;
    }
    /**
     * 编辑乡镇农村库
     *
     * @return
     */
    public String editVillage() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            villageLibManager.updateByPrimaryKeySelective(wyLibVillage);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }



    /**
     * 导出盲点库
     *
     * @return
     */
    public String exportVillageData() {
        User user = (User) getSession().getAttribute("user");
        Map<String, Object> map = buildMap();
        int total = 0;
        List<WyLibVillage> list = null;
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/villageDataTemplate.xls";
        String fileName = "乡镇农村库数据.xls";
        try {
            list = villageLibManager.selectByMap(map);
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引
            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (WyLibVillage libVillage : list) {
                List<String> cList = new ArrayList<String>();
                cList.add(StringUtil.null2String(rowIndex));
                cList.add(StringUtil.null2String(libVillage.getCityName()));
                cList.add(StringUtil.null2String(libVillage.getCountryName()));
                cList.add(StringUtil.null2String(libVillage.getTown()));
                cList.add(StringUtil.null2String(libVillage.getVillage()));
                cList.add(StringUtil.null2String(libVillage.getVillageName()));
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public WyLibVillage getWyLibVillage() {
        return wyLibVillage;
    }
    public void setWyLibVillage(WyLibVillage wyLibVillage) {
        this.wyLibVillage = wyLibVillage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
