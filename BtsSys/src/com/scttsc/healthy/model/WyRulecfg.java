package com.scttsc.healthy.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WyRulecfg {
    private Long id;

    private String ruledesc;

    private Integer grade;  //规则分数

    private Integer gradetype;//规则类型

    private BigDecimal aweight;

    private BigDecimal bweight;

    private BigDecimal cweight;

    private BigDecimal dweight;

    private Integer gradeflag;  //得分规则

    private String suggest;

    private Integer status;  //状态

    private Date updatetime;

    private Long userId;

    private String gradeTypeStr; //冗余
    private String statusStr;
    private String gradeflagStr;//得分规则

    //条件配置
    private List<WyRuleterm> wyRuletermList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuledesc() {
        return ruledesc;
    }

    public void setRuledesc(String ruledesc) {
        this.ruledesc = ruledesc;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGradetype() {
        return gradetype;
    }

    public void setGradetype(Integer gradetype) {
        this.gradetype = gradetype;
    }

    public BigDecimal getAweight() {
        return aweight;
    }

    public void setAweight(BigDecimal aweight) {
        this.aweight = aweight;
    }

    public BigDecimal getBweight() {
        return bweight;
    }

    public void setBweight(BigDecimal bweight) {
        this.bweight = bweight;
    }

    public BigDecimal getCweight() {
        return cweight;
    }

    public void setCweight(BigDecimal cweight) {
        this.cweight = cweight;
    }

    public BigDecimal getDweight() {
        return dweight;
    }

    public void setDweight(BigDecimal dweight) {
        this.dweight = dweight;
    }

    public Integer getGradeflag() {
        return gradeflag;
    }

    public void setGradeflag(Integer gradeflag) {
        this.gradeflag = gradeflag;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<WyRuleterm> getWyRuletermList() {
        return wyRuletermList;
    }

    public void setWyRuletermList(List<WyRuleterm> wyRuletermList) {
        this.wyRuletermList = wyRuletermList;
    }

    public String getGradeTypeStr() {
        if (gradetype != null) {
            switch (gradetype.intValue()) {
                case 1:
                    gradeTypeStr = "基础信息";
                    break;
                case 2:
                    gradeTypeStr = "巡检信息";
                    break;
                case 3:
                    gradeTypeStr = "告警信息";
                    break;
                case 4:
                    gradeTypeStr = "无线指标";
                    break;
                default:
                    break;
            }
        }
        return gradeTypeStr;
    }

    public void setGradeTypeStr(String gradeTypeStr) {
        this.gradeTypeStr = gradeTypeStr;
    }

    public String getStatusStr() {
        if (status != null) {
            switch (status.intValue()) {
                case 1:
                    statusStr = "评分";
                    break;
                case 2:
                    statusStr = "整改";
                    break;
                case 3:
                    statusStr = "评分/整改";
                    break;
                default:
                    break;
            }
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getGradeflagStr() {
        if (gradeflag != null) {
            switch (gradeflag.intValue()) {
                case 1:
                    gradeflagStr = "分数*权值";
                    break;
                default:
                    break;
            }
        }
        return gradeflagStr;
    }

    public void setGradeflagStr(String gradeflagStr) {
        this.gradeflagStr = gradeflagStr;
    }
}