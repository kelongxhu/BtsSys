package com.scttsc.healthy.model;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.common.util.ConstantUtil;

import java.util.List;

public class WyRuleterm {
    private Long id;

    private Long rulecfgid;

    private Long subcfgid;

    private Integer dataType;//数据类型

    private Integer symbol;

    private String value1;

    private String value2;

    private WySubcfg wySubcfg;

    private String dataTypeStr;//冗余

    private String symbolStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRulecfgid() {
        return rulecfgid;
    }

    public void setRulecfgid(Long rulecfgid) {
        this.rulecfgid = rulecfgid;
    }

    public Long getSubcfgid() {
        return subcfgid;
    }

    public void setSubcfgid(Long subcfgid) {
        this.subcfgid = subcfgid;
    }

    public Integer getSymbol() {
        return symbol;
    }

    public void setSymbol(Integer symbol) {
        this.symbol = symbol;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1 == null ? null : value1.trim();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2 == null ? null : value2.trim();
    }


    public WySubcfg getWySubcfg() {
        return wySubcfg;
    }

    public void setWySubcfg(WySubcfg wySubcfg) {
        this.wySubcfg = wySubcfg;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeStr() {
        if (dataType != null) {
            switch (dataType.intValue()) {
                case 1:
                    dataTypeStr = "字符串";
                    break;
                case 2:
                    dataTypeStr = "数字";
                    break;
                case 3:
                    dataTypeStr = "日期";
                    break;
                default:
                    break;
            }
        }
        return dataTypeStr;
    }

    public void setDataTypeStr(String dataTypeStr) {
        this.dataTypeStr = dataTypeStr;
    }

    public String getSymbolStr() {
        List<Cons> cons = ConstantUtil.getInstance().getConListByGroupCode("SYMBOL");
        if (cons != null) {
            for (Cons con : cons) {
                int code = con.getCode();
                if (code == symbol) {
                    symbolStr = con.getName();
                    break;
                }
            }
        }
        return symbolStr;
    }

    public void setSymbolStr(String symbolStr) {
        this.symbolStr = symbolStr;
    }
}