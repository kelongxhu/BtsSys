package com.scttsc.test;


import com.scttsc.common.util.DateUtils;

import java.util.Date;

/**
 * Created by _think on 2014/10/24.
 */
public class UtilTest {
    public void dateUtil(){
        System.out.println(DateUtils.getLongTime());
        System.out.println(DateUtils.formatDateTime(new Date(),"yyyyMMddHHmm"));
    }

    public static void main(String[] args){
        UtilTest test=new UtilTest();
        test.dateUtil();
    }
}
