package com.scttsc.healthy.web;

import com.scttsc.admin.model.User;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import com.scttsc.healthy.model.WyRulecfg;
import com.scttsc.healthy.model.WySpeciallycfg;
import com.scttsc.healthy.service.SpeciallyManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Liuy
 * Date: 13-8-26
 * Time: 上午10:14
 * Description:
 */
public class SpecialAction extends BaseAction {

    @Autowired
    private SpeciallyManager specialManager;

    private String name;

    private Long id;

    private WySpeciallycfg wySpeciallycfg;

    private Integer editFlag;

    private String ids;//ID集合


    /**
     * 专项配置列表页面
     *
     * @return
     */
    public String specialCfg() {
        return SUCCESS;
    }

    /**
     * 专项配置列表
     *
     * @return
     */
    public String specialCfgList() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WySpeciallycfg> list = null;
        try {
            if (!Common.isEmpty(name)) {
                name = Common.decodeURL(name).trim();
                map.put("name", "%" + name + "%");
            }
            total = specialManager.selectSpeciallyCount(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = specialManager.selectSpeciallyList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 专项增加编辑页面
     *
     * @return
     */
    public String addPage() {
        try {
            if (!Common.isEmpty(id)) {
                wySpeciallycfg = specialManager.selectByPrimaryKey(id);
                List<WyRulecfg> wyRulecfgList = wySpeciallycfg.getWyRulecfgList();
                StringBuilder sb = new StringBuilder();
                if (wyRulecfgList != null) {
                    for (WyRulecfg ruleCfg : wyRulecfgList) {
                        sb.append(ruleCfg.getId() + ",");
                    }
                    if (sb.length() > 1) {
                        sb.delete(sb.length() - 1, sb.length());
                    }
                }
                wySpeciallycfg.setRuleCfgIds(sb.toString());

            }
            if (wySpeciallycfg != null) {
                editFlag = 1;//编辑
            } else {
                editFlag = 0;//增加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加编辑专项
     *
     * @return
     */
    public String addSpecial() {
        User user=(User)this.getSession().getAttribute("user");
        try {
            wySpeciallycfg.setUserid(user.getIntId());
            wySpeciallycfg.setUpdatetime(new Date());
            if (editFlag == 0) {
                specialManager.insert(wySpeciallycfg);
            } else if (editFlag == 1) {
                specialManager.updateByPrimaryKeySelective(wySpeciallycfg);
            }
            jsonMap.put("result", 1);//增加成功
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 删除专项
     *
     * @return
     */
    public String delSpecial() {
        try {
            if (!Common.isEmpty(ids)) {
                String[] idArr=ids.split(",");
                for(String s:idArr){
                    specialManager.deleteByPrimaryKey(new Long(s));
                }
                jsonMap.put("result", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", 0);
        }
        return SUCCESS;
    }

    /**
     * 专项信息
     * @return
     */
    public String specialInfo(){
        try {
            wySpeciallycfg = specialManager.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WySpeciallycfg getWySpeciallycfg() {
        return wySpeciallycfg;
    }

    public void setWySpeciallycfg(WySpeciallycfg wySpeciallycfg) {
        this.wySpeciallycfg = wySpeciallycfg;
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
}
