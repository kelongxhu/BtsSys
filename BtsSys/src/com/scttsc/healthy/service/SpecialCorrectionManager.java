package com.scttsc.healthy.service;

import com.scttsc.business.model.Bts;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;

import java.util.List;
import java.util.Map;

/**
 * User: Liuy
 * Date: 13-9-11
 * Time: 下午2:45
 * Description:
 */
public interface SpecialCorrectionManager {

    int countSpecial(Map map) throws Exception;

    List<WySpeciallycfg> listSpecial(Map map) throws Exception;

    int countSpecialBts(Map map) throws Exception;

    List<WySpeciallyBts> listSpecialBts(Map map) throws Exception;
}
