package com.scttsc.common.util;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.CityTreeNode;
import com.scttsc.baselibs.model.Cons;
import com.scttsc.common.model.TreeNodeHelper;
import com.scttsc.healthy.model.WySubcfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantUtil {
    private static Map<String, Object> constants = new HashMap<String, Object>();
    // 地市数据Map的KEY
    public static String CITYKEY = "cityList";
    // 区县数据Map的KEY
    public static String COUNTRYKEY = "countryList";
    // 初始化常量Map
    public static Map<String, Map<String, Cons>> consMap = new HashMap<String, Map<String, Cons>>();

    //初始化常量Map2
    public static Map<String, List<Cons>> consGroupMap = new HashMap<String, List<Cons>>();

    //初始化常量Map3,通过code找对应Cons
    public static Map<String,Map<String,Cons>> consCodeMap=new HashMap<String,Map<String,Cons>>();



    //健康管理基础项（不是经常用，没必要存在内存呀。。）
    public static Map<String, List<WySubcfg>> subCfgMap = new HashMap<String, List<WySubcfg>>();
    //存储本地网，方便通过名称查找本地网
    public static Map<String, City> cityMap = new HashMap<String, City>();
    //存储区县，方便通过区县名称查找区县
    public static Map<String, City> countryMap = new HashMap<String, City>();

    private ConstantUtil() {
    }

    private static ConstantUtil cu;

    public static ConstantUtil getInstance() {
        if (null == cu) {
            cu = new ConstantUtil();
        }
        return cu;
    }

    /**
     * 地市级别
     *
     * @param rootId
     * @return
     */
    public static CityTreeNode buildCityTree(String rootId) {
        List<City> cityList = (List<City>) constants.get(ConstantUtil.CITYKEY);
        return buildTree(cityList, rootId);
    }

    /**
     * 区县级别
     *
     * @param rootId
     * @return
     */
    public static CityTreeNode buildCountryTree(String rootId) {
        List<City> countryList = (List<City>) constants
                .get(ConstantUtil.COUNTRYKEY);
        return buildTree(countryList, rootId);
    }

    /**
     * 创建地市树
     *
     * @param rootId ：父节点
     * @return：树型结构
     */
    public static CityTreeNode buildTree(List<City> cityList, String rootId) {
        List<CityTreeNode> treeList = new ArrayList<CityTreeNode>();
        CityTreeNode root = null;
        for (City city : cityList) {
            CityTreeNode node = new CityTreeNode(city);
            treeList.add(node);
        }
        TreeNodeHelper helper = new TreeNodeHelper(treeList, rootId);
        root = (CityTreeNode) helper.getRoot();
        return root;
    }

    /**
     * 地市数据存储Map结构
     *
     * @param cityList
     */
    public void putCitys(List<City> cityList) {
        constants.put(ConstantUtil.CITYKEY, cityList);
    }

    /**
     * 区县数据存储Map结构
     *
     * @param countryList
     */
    public void putCountrys(List<City> countryList) {
        constants.put(ConstantUtil.COUNTRYKEY, countryList);
        if (countryList != null) {
            for (City city : countryList) {
                if (city.getParentId() == 10001) {
                    cityMap.put(city.getCityName(), city);
                } else if (city.getParentId() != -1) {
                    countryMap.put(city.getCityName(), city);
                }
            }
        }
    }

    /**
     * 存储常量Map
     *
     * @param cons
     */
    public void putCons(List<Cons> cons) {
        for (Cons con : cons) {
            putGroupConsList(con);
            putGroupConsMap(con);
            putGroupCodeConsMap(con);
        }
    }

    /**
     * 增加常量到常量存储map
     *
     * @param con
     */
    public void putGroupConsList(Cons con) {
        List<Cons> cons = consGroupMap.get(con.getGroupCode());
        if (cons == null) {
            cons = new ArrayList<Cons>();
            cons.add(con);
            consGroupMap.put(con.getGroupCode(), cons);
        } else {
            cons.add(con);
        }
    }

    public void putGroupConsMap(Cons con) {
        Map<String, Cons> objMap = consMap.get(con.getGroupCode());
        if (objMap == null) {
            objMap = new HashMap<String, Cons>();
            objMap.put(con.getName(), con);
            consMap.put(con.getGroupCode(), objMap);
        }else{
            objMap.put(con.getName(), con);
        }
    }

    /**
     * 通过Cons的code获取CONS的存储结构
     * @param con
     */
    public void putGroupCodeConsMap(Cons con) {
           Map<String, Cons> objMap = consCodeMap.get(con.getGroupCode());
           if (objMap == null) {
               objMap = new HashMap<String, Cons>();
               objMap.put(con.getCode()+"", con);
               consCodeMap.put(con.getGroupCode(), objMap);
           } else{
               objMap.put(con.getCode()+"", con);
           }
       }

    /**
     * 通过常量名称取的常量对象
     *
     * @param name
     * @return
     */
    public Cons getCons(String groupCode, String name) {
        Cons cons = null;
        Map<String, Cons> map = consMap.get(groupCode);
        if (map != null) {
            cons = map.get(name);
        }
        return cons;
    }


    /**
        * 通过常量CODE获取常量对象
        *
        * @param name
        * @return
        */
       public Cons getConsByCode(String groupCode, String code) {
           Cons cons = null;
           Map<String, Cons> map = consCodeMap.get(groupCode);
           if (map != null) {
               cons = map.get(code);
           }
           return cons;
       }

    public List<Cons> getConListByGroupCode(String groupCode) {
        return consGroupMap.get(groupCode);
    }

    public static void cleanAll() {
        constants.clear();
    }

    /**
     * 存储定义选项
     *
     * @param subCfgs
     */

    public void putSubCfgs(List<WySubcfg> subCfgs) {
        if (subCfgs != null) {
            for (WySubcfg subCfg : subCfgs) {
                List<WySubcfg> wySubcfgs = subCfgMap.get(subCfg.getSubType() + "");
                if (wySubcfgs == null) {
                    wySubcfgs = new ArrayList<WySubcfg>();
                    wySubcfgs.add(subCfg);
                    subCfgMap.put(subCfg.getSubType() + "", wySubcfgs);
                } else {
                    wySubcfgs.add(subCfg);
                }
            }
        }
    }

    public List<WySubcfg> getSubCfgListByType(String subType) {
        return subCfgMap.get(subType);
    }

    public City getCity(String name) {
        return cityMap.get(name);
    }

    public City getCountry(String name) {
        return countryMap.get(name);
    }


}
