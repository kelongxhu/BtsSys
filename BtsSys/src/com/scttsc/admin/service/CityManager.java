package com.scttsc.admin.service;

import com.scttsc.admin.model.City;

import java.util.List;

public interface CityManager {
    /**
     * 获取城市列表
     * @return
     * @throws Exception
     */
    List<City> getCitys() throws Exception;

    /**
     * 获取城市信息
     * @param id
     * @return
     * @throws Exception
     */
    City getById(Long id) throws Exception;

    /**获取区县列表
     * @return
     * @throws Exception
     */
    List<City> getCountry() throws Exception;

    /**
     * 查询地市区县
     * @param map
     * @return
     * @throws Exception
     */
    List<City> getByMap(Object map) throws Exception;
}
