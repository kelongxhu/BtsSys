package com.scttsc.business.service.impl;

import com.scttsc.baselibs.dao.*;
import com.scttsc.baselibs.model.AirLib;
import com.scttsc.baselibs.model.RoadLib;
import com.scttsc.baselibs.model.TunnelLib;
import com.scttsc.baselibs.model.WyLibScene;
import com.scttsc.business.dao.*;
import com.scttsc.business.model.*;
import com.scttsc.business.service.CellManualManager;
import com.scttsc.business.service.WyLogManager;
import com.scttsc.business.util.BusiUtil;
import com.scttsc.business.util.Constants;
import com.scttsc.business.util.CustomizeException;
import com.scttsc.business.vo.FindBackReponse;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cellManualManager")
@Transactional(readOnly = true)
public class CellManualManagerImpl implements CellManualManager {

    private static Logger LOG = Logger.getLogger(TunelManualManagerImpl.class);

    @Autowired
    private CellManualDao cellManualDao;
    @Autowired
    private CellLibDao cellLibDao;
    @Autowired
    private CellAirLibDao cellAirLibDao;
    @Autowired
    private CellDao cellDao;
    @Autowired
    private CcellDao ccellDao;
    @Autowired
    private CparCellDao cparCellDao;
    @Autowired
    private AirLibDao airLibDao;
    @Autowired
    private VitoLibDao vitoLibDao;
    @Autowired
    private RoadLibDao roadLibDao;
    @Autowired
    private TunnelLibDao tunnelLibDao;
    @Autowired
    private SchoolLibDao schoolLibDao;
    @Autowired
    private SecneryLibDao secneryLibDao;
    @Autowired
    private WyLogManager wyLogManager;

    public CellManual selectById(Long intId) throws Exception {
        return cellManualDao.selectById(intId);
    }

    public int insert(CellManual record) throws Exception {
        return cellManualDao.insert(record);
    }

    public int update(CellManual record) throws Exception {
        return cellManualDao.update(record);
    }

