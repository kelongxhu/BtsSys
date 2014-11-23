package com.scttsc.baselibs.model;

import java.util.Date;

public class SchoolLib {
	private Long id;
	private long cityId;// 地市
	private String name;// 校园名称
	private String address;// 校园地址（选）
	private int schoolLevel;// 需要重要等级（选）
	private int schoolType;// 校园类型（选）
	private String coverageType;// 校园整体覆盖方式(选填)
	private int structuralType;// 校园结构特点（选）
	private int wifiType;// WiFi覆盖
	private int cusers;// C网用户总数量
	private int doUsers;// DO用户数量
	private int cusersPlan;// 计划发展C网用户数量
	private int doUsersPlan;// 计划发展DO用户数量
	private String mealType;// 套餐类型
	private String businessType;// 业务特点(手填)
	private String schoolImage;// 楼宇分布图
	private long userId;
	private Date updateTime;
	private String remark;// 备注

    private Integer deleteFlag;
	
	
	//冗余
	private String provinceName;//省份
	private String cityName;//地市
	private String schoolLevelName;//等级名称
	private String schoolTypeName;//类型名称
	private String structuralTypeName;//结构名称
	private String wifiTypeName;//WIFI类型名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(int schoolLevel) {
		this.schoolLevel = schoolLevel;
	}

	public int getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public int getStructuralType() {
		return structuralType;
	}

	public void setStructuralType(int structuralType) {
		this.structuralType = structuralType;
	}

	public int getWifiType() {
		return wifiType;
	}

	public void setWifiType(int wifiType) {
		this.wifiType = wifiType;
	}

	public int getCusers() {
		return cusers;
	}

	public void setCusers(int cusers) {
		this.cusers = cusers;
	}

	public int getDoUsers() {
		return doUsers;
	}

	public void setDoUsers(int doUsers) {
		this.doUsers = doUsers;
	}

	public int getCusersPlan() {
		return cusersPlan;
	}

	public void setCusersPlan(int cusersPlan) {
		this.cusersPlan = cusersPlan;
	}

	public int getDoUsersPlan() {
		return doUsersPlan;
	}

	public void setDoUsersPlan(int doUsersPlan) {
		this.doUsersPlan = doUsersPlan;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getSchoolImage() {
		return schoolImage;
	}

	public void setSchoolImage(String schoolImage) {
		this.schoolImage = schoolImage;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSchoolLevelName() {
		return schoolLevelName;
	}

	public void setSchoolLevelName(String schoolLevelName) {
		this.schoolLevelName = schoolLevelName;
	}

	public String getSchoolTypeName() {
		return schoolTypeName;
	}

	public void setSchoolTypeName(String schoolTypeName) {
		this.schoolTypeName = schoolTypeName;
	}

	public String getStructuralTypeName() {
		return structuralTypeName;
	}

	public void setStructuralTypeName(String structuralTypeName) {
		this.structuralTypeName = structuralTypeName;
	}

	public String getWifiTypeName() {
		return wifiTypeName;
	}

	public void setWifiTypeName(String wifiTypeName) {
		this.wifiTypeName = wifiTypeName;
	}

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
