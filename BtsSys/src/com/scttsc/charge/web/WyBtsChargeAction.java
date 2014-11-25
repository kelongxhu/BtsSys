package com.scttsc.charge.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.scttsc.admin.model.User;
import com.scttsc.charge.model.WyBtsCharge;
import com.scttsc.charge.service.WyBtsChargeManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;

/**
 * Created by _think on 2014/11/10.
 */
public class WyBtsChargeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(WyBtsChargeAction.class);
	
	private static String GROUP_CODE = "CHARGE_REMIND_CYCLE";
	
	//参数
	private BigDecimal intId;
	private String countryIds;
    private Integer btsType;
    private Integer costType;
    
    //高级检索参数
    private String btsName;
    private String bscName;
    private Integer btsId;
    
    private WyBtsCharge wyBtsCharge; 
    
    @Autowired
    private WyBtsChargeManager wyBtsChargeManager;
	
	/**
	 * 费用设置首页
	 * @return
	 * @throws Exception
	 */
	public String chargeSetting() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 查询数据
	 * @return
	 * @throws Exception
	 */
    public String query() throws Exception {
        int total = 0;
        List<WyBtsCharge> list = null;
        Map<String, Object> paramMap = buildParamMap();
        try {
            total = wyBtsChargeManager.selectWyBtsChargeSettingCountByMap(paramMap, btsType);
            if (total < pagesize) {
                page = 1;
            }
            paramMap.put("start", (page - 1) * pagesize + 1);
            paramMap.put("pagesize", pagesize);
            list = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        setJsonMapRows(list);
        setJsonMapTotal(total);
        return SUCCESS;
    }
    
    /**
     * 费用设置
     * @return
     * @throws Exception
     */
    public String setting()throws Exception {
    	try {
			Map<String, Object> paramMap = buildParamMap();
			List<WyBtsCharge> list = wyBtsChargeManager.selectWyBtsChargeSettingByMap(paramMap, btsType);
			getRequest().setAttribute("WyBtsCharge", list.get(0));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }
    
    public String doChargeSetting() throws Exception{
    	try {
    		User user = (User) this.getSession().getAttribute("user");
    		short aheadDay = (short)ConstantUtil.getInstance().getCons(GROUP_CODE, String.valueOf(wyBtsCharge.getCostType())).getCode();
    		
    		wyBtsCharge.setInUser(new BigDecimal(user.getIntId()));
    		wyBtsCharge.setInTime(new Date());
    		wyBtsCharge.setAheadDay(aheadDay);
    		wyBtsChargeManager.doChargeSetting(wyBtsCharge);
    		jsonMap.put("result", 1);
		} catch (Exception e) {
			jsonMap.put("result", 0);
			logger.error(e.getMessage(), e);
		}
    	return SUCCESS;
    }
	
    /**
     * 组装查询条件
     * @return
     */
    private Map<String, Object> buildParamMap() {
        User user = (User) this.getSession().getAttribute("user");
        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(countryIds)) {
            param.put("countryIds", countryIds);
        } else {
            param.put("countryIds", user.getCountryIds());
        }
        if (!StringUtil.isEmpty(btsName)) {
            param.put("btsName", Common.decodeURL(btsName).trim());
        }
        if (!StringUtil.isEmpty(bscName)) {
            param.put("bscName", Common.decodeURL(bscName).trim());
        }
        if (!StringUtil.isEmpty(btsId)) {
            param.put("btsId", btsId);
        }
        if(!StringUtil.isEmpty(btsType)){
        	param.put("btsType", btsType);
        }
        if(!StringUtil.isEmpty(intId)){
        	param.put("intId", intId);
        	param.remove("countryIds");
        }
        if(!StringUtil.isEmpty(costType)){
        	param.put("costType", costType);
        }
        return param;
    }
    
	public String getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(String countryIds) {
		this.countryIds = countryIds;
	}

	public String getBtsName() {
		return btsName;
	}

	public void setBtsName(String btsName) {
		this.btsName = btsName;
	}

	public String getBscName() {
		return bscName;
	}

	public void setBscName(String bscName) {
		this.bscName = bscName;
	}

	public Integer getBtsId() {
		return btsId;
	}

	public void setBtsId(Integer btsId) {
		this.btsId = btsId;
	}
	
	public Integer getBtsType() {
		return btsType;
	}

	public void setBtsType(Integer btsType) {
		this.btsType = btsType;
	}

	public BigDecimal getIntId() {
		return intId;
	}

	public void setIntId(BigDecimal intId) {
		this.intId = intId;
	}
	
	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	public void setWyBtsChargeManager(WyBtsChargeManager wyBtsChargeManager) {
		this.wyBtsChargeManager = wyBtsChargeManager;
	}

	public WyBtsCharge getWyBtsCharge() {
		return wyBtsCharge;
	}

	public void setWyBtsCharge(WyBtsCharge wyBtsCharge) {
		this.wyBtsCharge = wyBtsCharge;
	}
    
}