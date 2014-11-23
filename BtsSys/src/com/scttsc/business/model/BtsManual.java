package com.scttsc.business.model;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.common.util.Common;

import java.math.BigDecimal;
import java.util.Date;

public class BtsManual extends Entity {

    private Long intId;
    private String name;
    private String bscName;
    private Integer btsId;
    private Integer installPos;
    private String sharFlag;
    private String sharName;
    private String address;

    //修改开通时间类型为时间类型
    private String openTime;
    private String conf1x;  //合成字段
    private String confDo;  //合成字段

    //传输配套
    private String tranModel;
    private String tranFac;
    private String tranUpsitename; //传输上游节点名称
    private String tranDownsitename;//传输下游节点名称
    private String tranNetprotect;
    private String tranIsnode;//是否节点站
    private Integer tranSitenum;//节点站下挂基站数量
    //开关电源
    private String sourModel;
    private String sourFac;
    private String sourModuleModel;
    private Integer sourModuleNum;
    private Integer sourCapa;
    //交流配电防雷
    private String boxModel;
    private String boxFac;
    //蓄电池组或UPS
    private String cellModel;//
    private String cellFac;
    private Integer cellCapa;//容量（AH）
    private Integer cellNum;//数量（组）
    private Double cellPower;//机房设备总耗电（A）
    private Double cellDuar;//支撑基站设备运行时长（小时）
    private String cellUstime;//启用时间（年月）
    //塔桅
    private Integer towerType;
    private Integer towerHigh;
    private String towerPic;
    //空调系统
    private String acModel;
    private String acFac;
    private Integer acNum;
    //动环监控系统
    private String dhModel;
    private String dhFac;
    //新风系统
    private String xfModel;
    private String xfFac;
    //机房
    private Integer mrStrut;//机房结构
    private Double mrLength;
    private Double mrWidth;
    private Double mrHigh;
    private String mrOwner;
    private String mrPic;
    //外电
    private String wdType;
    private String wdModel;
    private String wdFac;
    private String wdGds;
    //油机配置
    private String oeType;
    private String oeModel;
    private String oePower;
    private String oeFac;
    private String remark;
    private Date updatetime;
    private Long updateuser;


    Cons installPosCons;  //主设备安装位置
    Cons towerTypeCons;//塔跪类型
    Cons mrStrutCons;//机房结构



    //冗余一个传输上游节点名称
    private String tranUpsitenameStr;
    private String tranDownsitenameStr;


    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
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

    public Integer getInstallPos() {
        return installPos;
    }

    public void setInstallPos(Integer installPos) {
        this.installPos = installPos;
    }

    public String getSharFlag() {
        return sharFlag;
    }

    public void setSharFlag(String sharFlag) {
        this.sharFlag = sharFlag;
    }

    public String getSharName() {
        return sharName;
    }

