package com.scttsc.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scttsc.admin.dao.CityDao;
import com.scttsc.admin.model.City;
import com.scttsc.admin.service.CityManager;

@Service("cityManager")
public class CityManagerImpl implements CityManager {
	@Autowired
	private CityDao cityDao;

	public List<City> getCitys() throws Exception {
		return cityDao.getCitys();
	}

	public City getById(Long id) throws Exception {
		return cityDao.getById(id);
	}
	public List<City> getCountry() throws Exception {
		return cityDao.getCountry();
	}

    public List<City> getByMap(Object map) throws Exception {
        return cityDao.getByMap(map);
    }
}
