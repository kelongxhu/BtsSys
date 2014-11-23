package com.scttsc.gis.service.impl;

import com.scttsc.business.model.Bbu;
import com.scttsc.common.util.Common;
import com.scttsc.gis.dao.GisDao;
import com.scttsc.gis.model.Gis;
import com.scttsc.gis.model.LatLng;
import com.scttsc.gis.service.GisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("gisManager")
@Transactional(readOnly = true)
public class GisManagerImpl implements GisManager{

    @Autowired
    private GisDao gisDao;

    public List<Gis> selectByMap(Object map)throws Exception{
        return  gisDao.selectByMap(map);
    }

    public int countByMap(Object map)throws Exception{
        return gisDao.countByMap(map);
    }

    public Gis getLatlngSum(Object map)throws Exception{
        return gisDao.getLatlngSum(map);
    }

    public int countLatlngNoZero(Object map)throws Exception{
        return gisDao.countLatlngNoZero(map);
    }

    public List<Bbu> selectBbuByMap(Object map) throws Exception {
        return gisDao.selectBbuByMap(map);
    }

    public int countBbuByMap(Object map) throws Exception {
        return gisDao.countBbuByMap(map);
    }

    public LatLng selectSzLatLngByName(String name) throws Exception {
        LatLng latLng = gisDao.selectSzLatLngFromBtsByName(name);
        if(Common.isEmpty(latLng)) latLng = gisDao.selectSzLatLngFromBbuByName(name);
        return latLng;
    }
}
