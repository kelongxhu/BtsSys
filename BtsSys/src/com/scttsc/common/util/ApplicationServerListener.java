package com.scttsc.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.scttsc.healthy.model.WySubcfg;
import com.scttsc.healthy.service.RuleManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.scttsc.admin.model.City;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.SchoolLibManager;

public class ApplicationServerListener implements ServletContextListener {

	private ServletContext sc;
	private WebApplicationContext springContext;

	public void contextDestroyed(ServletContextEvent arg0) {
		ConstantUtil.cleanAll();
	}

	@SuppressWarnings("rawtypes")
	public void contextInitialized(ServletContextEvent arg0) {
		sc = arg0.getServletContext();
		springContext = WebApplicationContextUtils.getWebApplicationContext(sc);
		// 初始化CityManager
		CityManager cityManager = (CityManager) springContext
				.getBean("cityManager");
		SchoolLibManager schoolLibManager=(SchoolLibManager)springContext.getBean("schoolLibManager");
        RuleManager ruleManager=(RuleManager)springContext.getBean("ruleManager");

		try {
			List<City> cityList = cityManager.getCitys();
			ConstantUtil.getInstance().putCitys(cityList);
			List<City> countryList=cityManager.getCountry();
			ConstantUtil.getInstance().putCountrys(countryList);
			Map map=new HashMap();
			List<Cons> cons=schoolLibManager.getCons(map);
			ConstantUtil.getInstance().putCons(cons);
            List<WySubcfg> subCfgs=ruleManager.selectSubCfgByMap(null);
            ConstantUtil.getInstance().putSubCfgs(subCfgs);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
