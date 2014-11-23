package com.scttsc.business.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.CityManager;
import com.scttsc.business.model.WyTransfernode;
import com.scttsc.business.service.TransferManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ExcelUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-10-17
 * Time: 下午2:57
 * Email: wokzhen@vip.qq.com
 */
public class TransferNodeAction extends BaseAction{

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private CityManager cityManager;

    private WyTransfernode wyTransfernode;

    private Long transferId;

    private String transferIds;

    private String countryIds;

    private Long type;

    private String name;

    private InputStream inputStream;

    private String attachmentFileName;

    private String attachmentContentType;

    /**
     * 展示传输节点列表页面
     * @return
     */
    public String showTransferList(){
        return SUCCESS;
    }

    /**
     * 查询传输节点列表
     * @return
     */
    public String queryTransferList(){
        Map cond = new HashMap();
        putConditions(cond);
        int total = 0;
        List<WyTransfernode> transfernodeList = null;
        try {
            total = transferManager.selectTransferCount(cond);
            if (total < pagesize) {
                page = 1;
            }
            cond.put("start", (page - 1) * pagesize + 1);
            cond.put("pagesize", pagesize);
            cond.put("sortname", sortname);
            cond.put("sortorder", sortorder);
            transfernodeList = transferManager.selectTransferList(cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapTotal(total);
        setJsonMapRows(transfernodeList);
        return SUCCESS;
    }

    /**
     * 展示传输节点详情
     * @return
     */
    public String showTransferDetail(){
        if(!Common.isEmpty(transferId)){
            try {
                JSONObject obj = JSONObject.fromObject(transferManager.selectByPrimaryKey(transferId));
                getRequest().setAttribute("wyTransfernode", obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SUCCESS;
    }

    /**
     * 展示添加或修改传输节点页面
     * @return
     */
    public String showTransferAddEdit(){
        if(!Common.isEmpty(transferId)){
            try {
                setWyTransfernode(transferManager.selectByPrimaryKey(transferId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SUCCESS;
    }

    /**
     * 添加/编辑传输节点
     * @return
     */
    public String transferAddEdit(){
        boolean result = true;
        String msg = "操作成功...";
        if(Common.isEmpty(wyTransfernode)) {
            result = false;
            msg = "系统错误,请重试...";
        }else{
            Long id = wyTransfernode.getId();
            Long countryId = wyTransfernode.getCountryId();
            try {
                if(!Common.isEmpty(countryId)){
                    City city = cityManager.getById(countryId);
                    if(!Common.isEmpty(city)) wyTransfernode.setCityId(city.getParentId());
                }
                if(Common.isEmpty(id)){//添加操作
                    transferManager.insertSelective(wyTransfernode);
                }else{//编辑操作
                    transferManager.updateByPrimaryKeySelective(wyTransfernode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
                msg = e.getMessage();
            }
        }
        jsonMap.put("result", result);
        jsonMap.put("msg", msg);
        return SUCCESS;
    }

    /**
     * 删除传输节点
     * @return
     */
    public String transferDelete(){
        boolean result = true;
        String msg = "删除成功...";
        if(StringUtil.isBlank(transferIds)){
            msg = "删除失败, 请重试...";
            result = false;
        }else{
            try {
                transferManager.deleteByPks(transferIds);
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                result = false;
            }
        }
        jsonMap.put("result", result);
        jsonMap.put("msg", msg);
        return SUCCESS;
    }

    /**
     * 导出传输节点结果集
     * @return
     */
    public String transferExport(){
        Map cond = new HashMap();
        putConditions(cond);
        try {
            List<WyTransfernode> transfernodeList = transferManager.selectTransferList(cond);
            exportExcel("传输节点信息.xls", "传输节点信息", getTransferHeaders(), transfernodeList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = e.getMessage();
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 获取传输节点的列表头信息
     * @return 列表头信息
     */
    private Map<String, String> getTransferHeaders(){
        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("cityName", "本地网");
        headers.put("countryName", "区县");
        headers.put("name", "站名");
        headers.put("typeName", "所属类型");
        headers.put("longitude", "经度");
        headers.put("latitude", "纬度");
        headers.put("remark", "备注");
        headers.put("btsList", "下挂站点");
        return headers;
    }

    /**
     * 导出excel
     */
    private void exportExcel(String fileName, String sheetName, Map<String, String> headers,List<WyTransfernode> datas) throws IOException {
        attachmentFileName = fileName;
        attachmentContentType = "application/msexcel";
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 25);
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);
        HSSFCellStyle headStyle = ExcelUtil.setHeaderStype(workbook);
        HSSFCellStyle style = ExcelUtil.setStyle(workbook);
        //填充表头
        short columnIndex = 0;
        String[] headerKeyArray = {};
        headerKeyArray = headers.keySet().toArray(headerKeyArray);
        for(String headerKey : headerKeyArray){
            HSSFCell cell = row.createCell(columnIndex);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(headerKey));
            cell.setCellValue(text);
            columnIndex++;
        }
        //填充数据
        JSONArray jsondatas = JSONArray.fromObject(datas);
        for (short i = 0; i < jsondatas.size(); i++) {
            JSONObject data = jsondatas.getJSONObject(i);
            row = sheet.createRow((short) i+1);
            columnIndex = 0;
            for(String headerKey : headerKeyArray){
                // 创建第columnIndex个单元格
                HSSFCell cell = row.createCell(columnIndex);
                cell.setCellStyle(style);
                if(headerKey.equalsIgnoreCase("btsList")){
                    JSONArray btsList = data.getJSONArray(headerKey);
                    String value = "";
                    if(!Common.isEmpty(btsList)){
                        for(short k = 0; k < btsList.size(); k++){
                            JSONObject bts = btsList.getJSONObject(k);
                            String name = Common.null2String(bts.get("name"));
                            value += name + ",";
                        }
                        if(StringUtil.isNotBlank(value)) value = value.substring(0, value.length() - 1);
                    }
                    cell.setCellValue(value);
                }else cell.setCellValue(Common.null2String(data.get(headerKey)));
                columnIndex++;
            }
        }
        sheet.setGridsPrinted(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        inputStream = bais;
    }

    /**
     * 设置参数
     * @param cond
     */
    private void putConditions(Map cond){
        if(StringUtil.isNotBlank(countryIds)) cond.put("countryIds", countryIds);
        else {
            User user = (User) getSession().getAttribute("user");
            if(!Common.isEmpty(user) && !Common.isEmpty(user.getCountryIds())){
                cond.put("countryIds", user.getCountryIds());
            }
        }
        if(!Common.isEmpty(type)) cond.put("type", type);
        if(StringUtil.isNotBlank(name)) cond.put("name", Common.decodeURL(name).trim());
    }

    /* SETTERS AND GETTERS */

    public WyTransfernode getWyTransfernode() {
        return wyTransfernode;
    }

    public void setWyTransfernode(WyTransfernode wyTransfernode) {
        this.wyTransfernode = wyTransfernode;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public void setTransferIds(String transferIds) {
        this.transferIds = transferIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachmentFileName() {
        try {
            return StringUtil.isBlank(attachmentFileName) ? attachmentFileName : new String(attachmentFileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return attachmentFileName;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return inputStream;
    }
}
