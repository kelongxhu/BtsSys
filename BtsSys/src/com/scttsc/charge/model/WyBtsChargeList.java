package com.scttsc.charge.model;

import com.scttsc.business.util.Constants;
import com.scttsc.common.util.DateUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

public class WyBtsChargeList {
    private BigDecimal id;

    private BigDecimal intId;

    private Short btsType;

    private Short costType;

    private BigDecimal money;

    private String proofFile;

    private String remark;

    private Date payTime;

    private String payUser;

    private Short isTimeout;

    private Date inTime;

    private Integer inUser;

    private BigDecimal monthDegree;

    private BigDecimal baseDegree;

    private Short payType;
    //冗余一个payCycle
    private int payCycle;
    //缴费时间
    private String payTimeStr;
    private String isTimeoutStr;//是否延期
    private String proofFileName;//凭证名称
    private String payTypeStr;//缴费类型
    private Integer payDay;//从费用设置处冗余一个payDay统计是否超时

    //冗余基站字段
    private String cityName;
    private String countryName;

    private Integer cityId;
    private Integer countryId;
    private String btsName;
    private String bscName;
    private Integer btsId;

    private String costTypeStr;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getProofFile() {
        return proofFile;
    }

    public void setProofFile(String proofFile) {
        this.proofFile = proofFile == null ? null : proofFile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }

    public Short getIsTimeout() {
        return isTimeout;
    }

    public void setIsTimeout(Short isTimeout) {
        this.isTimeout = isTimeout;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public BigDecimal getMonthDegree() {
        return monthDegree;
    }

    public void setMonthDegree(BigDecimal monthDegree) {
        this.monthDegree = monthDegree;
    }

    public BigDecimal getBaseDegree() {
        return baseDegree;
    }

    public void setBaseDegree(BigDecimal baseDegree) {
        this.baseDegree = baseDegree;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public Integer getInUser() {
        return inUser;
    }

    public void setInUser(Integer inUser) {
        this.inUser = inUser;
    }

    public int getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(int payCycle) {
        this.payCycle = payCycle;
    }

    public String getPayTimeStr() {
        return DateUtils.DateToStr(payTime, "yyyy-MM-dd");
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }

    public String getIsTimeoutStr() {
        if (isTimeout == null) {
            return "";
        }
        switch (isTimeout) {
            case 0:
                isTimeoutStr = "否";
                break;
            case 1:
                isTimeoutStr = "是";
                break;
            default:
                break;
        }
        return isTimeoutStr;
    }

    public void setIsTimeoutStr(String isTimeoutStr) {
        this.isTimeoutStr = isTimeoutStr;
    }


    public String getPayTypeStr() {
        if (payType == null) {
            return "";
        }
        switch (isTimeout) {
            case 0:
                payTypeStr = "人工";
                break;
            case 1:
                payTypeStr = "代扣";
                break;
            default:
                break;
        }
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public String getProofFileName() {
        if (proofFile != null) {
            int index = proofFile.lastIndexOf(File.separator);
            if (index != -1) {
                proofFileName = proofFile.substring(index);
            } else {
                proofFileName = proofFile;
            }
        }
        return proofFileName;
    }

    public void setProofFileName(String proofFileName) {
        this.proofFileName = proofFileName;
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
        if (costType == null) {
            return "";
        }
        switch (costType) {
            case 1:
                costTypeStr = "房租";
                break;
            case 2:
                costTypeStr = "物业";
                break;
            case 3:
                costTypeStr = "电费";
                break;
            default:
                break;
        }
        return costTypeStr;
    }

    public void setCostTypeStr(String costTypeStr) {
        this.costTypeStr = costTypeStr;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getPayDay() {
        return payDay;
    }

    public void setPayDay(Integer payDay) {
        this.payDay = payDay;
    }
}