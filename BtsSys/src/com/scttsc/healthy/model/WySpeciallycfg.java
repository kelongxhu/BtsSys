package com.scttsc.healthy.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WySpeciallycfg {
    private Long id;

    private String name;

    private String specdesc;

    private Long userid;

    private Date updatetime;

    private List<WyRulecfg> wyRulecfgList;

    //冗余
    private String ruleCfgIds;//规则ID集合。

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecdesc() {
        return specdesc;
    }

    public void setSpecdesc(String specdesc) {
        this.specdesc = specdesc == null ? null : specdesc.trim();
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<WyRulecfg> getWyRulecfgList() {
        return wyRulecfgList;
    }

    public void setWyRulecfgList(List<WyRulecfg> wyRulecfgList) {
        this.wyRulecfgList = wyRulecfgList;
    }

    public Long getUserid() {
        return userid;
    }

    public String getRuleCfgIds() {
        return ruleCfgIds;
    }

    public void setRuleCfgIds(String ruleCfgIds) {
        this.ruleCfgIds = ruleCfgIds;
    }
}