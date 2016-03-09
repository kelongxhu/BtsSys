package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.Bts;
import com.scttsc.business.model.ErectStation;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.ErectStationManager;
import com.scttsc.business.service.IndoorManualManager;
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
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("serial")
public class ErectstationAction extends BaseAction {

    private ErectStationManager erectStationManager;

    private IndoorManualManager indoorManualManager;
    @Autowired
    private BtsManager btsManager;

    private BigDecimal intId;

    private ErectStation erectStation;

    private BigDecimal userId;


    private File file;
    private String fileFileName;
    private String fileContentType;

    /**
     * 室分站点
     *
     * @return
     */
    public String erectStationData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<ErectStation> list = null;
        try {
            if (!Common.isEmpty(intId)) {
                map.put("wybtsintid", intId);
                total = erectStationManager.countByExample(map);
                map.put("sortorder", sortorder);
                list = erectStationManager.selectByExample(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }

    /**
     * 添加直放数据
     * @return
     */
    public String ereAddDate() {
        User user = (User) this.getSession().getAttribute("user");
        erectStation.setUpdateuser(user.getIntId());
        erectStation.setUpdatetime(new Date());
        try {
            if (erectStation.getId() != null) {
                erectStationManager.updateByPrimaryKeySelective(erectStation);
            } else {
                erectStationManager.insert(erectStation);
            }
            jsonMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
        }
        return SUCCESS;
    }

    /**
     * 删除直放数据
     * @return
     */
    public String delete() {
        try {
            String idString = this.getRequest().getParameter("ids");
            String[] ids = idString.split(",");
            for (String temp : ids) {
                BigDecimal id = BigDecimal.valueOf(Long.parseLong(temp));
                erectStationManager.deleteByPrimaryKey(id);
            }
            jsonMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
        }
        return SUCCESS;
    }

    /**
     * 导入直放数据
     *
     * @return
     */
    public String importErctsInputData() {
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
            List<String> errorList = new ArrayList<String>();
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseErectObj(i, row, errorList);
                if (obj != null) {
                    erectStationManager.importInsert(obj);
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
    public Map parseErectObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getErectCoulmnMap();
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
                if ("wybtsname".equals(dataKey)) {
                    Map m = new HashMap();
                    m.put("wybtsName", cellValue);
                    m.put("deleteFlag", 0);
                    List<Bts> btsList = btsManager.selectByMap(m);
                    if (btsList != null && btsList.size() > 0) {
                        Bts bts = btsList.get(0);
                        map.put("wybtsintid", bts.getIntId());
                        map.put("wybtsname", bts.getName());
                        map.put("cityId", bts.getCityId());
                    } else {
                        errorList.add("第" + rowNum + "行：" + "未找到对应室内分布站点");
                        return null;
                    }
                } else {
                    map.put(dataKey, cellValue);
                }
                j++;
            }
            map.put("updateuser", user.getIntId());
            map.put("updatetime", new Date());
            //其他属性
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 直放站导出
     *
     * @return
     */
    public String exportErect() {
        User user = (User) getSession().getAttribute("user");
        String path = FileRealPath.getPath();
        String templatePath = path + "template" + "/ErectDataTemplate.xls";
        String fileName = "直放站.xls";
        List<ErectStation> list = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                    templatePath));
            HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);


            list = erectStationManager.selectByExample(map);
            if (list != null) {
                int rowIndex = 1;
                for (ErectStation erectStation : list) {
                    List<String> cList = new ArrayList<String>();
//                    cList.add(rowIndex + "");
                    cList.add(erectStation.getWybtsname());
                    cList.add(erectStation.getNo() + "");
                    cList.add(erectStation.getType());
                    cList.add(erectStation.getModel());
                    cList.add(erectStation.getFac());
                    cList.add(erectStation.getAddress());
                    cList.add(erectStation.getCoverage());
                    cList.add(erectStation.getMonitorfalg());
                    cList.add(erectStation.getMonitornumber());
                    cList.add(erectStation.getMeteraddress());
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


    public IndoorManualManager getIndoorManualManager() {
        return indoorManualManager;
    }

    public void setIndoorManualManager(IndoorManualManager indoorManualManager) {
        this.indoorManualManager = indoorManualManager;
    }

    public ErectStationManager getErectStationManager() {
        return erectStationManager;
    }

    public void setErectStationManager(ErectStationManager erectStationManager) {
        this.erectStationManager = erectStationManager;
    }

    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public ErectStation getErectStation() {
        return erectStation;
    }

    public void setErectStation(ErectStation erectStation) {
        this.erectStation = erectStation;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
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
