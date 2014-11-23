package com.scttsc.business.util;

/**
 * Created by _think on 2014/10/19.
 */
public class BusiUtil {
    /**
     * 分割小区名称
     * @param name
     * @return
     */
    public static String splitCellName(String name){
        String[] arr=name.split("_");
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<arr.length;i++){
            if(i>2){
                sb.append(arr[i]+"_");
            }
        }
        sb=sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
}
