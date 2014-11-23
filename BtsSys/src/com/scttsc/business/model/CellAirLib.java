package com.scttsc.business.model;


import com.scttsc.baselibs.model.AirLib;

public class CellAirLib {

    private String cellIntId;
    private Integer airId;
    private double azimuth;
    private double hanghigh;
    private double electrondip;
    private double enginedip;
    private double totaldip;

    AirLib airLib;//关联天线库


   

    public String getCellIntId() {
		return cellIntId;
	}

	public void setCellIntId(String cellIntId) {
		this.cellIntId = cellIntId;
	}

    public Integer getAirId() {
        return airId;
    }

    public void setAirId(Integer airId) {
        this.airId = airId;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getHanghigh() {
        return hanghigh;
    }

    public void setHanghigh(double hanghigh) {
        this.hanghigh = hanghigh;
    }

    public double getElectrondip() {
        return electrondip;
    }

    public void setElectrondip(double electrondip) {
        this.electrondip = electrondip;
    }

    public double getEnginedip() {
        return enginedip;
    }

    public void setEnginedip(double enginedip) {
        this.enginedip = enginedip;
    }

    public double getTotaldip() {
        return totaldip;
    }

    public void setTotaldip(double totaldip) {
        this.totaldip = totaldip;
    }

    public AirLib getAirLib() {
        return airLib;
    }

    public void setAirLib(AirLib airLib) {
        this.airLib = airLib;
    }
}