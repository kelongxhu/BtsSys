package com.scttsc.business.model;

import com.scttsc.admin.model.City;
import com.scttsc.baselibs.model.Cons;
import org.apache.commons.lang.time.DateFormatUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ErectStation {

    private Long id;

    private BigDecimal wybtsintid;

    private String wybtsname;

    private BigDecimal cityId;

    private BigDecimal no;

    private String type;

    private String model;

    private String fac;

    private String address;

    private String coverage;

    private String monitorfalg;

    private String monitornumber;

    private String meteraddress;

    private String remark;

    private Date updatetime;

    private Long updateuser;

    private City country;//区县

    private Bts bts;


    public Bts getBts() {
        return bts;
    }

    public void setBts(Bts bts) {
        this.bts = bts;
    }

    public City getCountry() {
        return country;
    }

    public void setCountry(City country) {
        this.country = country;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.WYBTSINTID
     *
     * @return the value of WY_ERECTSTATION.WYBTSINTID
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public BigDecimal getWybtsintid() {
        return wybtsintid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.WYBTSINTID
     *
     * @param wybtsintid the value for WY_ERECTSTATION.WYBTSINTID
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setWybtsintid(BigDecimal wybtsintid) {
        this.wybtsintid = wybtsintid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.WYBTSNAME
     *
     * @return the value of WY_ERECTSTATION.WYBTSNAME
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getWybtsname() {
        return wybtsname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.WYBTSNAME
     *
     * @param wybtsname the value for WY_ERECTSTATION.WYBTSNAME
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setWybtsname(String wybtsname) {
        this.wybtsname = wybtsname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.CITY_ID
     *
     * @return the value of WY_ERECTSTATION.CITY_ID
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public BigDecimal getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.CITY_ID
     *
     * @param cityId the value for WY_ERECTSTATION.CITY_ID
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.NO
     *
     * @return the value of WY_ERECTSTATION.NO
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public BigDecimal getNo() {
        return no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.NO
     *
     * @param no the value for WY_ERECTSTATION.NO
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setNo(BigDecimal no) {
        this.no = no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.TYPE
     *
     * @return the value of WY_ERECTSTATION.TYPE
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.TYPE
     *
     * @param type the value for WY_ERECTSTATION.TYPE
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.MODEL
     *
     * @return the value of WY_ERECTSTATION.MODEL
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getModel() {
        return model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.MODEL
     *
     * @param model the value for WY_ERECTSTATION.MODEL
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.FAC
     *
     * @return the value of WY_ERECTSTATION.FAC
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getFac() {
        return fac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.FAC
     *
     * @param fac the value for WY_ERECTSTATION.FAC
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setFac(String fac) {
        this.fac = fac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.ADDRESS
     *
     * @return the value of WY_ERECTSTATION.ADDRESS
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.ADDRESS
     *
     * @param address the value for WY_ERECTSTATION.ADDRESS
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.COVERAGE
     *
     * @return the value of WY_ERECTSTATION.COVERAGE
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getCoverage() {
        return coverage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.COVERAGE
     *
     * @param coverage the value for WY_ERECTSTATION.COVERAGE
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.MONITORFALG
     *
     * @return the value of WY_ERECTSTATION.MONITORFALG
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getMonitorfalg() {
        return monitorfalg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.MONITORFALG
     *
     * @param monitorfalg the value for WY_ERECTSTATION.MONITORFALG
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setMonitorfalg(String monitorfalg) {
        this.monitorfalg = monitorfalg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.MONITORNUMBER
     *
     * @return the value of WY_ERECTSTATION.MONITORNUMBER
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getMonitornumber() {
        return monitornumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.MONITORNUMBER
     *
     * @param monitornumber the value for WY_ERECTSTATION.MONITORNUMBER
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setMonitornumber(String monitornumber) {
        this.monitornumber = monitornumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.METERADDRESS
     *
     * @return the value of WY_ERECTSTATION.METERADDRESS
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getMeteraddress() {
        return meteraddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.METERADDRESS
     *
     * @param meteraddress the value for WY_ERECTSTATION.METERADDRESS
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setMeteraddress(String meteraddress) {
        this.meteraddress = meteraddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.REMARK
     *
     * @return the value of WY_ERECTSTATION.REMARK
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.REMARK
     *
     * @param remark the value for WY_ERECTSTATION.REMARK
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WY_ERECTSTATION.UPDATETIME
     *
     * @return the value of WY_ERECTSTATION.UPDATETIME
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public Date getUpdatetime() {

        return updatetime;
    }

    public String getUpdatetimeToString() {
        return updatetime==null?"": DateFormatUtils.format(updatetime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WY_ERECTSTATION.UPDATETIME
     *
     * @param updatetime the value for WY_ERECTSTATION.UPDATETIME
     *
     * @mbggenerated Tue Apr 09 11:31:44 CST 2013
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }
}