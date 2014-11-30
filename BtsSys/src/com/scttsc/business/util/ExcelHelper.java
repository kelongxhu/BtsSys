package com.scttsc.business.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelHelper {
    /**
     * 获取Excel值
     *
     * @param cell
     * @return
     */
    public static String getValue(HSSFCell cell) {
        String ret = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_BLANK:
                ret = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                ret = null;
                break;
            case HSSFCell.CELL_TYPE_STRING: // 获取字符串类型
                ret = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC: // 获取数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是时间格式
                    double d = cell.getNumericCellValue();
                    Date theDate = HSSFDateUtil.getJavaDate(d);
                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                    ret = simpleDateFormat.format(theDate);
                } else {
                    //数字格式
                    ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            default:
                break;
        }
        return ret;

    }
    
    public static String getValue(HSSFCell cell, Validity validity) {
        String ret = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_BLANK:
                ret = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                ret = null;
                break;
            case HSSFCell.CELL_TYPE_STRING: // 获取字符串类型
                ret = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC: // 获取数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是时间格式
                    double d = cell.getNumericCellValue();
                    Date theDate = HSSFDateUtil.getJavaDate(d);
                    DateFormat simpleDateFormat = new SimpleDateFormat(validity.getDateFormater());
                    ret = simpleDateFormat.format(theDate);
                } else {
                    //数字格式
                    ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            default:
                break;
        }
        return ret;

    }

    /**
     * 组装bts列和列验证
     *
     * @return
     */
    public static Map<String, Validity> getBtsCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("intId", new Validity("基站标识", true, 1, null));//不能为空
        cols.put("name", new Validity("基站名称", true, 1, null)); // 不能为空
        cols.put("bscName", new Validity("所属BSC", true, 1, null));//不能为空
        cols.put("btsId", new Validity("基站网管编号", true, 2, null));//不能为空，数字
        cols.put("installPos", new Validity("主设备安装位置", true, 1, null));//不能为空，且要特殊处理
        cols.put("sharFlag", new Validity("是否共建共享", true, 1, new String[]{"是", "否"})); //不能为空，固定填值
        cols.put("sharName", new Validity("共享方名称", true, 1, new String[]{"移动", "联通", "其他", "无"}));//不能为空
        cols.put("address", new Validity("详细地址", true, 1, null));//不能为空
        cols.put("openTime", new Validity("基站开通年月", true, 3, null));//不能为空  ,时间
        cols.put("tranModel", new Validity("规格型号", true, 1, null)); //不能为空
        cols.put("tranFac", new Validity("生产厂家", true, 1, null)); //不能为空
        cols.put("tranUpsitename", new Validity("传输拓扑上游站点名称", true, 1, null));//不能为空
        cols.put("tranDownsitename", new Validity("传输拓扑下游站点名称", false, 1, null));
        cols.put("tranNetprotect", new Validity("是否在传输环网保护", true, 1, new String[]{"是", "否"}));
        cols.put("tranIsnode", new Validity("是否节点站", true, 1, new String[]{"是", "否"}));
        cols.put("tranSitenum", new Validity("节点站下挂基站数量", true, 2, null)); //不能为空，数字
        cols.put("sourModel", new Validity("开关电源规格型号", false, 1, null));
        cols.put("sourFac", new Validity("开关电源生产厂家", false, 1, null));
        cols.put("sourModuleModel", new Validity("整流模块型号", false, 1, null));
        cols.put("sourModuleNum", new Validity("整流模块数量", false, 2, null));
        cols.put("sourCapa", new Validity("满架容量（A）", false, 2, null));
        cols.put("boxModel", new Validity("交流配电防雷规格型号", false, 1, null));
        cols.put("boxFac", new Validity("交流配电防雷生产厂家", false, 1, null));
        cols.put("cellModel", new Validity("蓄电池组或UPS型号", false, 1, null));
        cols.put("cellFac", new Validity("蓄电池组或UPS生产厂家", false, 1, null));
        cols.put("cellCapa", new Validity("蓄电池组或UPS容量（AH）", false, 2, null));
        cols.put("cellNum", new Validity("蓄电池组或UPS数量（组）", false, 2, null));
        cols.put("cellPower", new Validity("蓄电池组或UPS机房设备总耗电（A）", false, 2, null)); //整数
        cols.put("cellDuar", new Validity("蓄电池组或UPS支撑基站设备运行时长（小时）", false, 2, null));//整数
        cols.put("cellUstime", new Validity("蓄电池组或UPS启用时间（年月）", false, 3, null));
        cols.put("towerType", new Validity("塔桅类型", false, 1, null));  //不为空，特殊处理
        cols.put("towerHigh", new Validity("塔桅高度(m)", false, 2, null));
        cols.put("acModel", new Validity("空调系统规格型号", false, 1, null));//选无
        cols.put("acFac", new Validity("空调系统生产厂家", false, 1, null));
        cols.put("acNum", new Validity("空调系统数量", false, 2, null));//数字
        cols.put("dhModel", new Validity("动环监控系统规格型号", false, 1, null));
        cols.put("dhFac", new Validity("动环监控系统生产厂家", false, 1, null));
        cols.put("xfModel", new Validity("新风系统规格型号", false, 1, null));
        cols.put("xfFac", new Validity("新风系统生产厂家", false, 1, null));
        cols.put("mrStrut", new Validity("机房结构", false, 1, null));
        cols.put("mrLength", new Validity("机房长度（米）", false, 2, null));
        cols.put("mrWidth", new Validity("机房宽度（米）", false, 2, null));
        cols.put("mrHigh", new Validity("机房高度（米）", false, 2, null));
        cols.put("mrOwner", new Validity("机房所属业主或单位", false, 1, null));
        cols.put("wdType", new Validity("市电引入方式", false, 1, new String[]{"10KV", "380V", "220V", "直流供电", "直流远供"}));
        cols.put("wdModel", new Validity("变压器型号", false, 1, null));
        cols.put("wdFac", new Validity("变压器厂家", false, 1, null));
        cols.put("wdGds", new Validity("所属供电局所", false, 1, null));
        cols.put("oeType", new Validity("油机类型", false, 1, null));
        cols.put("oeModel", new Validity("油机型号", false, 1, null));
        cols.put("oePower", new Validity("油机功率", false, 1, null));
        cols.put("oeFac", new Validity("油机厂家", false, 1, null));
        cols.put("remark", new Validity("备注", false, 1, null));
        return cols;
    }

    public static Map<String, Validity> getBtsChargeCoulmnMap() {
    	Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("contractStarttime", new Validity("合同开始日期", true, Validity.D3, null));//不能为空
        cols.put("contractEndtime", new Validity("合同结束日期", true, Validity.D3, null));//不能为空
        cols.put("payCycle", new Validity("缴费周期", true, Validity.NUM, null));//不能为空
        cols.put("payDay", new Validity("缴费日期", true, Validity.NUM, null));//不能为空
        cols.put("remindUser", new Validity("提醒人员", true, Validity.STR, null));//不能为空
        cols.put("remindTel", new Validity("提醒号码", true, Validity.STR, null));//不能为空
        cols.put("money", new Validity("金额", true, Validity.NUM, null));//不能为空
        cols.put("lastPayTime", new Validity("上次缴费时间", true, Validity.D3, null));//不能为空
        cols.put("eachTel", new Validity("对方联系号码", false, Validity.STR, null));//不能为空
        cols.put("eachAccountname", new Validity("对方账号名称", false, Validity.STR, null));//不能为空
        cols.put("eachBanknum", new Validity("对方银行账号", false, Validity.STR, null));//不能为空
        cols.put("remark", new Validity("备注", false, Validity.STR, null));//不能为空
    	return cols;
    }
        
    public static Map<String , Validity> getBtsPowerChargeCoulmnMap() {
    	Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
    	cols.put("payType", new Validity("缴费方式", true, Validity.STR, new String[]{"人工缴费","自动代扣"}));//不能为空
    	cols.put("payCycle", new Validity("缴费周期", true, Validity.NUM, null));//不能为空
    	cols.put("payDay", new Validity("缴费日期", true, Validity.NUM, null));//不能为空
    	cols.put("remindUser", new Validity("提醒人员", true, Validity.STR, null));//不能为空
    	cols.put("remindTel", new Validity("提醒号码", true, Validity.STR, null));//不能为空
    	cols.put("money", new Validity("单价", true, Validity.NUM, null));//不能为空
    	cols.put("bankAccount", new Validity("代扣银行账号", false, Validity.STR, null));//不能为空
    	cols.put("balance", new Validity("当前账户余额", true, Validity.NUM, null));//不能为空
    	return cols;
    }
    
    /**
     * 字段和常量groupCode关系
     *
     * @return
     */
    public static Map<String, String> getGroupCodeMap() {
        Map<String, String> groupCodeMap = new HashMap<String, String>();
        groupCodeMap.put("installPos", "BTSINSTALL");
        groupCodeMap.put("towerType", "TOWERTYPE");
        groupCodeMap.put("mrStrut", "MRSTRUT");
        groupCodeMap.put("prop", "TUNELPROP");
        groupCodeMap.put("shareflag", "TUNEL_SHARE");
        return groupCodeMap;
    }


    /**
     * 组装BBU列和列验证
     *
     * @return
     */
    public static Map<String, Validity> getBbuCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("intId", new Validity("基站标识", true, 1, null));//不能为空
        cols.put("name", new Validity("基站名称", true, 1, null)); // 不能为空
        cols.put("bscName", new Validity("所属BSC", true, 1, null));//不能为空
        cols.put("btsId", new Validity("基站网管编号", true, 2, null));//不能为空，数字
        cols.put("longitude", new Validity("经度", true, 2, null));//不能为空，数字
        cols.put("latitude", new Validity("纬度", true, 2, null));//不能为空，数字
        cols.put("sharFlag", new Validity("是否共建共享", true, 1, new String[]{"是", "否"})); //不能为空，固定填值
        cols.put("sharName", new Validity("共享方名称", true, 1, new String[]{"移动", "联通", "其他", "无"}));//不能为空
        cols.put("address", new Validity("详细地址", true, 1, null));//不能为空
        cols.put("openTime", new Validity("基站开通年月", true, 3, null));//不能为空
        cols.put("tranModel", new Validity("规格型号", true, 1, null)); //不能为空
        cols.put("tranFac", new Validity("生产厂家", true, 1, null)); //不能为空
        cols.put("tranUpsitename", new Validity("传输拓扑上游站点名称", true, 1, null));//不能为空
        cols.put("tranDownsitename", new Validity("传输拓扑下游站点名称", false, 1, null));
        cols.put("tranNetprotect", new Validity("是否在传输环网保护", true, 1, new String[]{"是", "否"}));
        cols.put("tranIsnode", new Validity("是否节点站", true, 1, new String[]{"是", "否"}));
        cols.put("tranSitenum", new Validity("节点站下挂基站数量", true, 2, null)); //不能为空，数字
        cols.put("sourModel", new Validity("开关电源规格型号", false, 1, null));
        cols.put("sourFac", new Validity("开关电源生产厂家", false, 1, null));
        cols.put("sourModuleModel", new Validity("整流模块型号", false, 1, null));
        cols.put("sourModuleNum", new Validity("整流模块数量", false, 2, null));
        cols.put("sourCapa", new Validity("满架容量（A）", false, 2, null));
        cols.put("boxModel", new Validity("交流配电防雷规格型号", false, 1, null));
        cols.put("boxFac", new Validity("交流配电防雷生产厂家", false, 1, null));
        cols.put("cellModel", new Validity("蓄电池组或UPS型号", false, 1, null));
        cols.put("cellFac", new Validity("蓄电池组或UPS生产厂家", false, 1, null));
        cols.put("cellCapa", new Validity("蓄电池组或UPS容量（AH）", false, 2, null));
        cols.put("cellNum", new Validity("蓄电池组或UPS数量（组）", false, 2, null));
        cols.put("cellPower", new Validity("蓄电池组或UPS机房设备总耗电（A）", false, 2, null)); //整数
        cols.put("cellDuar", new Validity("蓄电池组或UPS支撑基站设备运行时长（小时）", false, 2, null));//整数
        cols.put("cellUstime", new Validity("蓄电池组或UPS启用时间（年月）", false, 3, null));
        cols.put("acModel", new Validity("空调系统规格型号", false, 1, null));//选无
        cols.put("acFac", new Validity("空调系统生产厂家", false, 1, null));
        cols.put("acNum", new Validity("空调系统数量", false, 2, null));//数字
        cols.put("dhModel", new Validity("动环监控系统规格型号", false, 1, null));
        cols.put("dhFac", new Validity("动环监控系统生产厂家", false, 1, null));
        cols.put("xfModel", new Validity("新风系统规格型号", false, 1, null));
        cols.put("xfFac", new Validity("新风系统生产厂家", false, 1, null));
        cols.put("mrStrut", new Validity("机房结构", true, 1, null));
        cols.put("mrLength", new Validity("机房长度（米）", false, 2, null));
        cols.put("mrWidth", new Validity("机房宽度（米）", false, 2, null));
        cols.put("mrHigh", new Validity("机房高度（米）", false, 2, null));
        cols.put("mrOwner", new Validity("机房所属业主或单位", false, 1, null));
        cols.put("wdType", new Validity("市电引入方式", true, 1, new String[]{"10KV", "380V", "220V", "直流供电", "直流远供"}));
        cols.put("wdModel", new Validity("变压器型号", false, 1, null));
        cols.put("wdFac", new Validity("变压器厂家", true, 1, null));
        cols.put("wdGds", new Validity("所属供电局所", true, 1, null));
        cols.put("oeType", new Validity("油机类型", false, 1, null));
        cols.put("oeModel", new Validity("油机型号", false, 1, null));
        cols.put("oePower", new Validity("油机功率", false, 1, null));
        cols.put("oeFac", new Validity("油机厂家", false, 1, null));
        cols.put("remark", new Validity("备注", false, 1, null));
        return cols;
    }

    /**
     * 组装indoor列，包含字段验证
     *
     * @return
     */
    public static Map<String, Validity> getIndoorCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("intId", new Validity("室分标识", false, 1, null));
        cols.put("name", new Validity("室内分布站点名称", true, 1, null));
        cols.put("cityId", new Validity("本地网", true, 1, null));
        cols.put("countryId", new Validity("所属区县", true, 1, null));
        cols.put("longitude", new Validity("经度", true, 1, null));
        cols.put("latitude", new Validity("纬度", true, 1, null));
        cols.put("vendorBtstype", new Validity("信源设备类型", true, 1, null));
        cols.put("bscName", new Validity("对应基站所属BSC", true, 1, null));
        cols.put("btsName", new Validity("对应基站名称", true, 1, null));
        cols.put("cellSeq", new Validity("小区序号", true, 2, null));
        cols.put("pn", new Validity("PN", true, 2, null));
        cols.put("ci", new Validity("CI", true, 2, null));
        cols.put("prop1", new Validity("站点性质一", true, 1, null));
        cols.put("prop2", new Validity("站点性质二", true, 1, null));
        cols.put("grade", new Validity("站点分级", true, 1, null));
        cols.put("coverage", new Validity("覆盖范围描述", true, 1, null));
        cols.put("buildingnum", new Validity("楼宇数量", true, 2, null));
        cols.put("address", new Validity("详细地址", true, 1, null));
        cols.put("devicenum", new Validity("信源设备数量", true, 1, null));
        cols.put("monitorflag", new Validity("信源有无监控", true, 1, new String[]{"有", "无"}));
        cols.put("monitornumber", new Validity("监控号码", true, 1, null));
        cols.put("deviceaddress", new Validity("信源设备安装位置", true, 1, null));
        cols.put("repeaternum", new Validity("直放站数量", true, 1, null));
        cols.put("drynum", new Validity("干放设备数量", true, 1, null));
        cols.put("distributedesc", new Validity("分布系统共建共享情况", true, 1, null));
        cols.put("distributefac", new Validity("分布系统集成厂家", true, 1, null));
        cols.put("motorflag", new Validity("有无机房", true, 1, new String[]{"有", "无"}));
        cols.put("motorarea", new Validity("机房面积", true, 2, null));
        cols.put("motorright", new Validity("机房产权", true, 1, null));
        cols.put("meteraddress", new Validity("对应信源设备的电表安装位置", true, 1, null));
        cols.put("constructiondate", new Validity("建设时间", true, 3, null));
        cols.put("wlanflag", new Validity("是否有WLAN", true, 1, new String[]{"有", "无"}));
        cols.put("wlanshare", new Validity("WLAN与C网是否共享分布系统", true, 1, new String[]{"是", "否"}));
        cols.put("wlancoverage", new Validity("WLAN覆盖范围描述", true, 1, null));
        cols.put("ownername", new Validity("业主联系人", true, 1, null));
        cols.put("ownerphone", new Validity("业主联系电话", true, 1, null));
        cols.put("monitordevicenum", new Validity("室分监控设备数量", true, 2, null));
        cols.put("monitordeviceids", new Validity("室分监控设备ID", true, 1, null));
        cols.put("remark", new Validity("备注", false, 1, null));

        return cols;
    }

    /**
     * 组装直放站的列
     *
     * @return
     */
    public static Map<String, Validity> getErectCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("wybtsname", new Validity("室内分布站点名称", true, 1, null));
        cols.put("no", new Validity("所带直放站编号", true, 1, null));
        cols.put("type", new Validity("直放站类型", true, 1, new String[]{"光纤", "无线", "移频"}));
        cols.put("model", new Validity("直放站型号", true, 1, null));
        cols.put("fac", new Validity("直放站厂家", true, 1, null));
        cols.put("address", new Validity("直放站安装位置", true, 1, null));
        cols.put("coverage", new Validity("直放站下接室分系统覆盖范围", true, 1, null));
        cols.put("monitorfalg", new Validity("有无监控", true, 1, new String[]{"有", "无"}));
        cols.put("monitornumber", new Validity("监控号码", true, 1, null));
        cols.put("meteraddress", new Validity("电表安装位置", true, 1, null));
        return cols;
    }

    /**
     * 组装干放站列
     *
     * @return
     */
    public static Map<String, Validity> getDryCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("wybtsname", new Validity("室内分布站点名称", true, 1, null));
        cols.put("no", new Validity("所带干放站编号", true, 2, null));
        cols.put("coverage", new Validity("干放下接室分系统覆盖范围描述", true, 1, null));
        cols.put("model", new Validity("干放设备型号", true, 1, null));
        cols.put("fac", new Validity("干放设备厂家", true, 1, null));
        cols.put("address", new Validity("干放站安装位置", true, 1, null));
        cols.put("monitorflag", new Validity("有无监控", true, 1, new String[]{"有", "无"}));
        cols.put("monitornumber", new Validity("监控号码", true, 1, null));
        cols.put("parentdevice", new Validity("干放上级设备（归属）", true, 1, null));
        cols.put("meteraddress", new Validity("电表安装位置", true, 1, null));
        return cols;
    }


    /**
     * 组装小区列，及验证字段
     *
     * @return
     */
    public static Map<String, Validity> getCellCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("intId", new Validity("小区标识", true, 1, null));
        cols.put("name", new Validity("小区名称", true, 1, null));
        cols.put("bscName", new Validity("归属BSC", true, 1, null));
        cols.put("ci", new Validity("CI", true, 1, null));
        cols.put("gfflag", new Validity("是否功分", true, 1, new String[]{"是", "否"}));
        cols.put("gfnum", new Validity("功分编号", true, 2, null));
        cols.put("indoornum", new Validity("所带室内分布数量", true, 2, null));
        //天线字段
        cols.put("airModel", new Validity("天线型号", true, 1, null));  //天线型号
        cols.put("azimuth", new Validity("天线方位角", true, 1, null));   //天线方位角
        cols.put("hanghigh", new Validity("天线挂高", true, 1, null));   //天线挂高
        cols.put("electrondip", new Validity("天线电子倾角", true, 1, null));   //天线电子倾角
        cols.put("enginedip", new Validity("天线机械倾角", true, 1, null));   //天线机械倾角
        //小区
        cols.put("borderflag", new Validity("多载频边界Border扇区", true, 1, new String[]{"是", "否"}));    //多载频边界Border扇区
        cols.put("feederlength", new Validity("馈线长度", true, 2, null));  //馈线长度
        cols.put("coverarea", new Validity("扇区覆盖区域类型", true, 1, null)); //扇区覆盖区域类型
        cols.put("countryLib", new Validity("农村、乡镇名称", false, 1, null));  //农村乡镇库
        cols.put("coverroad", new Validity("扇区覆盖道路类型", false, 1, null)); //扇区覆盖道路类型
        cols.put("roadLib", new Validity("道路名称", false, 1, null));// 关联道路库或者隧道库
        cols.put("coverhot", new Validity("扇区覆盖热点类型", true, 1, null));  //扇区覆盖热点类型
        cols.put("hotLib", new Validity("热点名称", false, 1, null)); //风景库或者校园库
        cols.put("boundarycellflag", new Validity("是否属边界扇区", false, 1, null)); //是否属边界扇区
        cols.put("borderprovince", new Validity("接壤省份名称", false, 1, null)); //接壤省份名称
        cols.put("bordercity", new Validity("接壤城市名称", false, 1, null)); //接壤城市名称
        return cols;
    }


    /**
     * 组装导入农村库信息
     *
     * @return
     */
    public static Map<String, Validity> getTownCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("cityId", new Validity("本地网标识", true, 1, null));//不能为空
        cols.put("countryId", new Validity("区县标识", true, 1, null)); // 不能为空
        cols.put("name", new Validity("名称", true, 1, null));//不能为空
        cols.put("gpsLong", new Validity("GPS经度", true, 2, null));//不能为空
        cols.put("gpsLat", new Validity("GPS纬度", true, 2, null));//不能为空
        cols.put("cmda1xCovgrate", new Validity("电信CDMA1X 2000网络覆盖率", true, 2, null));//
        cols.put("mobgsmCovgrate", new Validity("移动GSM网络覆盖率", true, 2, null));//
        cols.put("covgDifCovgrate", new Validity("电信与移动网络覆盖差值网络覆盖率", true, 2, null));//
        cols.put("unicgsmCovgrate", new Validity("联通GSM网络覆盖率", true, 2, null));//
        cols.put("evdoCovgrate", new Validity("电信EVDO网络覆盖率", true, 2, null));//
        cols.put("cdma1xMos", new Validity("语音MOS值电信CDMA1X 2000", true, 2, null));//数字
        cols.put("mobgsmMos", new Validity("语音MOS值移动GSM", false, 2, null));//数字
        cols.put("unicgsmMos", new Validity("语音MOS值联通GSM", false, 2, null));//数字
        cols.put("testtime", new Validity("测试起始时间", false, 1, null));//
        cols.put("testuser", new Validity("测试人员", false, 1, null));//
        return cols;
    }


    /**
     * 组装导入农村库信息
     *
     * @return
     */
    public static Map<String, Validity> getCountryCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("cityId", new Validity("本地网标识", true, 1, null));//不能为空
        cols.put("countryId", new Validity("区县标识", true, 1, null)); // 不能为空
        cols.put("parentId", new Validity("乡镇标识", true, 1, null));
        cols.put("name", new Validity("名称", true, 1, null));//不能为空
        cols.put("gpsLong", new Validity("GPS经度", true, 2, null));//不能为空
        cols.put("gpsLat", new Validity("GPS纬度", true, 2, null));//不能为空
        cols.put("cmda1xCovgrate", new Validity("电信CDMA1X 2000网络覆盖率", true, 2, null));//
        cols.put("mobgsmCovgrate", new Validity("移动GSM网络覆盖率", true, 2, null));//
        cols.put("covgDifCovgrate", new Validity("电信与移动网络覆盖差值网络覆盖率", true, 2, null));//
        cols.put("unicgsmCovgrate", new Validity("联通GSM网络覆盖率", true, 2, null));//
        cols.put("evdoCovgrate", new Validity("电信EVDO网络覆盖率", true, 2, null));//
        cols.put("cdma1xMos", new Validity("语音MOS值电信CDMA1X 2000", true, 2, null));//数字
        cols.put("mobgsmMos", new Validity("语音MOS值移动GSM", true, 2, null));//数字
        cols.put("unicgsmMos", new Validity("语音MOS值联通GSM", true, 2, null));//数字
        cols.put("testtime", new Validity("测试起始时间", false, 1, null));//
        cols.put("testuser", new Validity("测试人员", false, 1, null));//
        return cols;
    }


    /**
     * 组装导入隧道库的列
     *
     * @return
     */
    public static Map<String, Validity> getTunelCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("intId", new Validity("隧道标识", true, 1, null));
        cols.put("prop", new Validity("隧道性质" +
                "", true, 1, null));
        cols.put("roadId", new Validity("所属道路", true, 1, null));
        cols.put("tunelLength", new Validity("隧道长度", true, 2, null));
        cols.put("antennatype", new Validity("天馈类型", true, 1, null));
        cols.put("linetype", new Validity("天线型号", true, 1, null));
        cols.put("covertype", new Validity("覆盖类型", true, 1, new String[]{"单向覆盖", "双向覆盖"}));
        cols.put("coverrangedesc", new Validity("覆盖范围描述", true, 1, null));
        cols.put("address", new Validity("详细地址", true, 1, null));
        cols.put("tunelHigh", new Validity("海拔高度", true, 2, null));
        cols.put("rrunum", new Validity("信源设备数量", true, 2, null));
        cols.put("rruaddress", new Validity("信源设备安装位置", true, 1, null));
        cols.put("repeaternum", new Validity("下挂直放站数量", true, 2, null));
        cols.put("repeateraddress", new Validity("直放站安装位置", true, 1, null));
        cols.put("drynum", new Validity("下挂干放数量", true, 2, null));
        cols.put("dryaddress", new Validity("干放安装位置", true, 1, null));
        cols.put("shareflag", new Validity("共建共享情况", true, 1, null));
        cols.put("mchroomflag", new Validity("有无机房", true, 1, new String[]{"有", "无"}));
        cols.put("mchroonarea", new Validity("机房面积", true, 2, null));
        cols.put("meteradress", new Validity("对应信源设备的电表安装位置", true, 1, null));
        cols.put("builddate", new Validity("建设时间（年月）", true, 4, null));
        cols.put("owner", new Validity("业主联系人", true, 1, null));
        cols.put("ownertel", new Validity("业主联系电话", true, 1, null));
        cols.put("remark", new Validity("备注", true, 1, null));
        return cols;
    }


    public static String[] BLINDTYPE2 = new String[]{"高铁", "高速公路", "机场", "车站港口码头", "住宅小区", "风景区",
            "宾馆", "写字楼", "商场", "体育场", "地铁", "海域", "校园", "隧道", "医院", "政府机关", "工业园区", "农村",
            "水运", "聚类市场", "休闲娱乐场所", "其它"};

    public static String[] SOLTYPE = new String[]{"网络优化", "规划建站", "辅助手段", "WiFi解决"};

    public static String[] ASSISTTYPE = new String[]{"BPA", "塔放", "壁虎", "手机伴侣", "五类线", "其它", "无"};

    public static String[] GRIDTYPE = new String[]{"市区", "城郊", "县城", "乡镇", "农村"};

    public static String[] ISHAS = new String[]{"无", "有"};

    public static String[] STATUS = new String[]{"未解决", "已解决"};

    public static String[] BLINDTYPE1 = new String[]{"室外", "室内"};

    public static String[] ISNO = new String[]{"否", "是"};

    public static Map<String, Validity> getBlindCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("cityId", new Validity("本地网", true, 1, null));//不能为空
        cols.put("countyId", new Validity("区县", true, 1, null)); // 不能为空
        cols.put("vitoId", new Validity("农村和乡镇", false, 1, null));
        cols.put("longitude", new Validity("GPS经度", true, 2, null));//不能为空
        cols.put("latitude", new Validity("GPS纬度", true, 2, null));//不能为空
        cols.put("name", new Validity("盲点名称", true, 1, null));//不能为空
        cols.put("address", new Validity("盲点地址", true, 1, null));//不能为空
        cols.put("landMark", new Validity("标志性建筑", true, 1, null));//不能为空
        cols.put("isPlan", new Validity("是否有规划", false, 1, ISHAS));
        cols.put("status", new Validity("盲点状态", true, 1, STATUS));//不能为空
        cols.put("gridType", new Validity("网格类型", true, 1, GRIDTYPE));//不能为空
        cols.put("blindType1", new Validity("盲点区域类型1", true, 1, BLINDTYPE1));//不能为空
        cols.put("blindType2", new Validity("盲点区域类型2", false, 1, BLINDTYPE2));
        cols.put("is1x", new Validity("是否1X盲点", true, 1, ISNO));//不能为空
        cols.put("isDo", new Validity("是否DO盲点", true, 1, ISNO));//不能为空
        cols.put("isLte", new Validity("是否LTE盲点", true, 1, ISNO));//不能为空
        cols.put("causesBy", new Validity("形成原因", true, 1, null));//不能为空
        cols.put("solType", new Validity("解决方式", true, 1, SOLTYPE));//不能为空
        cols.put("assistType", new Validity("辅助手段", false, 1, ASSISTTYPE));//不能为空
        cols.put("planSolutionTime", new Validity("计划解决时间", false, Validity.D3, null));//不能为空
        cols.put("realSolutionTime", new Validity("实际解决时间", false, Validity.D3, null));//不能为空
        cols.put("remarks", new Validity("备注", false, 1, null));//不能为空
        return cols;
    }


    /**
     * 物业和房租导入字段
     *
     * @return
     */
    public static Map<String, Validity> getRoomChargeCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("btsName", new Validity("站点名称", true, 1, null));//不能为空
        cols.put("intId",new Validity("INT_ID", true, 1, null));
        cols.put("btsType",new Validity("BTS_TYPE", true, 1, null));
        cols.put("cityId", new Validity("本地网", true, 1, null));//忽略
        cols.put("countryId",  new Validity("区县", true, 1, null));//忽略
        cols.put("bscName",  new Validity("bscName", true, 1, null));//忽略
        cols.put("btsId", new Validity("btsId", true, 1, null));//忽略
        cols.put("money", new Validity("金额", true, 2, null)); // 不能为空
        cols.put("payTime", new Validity("缴费时间", true, 5, null));
        cols.put("payUser", new Validity("缴费人员", true, 1, null));
        cols.put("remark", new Validity("备注", false, 1, null));
        return cols;
    }


    /**
     * 电费导入字段
     *
     * @return
     */
    public static Map<String, Validity> getPowerChargeCoulmnMap() {
        Map<String, Validity> cols = new LinkedHashMap<String, Validity>();
        cols.put("btsName", new Validity("站点名称", true, 1, null));//不能为空
        cols.put("intId",new Validity("INT_ID", true, 1, null));
        cols.put("btsType",new Validity("BTS_TYPE", true, 1, null));
        cols.put("cityId", new Validity("本地网", true, 1, null));//忽略
        cols.put("countryId",  new Validity("区县", true, 1, null));//忽略
        cols.put("bscName",  new Validity("bscName", true, 1, null));//忽略
        cols.put("btsId", new Validity("btsId", true, 1, null));//忽略
        cols.put("money", new Validity("金额", true, 2, null)); // 不能为空
        cols.put("baseDegree", new Validity("底度", true, 2, null)); // 不能为空
        cols.put("monthDegree", new Validity("当月度数", true, 2, null)); // 不能为空
        cols.put("monthDegree", new Validity("当月度数", true, 2, null)); // 不能为空
        cols.put("payType", new Validity("缴费类型", true, 1, new String[]{"人工", "代扣"})); // 不能为空
        cols.put("payTime", new Validity("缴费时间", true, 5, null));
        cols.put("payUser", new Validity("缴费人员", true, 1, null));
        cols.put("remark", new Validity("备注", false, 1, null));
        return cols;
    }

}
