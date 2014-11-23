package com.scttsc.gis.dao;

import com.scttsc.business.model.Bbu;
import com.scttsc.gis.model.Gis;
import com.scttsc.gis.model.LatLng;

import java.util.List;

public interface GisDao {

    List<Gis> selectByMap(Object map)throws Exception;

    int countByMap(Object map)throws Exception;

    Gis getLatlngSum(Object map)throws Exception;

    int countLatlngNoZero(Object map)throws Exception;

    List<Bbu> selectBbuByMap(Object map)throws Exception;

    int countBbuByMap(Object map)throws Exception;

    LatLng selectSzLatLngFromBtsByName(String name)throws Exception;

    LatLng selectSzLatLngFromBbuByName(String name)throws Exception;

}
