package com.scttsc.business.model;

/**
 * 废弃数据和现有数据
 * Created by _think on 2014/10/12.
 */
public class MateEntry {
    private String del_IntId;
    private String del_Name;
    private String del_BscName;
    private Integer del_BtsId;
    private Integer del_ManualFlag;
    private Integer del_ci;
    private Integer del_pn;
    private String intId;
    private String name;
    private String bscName;
    private Integer btsId;
    private Integer manualFlag;
    private Integer ci;
    private Integer pn;

    public String getDel_IntId() {
        return del_IntId;
    }

    public void setDel_IntId(String del_IntId) {
        this.del_IntId = del_IntId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

    public String getDel_Name() {
        return del_Name;
    }

    public void setDel_Name(String del_Name) {
        this.del_Name = del_Name;
    }

    public String getDel_BscName() {
        return del_BscName;
    }

    public void setDel_BscName(String del_BscName) {
        this.del_BscName = del_BscName;
    }

    public Integer getDel_BtsId() {
        return del_BtsId;
    }

    public void setDel_BtsId(Integer del_BtsId) {
        this.del_BtsId = del_BtsId;
    }

    public String getIntId() {
        return intId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getDel_ManualFlag() {
        return del_ManualFlag;
    }

    public void setDel_ManualFlag(Integer del_ManualFlag) {
        this.del_ManualFlag = del_ManualFlag;
    }

    public Integer getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public Integer getDel_ci() {
        return del_ci;
    }

    public void setDel_ci(Integer del_ci) {
        this.del_ci = del_ci;
    }

    public Integer getDel_pn() {
        return del_pn;
    }

    public void setDel_pn(Integer del_pn) {
        this.del_pn = del_pn;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }
}
