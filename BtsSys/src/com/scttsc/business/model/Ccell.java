package com.scttsc.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ccell {

    private String intId;
    private String objectRdn;

    private String name;

    private BigDecimal bssid;
    private BigDecimal btsid;
    private BigDecimal cellid;

    private BigDecimal adjNum;

    private String uniqueRdn;

    private String zhName;


    private BigDecimal vendorId;

    private BigDecimal omcId;


    private BigDecimal relatedOmc;


    private BigDecimal relatedMsc;

    private BigDecimal relatedBsc;


    private BigDecimal relatedBts;


    private String mscName;


    private String bscName;


    private String btsName;

    private BigDecimal cityId;


    private Date timeStamp;


    private BigDecimal confirmed;


    private BigDecimal ci;


    private BigDecimal sectorloc;


    private BigDecimal numfa;


    private Double capacity;


    private BigDecimal microcell;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.PN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal pn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.PN_INC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal pnInc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.LAC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal lac;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.STATUS
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.DO_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal doCell;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.RELATED_RNC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal relatedRnc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.RNC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private String rncName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.SID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal sid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.NID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal nid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.NUMFA_DO
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private BigDecimal numfaDo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column C_CELL.RRU_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    private String rruCell;

  
    

    public String getIntId() {
		return intId;
	}

	public void setIntId(String intId) {
		this.intId = intId;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.OBJECT_RDN
     *
     * @return the value of C_CELL.OBJECT_RDN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getObjectRdn() {
        return objectRdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.OBJECT_RDN
     *
     * @param objectRdn the value for C_CELL.OBJECT_RDN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setObjectRdn(String objectRdn) {
        this.objectRdn = objectRdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.NAME
     *
     * @return the value of C_CELL.NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.NAME
     *
     * @param name the value for C_CELL.NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.BSSID
     *
     * @return the value of C_CELL.BSSID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getBssid() {
        return bssid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.BSSID
     *
     * @param bssid the value for C_CELL.BSSID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setBssid(BigDecimal bssid) {
        this.bssid = bssid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.BTSID
     *
     * @return the value of C_CELL.BTSID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getBtsid() {
        return btsid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.BTSID
     *
     * @param btsid the value for C_CELL.BTSID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setBtsid(BigDecimal btsid) {
        this.btsid = btsid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.CELLID
     *
     * @return the value of C_CELL.CELLID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getCellid() {
        return cellid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.CELLID
     *
     * @param cellid the value for C_CELL.CELLID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setCellid(BigDecimal cellid) {
        this.cellid = cellid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.ADJ_NUM
     *
     * @return the value of C_CELL.ADJ_NUM
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getAdjNum() {
        return adjNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.ADJ_NUM
     *
     * @param adjNum the value for C_CELL.ADJ_NUM
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setAdjNum(BigDecimal adjNum) {
        this.adjNum = adjNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.UNIQUE_RDN
     *
     * @return the value of C_CELL.UNIQUE_RDN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getUniqueRdn() {
        return uniqueRdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.UNIQUE_RDN
     *
     * @param uniqueRdn the value for C_CELL.UNIQUE_RDN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setUniqueRdn(String uniqueRdn) {
        this.uniqueRdn = uniqueRdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.ZH_NAME
     *
     * @return the value of C_CELL.ZH_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.ZH_NAME
     *
     * @param zhName the value for C_CELL.ZH_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.VENDOR_ID
     *
     * @return the value of C_CELL.VENDOR_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getVendorId() {
        return vendorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.VENDOR_ID
     *
     * @param vendorId the value for C_CELL.VENDOR_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setVendorId(BigDecimal vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.OMC_ID
     *
     * @return the value of C_CELL.OMC_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getOmcId() {
        return omcId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.OMC_ID
     *
     * @param omcId the value for C_CELL.OMC_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setOmcId(BigDecimal omcId) {
        this.omcId = omcId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RELATED_OMC
     *
     * @return the value of C_CELL.RELATED_OMC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getRelatedOmc() {
        return relatedOmc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RELATED_OMC
     *
     * @param relatedOmc the value for C_CELL.RELATED_OMC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRelatedOmc(BigDecimal relatedOmc) {
        this.relatedOmc = relatedOmc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RELATED_MSC
     *
     * @return the value of C_CELL.RELATED_MSC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getRelatedMsc() {
        return relatedMsc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RELATED_MSC
     *
     * @param relatedMsc the value for C_CELL.RELATED_MSC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRelatedMsc(BigDecimal relatedMsc) {
        this.relatedMsc = relatedMsc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RELATED_BSC
     *
     * @return the value of C_CELL.RELATED_BSC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getRelatedBsc() {
        return relatedBsc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RELATED_BSC
     *
     * @param relatedBsc the value for C_CELL.RELATED_BSC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRelatedBsc(BigDecimal relatedBsc) {
        this.relatedBsc = relatedBsc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RELATED_BTS
     *
     * @return the value of C_CELL.RELATED_BTS
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getRelatedBts() {
        return relatedBts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RELATED_BTS
     *
     * @param relatedBts the value for C_CELL.RELATED_BTS
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRelatedBts(BigDecimal relatedBts) {
        this.relatedBts = relatedBts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.MSC_NAME
     *
     * @return the value of C_CELL.MSC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getMscName() {
        return mscName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.MSC_NAME
     *
     * @param mscName the value for C_CELL.MSC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setMscName(String mscName) {
        this.mscName = mscName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.BSC_NAME
     *
     * @return the value of C_CELL.BSC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getBscName() {
        return bscName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.BSC_NAME
     *
     * @param bscName the value for C_CELL.BSC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setBscName(String bscName) {
        this.bscName = bscName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.BTS_NAME
     *
     * @return the value of C_CELL.BTS_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getBtsName() {
        return btsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.BTS_NAME
     *
     * @param btsName the value for C_CELL.BTS_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.CITY_ID
     *
     * @return the value of C_CELL.CITY_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.CITY_ID
     *
     * @param cityId the value for C_CELL.CITY_ID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.TIME_STAMP
     *
     * @return the value of C_CELL.TIME_STAMP
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.TIME_STAMP
     *
     * @param timeStamp the value for C_CELL.TIME_STAMP
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.CONFIRMED
     *
     * @return the value of C_CELL.CONFIRMED
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getConfirmed() {
        return confirmed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.CONFIRMED
     *
     * @param confirmed the value for C_CELL.CONFIRMED
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setConfirmed(BigDecimal confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.CI
     *
     * @return the value of C_CELL.CI
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getCi() {
        return ci;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.CI
     *
     * @param ci the value for C_CELL.CI
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setCi(BigDecimal ci) {
        this.ci = ci;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.SECTORLOC
     *
     * @return the value of C_CELL.SECTORLOC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getSectorloc() {
        return sectorloc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.SECTORLOC
     *
     * @param sectorloc the value for C_CELL.SECTORLOC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setSectorloc(BigDecimal sectorloc) {
        this.sectorloc = sectorloc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.NUMFA
     *
     * @return the value of C_CELL.NUMFA
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getNumfa() {
        return numfa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.NUMFA
     *
     * @param numfa the value for C_CELL.NUMFA
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setNumfa(BigDecimal numfa) {
        this.numfa = numfa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.CAPACITY
     *
     * @return the value of C_CELL.CAPACITY
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public Double getCapacity() {
        return capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.CAPACITY
     *
     * @param capacity the value for C_CELL.CAPACITY
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.MICROCELL
     *
     * @return the value of C_CELL.MICROCELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getMicrocell() {
        return microcell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.MICROCELL
     *
     * @param microcell the value for C_CELL.MICROCELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setMicrocell(BigDecimal microcell) {
        this.microcell = microcell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.PN
     *
     * @return the value of C_CELL.PN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getPn() {
        return pn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.PN
     *
     * @param pn the value for C_CELL.PN
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setPn(BigDecimal pn) {
        this.pn = pn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.PN_INC
     *
     * @return the value of C_CELL.PN_INC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getPnInc() {
        return pnInc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.PN_INC
     *
     * @param pnInc the value for C_CELL.PN_INC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setPnInc(BigDecimal pnInc) {
        this.pnInc = pnInc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.LAC
     *
     * @return the value of C_CELL.LAC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getLac() {
        return lac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.LAC
     *
     * @param lac the value for C_CELL.LAC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setLac(BigDecimal lac) {
        this.lac = lac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.STATUS
     *
     * @return the value of C_CELL.STATUS
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.STATUS
     *
     * @param status the value for C_CELL.STATUS
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.DO_CELL
     *
     * @return the value of C_CELL.DO_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getDoCell() {
        return doCell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.DO_CELL
     *
     * @param doCell the value for C_CELL.DO_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setDoCell(BigDecimal doCell) {
        this.doCell = doCell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RELATED_RNC
     *
     * @return the value of C_CELL.RELATED_RNC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getRelatedRnc() {
        return relatedRnc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RELATED_RNC
     *
     * @param relatedRnc the value for C_CELL.RELATED_RNC
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRelatedRnc(BigDecimal relatedRnc) {
        this.relatedRnc = relatedRnc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RNC_NAME
     *
     * @return the value of C_CELL.RNC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getRncName() {
        return rncName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RNC_NAME
     *
     * @param rncName the value for C_CELL.RNC_NAME
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRncName(String rncName) {
        this.rncName = rncName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.SID
     *
     * @return the value of C_CELL.SID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.SID
     *
     * @param sid the value for C_CELL.SID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setSid(BigDecimal sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.NID
     *
     * @return the value of C_CELL.NID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getNid() {
        return nid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.NID
     *
     * @param nid the value for C_CELL.NID
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setNid(BigDecimal nid) {
        this.nid = nid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.NUMFA_DO
     *
     * @return the value of C_CELL.NUMFA_DO
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public BigDecimal getNumfaDo() {
        return numfaDo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.NUMFA_DO
     *
     * @param numfaDo the value for C_CELL.NUMFA_DO
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setNumfaDo(BigDecimal numfaDo) {
        this.numfaDo = numfaDo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column C_CELL.RRU_CELL
     *
     * @return the value of C_CELL.RRU_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public String getRruCell() {
        return rruCell;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column C_CELL.RRU_CELL
     *
     * @param rruCell the value for C_CELL.RRU_CELL
     *
     * @mbggenerated Wed Apr 10 15:30:30 CST 2013
     */
    public void setRruCell(String rruCell) {
        this.rruCell = rruCell;
    }
}