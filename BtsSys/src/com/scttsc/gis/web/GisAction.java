package com.scttsc.gis.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.User;
import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.WyTransfernode;
import com.scttsc.business.service.TransferManager;
import com.scttsc.clientinterface.model.Alarm;
import com.scttsc.clientinterface.service.InterfaceManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import com.scttsc.gis.model.Gis;
import com.scttsc.gis.model.LatLng;
import com.scttsc.gis.service.GisManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * 源于WY_BTS[与Bts.java相近但有区别]，地图中展现
 */
@SuppressWarnings("serial")
public class GisAction extends BaseAction {

    private GisManager gisManager;
    @Autowired
    private TransferManager transferManager;
    Gis gis;
    private static final byte[] lock = new byte[0];

    @Autowired
    private InterfaceManager interfaceManager;

//    Long intId;// ID

    //基站查询条件
    String countryIds;// 本地网集合
    String name; //基站名称
    String types;//标记类型
    String bscName; //基站bsc名称
    String btsId;   //网管编号
    String queryFlag;//查询标识
    Integer gisCount;
    Integer bbuCount;
    Integer transferCount;


    //============================
    private static final String SOUGOU_SEARCH_URL = "http://api.go2map.com/engine/api/search/json?what=id:{0}";

    private static final String[] JSON_OBJECT_KEY = new String[]{"response", "data", "feature", "detail"};

    private static final String STATUS = "status";

    private static final String COUNTY = "county";

    private static final String OK = "ok";

    private String id;



    public String gis() {
        return SUCCESS;
    }


    public String loadCountyId() throws Exception{
        long countyId = -1;
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(MessageFormat.format(SOUGOU_SEARCH_URL, id));
            String status = null;
            int count = 0;
            do{
                try {
                    httpClient.executeMethod(getMethod);
                    String res = getMethod.getResponseBodyAsString();
                    JSONObject jsonObject = JSONObject.fromObject(res);
                    status = jsonObject.get(STATUS).toString();
                    if (OK.equals(status)) {
                        String county = getCounty(res);
                        Map<String, City> map = ConstantUtil.countryMap;
                        for (Map.Entry<String, City> entry : map.entrySet()) {
                            String name = entry.getKey();
                            if (county != null && county.indexOf(name) != -1) {
                                countyId = entry.getValue().getId();
                                break;
                            }
                        }
                    }
                }catch (Exception e) {
                      LOG.error(e.getMessage(), e);
                 }
                count++;
                if (count > 2) break;
            }while(!OK.equals(status)||countyId==-1);
            getMethod.releaseConnection();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("countryId:"+countyId);
        jsonMap.put("countyId", countyId);
        return SUCCESS;
    }


    private String getCounty(String jsonString)throws Exception{
        Object country=null;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONObject responseObject=(JSONObject)jsonObject.get("response");
        JSONObject dataObject=(JSONObject)responseObject.get("data");
        JSONArray featureArray=(JSONArray)dataObject.get("feature");
        if(featureArray!=null&&featureArray.size()>0){
            JSONObject featureObject=(JSONObject)featureArray.get(0);
            JSONObject detailObject=(JSONObject)featureObject.get("detail");
            country=detailObject.get("county");
        }
        return country==null?"":country.toString();
    }




    /**
     * 站点查询
     * @return
     */
    public String selectByMap() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        String cityIds = user.getCityIds();
        BigDecimal centerLongit = new BigDecimal(0);      //中心经度
        BigDecimal centerLatit =  new BigDecimal(0);       //中心纬度

