package com.scttsc.baselibs.service;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.model.SchoolLib;

import java.util.List;
import java.util.Map;

public interface SchoolLibManager {
    /**
     * 插入校园库
     * @param schoolLib
     * @throws Exception
     */
	void insert(SchoolLib schoolLib) throws Exception;

    /**
     * 更新校园库
     * @param schoolLib
     * @throws Exception
     */
	void update(SchoolLib schoolLib) throws Exception;

    /**
     * 分页获取校园库
     * @param map
     * @return
     * @throws Exception
     */
	List<SchoolLib> getByConds(Map<String,Object> map)throws Exception;

    /**
     * 获取校园库总数
     * @param map
     * @return
     * @throws Exception
     */
	int countByConds(Map<String,Object> map)throws Exception;

    /**
     * 根据条件查询常量列表
     * @param map
     * @return
     * @throws Exception
     */
	List<Cons> getCons(Object map)throws Exception;

    /**
     * 根据ID查询校园库
     * @param id
     * @return
     * @throws Exception
     */
	SchoolLib getById(Long id)throws Exception;

    /**
     * 删除校园库
     * @param map
     * @throws Exception
     */
	void delete(Object map)throws Exception;

    /**
     * 加载所有校园库
     * @return
     * @throws Exception
     */
	List<SchoolLib> loadAll()throws Exception;

    /**
     * 逻辑删除校园库
     * @param map
     * @return
     * @throws Exception
     */
    int deleteByDeleteFlag(Object map)throws Exception;
}
