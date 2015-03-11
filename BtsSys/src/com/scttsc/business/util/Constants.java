package com.scttsc.business.util;

public class Constants {

    //信息库
    public static Integer SchoolLib = 1;
    public static Integer VitoLib = 2;
    public static Integer RoadLib = 3;
    public static Integer AirLib = 4;
    public static Integer SecneryLib = 5;
    public static Integer TunnelLib = 6;
    public static int SceneLib = 7;//场景库


    //记录日志
    public static String LOG_FUN_1 = "update";
    public static String LOG_FUN_2 = "insert";
    public static String LOG_OBJ_1 = "物理站点";
    public static String LOG_OBJ_2 = "纯BBU";
    public static String LOG_OBJ_3 = "室分站点";
    public static String LOG_OBJ_4 = "小区";


    public static Integer SUCECSS = 1;
    public static Integer FAIL = 0;

    //站点的配置数据

    public static int BTS = 1;//BTS
    public static int BBU = 2;//纯BBU
    public static int INDOOR = 3;//室分
    public static int CELL = 4;//小区
    public static int TRANSFER = 5;//传输
    public static int TUNEL = 6;//隧道

    public static String CHARGE_FILE = "/charge_file/";

    public static String[] BTS_TYPE = {"室外覆盖站点", "纯BBU站点", "室内覆盖站点", "隧道覆盖站点"};


    public static String CELL_NOT_INDOOR="否";
    public static String CELL_INDOOR="是";
    public static String CELL_TUNEL="隧";


    public static String SPLIT_SYMBOL=",";


}
