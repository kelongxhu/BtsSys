package com.scttsc.business.web;


import com.scttsc.admin.model.City;
import com.scttsc.admin.service.CityManager;
import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.Bts;
import com.scttsc.business.model.Cell;
import com.scttsc.business.service.BbuManager;
import com.scttsc.business.service.BtsManager;
import com.scttsc.business.service.CellManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-13
 * Time: 上午1:30
 * 数据树展示
 */
public class QueryAction extends BaseAction {

    private BtsManager btsManager;

    private CityManager cityManager;

    private BbuManager bbuManager;

    private CellManager cellManager;

    private Long id;   //ID

    private Integer country;//区县

    private Integer treeLevel;//树节点层级

    private String type;//节点数据类型

    private String json;

    public String queryData() {
        try {
            int i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String query() {
        return SUCCESS;
    }

    /**
     * 生成基础数据异步树
     *
     * @return
     * @throws Exception
     */
    public String buildDataTree() {
        try {
            JSONObject font=new JSONObject();
            font.put("color","red");
            JSONArray arrayValue = new JSONArray();
            if (treeLevel == 1 || treeLevel == 2) {
                //展开本地网
                Map map = new HashMap();
                map.put("pid", id);
                List<City> cityList = cityManager.getByMap(map);
                for (City city : cityList) {
                    JSONObject oJson = new JSONObject();
                    oJson.put("id", city.getId());
                    oJson.put("text", city.getCityName());
                    oJson.put("treeLevel", treeLevel + 1);
                    oJson.put("isParent", true);
                    oJson.put("country",city.getId());
                    arrayValue.put(oJson);
                }
            } else if (treeLevel == 3) {
                //物理站点、纯BBU、室内分布站点
                JSONObject oJson = new JSONObject();
                oJson.put("id", 1);
                oJson.put("text", "物理站点");
                oJson.put("treeLevel", treeLevel + 1);
                oJson.put("isParent", true);
                oJson.put("country", country);
                JSONObject oJson2 = new JSONObject();
                oJson2.put("id", 2);
                oJson2.put("text", "纯BBU");
                oJson2.put("treeLevel", treeLevel + 1);
                oJson2.put("isParent", true);
                oJson2.put("country", country);
                JSONObject oJson3 = new JSONObject();
                oJson3.put("id", 3);
                oJson3.put("text", "室内分布");
                oJson3.put("treeLevel", treeLevel + 1);
                oJson3.put("isParent", true);
                oJson3.put("country", country);
                arrayValue.put(oJson);
                arrayValue.put(oJson2);
                arrayValue.put(oJson3);
            } else if (treeLevel == 4) {
                switch (id.intValue()) {
                    case 1:
                        //物理站点
                        Map siteSeach=new HashMap();
                        siteSeach.put("countryIds",country);
                        siteSeach.put("deleteFlag",0);//在用
                        siteSeach.put("isIndoor","否");
                        List<Bts> btss=btsManager.selectByMap(siteSeach);
                        for(Bts bts:btss){
                            JSONObject oJson=new JSONObject();
                            oJson.put("id",bts.getIntId());
                            oJson.put("text",bts.getName());
                            oJson.put("treeLevel",treeLevel+1);
                            oJson.put("isParent",true);
                            oJson.put("country",country);
                            oJson.put("type","bts");
                             BigDecimal manulFlag=bts.getManualFlag();
                            if(manulFlag!=null&&manulFlag.intValue()==0){
                                //未填写手工数据
                                 oJson.put("font",font);
                                 oJson.put("iconSkin","btsFlag0");
                            }else {
                                 oJson.put("iconSkin","btsFlag1");
                            }
                            arrayValue.put(oJson);
                        }
                        break;
                    case 2:
                        //纯BBU
                        Map seach = new HashMap();
                        seach.put("countryIds", country); //区县
                        seach.put("isShare", 0);    //纯BBU
                        seach.put("bbuType", "1,2");
                        seach.put("deleteFlag", 0); //在用
                        List<Bbu> bbus = bbuManager.selectBbuByConds(seach);
                        for (Bbu bbu : bbus) {
                            JSONObject oJson = new JSONObject();
                            oJson.put("id", bbu.getIntId());
                            oJson.put("text", bbu.getName());
                            oJson.put("treeLevel", treeLevel + 1);
                            //oJson.put("isParent", true);
                            oJson.put("country", country);
                            oJson.put("type","bbu");
                             BigDecimal manulFlag=bbu.getManualFlag();
                            if(manulFlag!=null&&manulFlag.intValue()==0){
                                //未填写手工数据
                                 oJson.put("font",font);
                                 oJson.put("iconSkin","btsFlag0");
                            }else {
                                 oJson.put("iconSkin","btsFlag1");
                            }
                            arrayValue.put(oJson);
                        }
                        break;
                    case 3:
                        //室内分布站点
                        Map indoorSeach=new HashMap();
                        indoorSeach.put("countryIds",country);
                        indoorSeach.put("deleteFlag",0);//在用
                        indoorSeach.put("isIndoor","是");
                        List<Bts> indoors=btsManager.selectByMap(indoorSeach);
                        for(Bts bts:indoors){
                            JSONObject oJson=new JSONObject();
                            oJson.put("id",bts.getIntId());
                            oJson.put("text",bts.getName());
                            oJson.put("treeLevel",treeLevel+1);
                            oJson.put("isParent",false);
                            oJson.put("country",country);
                            oJson.put("type","indoor");
                            BigDecimal manulFlag=bts.getManualFlag();
                            if(manulFlag!=null&&manulFlag.intValue()==0){
                                //未填写手工数据
                                 oJson.put("font",font);
                                 oJson.put("iconSkin","btsFlag0");
                            }else {
                                 oJson.put("iconSkin","btsFlag1");
                            }
                            arrayValue.put(oJson);
                        }
                        break;
                }
            }else if(treeLevel == 5){
                if(!Common.isEmpty(type)) {
                    if("bts".equals(type)){
                        //站点节点下展示小区
                        List<Cell> cells= cellManager.selectByBtsId(id);
                        for(Cell cell:cells){
                            JSONObject oJson=new JSONObject();
                            oJson.put("id",cell.getIntId());
                            oJson.put("text",cell.getName());
                            oJson.put("treeLevel",treeLevel+1);
                            oJson.put("country",country);
                            oJson.put("type","cell");
                            BigDecimal manulFlag=cell.getManualFlag();
                            if(manulFlag!=null&&manulFlag.intValue()==0){
                                oJson.put("font",font);
                                oJson.put("iconSkin","cellFlag0");
                            }else{
                                oJson.put("iconSkin","cellFlag1");
                            }
                            arrayValue.put(oJson);
                        }
                    }
                }
            }
            json = arrayValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResponse(json);
        return null;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setBtsManager(BtsManager btsManager) {
        this.btsManager = btsManager;
    }

    public void setCityManager(CityManager cityManager) {
        this.cityManager = cityManager;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public void setBbuManager(BbuManager bbuManager) {
        this.bbuManager = bbuManager;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CellManager getCellManager() {
        return cellManager;
    }

    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }
}
