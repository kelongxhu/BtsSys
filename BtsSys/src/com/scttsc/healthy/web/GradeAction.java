package com.scttsc.healthy.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.service.BtsManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import com.scttsc.healthy.model.WyGrade;
import com.scttsc.healthy.service.GradeManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-8-28
 * Time: 上午10:23
 * Email: wokzhen@vip.qq.com
 */
public class GradeAction extends BaseAction{

    @Autowired
    private GradeManager gradeManager;

    @Autowired
    private BtsManager btsManager;

    private String countryIds;

    private String nameSearch;

    private Long intId;

    private Integer gradeType;

    private WyGrade wyGrade;

    private String fusionChartType;

    /**
     * 展示评分列表页面
     * @return
     */
    public String showGradeList(){
        return SUCCESS;
    }

    /**
     * 查询得分情况
     * @return
     */
    public String queryGridList(){
        Map cond = new HashMap();
        putConditions(cond);//放入查询条件
        int total = 0;
        List<WyGrade> gradeList = null;
        try {
            total = gradeManager.selectGradeCount(cond);
            if (total < pagesize) {
                page = 1;
            }
            cond.put("start", (page - 1) * pagesize + 1);
            cond.put("pagesize", pagesize);
            cond.put("sortname", sortname);
            cond.put("sortorder", sortorder);
            gradeList = gradeManager.selectGradeList(cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapTotal(total);
        setJsonMapRows(gradeList);
        return SUCCESS;
    }

    /**
     * 查看评分详情
     * @return
     */
    public String showGradeDetail(){
        try {
            wyGrade = gradeManager.getAllInfoById(intId);
            if(Common.isEmpty(wyGrade)) throw new Exception("系统错误,请重试!");
            getRequest().setAttribute("wyGrade", JSONObject.fromObject(wyGrade));
        } catch (Exception e) {
            e.printStackTrace();
            setJsonString(e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 基站评分分段个数统计
     * @return
     */
    public String countBtsGradeNumForFusionChart(){
        User user = (User)getSession().getAttribute("user");
        String cityIds = user.getCityIds();
        List<Map> result = null;
        try {
            result = gradeManager.countBtsGradeNumByCityIds(cityIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer xml = new StringBuffer();
        StringBuffer set = new StringBuffer();
        if(!Common.isEmpty(result)){
            if(fusionChartType.equals("chart1")){
                for(Map map : result){
                    String name = Common.null2String(map.get("NAME"));
                    String num = Common.null2String(map.get("NUM"));
                    String color = Common.null2String(map.get("COLOR"));
                    set.append("<set name='" + name + "' value='" + num + "' color='" + color + "'/>");
                }
            }else{
                int totalNum = 0;
                for(Map map : result){
                    totalNum += Common.string2Int(Common.null2String(map.get("NUM")));
                }
                for(Map map : result){
                    String name = Common.null2String(map.get("NAME"));
                    float num = (float)Common.string2Int(Common.null2String(map.get("NUM")));
                    float value = num/totalNum * 100;
                    String color = Common.null2String(map.get("COLOR"));
                    set.append("<set name='" + name + "' value='" + String.format("%.2f", value) + "' color='" + color + "'/>");
                }
            }
        }
        if(fusionChartType.equals("chart1")) xml.append("<graph caption='基站评分分段个数统计' numberSuffix='个' canvasBorderThickness='1' xAxisName='评分范围' yAxisName='基站个数' showBorder='0' showAlternateHGridColor='1' AlternateHGridColor='ff5904' divLineColor='ff5904' bgColor='FFFFFF' divLineAlpha='20' alternateHGridAlpha='5' canvasBorderColor='666666' baseFontColor='666666' outCnvBaseFontSize='13' lineAlpha='85' canvaspadding='8' chartRightMargin='35' chartTopMargin='10' chartLeftMargin='0'  showNames='1' showValues='1' animation='1'>");
        else xml.append("<graph caption='基站评分分段个数占比统计' pieYScale='70' pieRadius='120' pieSliceDepth='0' numberSuffix='%' showBorder='0' bgColor='FFFFFF' divLineAlpha='20' baseFontColor='666666' baseFontSize='13' showNames='1'  showValues='1' animation='1'>");
        xml.append(set);
        xml.append("</graph>");
        jsonMap.put("xml", xml.toString());
        return SUCCESS;
    }

    public String getSortedBtsAvgGradeWithCity(){
        try {
            List<Map> result = gradeManager.getSortedBtsAvgGradeWithCity();
            jsonMap.put("result", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    private void putConditions(Map cond){
        if(StringUtil.isNotBlank(countryIds)) cond.put("countryIds", countryIds);
        else {
            User user = (User) getSession().getAttribute("user");
            if(!Common.isEmpty(user) && !Common.isEmpty(user.getCountryIds())){
                cond.put("countryIds", user.getCountryIds());
            }
        }
        if(StringUtil.isNotBlank(nameSearch)) cond.put("nameSearch", nameSearch);
    }

    /*
     * GETTERS AND SETTERS
     */

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    public Integer getGradeType() {
        return gradeType;
    }

    public void setGradeType(Integer gradeType) {
        this.gradeType = gradeType;
    }

    public WyGrade getWyGrade() {
        return wyGrade;
    }

    public void setWyGrade(WyGrade wyGrade) {
        this.wyGrade = wyGrade;
    }

    public void setFusionChartType(String fusionChartType) {
        this.fusionChartType = fusionChartType;
    }
}