    /**
     * 导入小区手工表及对应表
     *
     * @param record
     * @throws Exception
     */
    public void insertImport(Map record, Map<String, Map> libMap) throws Exception {
        CellManual cellManual = new CellManual();
        BeanUtils.populate(cellManual, record);
        Ccell ccell = ccellDao.selectById(new Long(cellManual.getIntId()));
        Cell cell = cellDao.selectById(new Long(cellManual.getIntId()));
        if (ccell == null || cell == null) {
            throw new CustomizeException("小区信息未找到.");
        }
        cellManual = autoFullCellManual(cellManual, ccell, cell);
        //通过cellId查找手工数据是否已经录入，已经录入则删除数据以及关联数据，从新导入新数据
        CellManual old = cellManualDao.selectById(new Long(cellManual.getIntId()));
        if (old != null) {
            cellManualDao.deleteById(new Long(cellManual.getIntId())); //删除手工数据
            Map map = new HashMap();
            map.put("cellId", cellManual.getIntId());// 删除小区所有覆盖库信息
            cellLibDao.deleteByMap(map);
            cellAirLibDao.deleteByMap(map);
        }
        //插入数据以及关联数据
        cellManualDao.insert(cellManual); //插入小区手工表
        //组装关联天线库信息
        Object obj = record.get("airModel");
        if (obj != null) {
            String airModel = StringUtil.null2String(obj);
            String azimuth = StringUtil.null2String(record.get("azimuth"));//天线方位角
            String hanghigh = StringUtil.null2String(record.get("hanghigh"));  //天线挂高
            String electrondip = StringUtil.null2String(record.get("electrondip"));//天线电子倾角
            String enginedip = StringUtil.null2String(record.get("enginedip"));  //天线机械倾角
            //插入所有天线库
            Map<String, AirLib> airLibMap = libMap.get(Constants.AirLib + "");
            String[] airArray = airModel.split(";");
            String[] azimuthArray = azimuth.split(";");
            String[] hanghighArray = hanghigh.split(";");
            String[] electrondipArray = electrondip.split(";");
            String[] enginedipArray = enginedip.split(";");
            for (int i = 0; i < airArray.length; i++) {
                AirLib lib = airLibMap.get(airArray[i]);
                if (lib != null) {
                    CellAirLib cellAirLib = new CellAirLib();
                    cellAirLib.setCellIntId(cellManual.getIntId());
                    cellAirLib.setAirId(lib.getId().intValue());
                    if (azimuthArray.length > i) {
                        cellAirLib.setAzimuth(StringUtil.null2Double0(azimuthArray[i]));
                    }
                    if (hanghighArray.length > i) {
                        cellAirLib.setHanghigh(StringUtil.null2Double0(hanghighArray[i]));
                    }
                    if (electrondipArray.length > i) {
                        cellAirLib.setElectrondip(StringUtil.null2Double0(electrondipArray[i]));
                    }
                    if (enginedipArray.length > i) {
                        cellAirLib.setEnginedip(StringUtil.null2Double0(enginedipArray[i]));
                    }
                    cellAirLib.setTotaldip(autoTotaldip(cellAirLib));//设置天线总倾角
                    cellAirLibDao.insert(cellAirLib);
                } else {
                    throw new CustomizeException("填写天线库未找到," + airArray[i]);
                }
            }
        }

        //获取各个库Map
        Map<String, RoadLib> roadLibMap = libMap.get(Constants.RoadLib + "");
        Map<String, TunnelLib> tunnelLibMap = libMap.get(Constants.TunnelLib + "");
        Map<String,WyLibScene> sceneLibMap=libMap.get(Constants.SceneLib+"");


        //道路、隧道库
        String roadLib = cellManual.getRoadLib();
        if (!Common.isEmpty(roadLib)) {
            String[] roadLibArray = roadLib.split(";");
            for (String roadName : roadLibArray) {
                RoadLib lib = roadLibMap.get(roadName);
                boolean roadFlag = false;//该道路是否属于道路或者隧道
                //检测是否有道路覆盖
                if (lib != null) {
                    CellLib cellLib = new CellLib();
                    cellLib.setCellIntId(cellManual.getIntId());
                    cellLib.setLibId(lib.getId().intValue());
                    cellLib.setLibType(Constants.RoadLib);
                    cellLibDao.insert(cellLib);
                    continue;
                }
                //是否有隧道覆盖
                TunnelLib tunnel = tunnelLibMap.get(roadName);
                if (tunnel != null) {
                    CellLib cellLib = new CellLib();
                    cellLib.setCellIntId(cellManual.getIntId());
                    cellLib.setLibId(tunnel.getId().intValue());
                    cellLib.setLibType(Constants.TunnelLib);
                    cellLibDao.insert(cellLib);
                    roadFlag = true;
                }

                if (!roadFlag) {
                    throw new CustomizeException("道路和隧道库中未找到填写信息," + roadName);
                }

            }
        }
        //校园、风景库
        String hotLib = cellManual.getHotLib();
        if (!Common.isEmpty(hotLib)) {
            String[] hotLibArray = hotLib.split(";");
            for (String hot : hotLibArray) {
                WyLibScene sceneLib = sceneLibMap.get(hot);
                if (sceneLib==null) {
                    throw new CustomizeException("场景库中未找到该信息," + hot);
                }
                CellLib cellLib = new CellLib();
                cellLib.setCellIntId(cellManual.getIntId());
                cellLib.setLibId(sceneLib.getId().intValue());
                cellLib.setLibType(Constants.SceneLib);
                cellLibDao.insert(cellLib);
            }
        }


        // 将手工标识置为1，表示手工录入成功

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intId", cellManual.getIntId());
        map.put("manualFlag", 1);
        cellDao.updateByMap(map);

        //导入切入日志
        wyLogManager.insertLog(cellManual);

    }

    /**
     * 填充合成字段
     *
     * @param record
     * @param ccell
     * @param cell
     * @return
     * @throws Exception
     */

