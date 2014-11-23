package com.scttsc.business.model;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.DateUtils;

import java.util.Date;

public class WyColumneditInterface {
    private Long id;

    private Long intId;

    private String name;//冗余名称

    private String cncolumnname;

    private String encolumnname;

    private String columnvalue;

    private Integer type;

    private Integer status;

    private Long userid;

    private Date intime;

    private Long confirmuserid;

    private Date updatetime;

    private String columnValueOld; //增加修改前的值

    private Integer cityId;//本地网ID

    private String cityname;//本地网名称

    //冗余
    private String typeStr;
    private String statusStr;
    private String intimeStr;
    private String updatetimeStr;

    private User user;
    private User confirmUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCncolumnname() {
        return cncolumnname;
    }

    public void setCncolumnname(String cncolumnname) {
        this.cncolumnname = cncolumnname;
    }

    public String getEncolumnname() {
        return encolumnname;
    }

    public void setEncolumnname(String encolumnname) {
        this.encolumnname = encolumnname;
    }

    public String getColumnvalue() {
        return columnvalue;
    }

    public void setColumnvalue(String columnvalue) {
        this.columnvalue = columnvalue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Long getConfirmuserid() {
        return confirmuserid;
    }

    public void setConfirmuserid(Long confirmuserid) {
        this.confirmuserid = confirmuserid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getTypeStr() {
        if (type != null) {
            switch (type.intValue()) {
                case 1:
                    typeStr = "物理站点";
                    break;
                case 2:
                    typeStr = "BBU";
                    break;
                case 3:
                    typeStr = "室分站点";
                    break;
                case 4:
                    typeStr = "小区";
                    break;
                default:
                    break;
            }
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getStatusStr() {
        if (status == null) {
            return "";
        }
        switch (status.intValue()) {
            case 0:
                statusStr = "未审核";
                break;
            case 1:
                statusStr = "审核通过";
                break;
            case 99:
                statusStr = "审核不通过";
                break;
            default:
                break;
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getIntimeStr() {
        return (intime==null)?"": DateUtils.formatDateTime(intime,"yyyy-MM-dd HH:mm");
    }

    public void setIntimeStr(String intimeStr) {
        this.intimeStr = intimeStr;
    }

    public String getUpdatetimeStr() {
        return (updatetime==null)?"": DateUtils.formatDateTime(updatetime,"yyyy-MM-dd HH:mm");
    }

    public void setUpdatetimeStr(String updatetimeStr) {
        this.updatetimeStr = updatetimeStr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(User confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getColumnValueOld() {
        return columnValueOld;
    }

    public void setColumnValueOld(String columnValueOld) {
        this.columnValueOld = columnValueOld;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}