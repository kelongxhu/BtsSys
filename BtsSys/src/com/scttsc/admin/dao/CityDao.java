package com.scttsc.admin.dao;

import java.util.List;

import com.scttsc.admin.model.City;
import com.scttsc.common.dao.MyBatisRepository;

@MyBatisRepository
public interface CityDao {
	List<City> getCitys() throws Exception;

	City getById(Long id) throws Exception;

	List<City> getCountry() throws Exception;

    List<City> getByMap(Object map)throws Exception;
}
