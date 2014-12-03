package com.scttsc.business.service.impl;

import com.scttsc.admin.dao.CityDao;
import com.scttsc.admin.model.City;
import com.scttsc.business.dao.*;
import com.scttsc.business.service.DashBordManager;
import com.scttsc.business.vo.DataCityItem;
import com.scttsc.business.vo.DataCityItemNoManual;
import com.scttsc.common.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by _think on 2014/12/2.
 */
@Service("dashBordManager")
public class DashBordManagerImpl implements DashBordManager {
    Logger LOG = Logger.getLogger(DashBordManagerImpl.class);
    @Autowired
    private BtsDao btsDao;
    @Autowired
    private BbuDao bbuDao;
    @Autowired
    private WyTunelDao wyTunelDao;
    @Autowired
    private CellDao cellDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private WrongNameDao wrongNameDao;

    public List<DataCityItem> selectGroupbyCity() {
        List<DataCityItem> cityItemList = new ArrayList<DataCityItem>();
        try {
            Map param = new HashMap();
            param.put("pid", "10001");
            List<City> citys = cityDao.getByMap(param);
            List<Map> btsCitys = btsDao.selectBtsCountGroupByCity("否",null);//室外站点
            List<Map> bbuCitys = bbuDao.selectBbuCountGroupByCity(null);//bbu站点
            List<Map> indoorCitys = btsDao.selectBtsCountGroupByCity("是",null);//室分站点
            List<Map> tunelCitys = wyTunelDao.selectTunelCountGroupByCity(null);//隧道站点
            List<Map> outCellCitys = cellDao.selectCellCountGroupByCity("否",null);//室外
            List<Map> indoorCellCitys = cellDao.selectCellCountGroupByCity("是",null);//室分小区
            List<Map> tunelCellCitys = cellDao.selectCellCountGroupByCity("隧",null);//隧道小区
            //组装数据
            Map<String, DataCityItem> dataCityItemMap = new LinkedHashMap<String, DataCityItem>();
            for (City city : citys) {
                DataCityItem cityItem = new DataCityItem();
                cityItem.setCityId(city.getId());
                cityItem.setCityName(city.getCityName());
                dataCityItemMap.put(city.getId() + "", cityItem);
            }
            //組裝室外覆蓋站點
            for (Map btsCity : btsCitys) {
                String cityId = StringUtil.null2String(btsCity.get("CITY_ID"));
                int btsCount = StringUtil.null2Integer0(btsCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setOutBtsCount(btsCount);
                }
            }
            //组装bbu站点
            for (Map bbuCity : bbuCitys) {
                String cityId = StringUtil.null2String(bbuCity.get("CITY_ID"));
                int bbuCount = StringUtil.null2Integer0(bbuCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setBbuCount(bbuCount);
                }
            }
            //组装室分站点
            for (Map indoor : indoorCitys) {
                String cityId = StringUtil.null2String(indoor.get("CITY_ID"));
                int indoorCount = StringUtil.null2Integer0(indoor.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setIndoorBtsCount(indoorCount);
                }
            }
            //组装隧道站点
            for (Map tunelCity : tunelCitys) {
                String cityId = StringUtil.null2String(tunelCity.get("CITY_ID"));
                int tunelCount = StringUtil.null2Integer0(tunelCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setTunelCount(tunelCount);
                }
            }
            //组装室外小区
            for (Map cellCity : outCellCitys) {
                String cityId = StringUtil.null2String(cellCity.get("CITY_ID"));
                int cellCount = StringUtil.null2Integer0(cellCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setOutCellCount(cellCount);
                }
            }
            //室内小区
            for (Map cellCity : indoorCellCitys) {
                String cityId = StringUtil.null2String(cellCity.get("CITY_ID"));
                int cellCount = StringUtil.null2Integer0(cellCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setIndoorCellCount(cellCount);
                }
            }
            //隧道小区
            for (Map cellCity : tunelCellCitys) {
                String cityId = StringUtil.null2String(cellCity.get("CITY_ID"));
                int cellCount = StringUtil.null2Integer0(cellCity.get("NUM"));
                DataCityItem cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setTunelCellCount(cellCount);
                }
            }
            //
            for (Map.Entry<String, DataCityItem> dataCityItemEntry : dataCityItemMap.entrySet()) {
                cityItemList.add(dataCityItemEntry.getValue());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return cityItemList;
    }

    public List<DataCityItemNoManual> statNoManualGroupByCity() {
        List<DataCityItemNoManual> cityItemList = new ArrayList<DataCityItemNoManual>();
        try {
            Map param = new HashMap();
            param.put("pid", "10001");
            List<City> citys = cityDao.getByMap(param);
            List<Map> btsCitys = btsDao.selectBtsCountGroupByCity("否",0);//室外站点
            List<Map> bbuCitys = bbuDao.selectBbuCountGroupByCity(0);//bbu站点
            List<Map> indoorCitys = btsDao.selectBtsCountGroupByCity("是",0);//室分小区
            List<Map> tunelCitys = wyTunelDao.selectTunelCountGroupByCity(0);//隧道小区
            List<Map> outCellCitys = cellDao.selectCellCountGroupByCity("否",0);//室外
            List<Map> wrongNameCitys=wrongNameDao.selectWrongNameGroupByCity();//错误小区
            //组装数据
            Map<String, DataCityItemNoManual> dataCityItemMap = new LinkedHashMap<String, DataCityItemNoManual>();
            for (City city : citys) {
                DataCityItemNoManual cityItem = new DataCityItemNoManual();
                cityItem.setCityId(city.getId());
                cityItem.setCityName(city.getCityName());
                dataCityItemMap.put(city.getId() + "", cityItem);
            }
            //組裝室外覆蓋站點
            for (Map btsCity : btsCitys) {
                String cityId = StringUtil.null2String(btsCity.get("CITY_ID"));
                int btsCount = StringUtil.null2Integer0(btsCity.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setOutBtsCount(btsCount);
                }
            }
            //组装bbu站点
            for (Map bbuCity : bbuCitys) {
                String cityId = StringUtil.null2String(bbuCity.get("CITY_ID"));
                int bbuCount = StringUtil.null2Integer0(bbuCity.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setBbuCount(bbuCount);
                }
            }
            //组装室分小区
            for (Map indoor : indoorCitys) {
                String cityId = StringUtil.null2String(indoor.get("CITY_ID"));
                int indoorCount = StringUtil.null2Integer0(indoor.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setIndoorCellCount(indoorCount);
                }
            }
            //组装隧道小区
            for (Map tunelCity : tunelCitys) {
                String cityId = StringUtil.null2String(tunelCity.get("CITY_ID"));
                int tunelCount = StringUtil.null2Integer0(tunelCity.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setTunelCellCount(tunelCount);
                }
            }
            //组装室外小区
            for (Map cellCity : outCellCitys) {
                String cityId = StringUtil.null2String(cellCity.get("CITY_ID"));
                int cellCount = StringUtil.null2Integer0(cellCity.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setOutCellCount(cellCount);
                }
            }

            //组装错误小区
            for(Map wrongNameCity:wrongNameCitys){
                String cityId = StringUtil.null2String(wrongNameCity.get("CITY_ID"));
                int wrongNameCount = StringUtil.null2Integer0(wrongNameCity.get("NUM"));
                DataCityItemNoManual cityItem = dataCityItemMap.get(cityId);
                if (cityItem != null) {
                    cityItem.setWrongNameCount(wrongNameCount);
                }
            }

            //
            for (Map.Entry<String, DataCityItemNoManual> dataCityItemEntry : dataCityItemMap.entrySet()) {
                cityItemList.add(dataCityItemEntry.getValue());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return cityItemList;
    }
}
