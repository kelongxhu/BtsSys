package com.scttsc.baselibs.model;

import com.scttsc.common.dao.MyBatisRepository;

import java.math.BigDecimal;
import java.util.Date;

@MyBatisRepository
public class RoadLib {

    private BigDecimal id;
    private BigDecimal cityId;
    private Integer roadProp;//道路类别
    private String roadNo;//道路编号
    private String name;
    private String domesiicStart;
    private String domesiicEnd;
    private BigDecimal mileage;
    private Integer openStatus;
    private Date intime;
    private Long inUser;
    private Date updatetime;
    private String remark;
    private Integer deleteFlag;
    //冗余字段
    private String cityName;//本地网名称
    private String roadPropName;//属性名称
    private String openStatusStr;//开通状态

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomesiicStart() {
        return domesiicStart;
    }

    public void setDomesiicStart(String domesiicStart) {
        this.domesiicStart = domesiicStart;
    }

    public String getDomesiicEnd() {
        return domesiicEnd;
    }

    public void setDomesiicEnd(String domesiicEnd) {
        this.domesiicEnd = domesiicEnd;
    }

    public BigDecimal getMileage() {
        return mileage;
    }

    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Long getInUser() {
        return inUser;
    }

    public void setInUser(Long inUser) {
        this.inUser = inUser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRoadPropName() {
        return roadPropName;
    }

    public void setRoadPropName(String roadPropName) {
        this.roadPropName = roadPropName;
    }

    public Integer getRoadProp() {
        return roadProp;
    }

    public void setRoadProp(Integer roadProp) {
        this.roadProp = roadProp;
    }

    public String getOpenStatusStr() {
        if (openStatus == null) {
            return "";
        }
        switch (openStatus) {
            case 1:
                openStatusStr = "已经开通";
                break;
            case 2:
                openStatusStr = "部分开通";
                break;
            default:
                break;
        }
        return openStatusStr;
    }

    public void setOpenStatusStr(String openStatusStr) {
        this.openStatusStr = openStatusStr;
    }
}