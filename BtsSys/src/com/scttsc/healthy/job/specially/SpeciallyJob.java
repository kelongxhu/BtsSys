package com.scttsc.healthy.job.specially;

import com.scttsc.business.service.BtsManager;
import com.scttsc.common.util.StringUtil;
import com.scttsc.healthy.model.GzBtsCheck;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;
import com.scttsc.healthy.service.JobManager;
import com.scttsc.healthy.service.RuleManager;
import com.scttsc.healthy.service.SpeciallyManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * User: Administrator
 * Date: 13-9-11
 * Time: 下午1:52
 */
public class SpeciallyJob {
    static Logger logger = Logger.getLogger("jobLog");
    @Autowired
    private BtsManager btsManager;
    @Autowired
    private JobManager jobManager;
    @Autowired
    private SpeciallyManager speciallyManager;
    @Autowired
    private RuleManager ruleManager;

    /**
     * 符合专项基站任务
     */
    public void speciallyExceue() {
        List<WySpeciallycfg> speciallycfgList = null;
        List<Map> btsDatas = null;
        List<WySpeciallyBts> spBtsList=new ArrayList<WySpeciallyBts>();
        try {
            logger.info("++++符合专项检测开始。。。");
            speciallycfgList = speciallyManager.selectAll();
            btsDatas = btsManager.selectExportDataByMap(null);
            int spSize = (speciallycfgList == null ? 0 : speciallycfgList.size());
            int btsSize = (btsDatas == null ? 0 : btsDatas.size());
            logger.info("++++检测到专项数量：" + spSize + ",检测到基站数量：" + btsSize);
            for (Map map : btsDatas) {
                for (WySpeciallycfg speciallycfg : speciallycfgList) {
                    List<WyRulecfg> wyRulecfgList = speciallycfg.getWyRulecfgList();
                    if(wyRulecfgList!=null){
                               boolean flag=false;
                               for(WyRulecfg ruleCfg:wyRulecfgList){
                                   flag=ruleCheck(map,ruleCfg);
                                   if(!flag){
                                       //不符合规则
                                       break;
                                   }
                               }
                               if(flag){
                                   //都符合
                                   Long intId = StringUtil.null2Long0(map.get("INT_ID"));
//                                   logger.info("++++检测到符合专项："+speciallycfg.getName()+"，基站:"+intId);
                                   WySpeciallyBts spBts=new WySpeciallyBts();
                                   spBts.setBtsIntId(intId);
                                   spBts.setSpecid(speciallycfg.getId());
                                   spBts.setUpdateTime(new Date());
                                   spBtsList.add(spBts);
                               }
                           }
                }
            }

            logger.info("++++统计到符合专项基站数量:"+spBtsList.size());
            speciallyManager.insertSpBts(spBtsList);
            logger.info("++++专项统计结束...");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("++++统计符合专项基站失败"+e.getMessage());
        }
    }


    /**
     *
     * @param map
     * @param wyRulecfg
     * @return
     */

    public boolean ruleCheck(Map map, WyRulecfg wyRulecfg) {
        boolean flag = false;
        try {
            Long intId = StringUtil.null2Long0(map.get("INT_ID"));
            int type = wyRulecfg.getGradetype();  //规则类型
            switch (type) {
                case 1:
                    //基础信息规则
                    flag = jobManager.infoCheck(map, wyRulecfg);
                    break;
                case 2:
                    //巡检规则
                    Map map1 = new HashMap();
                    map1.put("intId", intId);
                    List<GzBtsCheck> btsChecks = ruleManager.selectBy(map1);
                    if (btsChecks != null && btsChecks.size() > 0) {
                        GzBtsCheck check = btsChecks.get(0);
                        flag = jobManager.inspectCheck(check, wyRulecfg);
                    }
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