        List<Gis> list = null;
        try {
            if (!Common.isEmpty(cityIds)) {
                //查询数据权限
                map.put("cityIds", cityIds);
            } else {
                //默认数据权限，显示全部
                if (Common.isEmpty(queryFlag)) {
                    map.put("cityIds", user.getCityIds());
                }
            }
            //查询条件
            if (!Common.isEmpty(countryIds)) {
                //查询数据权限
                map.put("countryIds", countryIds);
            } else {
                //默认数据权限，显示全部
                if (Common.isEmpty(queryFlag)) {
                    map.put("countryIds", user.getCountryIds());
                }
            }
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

            list = gisManager.selectByMap(map);
            //计算中心点经、纬度，忽略空坐标点
            Gis gisLatlng = gisManager.getLatlngSum(map);
            BigDecimal longitSum = gisLatlng.getLongitude();      //经度
            BigDecimal latitSum = gisLatlng.getLatitude();       //纬度
            int cntNoZero = gisManager.countLatlngNoZero(map);
            centerLongit = longitSum.divide(new BigDecimal(cntNoZero), 14, BigDecimal.ROUND_HALF_UP);
            centerLatit = latitSum.divide(new BigDecimal(cntNoZero),14, BigDecimal.ROUND_HALF_UP);
              String f = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        //设置中心点经、纬度
        setJsonMapCenterLongitude(centerLongit);
        setJsonMapCenterLatitude(centerLatit);

        return SUCCESS;
    }

