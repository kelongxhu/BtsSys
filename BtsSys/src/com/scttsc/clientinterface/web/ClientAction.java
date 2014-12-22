package com.scttsc.clientinterface.web;

import com.scttsc.admin.model.User;
import com.scttsc.admin.service.UserManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.SchoolLibManager;
import com.scttsc.business.model.*;
import com.scttsc.business.service.*;
import com.scttsc.business.util.ReflectUtil;
import com.scttsc.clientinterface.model.Alarm;
import com.scttsc.clientinterface.service.InterfaceManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: Administrator
 * Date: 13-10-29
 * Time: 下午5:02
 */
public class ClientAction extends BaseAction {
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private BbuManager bbuManager;
    @Autowired
    private IndoorManualManager indoorManualManager;
    @Autowired
    private CellManager cellManager;
    @Autowired
    private BtsManualManager btsManualManager;
    @Autowired
    private BbuManualManager bbuManualManager;
    @Autowired
    private CellManualManager cellManualManager;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private SchoolLibManager schoolLibManager;
    @Autowired
    private ColumneditInterfaceManager columneditInterfaceManager;
    @Autowired
    private InterfaceManager interfaceManager;

    private String name;

    private Integer btsId;

    private String bscName;

    private String imsi;

    private Integer pn;

    private Integer ci;

    private String groupCode;

    //物理站点

    private BtsManual btsManual;

    //bbu
    private BbuManual bbuManual;

    //室内
    private IndoorManual indoorManual;

    //小区
    private CellManual cellManual;


    private String cityName;//告警接口


