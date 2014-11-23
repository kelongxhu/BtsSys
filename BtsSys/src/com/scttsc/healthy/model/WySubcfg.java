package com.scttsc.healthy.model;

import java.math.BigDecimal;

public class WySubcfg {
    private BigDecimal id;

    private String sub;

    private String subchild;

    private String columnname;

    private BigDecimal seq;

    private String subdesc;

    private int subType;

    private int code1;

    private int code2;



    //冗余一个字段
    private String subjectName;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub == null ? null : sub.trim();
    }

    public String getSubchild() {
        return subchild;
    }

    public void setSubchild(String subchild) {
        this.subchild = subchild == null ? null : subchild.trim();
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }


    public BigDecimal getSeq() {
        return seq;
    }

    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }

    public String getSubdesc() {
        return subdesc;
    }

    public void setSubdesc(String subdesc) {
        this.subdesc = subdesc == null ? null : subdesc.trim();
    }

    public String getSubjectName() {
        return sub+"_"+subchild;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getCode1() {
        return code1;
    }

    public void setCode1(int code1) {
        this.code1 = code1;
    }

    public int getCode2() {
        return code2;
    }

    public void setCode2(int code2) {
        this.code2 = code2;
    }
}