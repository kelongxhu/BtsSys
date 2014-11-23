package com.scttsc.healthy.web;

import com.scttsc.business.model.Bts;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import com.scttsc.healthy.model.WySpeciallyBts;
import com.scttsc.healthy.model.WySpeciallycfg;
import com.scttsc.healthy.service.SpecialCorrectionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Liuy
 * Date: 13-9-11
 * Time: 上午10:14
 * Description:
 */
public class SpecialCorrectionAction extends BaseAction {

    private String specialName;

    private String btsName;

    private int specialId;

    @Autowired
    private SpecialCorrectionManager specialCorrectionManager;

    /**
     * 专项配置整改列表页面
     *
     * @return
     */
    public String specialCorrectionPage() {
        return SUCCESS;
    }

    /**
     * 专项配置列表
     *
     * @return
     */
    public String specialListByCorrection() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WySpeciallycfg> list = null;
        try {
            if (!Common.isEmpty(specialName)) {
                specialName = Common.decodeURL(specialName).trim();
                map.put("specialName", "%" + specialName + "%");
            }
            if (!Common.isEmpty(btsName)) {
                btsName = Common.decodeURL(btsName).trim();
                map.put("btsName", "%" + btsName + "%");
            }
            total = specialCorrectionManager.countSpecial(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = specialCorrectionManager.listSpecial(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }


    /**
     * 专项基站数据列表
     *
     * @return
     */
    public String specialBtsListByCorrection() {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = 0;
        List<WySpeciallyBts> list = null;
        try {
            if (!Common.isEmpty(specialId)) {
                map.put("specialId",specialId);
            }
            total = specialCorrectionManager.countSpecialBts(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = specialCorrectionManager.listSpecialBts(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }
}