    /**
     * 查询物理站点
     *
     * @return
     */
    public String bts() {
        List<Map> btsList = null;
        User user = null;
        List<List<Map>> dataList = new ArrayList<List<Map>>();
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map map = new HashMap();
            map.put("countryIds", user.getCountryIds());
            if (!Common.isEmpty(name)) {
//                name = new String(name.getBytes("iso8859-1"), "GBK");
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(bscName)) {
                map.put("bscName", "%" + bscName + "%");
            }
            btsList = btsManager.selectExportDataByMap(map);
            //自动组装字段

            Map columnsMap = new HashMap();
            columnsMap.put("type", 1);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //字段配置
            Map<String, ColumnsConfig> columnsConfigMap = new LinkedHashMap<String, ColumnsConfig>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    String enName = columnsConfig.getEnName();
                    columnsConfigMap.put(enName, columnsConfig);
                }
            }
            if (btsList != null) {
                for (Map btsData : btsList) {
                    List data = new ArrayList();
                    for (String columnsKey : columnsConfigMap.keySet()) {
                        ColumnsConfig config = columnsConfigMap.get(columnsKey);
                        Map rowData = new LinkedHashMap();
                        if (config != null) {
                            rowData.put("cnName", config.getCnName());
                            rowData.put("enName", config.getEnName());
                            rowData.put("enEdit", config.getEnedit());
                            rowData.put("comboBox", config.getComBoBox());
                        }
                        rowData.put("columnData", btsData.get(columnsKey));
                        data.add(rowData);
                    }
                    dataList.add(data);
                }
            }
            jsonMap.put("result", 1); //解析成功
            setJsonMapRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);//程序异常
        }
        return SUCCESS;
    }

    /**
     * 查询bbu方法
     *
     * @return
     */
    public String bbu() {
        List<Map> bbuList = null;
        User user = null;
        List<List<Map>> dataList = new ArrayList<List<Map>>();
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map map = new HashMap();
            map.put("countryIds", user.getCountryIds());
            if (!Common.isEmpty(name)) {
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(bscName)) {
                map.put("bscName", "%" + bscName + "%");
            }
            bbuList = bbuManager.selectExportBbuDataByMap(map);

            Map columnsMap = new HashMap();
            columnsMap.put("type", 2);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //字段配置
            Map<String, ColumnsConfig> columnsConfigMap = new LinkedHashMap<String, ColumnsConfig>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    String enName = columnsConfig.getEnName();
                    columnsConfigMap.put(enName, columnsConfig);
                }
            }
            if (bbuList != null) {
                for (Map bbuData : bbuList) {
                    List data = new ArrayList();
                    for (String columnsKey : columnsConfigMap.keySet()) {
                        ColumnsConfig config = columnsConfigMap.get(columnsKey);
                        Map rowData = new LinkedHashMap();
                        if (config != null) {
                            rowData.put("cnName", config.getCnName());
                            rowData.put("enName", config.getEnName());
                            rowData.put("enEdit", config.getEnedit());
                            rowData.put("comboBox", config.getComBoBox());
                        }
                        rowData.put("columnData", bbuData.get(columnsKey));
                        data.add(rowData);
                    }
                    dataList.add(data);
                }
            }
            jsonMap.put("result", 1); //解析成功
            setJsonMapRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);//程序异常
        }
        return SUCCESS;
    }

    /**
     * 室分查询
     *
     * @return
     */
    public String indoor() {
        List<Map> indoorList = null;
        User user = null;
        List<List<Map>> dataList = new ArrayList<List<Map>>();
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map map = new HashMap();
            map.put("countryIds", user.getCountryIds());
            if (!Common.isEmpty(name)) {
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(bscName)) {
                map.put("bscName", "%" + bscName + "%");
            }
            indoorList = indoorManualManager.selectExportIndoorDataByMap(map);
            Map columnsMap = new HashMap();
            columnsMap.put("type", 3);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //字段配置
            Map<String, ColumnsConfig> columnsConfigMap = new LinkedHashMap<String, ColumnsConfig>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    String enName = columnsConfig.getEnName();
                    columnsConfigMap.put(enName, columnsConfig);
                }
            }
            if (indoorList != null) {
                for (Map indoorData : indoorList) {
                    List data = new ArrayList();
                    for (String columnsKey : columnsConfigMap.keySet()) {
                        ColumnsConfig config = columnsConfigMap.get(columnsKey);
                        Map rowData = new LinkedHashMap();
                        if (config != null) {
                            rowData.put("cnName", config.getCnName());
                            rowData.put("enName", config.getEnName());
                            rowData.put("enEdit", config.getEnedit());
                            rowData.put("comboBox", config.getComBoBox());
                        }
                        rowData.put("columnData", indoorData.get(columnsKey));
                        data.add(rowData);
                    }
                    dataList.add(data);
                }
            }
            jsonMap.put("result", 1); //解析成功
            setJsonMapRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);//程序异常
        }
        return SUCCESS;
    }

    /**
     * 小区查询
     *
     * @return
     */
    public String cell() {
        List<Map> cellList = null;
        User user = null;
        List<List<Map>> dataList = new ArrayList<List<Map>>();
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map map = new HashMap();
            map.put("countryIds", user.getCountryIds());
            if (!Common.isEmpty(name)) {
                map.put("name", "%" + name + "%");
            }
            if (!Common.isEmpty(btsId)) {
                map.put("btsId", btsId);
            }
            if (!Common.isEmpty(bscName)) {
                map.put("bscName", "%" + bscName + "%");
            }
            if (!Common.isEmpty(pn)) {
                map.put("pn", pn);
            }
            if (!Common.isEmpty(ci)) {
                map.put("ci", ci);
            }
            cellList = cellManager.selectExportCellDataByMap(map);
            Map columnsMap = new HashMap();
            columnsMap.put("type", 4);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //字段配置
            Map<String, ColumnsConfig> columnsConfigMap = new LinkedHashMap<String, ColumnsConfig>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    String enName = columnsConfig.getEnName();
                    columnsConfigMap.put(enName, columnsConfig);
                }
            }
            if (cellList != null) {
                for (Map cellData : cellList) {
                    List data = new ArrayList();
                    for (String columnsKey : columnsConfigMap.keySet()) {
                        ColumnsConfig config = columnsConfigMap.get(columnsKey);
                        Map rowData = new LinkedHashMap();
                        rowData.put("cnName", config.getCnName());
                        rowData.put("enName", config.getEnName());
                        rowData.put("enEdit", config.getEnedit());
                        rowData.put("comboBox", config.getComBoBox());
                        //如果是airLib则返回天线库信息
                        if("AIRLIB".equals(config.getEnName())){
                            Long cellId=StringUtil.null2Long0(cellData.get("INT_ID"));
                            List<Map> airLib=cellManager.selectCellAirLibByCellId(cellId);
                            rowData.put("rowData",airLib);
                        }else{
                            rowData.put("columnData", cellData.get(columnsKey));
                        }
                        data.add(rowData);
                    }
                    dataList.add(data);
                }
            }
            jsonMap.put("result", 1); //解析成功
            setJsonMapRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);//程序异常
        }
        return SUCCESS;
    }

    /**
     * 更新物理站点接口
     *
     * @return
     */
    public String updatebts() {
        User user = null;
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map updateMap = new HashMap();
            Map columnsMap = new HashMap();
            columnsMap.put("type", 1);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //定义修改字段
            StringBuilder cncolumnnameSb = new StringBuilder();
            StringBuilder encolumnnameSb = new StringBuilder();
            StringBuilder columnvalueSb = new StringBuilder();
            Long intId = 0l;
            Bts bts;
            List<WyColumneditInterface> wyColumneditInterfaceList = new ArrayList<WyColumneditInterface>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    HttpServletRequest request = this.getRequest();
                    Object object = request.getParameter(columnsConfig.getEnName());
                    if (object != null) {
//                        updateMap.put(columnsConfig.getEnName(), object);
                        //修改变量
                        if ("INT_ID".equalsIgnoreCase(columnsConfig.getEnName())) {
                            intId = StringUtil.null2Long0(object);
                        } else {
                            //获取修改字段值
                            WyColumneditInterface wyColumneditInterface = new WyColumneditInterface();
                            wyColumneditInterface.setCncolumnname(columnsConfig.getCnName());
                            wyColumneditInterface.setEncolumnname(columnsConfig.getEnName());
                            wyColumneditInterface.setColumnvalue(object.toString());
                            wyColumneditInterfaceList.add(wyColumneditInterface);
                        }
                    }
                }
            }
            bts = btsManager.getById(intId);
            Map record=new HashMap();
            record.put("intId",intId);
            List<Map> btsList=btsManager.selectExportDataByMap(record);
            Map data=null;
            if(btsList!=null&&btsList.size()>0){
                 data=btsList.get(0);
            }
            for (WyColumneditInterface wyColumneditInterface : wyColumneditInterfaceList) {
                wyColumneditInterface.setIntId(intId);
                wyColumneditInterface.setName(bts.getName());
                Object columnData=(data==null)?null:data.get(wyColumneditInterface.getEncolumnname());
                wyColumneditInterface.setColumnValueOld(columnData == null ? "" : columnData.toString());
                wyColumneditInterface.setCityId(bts.getCityId().intValue());
                wyColumneditInterface.setType(1);
                wyColumneditInterface.setStatus(0);
                wyColumneditInterface.setIntime(new Date());
                wyColumneditInterface.setUserid(user.getIntId());
                columneditInterfaceManager.insert(wyColumneditInterface);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        return SUCCESS;
    }


    /**
     * 更新物理站点接口
     *
     * @return
     */
    public String updatebbu() {
        User user = null;
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map updateMap = new HashMap();
            Map columnsMap = new HashMap();
            columnsMap.put("type", 2);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //定义修改字段
            StringBuilder cncolumnnameSb = new StringBuilder();
            StringBuilder encolumnnameSb = new StringBuilder();
            StringBuilder columnvalueSb = new StringBuilder();
            Long intId = 0l;
            Bbu bbu;
            List<WyColumneditInterface> wyColumneditInterfaceList = new ArrayList<WyColumneditInterface>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    HttpServletRequest request = this.getRequest();
                    Object object = request.getParameter(columnsConfig.getEnName());
                    if (object != null) {
                        //                        updateMap.put(columnsConfig.getEnName(), object);
                        //修改变量
                        if ("INT_ID".equalsIgnoreCase(columnsConfig.getEnName())) {
                            intId = StringUtil.null2Long0(object);
                        } else {
                            //获取修改字段值
                            WyColumneditInterface wyColumneditInterface = new WyColumneditInterface();
                            wyColumneditInterface.setCncolumnname(columnsConfig.getCnName());
                            wyColumneditInterface.setEncolumnname(columnsConfig.getEnName());
                            wyColumneditInterface.setColumnvalue(object.toString());
                            wyColumneditInterfaceList.add(wyColumneditInterface);
                        }
                    }
                }
            }
            bbu = bbuManager.getById(intId);
            Map record = new HashMap();
            record.put("intId", intId);
            List<Map> bbuList = bbuManager.selectExportBbuDataByMap(record);
            Map data = null;
            if (bbuList != null && bbuList.size() > 0) {
                data = bbuList.get(0);
            }
            for (WyColumneditInterface wyColumneditInterface : wyColumneditInterfaceList) {
                wyColumneditInterface.setIntId(intId);
                wyColumneditInterface.setName(bbu.getName());
                Object columnData=(data==null)?null:data.get(wyColumneditInterface.getEncolumnname());
                wyColumneditInterface.setColumnValueOld(columnData == null ? "" : columnData.toString());
                wyColumneditInterface.setCityId(bbu.getCityId().intValue());
                wyColumneditInterface.setType(2);
                wyColumneditInterface.setStatus(0);
                wyColumneditInterface.setIntime(new Date());
                wyColumneditInterface.setUserid(user.getIntId());
                columneditInterfaceManager.insert(wyColumneditInterface);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        return SUCCESS;
    }

    /**
     * 编辑室分手工数据
     *
     * @return
     */
    public String updateIndoor() {
        User user = null;
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map updateMap = new HashMap();
            Map columnsMap = new HashMap();
            columnsMap.put("type", 3);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //定义修改字段
            StringBuilder cncolumnnameSb = new StringBuilder();
            StringBuilder encolumnnameSb = new StringBuilder();
            StringBuilder columnvalueSb = new StringBuilder();
            Long intId = 0l;
            Bts bts;
            List<WyColumneditInterface> wyColumneditInterfaceList = new ArrayList<WyColumneditInterface>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    HttpServletRequest request = this.getRequest();
                    Object object = request.getParameter(columnsConfig.getEnName());
                    if (object != null) {
                        //                        updateMap.put(columnsConfig.getEnName(), object);
                        //修改变量
                        if ("INT_ID".equalsIgnoreCase(columnsConfig.getEnName())) {
                            intId = StringUtil.null2Long0(object);
                        } else {
                            //获取修改字段值
                            WyColumneditInterface wyColumneditInterface = new WyColumneditInterface();
                            wyColumneditInterface.setCncolumnname(columnsConfig.getCnName());
                            wyColumneditInterface.setEncolumnname(columnsConfig.getEnName());
                            wyColumneditInterface.setColumnvalue(object.toString());
                            wyColumneditInterfaceList.add(wyColumneditInterface);
                        }
                    }
                }
            }
            bts = btsManager.getById(intId);
            Map record = new HashMap();
            record.put("intId", intId);
            List<Map> indoorList = indoorManualManager.selectExportIndoorDataByMap(record);
            Map data = null;
            if (indoorList != null && indoorList.size() > 0) {
                data = indoorList.get(0);
            }
            for (WyColumneditInterface wyColumneditInterface : wyColumneditInterfaceList) {
                wyColumneditInterface.setIntId(intId);
                wyColumneditInterface.setName(bts.getName());
                Object columnData=(data==null)?null:data.get(wyColumneditInterface.getEncolumnname());
                wyColumneditInterface.setColumnValueOld(columnData == null ? "" : columnData.toString());
                wyColumneditInterface.setCityId(bts.getCityId().intValue());
                wyColumneditInterface.setType(3);
                wyColumneditInterface.setStatus(0);
                wyColumneditInterface.setIntime(new Date());
                wyColumneditInterface.setUserid(user.getIntId());
                columneditInterfaceManager.insert(wyColumneditInterface);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        return SUCCESS;
    }

    /**
     * 编辑小区手工信息
     *
     * @return
     */
    public String updateCell() {
        User user = null;
        try {
            if (!Common.isEmpty(imsi)) {
                User u = userManager.selectByImsi(imsi);
                if (u != null) {
                    user = userManager.getByPriKey(u.getIntId());
                    user = userManager.strongUser(user);
                }
            }
            if (user == null) {
                jsonMap.put("result", 0);//用户不存在
                return SUCCESS;
            }
            Map updateMap = new HashMap();
            Map columnsMap = new HashMap();
            columnsMap.put("type", 4);
            columnsMap.put("enable", 0);
            List<ColumnsConfig> columnsConfigs = templateManager.selectColumnsByConds(columnsMap);
            //定义修改字段
            StringBuilder cncolumnnameSb = new StringBuilder();
            StringBuilder encolumnnameSb = new StringBuilder();
            StringBuilder columnvalueSb = new StringBuilder();
            Long intId = 0l;
            Cell cell;
            List<WyColumneditInterface> wyColumneditInterfaceList = new ArrayList<WyColumneditInterface>();
            if (columnsConfigs != null) {
                for (ColumnsConfig columnsConfig : columnsConfigs) {
                    HttpServletRequest request = this.getRequest();
                    Object object = request.getParameter(columnsConfig.getEnName());
                    if (object != null) {
                        //                        updateMap.put(columnsConfig.getEnName(), object);
                        //修改变量
                        if ("INT_ID".equalsIgnoreCase(columnsConfig.getEnName())) {
                            intId = StringUtil.null2Long0(object);
                        } else {
                            //获取修改字段值
                            WyColumneditInterface wyColumneditInterface = new WyColumneditInterface();
                            wyColumneditInterface.setCncolumnname(columnsConfig.getCnName());
                            wyColumneditInterface.setEncolumnname(columnsConfig.getEnName());
                            wyColumneditInterface.setColumnvalue(object.toString());
                            wyColumneditInterfaceList.add(wyColumneditInterface);
                        }
                    }
                }
            }

            cell = cellManager.selectById(intId);
            Map record = new HashMap();
            record.put("intId", intId);
            List<Map> cellList = cellManager.selectExportCellDataByMap(record);
            Map data = null;
            if (cellList != null && cellList.size() > 0) {
                data = cellList.get(0);
            }
            for (WyColumneditInterface wyColumneditInterface : wyColumneditInterfaceList) {
                wyColumneditInterface.setIntId(intId);
                wyColumneditInterface.setName(cell.getName());
                Object columnData=(data==null)?null:data.get(wyColumneditInterface.getEncolumnname());
                wyColumneditInterface.setColumnValueOld(columnData == null ? "" : columnData.toString());
                wyColumneditInterface.setCityId(cell.getCityId().intValue());
                wyColumneditInterface.setType(4);
                wyColumneditInterface.setStatus(0);
                wyColumneditInterface.setIntime(new Date());
                wyColumneditInterface.setUserid(user.getIntId());
                columneditInterfaceManager.insert(wyColumneditInterface);
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        return SUCCESS;
    }

    /**
     * 获取变量列表
     *
     * @return
     */
    public String getCons() {
        List<Cons> list = null;
        Map<String, String> comboBoxColumnMap = new HashMap<String, String>();
        comboBoxColumnMap.put("INSTALL_POS_CODE_NAME", "BTSINSTALL"); //主设备安装位置
        comboBoxColumnMap.put("TOWER_TYPE_NAME", "TOWERTYPE");  //塔跪类型
        comboBoxColumnMap.put("MR_STRUT_NAME", "MRSTRUT");//机房结构j
        Map map = new HashMap();
        try {
            if (!Common.isEmpty(groupCode)) {
                //接口查询要columnsConfig的enName和groupCode要对应查询
                String comboBoxCode = comboBoxColumnMap.get(groupCode);
                if (comboBoxCode != null && !"".equals(comboBoxCode)) {
                    map.put("groupCode", comboBoxCode);
                } else {
                    map.put("groupCode", groupCode);
                }
                list = schoolLibManager.getCons(map);
                jsonMap.put("result", 1);
            } else {
                jsonMap.put("result", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        setJsonMapRows(list);
        return SUCCESS;
    }

    /**
     * 获取告警码为133157的告警数据
     *
     * @return
     */
    public String getAlarm() {
        List<Alarm> alarmList = null;
        Map map = new HashMap();
        try {
            alarmList = interfaceManager.getAlarms(cityName);
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 99);
        }
        setJsonMapRows(alarmList);
        return SUCCESS;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBtsId() {
        return btsId;
    }

    public void setBtsId(Integer btsId) {
        this.btsId = btsId;
    }

    public String getBscName() {
        return bscName;
    }

    public void setBscName(String bscName) {
        this.bscName = bscName;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public BtsManual getBtsManual() {
        return btsManual;
    }

    public void setBtsManual(BtsManual btsManual) {
        this.btsManual = btsManual;
    }

    public CellManual getCellManual() {
        return cellManual;
    }

    public void setCellManual(CellManual cellManual) {
        this.cellManual = cellManual;
    }

    public IndoorManual getIndoorManual() {
        return indoorManual;
    }

    public void setIndoorManual(IndoorManual indoorManual) {
        this.indoorManual = indoorManual;
    }

    public BbuManual getBbuManual() {
        return bbuManual;
    }

    public void setBbuManual(BbuManual bbuManual) {
        this.bbuManual = bbuManual;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
