package com.scttsc.business.model;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.model.WyLibVillage;
import com.scttsc.common.util.DateUtils;

import java.util.Date;

public class WyTunelManual {
    private Long intId;

    private Short prop;

    private Integer roadId;

    private Integer tunelLength;

    private String antennatype;

    private String linetype;

    private String covertype;

    private String coverrangedesc;

    private String address;

    private Integer tunelHigh;

    private Integer rrunum;

    private String rruaddress;

    private Integer repeaternum;

    private String repeateraddress;

    private Integer drynum;

    private String dryaddress;

    private Integer shareflag;

    private String mchroomflag;

    private Integer mchroonarea;

    private String meteradress;

    private Date builddate;

    private String owner;

    private String ownertel;

    private String remark;

    private Date intime;

    private Long inuser;

    private String builddateStr;

    //增加
    private String town;
    private String village;
    private WyLibVillage wyLibVillage;


    //冗余
    private Cons propCons;
    private Cons shareCons;
    private RoadLib roadLib;//关联道路库

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public Short getProp() {
        return prop;
    }

    public void setProp(Short prop) {
        this.prop = prop;
    }

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public Integer getTunelLength() {
        return tunelLength;
    }

    public void setTunelLength(Integer tunelLength) {
        this.tunelLength = tunelLength;
    }

    public String getAntennatype() {
        return antennatype;
    }

    public void setAntennatype(String antennatype) {
        this.antennatype = antennatype;
    }

    public String getLinetype() {
        return linetype;
    }

    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    public String getCovertype() {
        return covertype;
    }

    public void setCovertype(String covertype) {
        this.covertype = covertype;
    }

    public String getCoverrangedesc() {
        return coverrangedesc;
    }

    public void setCoverrangedesc(String coverrangedesc) {
        this.coverrangedesc = coverrangedesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTunelHigh() {
        return tunelHigh;
    }

    public void setTunelHigh(Integer tunelHigh) {
        this.tunelHigh = tunelHigh;
    }

    public Integer getRrunum() {
        return rrunum;
    }

    public void setRrunum(Integer rrunum) {
        this.rrunum = rrunum;
    }

    public String getRruaddress() {
        return rruaddress;
    }

    public void setRruaddress(String rruaddress) {
        this.rruaddress = rruaddress;
    }

    public Integer getRepeaternum() {
        return repeaternum;
    }

    public void setRepeaternum(Integer repeaternum) {
        this.repeaternum = repeaternum;
    }

    public String getRepeateraddress() {
        return repeateraddress;
    }

    public void setRepeateraddress(String repeateraddress) {
        this.repeateraddress = repeateraddress;
    }

    public Integer getDrynum() {
        return drynum;
    }

    public void setDrynum(Integer drynum) {
        this.drynum = drynum;
    }

    public String getDryaddress() {
        return dryaddress;
    }

    public void setDryaddress(String dryaddress) {
        this.dryaddress = dryaddress;
    }

    public Integer getShareflag() {
        return shareflag;
    }

    public void setShareflag(Integer shareflag) {
        this.shareflag = shareflag;
    }

    public String getMchroomflag() {
        return mchroomflag;
    }

    public void setMchroomflag(String mchroomflag) {
        this.mchroomflag = mchroomflag;
    }

    public Integer getMchroonarea() {
        return mchroonarea;
    }

    public void setMchroonarea(Integer mchroonarea) {
        this.mchroonarea = mchroonarea;
    }

    public String getMeteradress() {
        return meteradress;
    }

    public void setMeteradress(String meteradress) {
        this.meteradress = meteradress;
    }

    public Date getBuilddate() {
        return builddate;
    }

    public void setBuilddate(Date builddate) {
        this.builddate = builddate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnertel() {
        return ownertel;
    }

    public void setOwnertel(String ownertel) {
        this.ownertel = ownertel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Long getInuser() {
        return inuser;
    }

    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }

    public String getBuilddateStr() {
        return builddate==null?"": DateUtils.DateToStr(builddate, "yyyy-MM");
    }

    public void setBuilddateStr(String builddateStr) {
        this.builddateStr = builddateStr;
    }

    public Cons getPropCons() {
        return propCons;
    }

    public void setPropCons(Cons propCons) {
        this.propCons = propCons;
    }

    public Cons getShareCons() {
        return shareCons;
    }

    public void setShareCons(Cons shareCons) {
        this.shareCons = shareCons;
    }

    public RoadLib getRoadLib() {
        return roadLib;
    }

    public void setRoadLib(RoadLib roadLib) {
        this.roadLib = roadLib;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public WyLibVillage getWyLibVillage() {
        return wyLibVillage;
    }

    public void setWyLibVillage(WyLibVillage wyLibVillage) {
        this.wyLibVillage = wyLibVillage;
    }
}