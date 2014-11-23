package com.scttsc.healthy.job.info;

import com.scttsc.business.service.BtsManager;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.service.JobManager;
import com.scttsc.healthy.service.RuleManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-8-30
 * Time: 下午2:19
 */
@Service("infoJob")
public class InfoJob {
    static Logger logger = Logger.getLogger("jobLog");
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private RuleManager ruleManager;
    @Autowired
    private JobManager jobManager;

    /**
     * 基础信息评分
     */
    public void gradeByInfo() {
        List<WyRulecfg> ruleCfgs = null;
        try {
            logger.info("++++分析开始");
            Map map = new HashMap();
            map.put("gradetype", 1);
            map.put("status", "1,3");
            ruleCfgs = ruleManager.selectRuleCfgByMap(map);//定义的巡检规则
            Map map2 = new HashMap();
            List<Map> btsDatas = btsManager.selectExportDataByMap(map2);
            int btsNum = (btsDatas == null ? 0 : btsDatas.size());
            int ruleNum=(ruleCfgs==null?0:ruleCfgs.size());
            logger.info("++++检测到基站数量:"+btsNum);
            logger.info("++++检测到规则数量"+ruleNum);
            //所有基站都评分一次
            if (btsDatas != null) {
                for (Map data : btsDatas) {
                    jobManager.gradeByInfoCheck(data, ruleCfgs);
                }
            }
            logger.info("++++完成基础信息评分。");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("++++基础评分异常。");
        }
    }
}
