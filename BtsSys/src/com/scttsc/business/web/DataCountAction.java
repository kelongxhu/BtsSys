package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.ColumnsConfig;
import com.scttsc.business.service.*;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: 隆科
 * Date: 13-7-23
 * Time: 上午11:01
 */
public class DataCountAction extends BaseAction {

    private Integer typeId;

    private String ids;//统计字段列ID

    private String cityIds;//所选本地网

    private String cnNames;//默认统计字段中文名称
    private String enNames;//默认统计字段英文名称

    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private BbuManager bbuManager;
    @Autowired
    private IndoorManualManager indoorManualManager;
    @Autowired
    private CellManager cellManager;
    @Autowired
    private TunelManualManager tunelManualManager;
    @Autowired
    private TunelManager tunelManager;

    /**
     * 统计页面
     *
     * @return
     */
    public String count() {
        return SUCCESS;
    }

    /**
     * 弹出选择统计字段框
     *
     * @return
     */
    public String selectColumns() {
        return SUCCESS;
    }

    /**
     * 根据数据类型、统计字段动态生成统计结果
     *
     * @return
     */
    public String countResult() {
        User user = (User) this.getSession().getAttribute("user");
        List<ColumnsConfig> columnsConfigList = null;
        List<String> columnsList = new ArrayList<String>();//存储统计字段
        List<Map> list = null;//返回结果
        int total = 0;
        Map record = new HashMap();//组装查询条件
        StringBuilder sqlsb = new StringBuilder();
        try {
            Map map = new HashMap();
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            columnsConfigList = templateManager.selectColumnsByConds(map);
            if (columnsConfigList != null) {
                for (ColumnsConfig columnsConfig : columnsConfigList) {
                    sqlsb.append(columnsConfig.getEnName() + ",");
                }
            }
            //分组条件
            record.put("sqlsb", sqlsb.substring(0, sqlsb.length() - 1).toString());

            //组装排序条件
//            map.put("sortname", sortname);
//            map.put("sortorder", sortorder);

            if (!Common.isEmpty(sortname) && !Common.isEmpty(sortorder)) {
                record.put("orderCondition", sortname + " " + sortorder);
            }

            //用户所属本地网
            if (!Common.isEmpty(cityIds)) {
                record.put("cityIds", cityIds);
            } else {
                record.put("cityIds", user.getCityIds());
            }

            if (!Common.isEmpty(typeId)) {
                switch (typeId.intValue()) {
                    case 1:
                        //室外覆盖站点
                        total = btsManager.countBtsGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = btsManager.selectBtsGroupByColumns(record);
                        break;
                    case 2:
                        //bbu站点
                        total = bbuManager.countBbuGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = bbuManager.selectBbuGroupByColumns(record);
                        break;
                    case 3:
                        //室内分布小区
                        total = indoorManualManager.countIndoorDataGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = indoorManualManager.selectIndoorDataGroupByCoulmns(record);
                        break;
                    case 4:
                        //室外覆盖小区
                        total = cellManager.countCellGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = cellManager.selectCellGroupByColumns(record);
                        break;
                    case 5:
                        //纯bbu统计
                        record.put("isShare", 0);
                        record.put("bbuType", "1,2");
                        total = bbuManager.countBbuGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = bbuManager.selectBbuGroupByColumns(record);
                        break;

                    //隧道覆盖小区
                    case 6:
                        total = tunelManager.countWytunelCellGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = tunelManager.selectWyTunelCellGroupByColumns(record);
                        break;
                    case 7:
                        //室分站点
                        total = btsManager.countWyIndoorBtsGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = btsManager.selectWyIndoorBtsGroupByColumns(record);
                        break;
                    case 8:
                        //隧道站点
                        total =tunelManager.countWytunelGroupByColumns(record);
                        if (total < pagesize) {
                            page = 1;
                        }
                        record.put("start", (page - 1) * pagesize + 1);
                        record.put("pagesize", pagesize);
                        list = tunelManager.selectWyTunelGroupByColumns(record);
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 导出按字段统计结果
     *
     * @return
     */
    public String exportCountResult() {
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<Map> list = null;
        String fileName = "";
        try {
            List<ColumnsConfig> columnsConfigList = null;
            List<String> headerList = new ArrayList<String>();//存储统计字段
            List<String> columnsList = new ArrayList<String>();
            //组装查询条件，
            Map record = new HashMap();
            StringBuilder sqlsb = new StringBuilder();
            Map map = new HashMap();
            if (!Common.isEmpty(ids)) {
                map.put("ids", ids);
            }
            columnsConfigList = templateManager.selectColumnsByConds(map);
            if (columnsConfigList != null) {
                for (ColumnsConfig columnsConfig : columnsConfigList) {
                    sqlsb.append(columnsConfig.getEnName() + ",");
                    headerList.add(columnsConfig.getCnName());  //组装表头
                    columnsList.add(columnsConfig.getEnName());//组装字段名称
                }
                headerList.add("数量");
                columnsList.add("NUM");
            }
            //分组条件
            record.put("sqlsb", sqlsb.substring(0, sqlsb.length() - 1).toString());

            //组装排序条件
            //            map.put("sortname", sortname);
            //            map.put("sortorder", sortorder);

            if (!Common.isEmpty(sortname) && !Common.isEmpty(sortorder)) {
                record.put("orderCondition", sortname + " " + sortorder);
            }

            //用户所属本地网
            if (!Common.isEmpty(cityIds)) {
                record.put("cityIds", cityIds);
            } else {
                record.put("cityIds", user.getCityIds());
            }


            if (!Common.isEmpty(typeId)) {
                switch (typeId.intValue()) {
                    case 1:
                        fileName = "室外覆盖物理站点";
                        list = btsManager.selectBtsGroupByColumns(record);
                        break;
                    case 2:
                        fileName = "BBU";
                        list = bbuManager.selectBbuGroupByColumns(record);
                        break;
                    case 3:
                        fileName = "室内分布小区";
                        list = indoorManualManager.selectIndoorDataGroupByCoulmns(record);
                        break;
                    case 4:
                        fileName = "室外覆盖小区";
                        list = cellManager.selectCellGroupByColumns(record);
                        break;
                    case 5:
                        //纯bbu统计
                        fileName="纯BBU";
                        record.put("isShare", 0);
                        record.put("bbuType", "1,2");
                        list = bbuManager.selectBbuGroupByColumns(record);
                        break;
                    case 6:
                        fileName="隧道覆盖小区";
                        list = tunelManager.selectWyTunelGroupByColumns(record);
                        break;
                    case 7:
                        fileName="室内分布站点";
                        list = btsManager.selectWyIndoorBtsGroupByColumns(record);
                        break;
                    case 8:
                        fileName="隧道覆盖站点";
                        list = tunelManager.selectWyTunelGroupByColumns(record);
                        break;
                    default:
                        break;
                }
            }
            fileName = fileName + "_基础数据统计.xls";
            HSSFWorkbook demoWorkBook = new HSSFWorkbook();
            ;// 得到工作薄
            HSSFCellStyle style = ExcelUtil.setStyle(demoWorkBook);
            HSSFSheet demoSheet = ExcelUtil.createSheet(demoWorkBook, 0);// 第一个工作sheet:工作指引

            //自动生成表头
            HSSFRow headRow = demoSheet.createRow(0);

            for (short j = 0; j < headerList.size(); j++) {
                // 创建第i个单元格
                HSSFCell cell = headRow.createCell((short) j);
                cell.setCellStyle(ExcelUtil.setHeaderStype(demoWorkBook));
                cell.setCellValue(headerList.get(j));
            }

            //生成数据


            // 创建整个Excel表 //根据查询条件从DB取出list，生成excel
            int rowIndex = 1;
            for (Map m : list) {
                List<String> cList = new ArrayList<String>();
                for (String columnName : columnsList) {
                    cList.add(StringUtil.null2String(m.get(columnName)));
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
            LOG.error(e.getMessage(),e);
        }
        return null;
    }


    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCnNames() {
        return cnNames;
    }

    public void setCnNames(String cnNames) {
        this.cnNames = cnNames;
    }

    public String getEnNames() {
        return enNames;
    }

    public void setEnNames(String enNames) {
        this.enNames = enNames;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }
}