    private CellManual autoFullCellManual(CellManual record, Ccell ccell, Cell cell) throws Exception {
        String cellName = cell.getName();
        if (cellName.contains("室分")) {
            record.setCelltype("室内小区");
        } else if (cellName.contains("拉远")) {
            record.setCelltype("射频拉远");
        } else {
            record.setCelltype("标准小区");
        }
        /*
        // DO载频数量
        Map<String, Object> seachdoMap = new HashMap<String, Object>();
        seachdoMap.put("carType", "DO");
        seachdoMap.put("cellId", cell.getIntId());
        int dofrqnum = cellDao.countCarrierByCell(seachdoMap);
        record.setDofrqnum(dofrqnum);
        // DOPN
        CparCell cparCell = cparCellDao
                .selectById(new Long(cell.getIntId()));
        if (cparCell != null) {
            BigDecimal pilotpn = cparCell.getPilotpn();
            if (pilotpn != null) {
                record.setDopn(cparCell.getPilotpn().intValue());
            }
        }*/
        // 小区半径，室分小区为0.1，此外，城区0.2；县城、城郊0.3；农村乡镇0.5

        String coverArea = record.getCoverarea();

        if ("是".equals(cell.getIndoor())) {
            record.setRadii(0.1);
        } else if ("市区".equals(coverArea)) {
            record.setRadii(0.2);
        } else if ("城郊".equals(coverArea) || "县城".equals(coverArea)) {
            record.setRadii(0.3);
        } else if ("乡镇".equals(coverArea) || "农村".equals(coverArea)) {
            record.setRadii(0.5);

        }

        // 贵阳云岩、南明中；遵义遵义中的市区,定位因子取值:0.25,MAR:700
        // 其他市区,0.3,1000
        // 城郊:0.5,2500
        // 县城:0.4,2000
        // 乡镇:0.55,5000
        // 农村:0.75,10000

        double fixtext = 0;
        int mar = 0;
        if ("市区".equals(coverArea)) {
            int countryId = cell.getCountryId().intValue();
            int cityId = cell.getCityId().intValue();
            if (countryId == 10011 || countryId == 10012 || cityId == 10003) {
                fixtext = 0.25;
                mar = 700;
            } else {
                fixtext = 0.3;
                mar = 1000;
            }
        } else if ("城郊".equals(coverArea)) {
            fixtext = 0.5;
            mar = 2500;
        } else if ("县城".equals(coverArea)) {
            fixtext = 0.4;
            mar = 2000;
        } else if ("乡镇".equals(coverArea)) {
            fixtext = 0.55;
            mar = 5000;
        } else if ("农村".equals(coverArea)) {
            fixtext = 0.75;
            mar = 10000;
        }
        record.setFixtext(fixtext);
        record.setMar(mar);
        return record;
    }


    public void saveCellManual(CellManual record) throws Exception {

        // 补填自动合成字段
        Ccell ccell = ccellDao.selectById(new Long(record.getIntId()));
        Cell cell = cellDao.selectById(new Long(record.getIntId()));
        if (ccell != null) {
            record.setBscName(ccell.getBscName());
            record.setCi(ccell.getCi().intValue());
        }

        if (cell != null) {
            record.setName(cell.getName());
            record.setGfnum(StringUtil.null2Integer0(cell.getIsGf()));
            if ("0".equals(cell.getIsGf())) {
                record.setGfflag("否");
            } else {
                record.setGfflag("是");
            }
        }

        //填充小区手工信息
        record = autoFullCellManual(record, ccell, cell);
        // 插入小区手工信息
        cellManualDao.insert(record);
        insertAirLib(record);
        insertCellLib(record);
        // 将手工标识置为1，表示手工录入成功
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intId", record.getIntId());
        map.put("manualFlag", 1);
        cellDao.updateByMap(map);
        //切入日志，edit by 2013/8/2
        wyLogManager.insertLog(record);

    }

    /**
     * 编辑小区手工数据
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateCellManual(CellManual record) throws Exception {
        //切入日志
        wyLogManager.updateLog(record);
        //更新操作
        cellManualDao.update(record);// 更新小区手工数据可变字段
        Map map = new HashMap();
        map.put("cellId", record.getIntId());// 删除小区所有覆盖库信息
        cellLibDao.deleteByMap(map);
        insertCellLib(record);
        cellAirLibDao.deleteByMap(map);  //删除小区天线信息
        insertAirLib(record);
    }

    /**
     * 插入小区关联天线库
     *
     * @param record
     */
    void insertAirLib(CellManual record) {
        List<CellAirLib> airLibs = record.getAirLibs();
        // 插入小区天线信息
        if (airLibs != null) {
            for (CellAirLib obj : airLibs) {
                if (obj != null && obj.getAirId() != null) {
                    obj.setCellIntId(record.getIntId());
                    obj.setTotaldip(autoTotaldip(obj));  //设置天线总倾角
                    cellAirLibDao.insert(obj);
                }
            }
        }
    }

