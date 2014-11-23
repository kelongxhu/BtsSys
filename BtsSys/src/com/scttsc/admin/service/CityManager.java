package com.scttsc.admin.service;

import com.scttsc.admin.model.City;

import java.util.List;

public interface CityManager {
    List<City> getCitys() throws Exception;

    City getById(Long id) throws Exception;

    List<City> getCountry() throws Exception;

    List<City> getByMap(Object map) throws Exception;
}
