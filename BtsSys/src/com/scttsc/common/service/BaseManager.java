package com.scttsc.common.service;

import java.io.Serializable;
import java.util.List;

public interface BaseManager<T, PK extends Serializable> {

	public T get(PK id)throws Exception;

	public List<T> loadAll()throws Exception;

	public void save(T t)throws Exception;

	public void update(T t)throws Exception;

	public void deleteByKey(PK id)throws Exception;

	public void delete (T t)throws Exception;
	
	public List<T> selectByMap(Object map)throws Exception;
	
	public int countByMap(Object map)throws Exception;
}
