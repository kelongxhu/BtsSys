package com.scttsc.healthy.service.impl;

import com.scttsc.healthy.dao.*;
import com.scttsc.healthy.model.*;
import com.scttsc.healthy.service.RuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-8-20
 * Time: 下午1:14
 */
@Service("ruleManager")
@Transactional(readOnly = true)
public class RuleManagerImpl implements RuleManager {
    @Autowired
    private WyRulecfgDao wyRulecfgDao;
    @Autowired
    private WySubcfgDao wySubcfgDao;
    @Autowired
    private WyRuletermDao wyRuletermDao;
    @Autowired
    private GzBtsCheckDao gzBtsCheckDao;
    @Autowired
    private WyBtsAlarmDao wyBtsAlarmDao;
    @Autowired
    private WyDbconDao wyDbconDao;

    public List<WyRulecfg> selectRuleCfgList(Map record) throws Exception {
        return wyRulecfgDao.selectRuleCfgList(record);
    }

    public int selectRuleCfgCount(Map record) throws Exception {
        return wyRulecfgDao.selectRuleCfgCount(record);
    }

    public List<WySubcfg> selectSubCfgByMap(Map record) throws Exception {
        return wySubcfgDao.selectSubCfgByMap(record);
    }

    /**
     * 插入规则配置
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int insertRuleCfg(WyRulecfg record) throws Exception {
        wyRulecfgDao.insert(record);
        List<WyRuleterm> wyRuletermList = record.getWyRuletermList();
        if (wyRuletermList != null) {
            for (WyRuleterm ruleterm : wyRuletermList) {
                ruleterm.setRulecfgid(record.getId());
                wyRuletermDao.insert(ruleterm);
            }
        }
        return 1;
    }

    /**
     * 更新规则配置
     *
     * @param record
     * @return
     * @throws Exception
     */

    public int updateRuleCfg(WyRulecfg record) throws Exception {
        wyRulecfgDao.updateByPrimaryKeySelective(record);
        wyRuletermDao.deleteByRuleCfg(record.getId());
        List<WyRuleterm> wyRuletermList = record.getWyRuletermList();
        if (wyRuletermList != null) {
            for (WyRuleterm ruleterm : wyRuletermList) {
                if (ruleterm != null) {
                    ruleterm.setRulecfgid(record.getId());
                    wyRuletermDao.insert(ruleterm);
                }
            }
        }
        return 1;
    }

    /**
     * 通过ID查询规则，以及规则细项
     *
     * @param id
     * @return
     * @throws Exception
     */

    public WyRulecfg selectRuleCfgByPrimaryKey(Long id) throws Exception {
        return wyRulecfgDao.selectByPrimaryKey(id);
    }

    /**
     * 删除规则
     *
     * @param id
     * @return
     * @throws Exception
     */

    public int deleteRuleCfg(Long id) throws Exception {
        wyRulecfgDao.deleteByPrimaryKey(id);
        wyRuletermDao.deleteByRuleCfg(id);
        return 1;
    }

    /**
     * 根据条件查询规则
     *
     * @param record
     * @return
     * @throws Exception
     */
    public List<WyRulecfg> selectRuleCfgByMap(Map record) throws Exception {
        return wyRulecfgDao.selectRuleCfgByMap(record);
    }

    /**
     * 查询出最近时间的巡检数据
     *
     * @return
     * @throws Exception
     */
    public List<GzBtsCheck> selectBy(Map map) throws Exception {
        return gzBtsCheckDao.selectBy(map);
    }

    /**
     * 插入基站告警数结果表
     *
     * @param wyBtsAlarm
     * @return
     * @throws Exception
     */
    public int insertBtsAlarm(List<WyBtsAlarm> wyBtsAlarmList) throws Exception {
        wyBtsAlarmDao.delete();
        if (wyBtsAlarmList != null) {
            for (WyBtsAlarm btsAlarm : wyBtsAlarmList) {
                wyBtsAlarmDao.insert(btsAlarm);
            }
        }
        return 1;
    }

    /**
     * 获取该类型的总分
     * @param gradeType
     * @return
     * @throws Exception
     */
    public double selectSumGradeByType(Long gradeType) throws Exception {
        return wyRulecfgDao.selectSumGradeByType(gradeType);
    }

    /**
     * 通过ruleCfgId查找对应细则
     * @param ruleCfgId
     * @return
     * @throws Exception
     */

    public List<WyRuleterm> selectByRuleId(Long ruleCfgId) throws Exception {
        return wyRuletermDao.selectByRuleId(ruleCfgId);
    }

    /**
     * 告警数据库连接
     * @return
     * @throws Exception
     */
    public List<WyDbcon> selectAll() throws Exception {
        return wyDbconDao.selectAll();
    }
}
