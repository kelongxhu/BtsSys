package com.scttsc.baselibs.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class WyBlind {
    private Long id;

    private String name;

    private BigDecimal cityId;

    private BigDecimal countyId;

    private BigDecimal vitoId;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Short gridType;

    private Short blindType1;

    private Short blindType2;

    private Short is1x;

    private Short isDo;

    private Short isLte;

    private Short solType;

    private Short assistType;

    private Date inTime;

    private BigDecimal inUser;

    private String remarks;
    
    private Short deleteFlag;
    
    private String cityName;
    
    private String countryName;
    
    private String vitoName;
    
    private String inUserName;
    
    private String inTimeString;
    
    private String landMark;
    
    private String causesBy;
    
    private Short  status;
    
    private String solution;
    
    private Date planSolutionTime;
    
    private Date realSolutionTime;
    
    private Short isPlan;
    
    private String planSolutionTmStr;
    
    private String realSolutionTmStr;

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

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getCountyId() {
        return countyId;
    }

    public void setCountyId(BigDecimal countyId) {
        this.countyId = countyId;
    }

    public BigDecimal getVitoId() {
		return vitoId;
	}

	public void setVitoId(BigDecimal vitoId) {
		this.vitoId = vitoId;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Short getGridType() {
        return gridType;
    }

    public void setGridType(Short gridType) {
        this.gridType = gridType;
    }

    public Short getBlindType1() {
        return blindType1;
    }

    public void setBlindType1(Short blindType1) {
        this.blindType1 = blindType1;
    }

    public Short getBlindType2() {
        return blindType2;
    }

    public void setBlindType2(Short blindType2) {
        this.blindType2 = blindType2;
    }

    public Short getIs1x() {
        return is1x;
    }

    public void setIs1x(Short is1x) {
        this.is1x = is1x;
    }

    public Short getIsDo() {
        return isDo;
    }

    public void setIsDo(Short isDo) {
        this.isDo = isDo;
    }

    public Short getIsLte() {
        return isLte;
    }

    public void setIsLte(Short isLte) {
        this.isLte = isLte;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setIsPlan(Short isPlan) {
        this.isPlan = isPlan;
    }

    public Short getSolType() {
        return solType;
    }

    public void setSolType(Short solType) {
        this.solType = solType;
    }

    public Short getAssistType() {
        return assistType;
    }

    public void setAssistType(Short assistType) {
        this.assistType = assistType;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
        setInTimeString(new SimpleDateFormat("yyyy-MM-dd").format(inTime));
    }
    public BigDecimal getInUser() {
        return inUser;
    }

    public void setInUser(BigDecimal inUser) {
        this.inUser = inUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getVitoName() {
		return vitoName;
	}

	public void setVitoName(String vitoName) {
		this.vitoName = vitoName;
	}

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getInTimeString() {
		return inTimeString;
	}

	public void setInTimeString(String inTimeString) {
		this.inTimeString = inTimeString;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCausesBy() {
		return causesBy;
	}

	public void setCausesBy(String causesBy) {
		this.causesBy = causesBy;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Date getPlanSolutionTime() {
		return planSolutionTime;
	}

	public void setPlanSolutionTime(Date planSolutionTime) {
		this.planSolutionTime = planSolutionTime;
		setPlanSolutionTmStr(new SimpleDateFormat("yyyy-MM-dd").format(planSolutionTime));
	}

	public Date getRealSolutionTime() {
		return realSolutionTime;
	}

	public void setRealSolutionTime(Date realSolutionTime) {
		this.realSolutionTime = realSolutionTime;
		setRealSolutionTmStr(new SimpleDateFormat("yyyy-MM-dd").format(realSolutionTime));
	}

    public Short getStatus() {
        return status;
    }

    public Short getIsPlan() {
        return isPlan;
    }
    public Short getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getPlanSolutionTmStr() {
		return planSolutionTmStr;
	}

	public void setPlanSolutionTmStr(String planSolutionTmStr) {
		this.planSolutionTmStr = planSolutionTmStr;
	}

	public String getRealSolutionTmStr() {
		return realSolutionTmStr;
	}

	public void setRealSolutionTmStr(String realSolutionTmStr) {
		this.realSolutionTmStr = realSolutionTmStr;
	}
	
}