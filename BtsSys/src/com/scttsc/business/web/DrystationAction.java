package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.Bts;
import com.scttsc.business.model.DryStation;
import com.scttsc.business.model.ErectStation;
import com.scttsc.business.model.IndoorManual;
import com.scttsc.business.service.DryStationManager;
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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("serial")
public class DrystationAction extends BaseAction {

    private DryStationManager dryStationManager;

    private IndoorManualManager indoorManualManager;

    private DryStation dryStation;

    private File file;


    private String fileFileName;
    private String fileContentType;


    private BigDecimal intId;


    /**
     * 室分站点
     *
     * @return
     */
    public String dryStationData() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<DryStation> list = null;
        try {
            if (!Common.isEmpty(intId)) {
                map.put("wybtsintid", intId);
                total = dryStationManager.countByExample(map);
                map.put("sortorder", sortorder);
                list = dryStationManager.selectByExample(map);
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
     * 增加干放数据
     * @return
     */
    public String dryAddDate() {
        User user = (User) this.getSession().getAttribute("user");
        Bts bts = (Bts) this.getSession().getAttribute("bts");
        dryStation.setUpdatetime(new Date());
        dryStation.setUpdateuser(user.getIntId());
        try {
            if (dryStation.getId() != null) {
                dryStationManager.updateByPrimaryKeySelective(dryStation);
            } else {
                dryStationManager.insertSelective(dryStation);
            }
            jsonMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
        }
        return SUCCESS;
    }

    /**
     * 删除干放数据
     * @return
     */
    public String delete() {
        try {
            String idString = this.getRequest().getParameter("ids");
            String[] ids = idString.split(",");
            for (String temp : ids) {
                BigDecimal id = BigDecimal.valueOf(Long.parseLong(temp));
                dryStationManager.deleteByPrimaryKey(id);
            }
            jsonMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
        }
        return SUCCESS;
    }


    /**
     * 导入干放数据
     *
     * @return
     */
    public String importDryInputData() {
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
            List errorList=new ArrayList();
            for (int i = 1; i < rows; i++) // 从第二行开始取数
            {
                row = sheet.getRow(i);
                Map obj = parseDryObj(i,row,errorList);
                if (obj != null) {
                    dryStationManager.importInsert(obj);
                    sucess++;
                }
            }
            jsonMap.put("result", 1);
            jsonMap.put("sucess", sucess);
            jsonMap.put("errorList",errorList);
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
    public Map parseDryObj(int rowNum, HSSFRow row, List errorList) {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Validity> coulmnMap = ExcelHelper.getDryCoulmnMap();
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
                    m.put("name", cellValue);
                    List<IndoorManual> indoorManuals = indoorManualManager.selectByCons(m);
                    if (indoorManuals != null && indoorManuals.size() > 0) {
                        IndoorManual indoorManual = indoorManuals.get(0);
                        map.put("wybtsintid", indoorManual.getIntId());
                        map.put("wybtsname", indoorManual.getName());
                        map.put("cityId", indoorManual.getCityId());
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
             map.put("updatetime",new Date());
            //其他属性
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }


    /**
       * 干放站导出
       *
       * @return
       */
      public String exportDry() {
          User user = (User) getSession().getAttribute("user");
          String path = FileRealPath.getPath();
          String templatePath = path + "template" + "/DryDataTemplate.xls";
          String fileName = "干放站.xls";
          List<DryStation> list = null;
          Map<String, Object> map = new HashMap<String, Object>();
          try {
              POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(
                      templatePath));
              HSSFWorkbook demoWorkBook = ExcelUtil.getWorkbook(fis);// 得到工作薄
              HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
              HSSFSheet demoSheet = ExcelUtil.getSheet(demoWorkBook, 0);


              list = dryStationManager.selectByExample(map);
              if (list != null) {
                  int rowIndex = 1;
                  for (DryStation erectStation : list) {
                      List<String> cList = new ArrayList<String>();
//                      cList.add(rowIndex + "");
                      cList.add(erectStation.getWybtsname());
                      cList.add(erectStation.getNo() + "");
                      cList.add(erectStation.getCoverage());
                      cList.add(erectStation.getModel());
                      cList.add(erectStation.getFac());
                      cList.add(erectStation.getAddress());
                      cList.add(erectStation.getMonitorflag());
                      cList.add(erectStation.getParentdevice());
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

    public DryStationManager getDryStationManager() {
        return dryStationManager;
    }

    public void setDryStationManager(DryStationManager dryStationManager) {
        this.dryStationManager = dryStationManager;
    }

    public DryStation getDryStation() {
        return dryStation;
    }

    public void setDryStation(DryStation dryStation) {
        this.dryStation = dryStation;
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


    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }
}
