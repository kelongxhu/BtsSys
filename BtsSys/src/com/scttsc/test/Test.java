package com.scttsc.test;

import com.scttsc.business.model.Bts;
import com.scttsc.business.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

//        System.out.println("123:" + isDecimal("123"));
//        System.out.println("0.123:" + isDecimal("0.123"));
//        System.out.println(".123:" + isDecimal(".123"));
//        System.out.println("1.23:" + isDecimal("1.23"));
//        System.out.println("123.:" + isDecimal("123."));
//        System.out.println("00.123:" + isDecimal("00.123"));
//        System.out.println("10.0:" + isDecimal("10.0"));
//        System.out.println("123.00:" + isDecimal("123.00"));
//        System.out.println("0123:" + isDecimal("0123"));
//
//        Date d=null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
//        try {
//            d = simpleDateFormat.parse("2011-10");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


//        System.out.println(d);

//
//       int n= GradeUtil.getMonthNum(d,new Date());
//       System.out.println("++++++++++++++++++++"+n);


//       String aa="3*1*460036290410490btsCheckupView1376980613951.jpg*&";
//
//       String[] aaa=aa.split("&");
//        for(String s:aaa){
//            System.out.println("sss:"+s);
//            String[] ss=s.split("\\*");
//            System.out.println("++++++++++"+ss.length);
//            for(String sss:ss){
//                System.out.println("-------------"+sss);
//            }
//        }


//        Map<String,Integer> t=new HashMap<String,Integer>();
//        t.put("a",1);
//        Integer i=t.get("a");
//        if(i==null){
//            t.put("a",1);
//        }else{
//            i=new Integer(i.intValue()+1);
//        }


//        System.out.println("++++++++++++"+t.get("a"));


//       boolean flag=isInteger("149999999999995");
//
//        System.out.println("+++++++++++++"+flag);

//        String s ="3_福泉水落洞接入网";
//
//        int j=s.indexOf("_");
//
//        if(j!=-1){
//            System.out.println(s.substring(j)+1);
//        }else{
//            System.out.println(s);
//        }

//        Bts bts = new Bts();
//        bts.setName("aaa");
//        Test g=new Test();
//        g.deal2(2,48);

        String name="022222222_777_2_仁怀小耳沟_拉远_电_电_C";
//        System.out.print(name.indexOf("_",3));
//        String nameStr=name.substring("name".indexOf("_",3));
//        System.out.println(nameStr);
        String[] arr=name.split("_");
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<arr.length;i++){
            if(i>2){
                sb.append(arr[i]);
            }
        }
        System.out.println("sb:"+sb.toString());

      //  System.out.println(ReflectUtil.getValue(bts,"name"));


    }

    public static boolean isDecimal(String str) {
        return Pattern.compile("^([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();
    }


    public static boolean isInteger(String str) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("^-?([1-9]+[0-9]*|0)(\\.[\\d]+)?");
//        Pattern pattern = Pattern.compile("^[0-9]*$");


        //字符串不为空;
        if (str.length() > 0) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches() == true) {
                flag = true;
            }
        }
        return flag;
    }



    public void deal(int pageNow,int numPage){

        //第一页与当前页之间有多少个页面
        int per=pageNow-2;
        //当前页与最后一页有多少个页面
        int suf=numPage-pageNow-1;
        //中间页码的开始 页
        int start=pageNow-3;
        //中间页码的结束页
        int end=pageNow+2;
        if(start<1){
            //如果中间开始页小于1  -->则中间开始页为2
            start=2;
        }
        if(end>numPage){
            //如果中间结束页大于于    总页数     -->则中间结束页为   numPage-1
            end=numPage-1;
        }

        //-------------页码输出
        System.out.print(" "+1);
        if(per>3){
            //
            System.out.print(" ...");
        }
        for(int i=start;i<=end;i++){
            //
            System.out.print(" "+i);
        }
        if(suf>2){
            System.out.print(" ...");
        }
        System.out.print(" "+numPage);

    }




    public void deal2(int pageNow,int numPage){
        //中间页码的开始 页
        int start=pageNow-4;
        //中间页码的结束页
        int end=pageNow+4;
        if(start<1){
            //如果中间开始页小于1  -->则中间开始页为2
            end+=Math.abs(start)+1;
            start=1;
        }
        int pref=end-numPage;
        if(pref>0){
            //如果中间结束页大于于    总页数     -->则中间结束页为   numPage-1
            end=numPage;
            start-=pref;
            if(start<0){
                start=1;
            }
        }
        for(int i=start;i<=end;i++){
            System.out.print(" "+i);
        }
        if(numPage>end){
            System.out.print(" ...");
        }
//        System.out.print(" "+numPage);

    }

}