    /**
     * 自动合成总倾角
     *
     * @return
     */
    private double autoTotaldip(CellAirLib obj) {
        //自动合成一个天线总倾角
        double total = 0;
        try {
            double electrondipValue = obj.getElectrondip();
            double enginedipValue = obj.getEnginedip();
            total = electrondipValue + enginedipValue;
            AirLib airLib = airLibDao.getById(obj.getAirId().longValue());
            if (airLib != null) {
                total += airLib.getDegree();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 插入小区关联基本库
     *
     * @param record
     */
    void insertCellLib(CellManual record) {
        // 插入道路、隧道(道路：0_道路ID;道路ID_隧道ID)
        String roadLib = record.getRoadLib();
        if (!Common.isEmpty(roadLib)) {
            String[] strs = roadLib.split(Constants.SPLIT_SYMBOL);
            for (String str : strs) {
                if (!Common.isEmpty(str)) {
                    String s[] = str.split("_");
                    CellLib cellLib = new CellLib();
                    cellLib.setCellIntId(record.getIntId());
                    if ("-2".equals(s[0])) {
                        // 道路
                        cellLib.setLibId(StringUtil.null2Integer0(s[1]));
                        cellLib.setLibType(Constants.RoadLib);
                        cellLibDao.insert(cellLib);
                    } else if ("-3".equals(s[0])) {
                        // 隧道
                        cellLib.setLibId(StringUtil.null2Integer0(s[1]));
                        cellLib.setLibType(Constants.TunnelLib);
                        cellLibDao.insert(cellLib);
                    }
                }
            }
        }
        // 插入风景、校园库(风景：-2_风景ID,校园：-3_校园ID)
        String hotLib = record.getHotLib();
        if (!Common.isEmpty(hotLib)) {
            String[] strs = hotLib.split(Constants.SPLIT_SYMBOL);
            for (String str : strs) {
                if (!Common.isEmpty(str)) {
                    CellLib cellLib = new CellLib();
                    cellLib.setCellIntId(record.getIntId());
                    cellLib.setLibId(StringUtil.null2Integer0(str));
                    cellLib.setLibType(Constants.SceneLib);
                    cellLibDao.insert(cellLib);
                }
            }
        }
    }


    public List<CellLib> selectCellLibByMap(Object map) throws Exception {
        return cellLibDao.selectByMap(map);
    }

    public List<CellAirLib> selectCellAirLibByCellId(Long cellId) throws Exception {
        return cellAirLibDao.selectByCellId(cellId);
    }

    public int deleteCellLibByMap(Object map) throws Exception {
        return cellLibDao.deleteByMap(map);
    }


    public int deleteCellAirLibByMap(Object map) throws Exception {
        return cellAirLibDao.deleteByMap(map);
    }

    /**
     * 接口修改小区手工信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    public int updateByInterface(Map map) throws Exception {
        return cellManualDao.updateByInterface(map);
    }

    public List<MateEntry> findDelCellData(List<Long> ids) throws Exception {
        List<MateEntry> mateEntries = new ArrayList<MateEntry>();
        Long intId;
        if (ids.size() > 0) {
            intId = ids.get(0);
        } else {
            return mateEntries;
        }
        Cell cellDel = cellDao.selectById(intId);
        //根据名称查找再用数据
        Map map = new HashMap();
        String name = cellDel.getName();
        map.put("nameStr", BusiUtil.splitCellName(cellDel.getName()));
        map.put("deleteFlag", 0);
        List<Cell> cellList = cellDao.selectCellByMap(map);//在用bts
        //一个名称可能对多个再用小区
        if (cellList != null && cellList.size() > 0) {
            for (Cell cell : cellList) {
                //未录入的
                if (cell != null && cell.getManualFlag().intValue() == 0) {
                    MateEntry mateEntry = new MateEntry();
                    mateEntry.setDel_IntId(cellDel.getIntId());
                    mateEntry.setDel_BscName(cellDel.getBscName());
                    mateEntry.setDel_Name(cellDel.getName());
                    mateEntry.setDel_BtsId(cellDel.getBtsId());
                    mateEntry.setDel_ci(cellDel.getCi());
                    mateEntry.setDel_pn(cellDel.getPn());
                    mateEntry.setDel_ManualFlag(cellDel.getManualFlag().intValue());
                    mateEntry.setIntId(cell.getIntId());
                    mateEntry.setBscName(cell.getBscName());
                    mateEntry.setName(cell.getName());
                    mateEntry.setBtsId(cell.getBtsId());
                    mateEntry.setManualFlag(cell.getManualFlag().intValue());
                    mateEntry.setCi(cell.getCi());
                    mateEntry.setPn(cell.getPn());
                    mateEntries.add(mateEntry);
                }
            }
        }
        return mateEntries;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindBackReponse findManualData(List<String> ids) {
        FindBackReponse reponse = new FindBackReponse();
        int sucess = 0;
        try {
            for (String intIdObj : ids) {
                String[] pair = intIdObj.split(",");
                CellManual cellManual = cellManualDao.selectById(new Long(pair[0]));//废弃数据的手工数据
                //找回原则：废弃存在手工数据，当前再用不存在手工数据
                if (cellManual != null) {
                    Long intId = new Long(pair[1]);//再用intId
                    CellManual cellManual1 = cellManualDao.selectById(intId);
                    if (cellManual1 != null) {
                        cellManualDao.deleteById(intId);
                    }
                    //将废弃数据手工数据关联到现在的数据INT_ID
                    Map map = new HashMap();
                    map.put("newIntId", intId);
                    map.put("intId", cellManual.getIntId());
                    cellManualDao.updateByIntID(map);
                    //如果现在有数据，删除现在的，避免删除失败
                    //将现在的数据INT_ID状态更新为已录入
                    Map map1 = new HashMap();
                    map1.put("manualFlag", 1);
                    map1.put("intId", intId);
                    cellDao.updateByMap(map1);
                    //将废弃数据INT_ID状态更新未未录入
                    Map map2 = new HashMap();
                    map2.put("manualFlag", 0);
                    map2.put("intId", new Long(pair[0]));
                    cellDao.updateByMap(map2);
                    Map libUpdateMap = new HashMap();
                    libUpdateMap.put("newCellIntId", intId);
                    libUpdateMap.put("cellIntId", cellManual.getIntId());
                    //将天线库数据移到现有小区
                    cellAirLibDao.updateByMap(libUpdateMap);
                    //将其它库数据移到现有小区
                    cellLibDao.updateByMap(libUpdateMap);
                    sucess++;
                }
            }
            reponse.setResult("Y");
            reponse.setSucessNum(sucess);
        } catch (Exception e) {
            reponse.setSucessNum(sucess);
            reponse.setResult("N");
            LOG.error(e.getMessage(), e);
        }
        return reponse;
    }

    public CellManual getBtsMinCell(Long wybtsId) {
        CellManual cellManual = null;
        try {
            //计算小区覆盖情况
            List<Cell> cellList = cellDao.selectByBtsId(wybtsId);
            Cell minCcell = null;
            if (cellList != null) {
                int cellId = 0;
                int minCellId = 0;
                for (int i = 0; i < cellList.size(); i++) {
                    Cell cell = cellList.get(i);
                    cellId = cell.getCellId();
                    if (i == 0) {
                        minCellId = cellId;
                        minCcell = cell;
                    } else {
                        if (cellId < minCellId) {
                            minCellId = cellId;
                            minCcell = cell;
                        }
                    }
                }
            }
            if (minCcell != null) {
                //以最小cellId显示基站覆盖信息
                cellManual = cellManualDao.selectById(new Long(minCcell.getIntId()));//小区手工表
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return cellManual;
    }
}
