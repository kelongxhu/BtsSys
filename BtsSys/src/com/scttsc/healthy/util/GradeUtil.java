package com.scttsc.healthy.util;

import com.scttsc.common.util.Common;
import com.scttsc.common.util.DateConvert;
import com.scttsc.healthy.model.WyRulecfg;

import java.util.Date;

/**
 * User: Administrator
 * Date: 13-8-28
 * Time: 上午11:41
 * 运算符号公共类
 */
public class GradeUtil {

    /**
     * 根据权值计算分数
     *
     * @param ruleCfg
     * @param btsDj
     * @return
     */
    public static double grade(WyRulecfg ruleCfg, String btsDj) {
        double grade = 0;
        try {
            if ("A".equals(btsDj)) {
                grade = ruleCfg.getGrade() * ruleCfg.getAweight().doubleValue();
            } else if ("B".equals(btsDj)) {
                grade = ruleCfg.getGrade() * ruleCfg.getBweight().doubleValue();
            } else if ("C".equals(btsDj)) {
                grade = ruleCfg.getGrade() * ruleCfg.getCweight().doubleValue();
            } else if ("D".equals(btsDj)) {
                grade = ruleCfg.getGrade() * ruleCfg.getDweight().doubleValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grade;
    }

    /**
     * 判断是否满足规则
     *
     * @param dataType
     * @param symbol
     * @param value1
     * @param vale2
     * @param value
     * @return
     */
    public static boolean judge(int dataType, int symbol, String value1, String value2, String value) {
        boolean flag = false;
        switch (dataType) {
            case 1:
                flag = judgeStr(symbol, value1, value2, value);
                break;
            case 2:
                flag = judgeNum(symbol, value1, value2, value);
                break;
            case 3:
                flag = judgeDate(symbol, value1, value2, value);
                break;
        }
        return flag;
    }

    /**
     * 字符串判断
     *
     * @param symbol
     * @param value1
     * @param value2
     * @param value
     * @return
     */

    public static boolean judgeStr(int symbol, String value1, String value2, String value) {
        boolean flag = false;
        switch (symbol) {
            case 5:
                flag = symbol5(value1, value2, value);
                break;
            case 6:
                flag = symbol6(value1, value2, value);
                break;
            case 11:
                flag = symbol11(value1, value2, value);
                break;
            case 12:
                flag = symbol12(value1, value2, value);
                break;
            case 13:
                flag = symbol13(value1, value2, value);
                break;
            case 14:
                flag = symbol14(value1, value2, value);
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * 数字判断
     *
     * @param symbol
     * @param value1
     * @param value2
     * @param value
     * @return
     */

    public static boolean judgeNum(int symbol, String value1, String value2, String value) {
        boolean flag = false;
        switch (symbol) {
            case 1:
                flag = symbol1(value1, value2, value);
                break;
            case 2:
                flag = symbol2(value1, value2, value);
                break;
            case 3:
                flag = symbol13(value1, value2, value);
                break;
            case 4:
                flag = symbol4(value1, value2, value);
                break;
            case 5:
                flag = symbol5(value1, value2, value);
                break;
            case 6:
                flag = symbol6(value1, value2, value);
                break;
            case 7:
                flag = symbol7(value1, value2, value);
                break;
            case 8:
                flag = symbol8(value1, value2, value);
                break;
            case 9:
                flag = symbol9(value1, value2, value);
                break;
            case 10:
                flag = symbol10(value1, value2, value);
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * 时间判断
     *
     * @param symbol
     * @param value1
     * @param value2
     * @param value
     * @return
     */
    public static boolean judgeDate(int symbol, String value1, String value2, String value) {
        Date v = DateConvert.parse(value, "yyyy.MM");
        long monthNum = 0;
        if (v != null) {
            Date c = new Date();
            monthNum = getMonthNum(v,c); //当前时间和开通年月的差值
        } else {
            return false;
        }
        return judgeNum(symbol, value1, value2, monthNum + "");
    }

    /**
     *时间差的月份差
     * @param date1  ：之前年月
     * @param date2  ：现在年月
     * @return
     */
    public static int getMonthNum(Date date1,Date date2){
        int monthNum=0;
        if(date1.getYear()==date2.getYear()){
            monthNum=date2.getMonth()-date1.getMonth();
        }else{
            monthNum=(date2.getYear()-date1.getYear())*12+date2.getMonth()-date1.getMonth();
        }
        return monthNum;
    }


    /**
     * 符号1，大于A
     *
     * @param value1
     * @param vale2
     * @param value
     * @return
     */
    public static boolean symbol1(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v = Common.null2Int(value);
        if (v > v1) {
            return true;
        }
        return false;
    }

    /**
     * 符号2，大于等于A
     *
     * @return
     */

    public static boolean symbol2(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v = Common.null2Int(value);
        if (v >= v1) {
            return true;
        }
        return false;
    }

    /**
     * 符号3，小于A
     *
     * @return
     */

    public static boolean symbol3(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v = Common.null2Int(value);
        if (v < v1) {
            return true;
        }
        return false;
    }

    /**
     * 符号4，小于等于A
     *
     * @return
     */

    public static boolean symbol4(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v = Common.null2Int(value);
        if (v <= v1) {
            return true;
        }
        return false;
    }


    /**
     * 符号5，等于A的判断
     *
     * @param value1
     * @param vale2
     * @param value
     * @return
     */
    public static boolean symbol5(String value1, String vale2, String value) {
        try {
            if (value.equals(value1)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 符号6，不等于A判断
     *
     * @param value1
     * @param value2
     * @param value
     * @return
     */
    public static boolean symbol6(String value1, String value2, String value) {
        try {
            if (!value.equals(value1)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 符号7，大于A小于B
     *
     * @return
     */

    public static boolean symbol7(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v2 = Common.null2Int(value2);
        int v = Common.null2Int(value);
        if (v > v1 && v < v2) {
            return true;
        }
        return false;
    }

    /**
     * 符号8，大于A小于等于B
     *
     * @return
     */

    public static boolean symbol8(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v2 = Common.null2Int(value2);
        int v = Common.null2Int(value);
        if (v > v1 && v <= v2) {
            return true;
        }
        return false;
    }

    /**
     * 符号9，大于等于A小于B
     *
     * @return
     */

    public static boolean symbol9(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v2 = Common.null2Int(value2);
        int v = Common.null2Int(value);
        if (v >= v1 && v < v2) {
            return true;
        }
        return false;
    }

    /**
     * 符号10，大于等于A小于等于B
     *
     * @return
     */

    public static boolean symbol10(String value1, String value2, String value) {
        int v1 = Common.null2Int(value1);
        int v2 = Common.null2Int(value2);
        int v = Common.null2Int(value);
        if (v >= v1 && v <= v2) {
            return true;
        }
        return false;
    }

    /**
     * 符号10，包含A判断
     *
     * @param value1
     * @param value2
     * @param value
     * @return
     */

    public static boolean symbol11(String value1, String value2, String value) {
        if (value.contains(value1)) {
            return true;
        }
        return false;
    }

    /**
     * 符号11，不包含A判断
     *
     * @param value1
     * @param value2
     * @param value
     * @return
     */
    public static boolean symbol12(String value1, String value2, String value) {
        if (!value.contains(value1)) {
            return true;
        }
        return false;
    }

    /**
     * 符号12，A为空，判断
     *
     * @param value1
     * @param value2
     * @param value
     * @return
     */
    public static boolean symbol13(String value1, String value2, String value) {
        if (value == null || "".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 符号13,A不为空判断
     *
     * @param value1
     * @param value2
     * @param value
     * @return
     */

    public static boolean symbol14(String value1, String value2, String value) {
        if (value != null && !"".equals(value1)) {
            return true;
        }
        return false;
    }

}
