package com.scttsc.healthy.job.inspect;

import com.scttsc.healthy.model.*;
import com.scttsc.healthy.service.GradeManager;
import com.scttsc.healthy.service.JobManager;
import com.scttsc.healthy.service.RuleManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: Administrator
 * Date: 13-8-27
 * Time: 上午11:32
 * 巡检评分任务
 */
@Service("inspectJob")
public class InspectJob {
    @Autowired
    private RuleManager ruleManager;
    @Autowired
    private GradeManager gradeManager;
    @Autowired
    private JobManager jobManager;


    static Logger logger = Logger.getLogger("jobLog");

    /**
     * 巡检评分任务
     */
    public void gradeByInspect() {
        List<WyRulecfg> ruleCfgs = null;
        List<GzBtsCheck> checkList = null;
        try {
            logger.info("++++分析开始");
            Map map = new HashMap();
            map.put("gradetype", 2);
            map.put("status", "2,3");
            ruleCfgs = ruleManager.selectRuleCfgByMap(map);//定义的巡检规则
            checkList = ruleManager.selectBy(null);//最近的巡检数据
            int checkSize = (checkList == null) ? 0 : checkList.size();
            int ruleSize = (ruleCfgs == null) ? 0 : ruleCfgs.size();
            logger.info("++++此次巡检，巡检数据条数:" + checkSize + ",巡检配置规则数:" + ruleSize);
            if (checkList != null) {
                for (GzBtsCheck check : checkList) {
                        jobManager.gradeByInspectCheck(check,ruleCfgs);
                }
            }
            logger.info("++++分析结束。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
