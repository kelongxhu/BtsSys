package com.scttsc.charge.model;

import java.math.BigDecimal;
import java.util.Date;

public class WyBtsCharge {
    private BigDecimal intId;

    private Short btsType;

    private Short costType;

    private Date contractStarttime;

    private Date contractEndtime;

    private Short payCycle;

    private Short payDay;

    private Short aheadDay;

    private String remindUser;

    private String remindTel;

    private BigDecimal money;

    private String contractFile;

    private String remark;

    private String eachTel;

    private String eachAccountname;

    private String eachBanknum;

    private Short payType;

    private String bankAccount;

    private BigDecimal balance;

    private Date inTime;//插入时间

    private BigDecimal inUser;//插入用户


    //冗余基站字段
    private String cityName;
    private String countryName;
    private String btsName;
    private String bscName;
    private Integer btsId;

    private String costTypeStr;




    public BigDecimal getIntId() {
        return intId;
    }

    public void setIntId(BigDecimal intId) {
        this.intId = intId;
    }

    public Short getBtsType() {
        return btsType;
    }

    public void setBtsType(Short btsType) {
        this.btsType = btsType;
    }

    public Short getCostType() {
        return costType;
    }

    public void setCostType(Short costType) {
        this.costType = costType;
    }

    public Date getContractStarttime() {
        return contractStarttime;
    }

    public void setContractStarttime(Date contractStarttime) {
        this.contractStarttime = contractStarttime;
    }

    public Date getContractEndtime() {
        return contractEndtime;
    }

    public void setContractEndtime(Date contractEndtime) {
        this.contractEndtime = contractEndtime;
    }

    public Short getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(Short payCycle) {
        this.payCycle = payCycle;
    }

    public Short getPayDay() {
        return payDay;
    }

    public void setPayDay(Short payDay) {
        this.payDay = payDay;
    }

    public Short getAheadDay() {
        return aheadDay;
    }

    public void setAheadDay(Short aheadDay) {
        this.aheadDay = aheadDay;
    }

    public String getRemindUser() {
        return remindUser;
    }

    public void setRemindUser(String remindUser) {
        this.remindUser = remindUser == null ? null : remindUser.trim();
    }

    public String getRemindTel() {
        return remindTel;
    }

    public void setRemindTel(String remindTel) {
        this.remindTel = remindTel == null ? null : remindTel.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile == null ? null : contractFile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEachTel() {
        return eachTel;
    }

    public void setEachTel(String eachTel) {
        this.eachTel = eachTel == null ? null : eachTel.trim();
    }

    public String getEachAccountname() {
        return eachAccountname;
    }

    public void setEachAccountname(String eachAccountname) {
        this.eachAccountname = eachAccountname == null ? null : eachAccountname.trim();
    }

    public String getEachBanknum() {
        return eachBanknum;
    }

    public void setEachBanknum(String eachBanknum) {
        this.eachBanknum = eachBanknum == null ? null : eachBanknum.trim();
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public Integer getBtsId() {
        return btsId;
    }

    public void setBtsId(Integer btsId) {
        this.btsId = btsId;
    }


    public String getCostTypeStr() {
        if(costType==null){
            return "";
        }
        switch (costType){
            case 1:
                costTypeStr="房租";
                break;
            case 2:
                costTypeStr="物业";
                break;
            case 3:
                costTypeStr="电费";
                break;
            default:
                break;
        }
        return costTypeStr;
    }

    public void setCostTypeStr(String costTypeStr) {
        this.costTypeStr = costTypeStr;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public BigDecimal getInUser() {
        return inUser;
    }

    public void setInUser(BigDecimal inUser) {
        this.inUser = inUser;
    }
}