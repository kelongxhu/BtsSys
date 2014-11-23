package com.scttsc.gis.service;


import com.scttsc.business.model.Bbu;
import com.scttsc.gis.model.Gis;
import com.scttsc.gis.model.LatLng;

import java.util.List;

public interface GisManager {

    List<Gis> selectByMap(Object map)throws Exception;

    int countByMap(Object map)throws Exception;

    Gis getLatlngSum(Object map)throws Exception;

    int countLatlngNoZero(Object map)throws Exception;

    public List<Bbu> selectBbuByMap(Object map)throws Exception;

    public int countBbuByMap(Object map)throws Exception;

    public LatLng selectSzLatLngByName(String name)throws Exception;
}
