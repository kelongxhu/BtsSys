package com.scttsc.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.scttsc.baselibs.model.WyLibVillage;
import com.scttsc.baselibs.service.VillageLibManager;
import com.scttsc.healthy.model.WySubcfg;
import com.scttsc.healthy.service.RuleManager;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.scttsc.admin.model.City;
import com.scttsc.admin.service.CityManager;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.baselibs.service.SchoolLibManager;

public class ApplicationServerListener implements ServletContextListener {

    Logger LOG = Logger.getLogger(ApplicationServerListener.class);

    private ServletContext sc;
    private WebApplicationContext springContext;

    public void contextDestroyed(ServletContextEvent arg0) {
        ConstantUtil.cleanAll();
    }

    @SuppressWarnings("rawtypes")
    public void contextInitialized(ServletContextEvent arg0) {
        sc = arg0.getServletContext();
        springContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        LOG.info("初始化内存数据++++++++++++++++++");
        // 初始化CityManager
        CityManager cityManager = (CityManager) springContext.getBean("cityManager");
        SchoolLibManager schoolLibManager = (SchoolLibManager) springContext.getBean("schoolLibManager");
        RuleManager ruleManager = (RuleManager) springContext.getBean("ruleManager");
        VillageLibManager villageLibManager=(VillageLibManager)springContext.getBean("villageLibManager");
        try {
            List<City> cityList = cityManager.getCitys();
            ConstantUtil.getInstance().putCitys(cityList);
            LOG.info("初始化本地网数据," + cityList.size());
            List<City> countryList = cityManager.getCountry();
            ConstantUtil.getInstance().putCountrys(countryList);
            LOG.info("初始化区域数据," + countryList.size());
            Map map = new HashMap();
            List<Cons> cons = schoolLibManager.getCons(map);
            ConstantUtil.getInstance().putCons(cons);
            LOG.info("初始化配置变量," + cons.size());
            List<WySubcfg> subCfgs = ruleManager.selectSubCfgByMap(null);
            ConstantUtil.getInstance().putSubCfgs(subCfgs);
            LOG.info("初始化健康档案配置数据," + cons.size());
            List<WyLibVillage> villageList=villageLibManager.selectByConds(new HashMap<String,Object>());
            ConstantUtil.getInstance().putVillageLib(villageList);
            LOG.info("初始化乡镇农村库,"+villageList.size());
            LOG.info("初始化数据完毕+++++++++++++.");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
