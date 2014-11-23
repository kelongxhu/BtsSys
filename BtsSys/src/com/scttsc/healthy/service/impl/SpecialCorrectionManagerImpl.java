package com.scttsc.healthy.service.impl;

import com.scttsc.business.model.Bts;
import com.scttsc.healthy.dao.WySpeciallyBtsDao;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;
import com.scttsc.healthy.service.SpecialCorrectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: Liuy
 * Date: 13-9-11
 * Time: 下午2:46
 * Description:
 */
@Service("specialCorrectionService")
@Transactional(readOnly = true)
public class SpecialCorrectionManagerImpl implements SpecialCorrectionManager{

    @Autowired
    private WySpeciallyBtsDao wySpeciallyBtsDao;

    public int countSpecial(Map map) throws Exception {
        return wySpeciallyBtsDao.specialCountByCorrection(map);
    }

    public List<WySpeciallycfg> listSpecial(Map map) throws Exception {
        return wySpeciallyBtsDao.specialListByCorrection(map);
    }

    public int countSpecialBts(Map map) throws Exception {
        return wySpeciallyBtsDao.specialBtsCountByCorrection(map);
    }

    public List<WySpeciallyBts> listSpecialBts(Map map) throws Exception {
        return wySpeciallyBtsDao.specialBtsListByCorrection(map);
    }
}
