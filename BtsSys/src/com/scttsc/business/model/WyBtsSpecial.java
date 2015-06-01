package com.scttsc.business.model;

import com.scttsc.admin.model.City;
import com.scttsc.common.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class WyBtsSpecial {
    private BigDecimal intId;

    private String name;

    private BigDecimal cityId;

    private String bscName;

    private BigDecimal btsId;

    private String btsname;

    private Date intime;

    private Date updatetime;

    private BigDecimal type;

    private BigDecimal netType;

    private BigDecimal state;

    private BigDecimal deleteFlag;

    private City city;

    //冗余
    private String typeStr;
    private String netTypeStr;
    private String intimeStr;
    private String stateStr;

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
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName == null ? null : bscName.trim();
    }

    public BigDecimal getBtsId() {
        return btsId;
    }

    public void setBtsId(BigDecimal btsId) {
        this.btsId = btsId;
    }

    public String getBtsname() {
        return btsname;
    }

    public void setBtsname(String btsname) {
        this.btsname = btsname == null ? null : btsname.trim();
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public BigDecimal getNetType() {
        return netType;
    }

    public void setNetType(BigDecimal netType) {
        this.netType = netType;
    }

    public BigDecimal getState() {
        return state;
    }

    public void setState(BigDecimal state) {
        this.state = state;
    }

    public BigDecimal getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(BigDecimal deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getTypeStr() {
        if(type==null){
            return "";
        }
        switch (type.intValue()){
            case 1:
                typeStr="小区";
                break;
            case 2:
                typeStr="基站";
                break;
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getNetTypeStr() {
        if(netType==null){
            return "";
        }
        switch (netType.intValue()){
            case 1:
                netTypeStr="CDMA";
                break;
            case 2:
                netTypeStr="LTE";
                break;
        }
        return netTypeStr;
    }

    public void setNetTypeStr(String netTypeStr) {
        this.netTypeStr = netTypeStr;
    }

    public String getIntimeStr() {
         return intime == null ? "" : DateUtils.DateToStr(intime, "yyyy-MM-dd");
    }

    public void setIntimeStr(String intimeStr) {
        this.intimeStr = intimeStr;
    }

    public String getStateStr() {
        if(state==null){
            return "";
        }
        switch (state.intValue()){
            case 1:
                stateStr="新建未验收站";
                break;
            case 2:
                stateStr="调测站";
                break;
            case 3:
                stateStr="升级站";
                break;
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }
}