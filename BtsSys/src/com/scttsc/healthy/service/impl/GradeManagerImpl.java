package com.scttsc.healthy.service.impl;

import com.scttsc.healthy.dao.WyGradeDao;
import com.scttsc.healthy.dao.WyGradeDetailDao;
import com.scttsc.healthy.model.WyGrade;
import com.scttsc.healthy.model.WyGradeDetail;
import com.scttsc.healthy.service.GradeManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: pengzhi
 * Date: 13-8-28
 * Time: 下午2:54
 * Email: wokzhen@vip.qq.com
 */
@Service("gradeManager")
@Transactional(readOnly = true)
public class GradeManagerImpl implements GradeManager {

    static Logger logger = Logger.getLogger(GradeManagerImpl.class);

    @Autowired
    private WyGradeDao wyGradeDao;
    @Autowired
    private WyGradeDetailDao wyGradeDetailDao;

    public int selectGradeCount(Map cond) throws Exception {
        return wyGradeDao.selectGradeCount(cond);
    }

    public List<WyGrade> selectGradeList(Map cond) throws Exception {
        return wyGradeDao.selectGradeList(cond);
    }


    public WyGrade selectByPrimaryKey(Long intId) throws Exception {
        return wyGradeDao.selectByPrimaryKey(intId);
    }

    /**
     * 更新得分及细项
     *
     * @param record
     * @return
     * @throws Exception
     */

    public int updateByPrimaryKeySelective(WyGrade record) throws Exception {
        wyGradeDao.updateByPrimaryKeySelective(record);
        wyGradeDetailDao.deleteByBtsIntId(record.getIntId());
        List<WyGradeDetail> wyGradeDetailList = record.getWyGradeDetailList();
        if (wyGradeDetailList != null) {
            for (WyGradeDetail wyGradeDetail : wyGradeDetailList) {
                if (wyGradeDetail != null) {
                    wyGradeDetailDao.insert(wyGradeDetail);
                }
            }
        }
        return 1;
    }

    /**
     * 插入得分，及得分细项
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int insert(WyGrade record) throws Exception {
        wyGradeDao.insert(record);
        List<WyGradeDetail> wyGradeDetailList = record.getWyGradeDetailList();
        if (wyGradeDetailList != null) {
            for (WyGradeDetail wyGradeDetail : wyGradeDetailList) {
                if (wyGradeDetail != null) {
                    wyGradeDetailDao.insert(wyGradeDetail);
                }
            }
        }
        return 1;
    }


    public WyGrade getAllInfoById(Long id) throws Exception {
        return wyGradeDao.getAllInfoById(id);
    }

    public List<Map> countBtsGradeNumByCityIds(String cityIds) throws Exception{
        return wyGradeDao.countBtsGradeNumByCityIds(cityIds);
    }

    public List<Map> getSortedBtsAvgGradeWithCity() throws Exception {
        return wyGradeDao.getSortedBtsAvgGradeWithCity();
    }
}

