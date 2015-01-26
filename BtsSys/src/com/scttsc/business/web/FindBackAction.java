package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.*;
import com.scttsc.business.service.*;
import com.scttsc.business.vo.FindBackReponse;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-19
 * Time: 上午11:00
 * 基础数据查询
 */
public class FindBackAction extends BaseAction {
    @Autowired
    private IndoorManualManager indoorManualManager;
    @Autowired
    private CellManager cellManager;
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private BbuManager bbuManager;
    @Autowired
    private TunelManager tunelManager;
    @Autowired
    private BbuManualManager bbuManualManager;
    @Autowired
    private CellManualManager cellManualManager;
    @Autowired
    private TunelManualManager tunelManualManager;

    //查询条件
    private String countryIds;
    private String name;
    private String bscName;
    private String btsId;
    private Integer ci;
    private Integer pn;

    private String startTime;
    private String endTime;

    private Integer manualFlag;//是否已录入
    private Integer typeId;//基本库类型
    private String intIds;//数据intId集合
    private String isIndoor;//是，否，隧


    public String findback() {
        return SUCCESS;
    }

    /**
     * 删除的、已录入手工数据的室外覆盖站点数据
     *
     * @return
     */

    public String btsDataDel() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                btsId = Common.decodeURL(btsId).trim();
                map.put("btsId", btsId);
            }
            if(!Common.isEmpty(startTime)){
                map.put("startTime",startTime);
            }
            if(!Common.isEmpty(endTime)){
                map.put("endTime",endTime);
            }
            //固定条件
            map.put("manualFlag", 1);
            map.put("isIndoor", "否");//物理站点
            map.put("deleteFlag", 1);//在用
            total = btsManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = btsManager.getByConds(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 删除的已录入的纯bbu数据
     * @return
     */
    public String bbuDataDel() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bbu> list = null;
        try {
            //固定条件
            map.put("deleteFlag", 1);// 已刪除
            map.put("manualFlag", 1);
            map.put("isShare", 0);
            map.put("bbuType", "1,2");//纯BBU数据
            if (!Common.isEmpty(countryIds)) {
                // 查询条件
                map.put("countryIds", countryIds);
            } else {
                // 默认数据权限
                //用户权限
                map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                btsId = Common.decodeURL(btsId).trim();
                map.put("btsId", btsId);
            }
            if(!Common.isEmpty(startTime)){
                map.put("startTime",startTime);
            }
            if(!Common.isEmpty(endTime)){
                map.put("endTime",endTime);
            }

            total = bbuManager.countByExample(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = bbuManager.selectByExample(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }


    /**
     * 室分数据查询
     *
     * @return
     */

    public String indoorDataDel() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<Bts> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                btsId = Common.decodeURL(btsId).trim();
                map.put("btsId", btsId);
            }
            if(!Common.isEmpty(startTime)){
                map.put("startTime",startTime);
            }
            if(!Common.isEmpty(endTime)){
                map.put("endTime",endTime);
            }
            //固定条件
            map.put("manualFlag", 1);
            map.put("isIndoor", "是");//物理站点
            map.put("deleteFlag", 1);//在用
            total = btsManager.countByConds(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = btsManager.getByConds(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 废弃小区
     *
     * @return
     */
    public String cellDataDel() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) this.getSession().getAttribute("user");
        int total = 0;
        List<Cell> list = null;
        try {

            map.put("deleteFlag", 1);
            map.put("manualFlag", 1);
            if (!Common.isEmpty(countryIds)) {
                // 默认权限
                map.put("countryIds", countryIds);
            } else {
                // 默认数据权限
                //用户权限树
                map.put("countryIds", user.getCountryIds());
            }
            if(!Common.isEmpty(isIndoor)){
                isIndoor = Common.decodeURL(isIndoor).trim();
                map.put("isIndoor",isIndoor);
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(ci)) {
                map.put("ci", ci);
            }
            if (!Common.isEmpty(pn)) {
                map.put("pn", pn);
            }
            if(!Common.isEmpty(startTime)){
                map.put("startTime",startTime);
            }
            if(!Common.isEmpty(endTime)){
                map.put("endTime",endTime);
            }
            total = cellManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = cellManager.selectByMap(map);
        } catch (Exception e) {
           LOG.error(e.getMessage(),e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 废弃隧道
     *
     * @return
     */
    public String tunelDataDel() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyTunel> list = null;
        try {
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限
                map.put("countryIds", user.getCountryIds());
            }
            //查询条件
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                bscName = Common.decodeURL(bscName).trim();
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }

            if (!Common.isEmpty(manualFlag)) {
                map.put("manualFlag", manualFlag);
            }
            if(!Common.isEmpty(startTime)){
                map.put("startTime",startTime);
            }
            if(!Common.isEmpty(endTime)){
                map.put("endTime",endTime);
            }
            //固定条件
            map.put("deleteFlag", 1);//在用
            map.put("manualFlag", 1);
            total = tunelManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = tunelManager.selectByMap(map);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 匹配废弃数据和现有数据
     *
     * @return
     */
    public String getMate() {
        return SUCCESS;
    }


    /**
     * 根据名称一个废弃小区匹配多个再用小区
     * @return
     */
    public String getCellMate(){
        return SUCCESS;
    }


    /**
     * 勾选废弃数据匹配的数据，进行数据匹配
     *
     * @return
     */
    public String mateData() {
        List<MateEntry> mateEntries = null;
        List<Long> ids = new ArrayList<Long>();
        try {
            if (Common.isEmpty(intIds)) {
                return SUCCESS;
            }
            String[] arr = intIds.split(",");
            if (arr != null) {
                for (String id : arr) {
                    ids.add(new Long(id));
                }
            }
            switch (typeId) {
                case 1:
                    mateEntries = btsManager.findDelBtsData(ids);
                    break;
                case 2:
                    mateEntries = bbuManualManager.findDelBbuData(ids);
                    break;
                case 3:
                    mateEntries = btsManager.findDelBtsData(ids);
                    break;
                case 4:
                    mateEntries = cellManualManager.findDelCellData(ids);
                    break;
                case 6:
                    mateEntries = tunelManualManager.findDelTunelData(ids);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        setJsonMapRows(mateEntries);
        return SUCCESS;
    }

    /**
     * 根据类型找回数据
     *
     * @return
     */
    public String backMateData() {
        FindBackReponse backReponse = null;
        if (Common.isEmpty(intIds)) {
            backReponse = new FindBackReponse();
            backReponse.setResult("N");
            jsonMap.put("result", backReponse);
            return SUCCESS;
        }
        List<String> ids=new ArrayList<String>();//找回的数据对
        String[] arr = intIds.split(";");
        if (arr != null) {
            for (String id : arr) {
                ids.add(id);
            }
        }
        switch (typeId) {
            case 1:
                //室外覆盖站点
                backReponse = btsManager.findManualData(ids);
                break;
            case 2:
                //纯bbu
                backReponse=bbuManualManager.findManualData(ids);
                break;
            case 3:
                //室内分布小区
                backReponse=indoorManualManager.findManualData(ids);
                break;
            case 4:
                //室外覆盖小区
                backReponse=cellManualManager.findManualData(ids);
                break;
            case 6:
                //隧道覆盖小区
                backReponse=tunelManualManager.findManualData(ids);
                break;
            default:
                break;
        }
        jsonMap.put("result", backReponse);
        return SUCCESS;
    }


    public Integer getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
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

    public String getBtsId() {
        return btsId;
    }

    public void setBtsId(String btsId) {
        this.btsId = btsId;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getIntIds() {
        return intIds;
    }

    public void setIntIds(String intIds) {
        this.intIds = intIds;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIsIndoor() {
        return isIndoor;
    }

    public void setIsIndoor(String isIndoor) {
        this.isIndoor = isIndoor;
    }
}
