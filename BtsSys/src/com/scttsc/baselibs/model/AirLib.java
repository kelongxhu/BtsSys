package com.scttsc.baselibs.model;

import java.util.Date;

public class AirLib {
	private Long id;
	private String airModel;// 天线型号
	private int airFac;// 天线厂家
	private int prettifyFlag;// 是否美化天线
	private int ivaryFlag;// 是否可变电调
	private double degree;// 内置电下倾度数（单位度）
	private double freq;// 频率范围（单位MHz）
	private double trapWidth;// 频带宽度（单位MHz）
	private double airPlus;// 天线增益（单位dBi）
	private int airType;// 天线类型
	private int jhType;// 极化方式
	private double flatLobe;// 水平波瓣（单位度）
	private double uplobe;// 垂直波瓣（单位度）
	private double inputDrag;// 输入阻抗（单位欧姆）
	private double wireScale;// 天线前后比（单位dB）
	private double standingWave;// 驻波系数
	private double pitchUp;// 俯仰上旁瓣抑制（单位dB）
	private double pitchDown;// 俯仰下旁瓣抑制（单位dB）
	private long userId;
	private Date intime;
	private Date updateTime;
	private String remark;
    private Integer deleteFlag;
	
	
	//冗余
	private String airFacName;
	private String airTypeName;
	private String jhTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirModel() {
		return airModel;
	}

	public void setAirModel(String airModel) {
		this.airModel = airModel;
	}

	public int getAirFac() {
		return airFac;
	}

	public void setAirFac(int airFac) {
		this.airFac = airFac;
	}

	public int getPrettifyFlag() {
		return prettifyFlag;
	}

	public void setPrettifyFlag(int prettifyFlag) {
		this.prettifyFlag = prettifyFlag;
	}

	public int getIvaryFlag() {
		return ivaryFlag;
	}

	public void setIvaryFlag(int ivaryFlag) {
		this.ivaryFlag = ivaryFlag;
	}

	public double getDegree() {
		return degree;
	}

	public void setDegree(double degree) {
		this.degree = degree;
	}

	public double getFreq() {
		return freq;
	}

	public void setFreq(double freq) {
		this.freq = freq;
	}

	public double getTrapWidth() {
		return trapWidth;
	}

	public void setTrapWidth(double trapWidth) {
		this.trapWidth = trapWidth;
	}

	public double getAirPlus() {
		return airPlus;
	}

	public void setAirPlus(double airPlus) {
		this.airPlus = airPlus;
	}

	public int getAirType() {
		return airType;
	}

	public void setAirType(int airType) {
		this.airType = airType;
	}

	public int getJhType() {
		return jhType;
	}

	public void setJhType(int jhType) {
		this.jhType = jhType;
	}

	public double getFlatLobe() {
		return flatLobe;
	}

	public void setFlatLobe(double flatLobe) {
		this.flatLobe = flatLobe;
	}

	public double getUplobe() {
		return uplobe;
	}

	public void setUplobe(double uplobe) {
		this.uplobe = uplobe;
	}

	public double getInputDrag() {
		return inputDrag;
	}

	public void setInputDrag(double inputDrag) {
		this.inputDrag = inputDrag;
	}

	public double getWireScale() {
		return wireScale;
	}

	public void setWireScale(double wireScale) {
		this.wireScale = wireScale;
	}

	public double getStandingWave() {
		return standingWave;
	}

	public void setStandingWave(double standingWave) {
		this.standingWave = standingWave;
	}

	

	public double getPitchUp() {
		return pitchUp;
	}

	public void setPitchUp(double pitchUp) {
		this.pitchUp = pitchUp;
	}

	public double getPitchDown() {
		return pitchDown;
	}

	public void setPitchDown(double pitchDown) {
		this.pitchDown = pitchDown;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAirFacName() {
		return airFacName;
	}

	public void setAirFacName(String airFacName) {
		this.airFacName = airFacName;
	}

	public String getAirTypeName() {
		return airTypeName;
	}

	public void setAirTypeName(String airTypeName) {
		this.airTypeName = airTypeName;
	}

	public String getJhTypeName() {
		return jhTypeName;
	}

	public void setJhTypeName(String jhTypeName) {
		this.jhTypeName = jhTypeName;
	}

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
