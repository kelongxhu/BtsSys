package com.scttsc.healthy.web;

import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.web.BaseAction;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.model.WyRuleterm;
import com.scttsc.healthy.model.WySubcfg;
import com.scttsc.healthy.service.RuleManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-8-20
 * Time: 上午11:29
 */
public class RuleAction extends BaseAction {
    @Autowired
    private RuleManager ruleManger;

    private Integer subType;//评分项类型

    WyRulecfg wyRulecfg;

    Long id;//规则ID

    String ids;//ID集合

    Integer editFlag;//编辑标识

    Long ruleCfgId;



    /**
     * 规则配置列表页面
     *
     * @return
     */
    public String ruleCfg() {
        return SUCCESS;
    }

    /**
     * 规则配置列表查询
     *
     * @return
     */
    public String ruleCfgList() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyRulecfg> list = null;
        try {
            if (!Common.isEmpty(subType)) {
                map.put("gradetype", subType);
            }
            total = ruleManger.selectRuleCfgCount(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = ruleManger.selectRuleCfgList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        jsonMap.put("page", page);
        return SUCCESS;
    }


    /**
     * 专项配置定义规则
     *
     * @return
     */
    public String specialRules() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WyRulecfg> list = null;
        try {
            map.put("status", "2,3");
            total = ruleManger.selectRuleCfgCount(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = ruleManger.selectRuleCfgList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 增加配置页面
     *
     * @return
     */
    public String addCfgPg() {
        try {
            if (!Common.isEmpty(id)) {
                wyRulecfg = ruleManger.selectRuleCfgByPrimaryKey(id);
            }
            if (wyRulecfg != null) {
                editFlag = 1;
            } else {
                editFlag = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 规则信息
     *
     * @return
     */
    public String ruleCfgInfo() {
        try {
            if (!Common.isEmpty(id)) {
                wyRulecfg = ruleManger.selectRuleCfgByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 获取评分项
     *
     * @return
     */
    public String subCfg() {
        List<WySubcfg> subCfgs = null;
        Map map = new HashMap();
        try {
            if (!Common.isEmpty(subType)) {
//                map.put("subType", subType);
//                subCfgs = ruleManger.selectSubCfgByMap(map);
                subCfgs = ConstantUtil.getInstance().getSubCfgListByType(subType.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(subCfgs);
        return SUCCESS;
    }

    /**
     * 增加规则配置
     *
     * @return
     */
    public String addRuleCfg() {
        try {
            if (editFlag == 1) {
                ruleManger.updateRuleCfg(wyRulecfg); //编辑
            } else {
                ruleManger.insertRuleCfg(wyRulecfg); //增加
            }
            jsonMap.put("result", 1);//增加成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 删除规则
     *
     * @return
     */
    public String del() {
        try {
            if (!Common.isEmpty(ids)) {
                String[] str = ids.split(",");
                for (String cfgId : str) {
                    ruleManger.deleteRuleCfg(new Long(cfgId));
                }
            }
            jsonMap.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 同类规则的列表
     *
     * @return
     */
    public String ruleCfgListByType() {
        List<WyRulecfg> ruleCfgList = null;
        Map record = new HashMap();
        try {
            if (!Common.isEmpty(subType)) {
                record.put("gradetype", subType);
                ruleCfgList = ruleManger.selectRuleCfgByMap(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(ruleCfgList);
        return SUCCESS;
    }

    /**
     * 通过规则查找规则细则
     * @return
     */
    public String ruleItems() {
        List<WyRuleterm> wyRuleterms = null;
        try {
            if (!Common.isEmpty(ruleCfgId)) {
                wyRuleterms = ruleManger.selectByRuleId(ruleCfgId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(wyRuleterms);
        return SUCCESS;
    }


    /**
     * 获取类型的总分
     *
     * @return
     */
    public String getSum() {
        double sumGrade = 0;
        try {
            if (!Common.isEmpty(subType)) {
                sumGrade = ruleManger.selectSumGradeByType(new Long(subType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonMap.put("sumGrade", sumGrade);
        return SUCCESS;
    }


    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public WyRulecfg getWyRulecfg() {
        return wyRulecfg;
    }

    public void setWyRulecfg(WyRulecfg wyRulecfg) {
        this.wyRulecfg = wyRulecfg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getRuleCfgId() {
        return ruleCfgId;
    }

    public void setRuleCfgId(Long ruleCfgId) {
        this.ruleCfgId = ruleCfgId;
    }
}