    /**
     * 获取各个类型站点的个数
     * @return
     */
    public String loadMarkerCountBycond(){
        Map cond = new HashMap();
        putConditions(cond);
        int gisCount = 0;
        int bbuCount = 0;
        int transferCount = 0;
        //获取站点数据
        try {
            gisCount = gisManager.countByMap(cond);
            bbuCount = gisManager.countBbuByMap(cond);
            transferCount = transferManager.selectTransferCount(cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonMap.put("gisCount", gisCount);
        jsonMap.put("bbuCount", bbuCount);
        jsonMap.put("transferCount", transferCount);
        return SUCCESS;
    }

    /**
     * 根据条件加载标记(站点)
     * @return
     */
    public String loadMarkerByCond(){
        Map cond = new HashMap();
        putConditions(cond);
        Map markers = new LinkedHashMap();
        String errMsg = "";
        BigDecimal centerLongit;//中心经度
        BigDecimal centerLatit;//中心纬度
        int totalMarkerCount = 0;//标记总数
        BigDecimal totalLng = new BigDecimal(0);//总经度
        BigDecimal totalLat = new BigDecimal(0);//总纬度
        BigDecimal lng;//当前经度
        BigDecimal lat;//当前纬度
        Map<String, Object> gisMarker = new LinkedHashMap<String, Object>();
        Map<String, Object> bbuMarker = new LinkedHashMap<String, Object>();
        Map<String, Object> transferMarker = new LinkedHashMap<String, Object>();

        //加载告警数据，2014-9月增加
        Map<String,Alarm> alarmMap=new HashMap<String, Alarm>();
        try {
            //测试数据
//            List<Alarm> alarmList=new ArrayList<Alarm>();
//            Alarm alarm1=new Alarm();
//            alarm1.setBscName("Qiannan_BSS1");
//            alarm1.setBtsId("158");
//            alarm1.setAlarmCode("15017");
//            alarm1.setAlarmName("掉站");
//            alarm1.setAlarmLevel("严重");
//            alarm1.setAlarmTime("2014-09-20");
//            alarmList.add(alarm1);
//            Alarm alarm2=new Alarm();
//            alarm2.setBscName("Bijie_BSS2");
//            alarm2.setBtsId("264");
//            alarm2.setAlarmCode("15017");
//            alarm2.setAlarmName("掉站");
//            alarm2.setAlarmLevel("严重");
//            alarm2.setAlarmTime("2014-09-20");
//            alarmList.add(alarm2);
            List<Alarm> alarmList = interfaceManager.getAlarms("");
            if(alarmList!=null){
                for(Alarm alarm:alarmList){
                    String alarmKey=alarm.getBscName()+"_"+alarm.getBtsId();
                    alarmMap.put(alarmKey,alarm);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
           // errMsg = "加载告警数据失败!";
        }
        try {
            //如果没有选择标记类型,则显示所有标记
            if(StringUtil.isBlank(types)) types = "1,2,3,4";
            String[] typeArray = types.split(",");
            //获取站点数据
            for(String type : typeArray){
                if("1".equals(type)){//物理站点
                    cond.put("isIndoor", "否");
                    List<Gis> gisList = gisManager.selectByMap(cond);
                    for(Gis gis : gisList){
                        //是否告警
                        gis=addGisAlarm(gis,alarmMap);
                        //告警判断结束
                        lng = gis.getLongitude();
                        lat = gis.getLatitude();
                        if(!Common.isEmpty(lng) && !Common.isEmpty(lat) && !lng.equals(new BigDecimal(0)) && !lat.equals(new BigDecimal(0))){
                            gisMarker.put(gis.getName(), gis);
                            totalLng = totalLng.add(lng);
                            totalLat = totalLat.add(lat);
                            totalMarkerCount++;
                        }
                    }
                }else if("2".equals(type)){//室分站点
                    cond.put("isIndoor", "是");
                    List<Gis> gisList = gisManager.selectByMap(cond);
                    for(Gis gis : gisList){
                        //是否告警
                        gis=addGisAlarm(gis,alarmMap);
                        //告警判断结束
                        lng = gis.getLongitude();
                        lat = gis.getLatitude();
                        if(!Common.isEmpty(lng) && !Common.isEmpty(lat) && !lng.equals(new BigDecimal(0)) && !lat.equals(new BigDecimal(0))){
                            gisMarker.put(gis.getName(), gis);
                            totalLng = totalLng.add(lng);
                            totalLat = totalLat.add(lat);
                            totalMarkerCount++;
                        }
                    }
                }else if("3".equals(type)){//bbu
                    cond.put("isShare", 0);
                    cond.put("bbuType", "1,2");//纯BBU数据
                    List<Bbu> bbuList = gisManager.selectBbuByMap(cond);
                    for(Bbu bbu : bbuList){
                        String btsKey=bbu.getBscName()+"_"+bbu.getBtsId();
                        Alarm alarm=alarmMap.get(btsKey);
                        if(alarm!=null){
                            bbu.setAlarmName(alarm.getAlarmName());
                            bbu.setAlarmLevel(alarm.getAlarmLevel());
                            bbu.setAlarmCode(alarm.getAlarmCode());

                        }
                        lng = bbu.getLongitude();
                        lat = bbu.getLatitude();
                        if(!Common.isEmpty(lng) && !Common.isEmpty(lat) && !lng.equals(new BigDecimal(0)) && !lat.equals(new BigDecimal(0))){
                            bbuMarker.put(bbu.getName(), bbu);
                            totalLng = totalLng.add(lng);
                            totalLat = totalLat.add(lat);
                            totalMarkerCount++;
                        }
                    }
                }else if("4".equals(type)){//传输节点
                    List<WyTransfernode> transferList = transferManager.selectTransferList(cond);
                    for(WyTransfernode transfer : transferList){
                        lng = transfer.getLongitude();
                        lat = transfer.getLatitude();
                        if(!Common.isEmpty(lng) && !Common.isEmpty(lat) && !lng.equals(new BigDecimal(0)) && !lat.equals(new BigDecimal(0))){
                            transferMarker.put(transfer.getName(), transfer);
                            totalLng = totalLng.add(lng);
                            totalLat = totalLat.add(lat);
                            totalMarkerCount++;
                        }
                    }
                }
            }
            //设置各个节点关系
            Iterator<String> gisKeys = gisMarker.keySet().iterator();
            while(gisKeys.hasNext()){
                String gisKey = gisKeys.next();
                Gis gis = (Gis)gisMarker.get(gisKey);
                String isRru = gis.getRru();
                if("是".equals(isRru)){
                    String szGisName = "";
                    String btsName = gis.getBtsName();
                    if(!StringUtil.isBlank(btsName)){
                        int index = btsName.indexOf("_");
                        if(index < 0) szGisName = btsName;
                        else szGisName = btsName.substring(0, index);
                    }
                    gis.setSzGisName(szGisName);
                    LatLng szLatLng = null;
                    Gis szGis = (Gis)gisMarker.get(szGisName);
                    if(Common.isEmpty(szGis)){
                        Bbu szBbu = (Bbu)bbuMarker.get(szGisName);
                        if(!Common.isEmpty(szBbu)) szLatLng = new LatLng(szBbu.getName(), szBbu.getLatitude(), szBbu.getLongitude());
                    }else szLatLng = new LatLng(szGis.getName(), szGis.getLatitude(), szGis.getLongitude());
                    gis.setSzLatLng(szLatLng);
                }
                String tranUpSiteName = gis.getTranUpSiteName();
                if(StringUtil.isNotBlank(tranUpSiteName)){
                    LatLng upLatLng = null;
                    String[] values = tranUpSiteName.split("_");
                    if(values.length == 2){
                        String type = values[0];
                        String upSiteName = values[1];
                        if("1".equals(type)){//传输上游节点是BTS
                            Gis upGis = (Gis)gisMarker.get(upSiteName);
                            if(!Common.isEmpty(upGis)) upLatLng = new LatLng(upGis.getName(), upGis.getLatitude(), upGis.getLongitude());
                        }else if("2".equals(type)){//传输上游节点是transfernode
                            WyTransfernode upTransfer = (WyTransfernode)transferMarker.get(upSiteName);
                            if(!Common.isEmpty(upTransfer)) upLatLng = new LatLng(upTransfer.getName(), upTransfer.getLatitude(), upTransfer.getLongitude());
                        }else if("3".equals(type)){//传输上游节点是bbu
                            Bbu upBbu = (Bbu)bbuMarker.get(upSiteName);
                            if(!Common.isEmpty(upBbu)) upLatLng = new LatLng(upBbu.getName(), upBbu.getLatitude(), upBbu.getLongitude());
                        }
                        gis.setUpLatLng(upLatLng);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errMsg += e.getMessage();
        }
        if(StringUtil.isNotBlank(errMsg)) jsonMap.put("errMsg", errMsg);
        markers.put("bts", gisMarker);
        markers.put("bbu", bbuMarker);
        markers.put("transfer", transferMarker);
        jsonMap.put("markers", markers);
        //计算中心点经、纬度，忽略空坐标点
        if(totalMarkerCount > 0){
            centerLongit = totalLng.divide(new BigDecimal(totalMarkerCount), 14, BigDecimal.ROUND_HALF_UP);
            centerLatit = totalLat.divide(new BigDecimal(totalMarkerCount),14, BigDecimal.ROUND_HALF_UP);
            //设置中心点经、纬度
            setJsonMapCenterLongitude(centerLongit);
            setJsonMapCenterLatitude(centerLatit);
        }
        return SUCCESS;
    }


    private Gis addGisAlarm(Gis gis,Map<String,Alarm> alarmMap){
        String btsKey=gis.getBscName()+"_"+gis.getBtsId();
        Alarm alarm=alarmMap.get(btsKey);
        if(alarm!=null){
            gis.setAlarmName(alarm.getAlarmName());
            gis.setAlarmLevel(alarm.getAlarmLevel());
            gis.setAlarmCode(alarm.getAlarmCode());
            gis.setAlarmTime(alarm.getAlarmTime());
        }
        return gis;
    }

    private void putConditions(Map map){
        if(StringUtil.isNotBlank(countryIds)) map.put("countryIds", countryIds);
        else {
            User user = (User) getSession().getAttribute("user");
            if(!Common.isEmpty(user) && !Common.isEmpty(user.getCountryIds())){
                map.put("countryIds", user.getCountryIds());
            }
        }
        if (!Common.isEmpty(name)) map.put("name", "%" + name + "%");
        map.put("deleteFlag", 0);
        /*if (!Common.isEmpty(bscName)) {
            bscName = Common.decodeURL(bscName).trim();
            map.put("bscName", "%" + bscName + "%");
        }
        if (!Common.isEmpty(btsId)) {
            btsId = Common.decodeURL(btsId).trim();
            map.put("btsId", btsId);
        }*/
    }

    public GisManager getGisManager() {
        return gisManager;
    }

    public void setGisManager(GisManager gisManager) {
        this.gisManager = gisManager;
    }

    public Gis getGis() {
        return gis;
    }

    public void setGis(Gis gis) {
        this.gis = gis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
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

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(String countryIds) {
        this.countryIds = countryIds;
    }

    public void setGisCount(Integer gisCount) {
        this.gisCount = gisCount;
    }

    public void setBbuCount(Integer bbuCount) {
        this.bbuCount = bbuCount;
    }

    public void setTransferCount(Integer transferCount) {
        this.transferCount = transferCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
