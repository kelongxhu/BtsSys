package com.scttsc.healthy.service.impl;

import com.scttsc.common.util.Common;
import com.scttsc.healthy.dao.WySpeciallyBtsDao;
import com.scttsc.healthy.dao.WySpeciallyGradeDao;
import com.scttsc.healthy.dao.WySpeciallycfgDao;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallyGrade;
import com.scttsc.healthy.model.WySpeciallycfg;
import com.scttsc.healthy.service.SpeciallyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-9-4
 * Time: 下午3:18
 */
@Service("speciallyService")
@Transactional(readOnly = true)
public class SpeciallyManagerImpl implements SpeciallyManager {

    @Autowired
    private WySpeciallycfgDao wySpeciallycfgDao;
    @Autowired
    private WySpeciallyGradeDao wySpeciallyGradeDao;
    @Autowired
    private WySpeciallyBtsDao wySpeciallyBtsDao;

    public List<WySpeciallycfg> selectSpeciallyList(Map record) throws Exception {
        return wySpeciallycfgDao.selectSpeciallyList(record);
    }

    public int selectSpeciallyCount(Map record) throws Exception {
        return wySpeciallycfgDao.selectSpeciallyCount(record);
    }

    public WySpeciallycfg selectByPrimaryKey(Long id) throws Exception {
        return wySpeciallycfgDao.selectByPrimaryKey(id);
    }

    /**
     * 插入专项配置
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int insert(WySpeciallycfg record) throws Exception {
        wySpeciallycfgDao.insert(record);
        insertSpeciallyGrades(record);
        return 1;
    }

    /**
     * 更改专项配置
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int updateByPrimaryKeySelective(WySpeciallycfg record) throws Exception {
        wySpeciallycfgDao.updateByPrimaryKeySelective(record);
        wySpeciallyGradeDao.delete(record.getId());
        insertSpeciallyGrades(record);
        return 1;
    }

    private void insertSpeciallyGrades(WySpeciallycfg record) {
        String ruleCfgIds = record.getRuleCfgIds();
        if (!Common.isEmpty(ruleCfgIds)) {
            String[] ruleArr = ruleCfgIds.split(",");
            for (String ruleId : ruleArr) {
                WySpeciallyGrade speciallyGrade = new WySpeciallyGrade();
                speciallyGrade.setSpecid(record.getId());
                speciallyGrade.setGradecfgid(new Long(ruleId));
                wySpeciallyGradeDao.insert(speciallyGrade);
            }
        }
    }

    /**
     * 删除专项及关联规则
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteByPrimaryKey(Long id) throws Exception {
        wySpeciallycfgDao.deleteByPrimaryKey(id);
        wySpeciallyGradeDao.delete(id);
        return 1;
    }

    /**
     * 查询所有专项
     * @return
     * @throws Exception
     */
    public List<WySpeciallycfg> selectAll() throws Exception {
        return wySpeciallycfgDao.selectAll();
    }

    /**
     * 插入最新整改专项数据
     * @param spBtss
     * @return
     * @throws Exception
     */
    public int insertSpBts(List<WySpeciallyBts> spBtss) throws Exception {
        wySpeciallyBtsDao.deleteAll();
        if(spBtss!=null){
            for(WySpeciallyBts spBts:spBtss){
               wySpeciallyBtsDao.insert(spBts);
            }
        }
        return 1;
    }
}
