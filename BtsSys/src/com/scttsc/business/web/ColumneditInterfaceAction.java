package com.scttsc.business.web;

import com.scttsc.admin.model.User;
import com.scttsc.business.model.WyColumneditInterface;
import com.scttsc.business.service.*;
import com.scttsc.common.util.Common;
import com.scttsc.common.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-1-15
 * Time: 上午10:34
 */
public class ColumneditInterfaceAction extends BaseAction {
    @Autowired
    private ColumneditInterfaceManager wycolumneditInterfaceManager;

    private WyColumneditInterface wyColumneditInterface;

    Integer type;//数据类型

    Integer status;//提交状态

    Long id;//主键

    private String ids;//id集合

    private String cityIds;//本地网集合

    /**
     * 修改字段提交申请列表頁面
     *
     * @return
     */
    public String columneditInterface() {
        return SUCCESS;
    }

    /**
     * 修改字段提交申請列表
     *
     * @return
     */
    public String columneditInterfaceList() {
        User user = (User) this.getSession().getAttribute("user");
        List<WyColumneditInterface> list = null;
        int total = 0;
        Map map = new HashMap();
        try {
            if (!Common.isEmpty(cityIds)) {
                map.put("cityIds", cityIds);
            } else {
                map.put("cityIds", user.getCityIds());
            }
            if (!Common.isEmpty(type)) {
                map.put("type", type);
            }
            if (!Common.isEmpty(status)) {
                map.put("status", status);
            }
            total = wycolumneditInterfaceManager.countByMap(map);
            if (total < pagesize) {
                page = 1;
            }
            map.put("start", (page - 1) * pagesize + 1);
            map.put("pagesize", pagesize);
            map.put("sortname", sortname);
            map.put("sortorder", sortorder);
            list = wycolumneditInterfaceManager.selectByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }

    /**
     * 系统处理修改请求
     *
     * @return
     */
    public String editApply() {
        try {
            User user = (User) this.getSession().getAttribute("user");
            wycolumneditInterfaceManager.wyColumneditInterfaceEditOpposed(ids,status,user.getIntId());
            jsonMap.put("result", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", "操作失败!");
        }
        return SUCCESS;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
