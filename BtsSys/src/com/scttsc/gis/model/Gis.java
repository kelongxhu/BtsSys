package com.scttsc.gis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 源于WY_BTS[与Bts.java相近但有区别]，地图中展现
 */
public class Gis {

/*  SELECT A.NAME, A.BTS_ID, B.TOTAL_GRADE, D.CELL_NAME
    FROM WY_BTS A
    LEFT JOIN WY_GRADE B ON B.INT_ID=A.INT_ID
    LEFT JOIN (SELECT WY_BTS_INT_ID, replace(WMSYS.WM_CONCAT(C.NAME),',','；') CELL_NAME
    FROM WY_CELL C WHERE C.DELETE_FLAG = 0
    GROUP BY WY_BTS_INT_ID) D ON D.WY_BTS_INT_ID = A.INT_ID
    WHERE A.DELETE_FLAG=0
    ORDER BY A.NAME
    */
    private BigDecimal intId;    //主键
    private String name;         //物理站点名称
    private BigDecimal longitude;      //经度
    private BigDecimal latitude;       //纬度
    private String isIndoor;
    private String isRru;
    private String btsName;
    private String bscName;
    private BigDecimal btsId;        //基站ID
    private String circuitroomOwnership;
    private String transOwnership;
    private String vendorBtstype;
    private BigDecimal cityId;
    private BigDecimal countyId;
    private BigDecimal relatedBts;
    private BigDecimal manualFlag;
    private BigDecimal deleteFlag;      //废弃标识
    private Date deletetime;
    private Date updatetime;
    private String serviceLevel;
    private Long deleteResoncode;
    private String deleteText;

    private Double totalGrade;  //总分
    private String cellName;    //所辖小区名称

    private String tranUpSiteName;//传输上游节点名

    private String szGisName;//施主基站名

    private LatLng szLatLng; //施主基站
    private LatLng upLatLng; //传输拓扑上游节点


    //冗余告警属性
    private String alarmName;//告警名称
    private String alarmCode;//告警代码
    private String alarmLevel;//告警级别
    private String alarmTime;//告警时间



    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getIndoor() {
        return isIndoor;
    }

    public void setIndoor(String indoor) {
        isIndoor = indoor;
    }

    public String getRru() {
        return isRru;
    }

    public void setRru(String rru) {
        isRru = rru;
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

    public BigDecimal getBtsId() {
        return btsId;
    }

    public void setBtsId(BigDecimal btsId) {
        this.btsId = btsId;
    }

    public String getCircuitroomOwnership() {
        return circuitroomOwnership;
    }

    public void setCircuitroomOwnership(String circuitroomOwnership) {
        this.circuitroomOwnership = circuitroomOwnership;
    }

    public String getTransOwnership() {
        return transOwnership;
    }

    public void setTransOwnership(String transOwnership) {
        this.transOwnership = transOwnership;
    }

    public String getVendorBtstype() {
        return vendorBtstype;
    }

    public void setVendorBtstype(String vendorBtstype) {
        this.vendorBtstype = vendorBtstype;
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getCountyId() {
        return countyId;
    }

    public void setCountyId(BigDecimal countyId) {
        this.countyId = countyId;
    }

    public BigDecimal getRelatedBts() {
        return relatedBts;
    }

    public void setRelatedBts(BigDecimal relatedBts) {
        this.relatedBts = relatedBts;
    }

    public BigDecimal getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(BigDecimal manualFlag) {
        this.manualFlag = manualFlag;
    }

    public BigDecimal getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(BigDecimal deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Long getDeleteResoncode() {
        return deleteResoncode;
    }

    public void setDeleteResoncode(Long deleteResoncode) {
        this.deleteResoncode = deleteResoncode;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(String deleteText) {
        this.deleteText = deleteText;
    }

    public Double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(Double totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getTranUpSiteName() {
        return tranUpSiteName;
    }

    public void setTranUpSiteName(String tranUpSiteName) {
        this.tranUpSiteName = tranUpSiteName;
    }

    public String getSzGisName() {
        return szGisName;
    }

    public void setSzGisName(String szGisName) {
        this.szGisName = szGisName;
    }

    public LatLng getSzLatLng() {
        return szLatLng;
    }

    public void setSzLatLng(LatLng szLatLng) {
        this.szLatLng = szLatLng;
    }

    public LatLng getUpLatLng() {
        return upLatLng;
    }

    public void setUpLatLng(LatLng upLatLng) {
        this.upLatLng = upLatLng;
    }


    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
