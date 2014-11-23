package com.scttsc.test;

import com.scttsc.business.util.CustomizeException;
import com.scttsc.healthy.job.info.InfoJob;
import com.scttsc.healthy.job.inspect.InspectJob;
import com.scttsc.healthy.util.DbConn;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.util.Calendar;

/**
 * User: Administrator
 * Date: 13-7-31
 * Time: 下午3:51
 */
public class Test2 {
    public static void main(String[] args){
        try {
//            BtsManual obj1=new BtsManual();
//            obj1.setIntId(1l);
////            Field fields1[] = obj1.getClass().getDeclaredFields();
////            System.out.println(fields1.length);
//
//            BtsManual obj2=new BtsManual();
//                       obj2.setIntId(2l);
////                       Field fields2[] = obj2.getClass().getDeclaredFields();
////                       System.out.println(fields2.length);
////            for(int i=0;i<fields1.length;i++){
////                Field f1=fields1[i];
////                Field f2=fields2[i];
////                f1.setAccessible(true);
////                f2.setAccessible(true);
////                System.out.println(f1.getName()+":"+f1.get(obj1));
////                System.out.println(f2.getName()+":"+f2.get(obj2));
////            }
//
//
//            System.out.println(obj1.compareEntity(obj2));
//
//            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//            InspectJob job=(InspectJob)context.getBean("inspectJob");
            //InspectJob job=new InspectJob();
//            job.gradeByInspect();
//             InfoJob job=(InfoJob)context.getBean("infoJob");
             //job.test();

//
//            Calendar cal = Calendar.getInstance();
//            int month = cal.get(Calendar.MONTH);
//            System.out.println("+++++++++++++++"+month);
//            error();


            String url = "jdbc:microsoft:sqlserver://10.231.200.97:1433;DatabaseName=UEP4X_CAF_FM";
            String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
            String uid = "sa";
            String pwd = "ZXcdma2000";
            DbConn dbConn = new DbConn(url, driver, uid, pwd);
            Connection connection=dbConn.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void error()throws Exception{
            System.out.println("测试.");
            throw new CustomizeException("+++++++++++自定义异常");
    }
}
