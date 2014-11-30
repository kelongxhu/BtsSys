package com.scttsc.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Administrator
 * Date: 13-7-10
 * Time: 下午1:39
 */
public class Validity {

    private String name;//校验字段中文名称
    private boolean isEmput;  //是否为空
    private int dataType; //是否整数
    private String[] borderValue;//固定值
    private String msg;//提示信息

    private int index;
    private boolean ignore;


    //定义数据类型
    public static int STR = 1;//字符串
    public static int NUM = 2;//整数
    public static int D = 3;//日期
    public static int D2= 4; //日期2，yyyy-MM
    public static int D3= 5;


    public Validity(String name, boolean emput, int dataType, String[] borderValue) {
        this.name = name;
        isEmput = emput;
        this.dataType = dataType;
        this.borderValue = borderValue;
        ignore=false;
    }

    /**
     * 是否经过校验
     *
     * @param str
     * @return ：true:是 false:否
     */
    public boolean check(String str) {
        boolean flag = true;
        if (isEmput) {
            if (isEmput(str)) {
                //输入为空
                msg = "不能为空!";
                return false;
            }
        }else if(isEmput(str)){
            return true;
        }


        switch (dataType) {
            case 2:
                if (!isInteger(str)) {
                    msg = "所填入值["+str+"]必须输入数字类型!";
                    return false;
                }
                break;
            case 3:
                if (!isDate(str,"yyyy.MM")) {
                    msg = "所填入值["+str+"]请输入正确日期格式,yyyy.MM.";
                    return false;
                }
                break;
            case 4:
                if (!isDate(str,"yyyy-MM")) {
                    msg = "所填入值["+str+"]请输入正确日期格式,yyyy-MM.";
                    return false;
                }
                break;
            case 5:
                if(!isDate(str,"yyyy-MM-dd")){
                    msg = "所填入值["+str+"]请输入正确日期格式,yyyy-MM-dd.";
                    return false;
                }
                break;

            default:
                break;

        }

        if (borderValue != null && borderValue.length > 0) {
            boolean r1 = false;
            for(int i = 0; i < borderValue.length; i++){
                if (str.equals(borderValue[i])) {
                    r1 = true;//在选项中
                    setIndex(i);
                    break;
                }
            }
            if (!r1) {
                msg = "所填入值["+str+"]未在规定选项中:" + getbBorderValue();
                return false;
            }
        }
        return flag;
    }

    /**
     * 是否为空
     *
     * @param str
     * @return false:为空
     */
    public boolean isEmput(String str) {
        boolean flag = false;
        if (str == null || "".equals(str)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 验证是否是整数
     *
     * @param str
     * @return :true 是数字 false:不是数字
     */
    public boolean isInteger(String str) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("^-?([1-9]+[0-9]*|0)(\\.[\\d]+)?");
        //字符串不为空;
        if (str.length() > 0) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches() == true) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 是否日期格式
     *
     * @param str
     * @return
     */

    public boolean isDate(String str,String formater) {
        if (str == null || "".equals(str)) {
            return true;
        }
        Date d;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formater);
            d = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public String getMsg() {
        return name + ":" + msg;
    }

    public String getName() {
        return name;
    }

    private String getbBorderValue() {
        StringBuilder sb = new StringBuilder();
        if (borderValue != null) {
            for (String s : borderValue) {
                sb.append(s + ";");
            }
        }
        return sb.toString();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore() {
        this.ignore = true;
    }
}