    public void setSharName(String sharName) {
        this.sharName = sharName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getConf1x() {
        return conf1x;
    }

    public void setConf1x(String conf1x) {
        this.conf1x = conf1x;
    }

    public String getConfDo() {
        return confDo;
    }

    public void setConfDo(String confDo) {
        this.confDo = confDo;
    }

    public String getTranModel() {
        return tranModel;
    }

    public void setTranModel(String tranModel) {
        this.tranModel = tranModel;
    }

    public String getTranFac() {
        return tranFac;
    }

    public void setTranFac(String tranFac) {
        this.tranFac = tranFac;
    }

    public String getTranUpsitename() {
        return tranUpsitename;
    }

    public void setTranUpsitename(String tranUpsitename) {
        this.tranUpsitename = tranUpsitename;
    }

    public String getTranDownsitename() {
        return tranDownsitename;
    }

    public void setTranDownsitename(String tranDownsitename) {
        this.tranDownsitename = tranDownsitename;
    }

    public String getTranNetprotect() {
        return tranNetprotect;
    }

    public void setTranNetprotect(String tranNetprotect) {
        this.tranNetprotect = tranNetprotect;
    }

    public String getTranIsnode() {
        return tranIsnode;
    }

    public void setTranIsnode(String tranIsnode) {
        this.tranIsnode = tranIsnode;
    }

    public Integer getTranSitenum() {
        return tranSitenum;
    }

    public void setTranSitenum(Integer tranSitenum) {
        this.tranSitenum = tranSitenum;
    }

    public String getSourModel() {
        return sourModel;
    }

    public void setSourModel(String sourModel) {
        this.sourModel = sourModel;
    }

    public String getSourFac() {
        return sourFac;
    }

    public void setSourFac(String sourFac) {
        this.sourFac = sourFac;
    }

    public String getSourModuleModel() {
        return sourModuleModel;
    }

    public void setSourModuleModel(String sourModuleModel) {
        this.sourModuleModel = sourModuleModel;
    }

    public Integer getSourModuleNum() {
        return sourModuleNum;
    }

    public void setSourModuleNum(Integer sourModuleNum) {
        this.sourModuleNum = sourModuleNum;
    }

    public Integer getSourCapa() {
        return sourCapa;
    }

    public void setSourCapa(Integer sourCapa) {
        this.sourCapa = sourCapa;
    }

    public String getBoxModel() {
        return boxModel;
    }

    public void setBoxModel(String boxModel) {
        this.boxModel = boxModel;
    }

    public String getBoxFac() {
        return boxFac;
    }

    public void setBoxFac(String boxFac) {
        this.boxFac = boxFac;
    }

    public String getCellModel() {
        return cellModel;
    }

    public void setCellModel(String cellModel) {
        this.cellModel = cellModel;
    }

    public String getCellFac() {
        return cellFac;
    }

    public void setCellFac(String cellFac) {
        this.cellFac = cellFac;
    }

    public Integer getCellCapa() {
        return cellCapa;
    }

    public void setCellCapa(Integer cellCapa) {
        this.cellCapa = cellCapa;
    }

    public Integer getCellNum() {
        return cellNum;
    }

    public void setCellNum(Integer cellNum) {
        this.cellNum = cellNum;
    }

    public Double getCellPower() {
        return cellPower;
    }

    public void setCellPower(Double cellPower) {
        this.cellPower = cellPower;
    }

    public Double getCellDuar() {
        return cellDuar;
    }

    public void setCellDuar(Double cellDuar) {
        this.cellDuar = cellDuar;
    }

    public String getCellUstime() {
        return cellUstime;
    }

    public void setCellUstime(String cellUstime) {
        this.cellUstime = cellUstime;
    }

    public Integer getTowerType() {
        return towerType;
    }

    public void setTowerType(Integer towerType) {
        this.towerType = towerType;
    }

    public Integer getTowerHigh() {
        return towerHigh;
    }

    public void setTowerHigh(Integer towerHigh) {
        this.towerHigh = towerHigh;
    }

    public String getTowerPic() {
        return towerPic;
    }

    public void setTowerPic(String towerPic) {
        this.towerPic = towerPic;
    }

    public String getAcModel() {
        return acModel;
    }

    public void setAcModel(String acModel) {
        this.acModel = acModel;
    }

    public String getAcFac() {
        return acFac;
    }

    public void setAcFac(String acFac) {
        this.acFac = acFac;
    }

    public Integer getAcNum() {
        return acNum;
    }

    public void setAcNum(Integer acNum) {
        this.acNum = acNum;
    }

    public String getDhModel() {
        return dhModel;
    }

    public void setDhModel(String dhModel) {
        this.dhModel = dhModel;
    }

    public String getDhFac() {
        return dhFac;
    }

    public void setDhFac(String dhFac) {
        this.dhFac = dhFac;
    }

    public String getXfModel() {
        return xfModel;
    }

    public void setXfModel(String xfModel) {
        this.xfModel = xfModel;
    }

    public String getXfFac() {
        return xfFac;
    }

    public void setXfFac(String xfFac) {
        this.xfFac = xfFac;
    }

    public Integer getMrStrut() {
        return mrStrut;
    }

    public void setMrStrut(Integer mrStrut) {
        this.mrStrut = mrStrut;
    }


    public Double getMrLength() {
        return mrLength;
    }

    public void setMrLength(Double mrLength) {
        this.mrLength = mrLength;
    }

    public Double getMrWidth() {
        return mrWidth;
    }

    public void setMrWidth(Double mrWidth) {
        this.mrWidth = mrWidth;
    }

    public Double getMrHigh() {
        return mrHigh;
    }

    public void setMrHigh(Double mrHigh) {
        this.mrHigh = mrHigh;
    }

    public String getMrOwner() {
        return mrOwner;
    }

    public void setMrOwner(String mrOwner) {
        this.mrOwner = mrOwner;
    }

    public String getMrPic() {
        return mrPic;
    }

    public void setMrPic(String mrPic) {
        this.mrPic = mrPic;
    }

    public String getWdType() {
        return wdType;
    }

    public void setWdType(String wdType) {
        this.wdType = wdType;
    }

    public String getWdModel() {
        return wdModel;
    }

    public void setWdModel(String wdModel) {
        this.wdModel = wdModel;
    }

    public String getWdFac() {
        return wdFac;
    }

    public void setWdFac(String wdFac) {
        this.wdFac = wdFac;
    }

    public String getWdGds() {
        return wdGds;
    }

    public void setWdGds(String wdGds) {
        this.wdGds = wdGds;
    }

    public String getOeType() {
        return oeType;
    }

    public void setOeType(String oeType) {
        this.oeType = oeType;
    }

    public String getOeModel() {
        return oeModel;
    }

    public void setOeModel(String oeModel) {
        this.oeModel = oeModel;
    }

    public String getOePower() {
        return oePower;
    }

    public void setOePower(String oePower) {
        this.oePower = oePower;
    }

    public String getOeFac() {
        return oeFac;
    }

    public void setOeFac(String oeFac) {
        this.oeFac = oeFac;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }

    public Cons getInstallPosCons() {
        return installPosCons;
    }

    public void setInstallPosCons(Cons installPosCons) {
        this.installPosCons = installPosCons;
    }

    public Cons getTowerTypeCons() {
        return towerTypeCons;
    }

    public void setTowerTypeCons(Cons towerTypeCons) {
        this.towerTypeCons = towerTypeCons;
    }

    public Cons getMrStrutCons() {
        return mrStrutCons;
    }

    public void setMrStrutCons(Cons mrStrutCons) {
        this.mrStrutCons = mrStrutCons;
    }

    public String getTranUpsitenameStr() {
        tranUpsitenameStr= tranUpsitename;
        if (!Common.isEmpty(tranUpsitename)) {
            int index = tranUpsitename.indexOf("_");
            if (index != -1) {
                String[] tranUpsitenameArr=tranUpsitename.split("_");
                if ("1".equals(tranUpsitenameArr[0]) || "2".equals(tranUpsitenameArr[0]) || "3".equals(tranUpsitenameArr[0])) {
                    tranUpsitenameStr = tranUpsitename.substring(index + 1);
                }
            }
        }
        return tranUpsitenameStr;
    }

    public void setTranUpsitenameStr(String tranUpsitenameStr) {
        this.tranUpsitenameStr = tranUpsitenameStr;
    }

    public String getTranDownsitenameStr() {
        tranDownsitenameStr=tranDownsitename;
        if (!Common.isEmpty(tranDownsitename)) {
            int index = tranDownsitename.indexOf("_");
            if (index != -1) {
                String[] tranDownsitenameArr=tranDownsitename.split("_");
                if ("1".equals(tranDownsitenameArr[0]) || "2".equals(tranDownsitenameArr[0]) || "3".equals(tranDownsitenameArr[0])) {
                     tranDownsitenameStr = tranDownsitename.substring(index + 1);
                }
            }
        }
        return tranDownsitenameStr;
    }

    public void setTranDownsitenameStr(String tranDownsitenameStr) {
        this.tranDownsitenameStr = tranDownsitenameStr;
    }

    /**
     * 日志记录忽略字段
     * @return
     */
    public void setIgFields() {
        igFields.add("conf1x");
        igFields.add("confDo");
        igFields.add("installPosCons");
        igFields.add("towerTypeCons");
        igFields.add("mrStrutCons");
        igFields.add("updatetime");
    }

    @Override
    public String compareEntity(Object object) {
        return super.compareEntity(object);
    }
}