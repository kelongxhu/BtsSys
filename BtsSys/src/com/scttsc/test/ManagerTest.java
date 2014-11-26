package com.scttsc.test;

import com.scttsc.charge.job.SmsJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by _think on 2014/11/24.
 */
public class ManagerTest {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SmsJob smsJob=(SmsJob)context.getBean("smsJob");
//        Date date=smsJob.getNextPayTime(new Date(),1,10);
//        Date date1=smsJob.getRemindTime(date,1);
//        System.out.println(DateUtils.formatDateTime(date, "yyyy-MM-dd"));
//        System.out.println(DateUtils.formatDateTime(date1, "yyyy-MM-dd"));

        smsJob.sendMsg();

//       int aheadDay=DateUtils.daysBetween(date1,date);
//        System.out.println(aheadDay);
    }
}
