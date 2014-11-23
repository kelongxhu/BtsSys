/**
* Title:     e
* Company:    SCTTSC
* @author    liuyang
* @date      2012-6-17
*/

package com.scttsc.common.util;


import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * 
 */
public class DateUtils {
	public static final String JSESSION_COOKIE = "JSESSIONID";
	public static final String JSESSION_URL = "jsessionid";

	/**
	 * @return
	 */
	public static java.sql.Timestamp now() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return format.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String dataFormatWhole(Date date) {
		return formatw.format(date);
	}

	public static String formatDate(Date date, int style) {
		if (date == null) {
			return "";
		}
		switch (style) {
		case 4:
			return formats.format(date);
		case 3:
			return formatm.format(date);
		case 2:
			return format.format(date);
		default:
			return formatw.format(date);
		}
	}
	
	public static Date getNowTimeDate (){
		return DateUtils.getNowTime(DateUtils.getTime(5), "yyyy-MM-dd HH:mm:ss");
	}

	public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat formatw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat formatm = new SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat formats = new SimpleDateFormat("MM-dd");
	public static FilenameFilter DIR_FILE_FILTER = new FilenameFilter() {

	public boolean accept(File dir, String name) {
			if (dir.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}
	};
	
	/**
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date,String strFormat) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(strFormat);
	   String str = format.format(date);
	   return str;
	} 

	/**
	* @param str
	* @return date 
	*/
	public static Date strToDate(String str,String strFormat) {	  
	   SimpleDateFormat format = new SimpleDateFormat(strFormat);
	   Date date = null;
	   try {
		   date = format.parse(str);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return date;
	}
	
	public static Date getNowTime(String strDate,String strFormat){		
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try
		{
			date = format.parse(strDate);
		} catch(Exception e){}
		return date;
	}

	public static String getTime(int type)
	{
		String t = null;
		SimpleDateFormat format = null;
		Calendar cal = Calendar.getInstance();
		switch (type)
		{
			case 0:
				format = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 1:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 2:
				format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
				break;
			case 3:
				format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
				break;
			case 4:
				format = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 5:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 6:
				format = new SimpleDateFormat("yyyyMMdd");
				break;
			case 7:
				format = new SimpleDateFormat("yyyy-MM");
				break;
			case 8:
				format = new SimpleDateFormat("HH:mm:ss");
				break;
			case 9:
				format = new SimpleDateFormat("yyyy");
				break;
			case 10:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				break;
			default:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
		}
		t = format.format(cal.getTime());
		return t;
	}
	
	public static long getLongTime() {
		return System.currentTimeMillis();
	}
	
	public static long compareDate(Date iDate,String format){
		long distime = 0;
		if (iDate != null) {
			try {
				distime = DateUtils.getLongTime() - DateFormat.getDateTimeInstance().parse(DateUtils.formatDateTime(iDate, format)).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			distime = distime/1000/60/60/24;	        
		}		
		return distime;
	}
	
	
	 public static boolean isDateBefore(Date date2){
	  try{
		   Date date1 = new Date();
		   DateFormat df = DateFormat.getDateTimeInstance();
		   return date1.before(df.parse(DateUtils.formatDateTime(date2, "yyyy-MM-dd HH:mm:ss"))); 
	  }catch(ParseException e){
		  System.out.print("[isDateBefore] " + e.getMessage());
		  return false;
	  }
	 }
	 
	 public static boolean isDateBefore(String date1,String date2){
	  try{
		   DateFormat df = DateFormat.getDateTimeInstance();
		   return df.parse(date1).before(df.parse(date2)); 
	  }catch(ParseException e){
		  System.out.print("[isDateBefore] " + e.getMessage());
		  return false;
	  }
	 }

	
	public static Date strToDate(String dateStr) {
		Date dateTemp = null;
		try {
			dateStr = dateStr.substring(0, 10);
			StringTokenizer token = new StringTokenizer(dateStr, "-");
			int year = Integer.parseInt(token.nextToken());
			int month = Integer.parseInt(token.nextToken()) - 1;
			int day = Integer.parseInt(token.nextToken());
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			dateTemp = cal.getTime();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return dateTemp;
	}
	
	public static String formatDateTime(Date date,String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}
	
	  public static long getTwoDateDays(Date beginDate,Date endDate)
	  {
			long beginTime = beginDate.getTime();
			long endTime = endDate.getTime();
			long betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24) + 0.5);
			return betweenDays;
	  }
	
	public static String returnNDay(int outday)
	{
		String newdatetime = "";
		SimpleDateFormat fmtdt = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, outday);
		Date tempnewdatetime = c.getTime();
		newdatetime = fmtdt.format(tempnewdatetime).toString();
		return newdatetime;
	}


	public static Date getLastOrBeforeDate(Date date, int in) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, in);
		return cal.getTime();
	}
	
	public static final Date getNowDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	public static String thisWeekStartAndEnd(){
		GregorianCalendar t = new GregorianCalendar();   
		t.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);   
		System.out.println(t.get(t.YEAR)+"-"+(t.get(t.MONTH)+1)+"-"+t.get(t.DATE));
		
	}
	*/
	
	/**
	 * 获取昨天时间
	 */
	public static Date getLastDay(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
        Date d=calendar.getTime();


		return calendar.getTime();
	}
	/**
	 * 获取当前时间本月月初时间
	 * @return
	 */
	public static Date getFirstDayOfMonth(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -0);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间本月月底时间
	 * @return
	 */
	public static Date getLastDayOfMonth(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DATE,1);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间上月月初时间
	 * @return
	 */
	public static Date getFirstDayOfLastMonth(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间上月月底时间
	 * @return
	 */
	public static Date getLastDayOfLastMonth(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DATE,1);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取上周开始
	 * @param args
	 */
	public static Date getFirstDayOfWeek(){
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		calendar.add(Calendar.DAY_OF_WEEK, -7);  //上周
//		calendar.add(Calendar.DAY_OF_WEEK, 0);   //本周
		return calendar.getTime();
	}
	/**
	 * 获取上周结束
	 * @return
	 */
	public static Date getLastDayOfWeek(){
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
		calendar.add(Calendar.DAY_OF_WEEK, 1); //上周
//		calendar.add(Calendar.DAY_OF_WEEK, 8); //本周
		return calendar.getTime();
	}
	
	/**
	 * 获取当前年
	 * @return
	 */
	public static String getYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR)+""; 
	}
	/**
	 * 获取当前月
	 * @return
	 */
	public static String getMonth(){
		Calendar cal = Calendar.getInstance();
		int re = cal.get(Calendar.MONTH)+1;
		if(re < 10) return "0"+re;
		return re+""; 
	}
	
	public static String getMonthWith(){
		Calendar cal = Calendar.getInstance();
		int re = cal.get(Calendar.MONTH);
		return re+""; 
	}
	
	public static void main(String[] args) {
//		System.out.println(dateFormat(getFirstDayOfMonth()));
//		System.out.println(dateFormat(getLastDayOfMonth()));
//		System.out.println(dateFormat(getFirstDayOfWeek()));
//		System.out.println(dateFormat(getLastDayOfWeek()));
//		System.out.println(getBefore2Month());
		System.out.println(getMonthWith());
		System.out.println(getBefore1Day());
	}
	
	/**
	 * 获取当前时间前俩月初时间
	 * @return
	 */
	public static Date getBefore2Month(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间前月初时间
	 * @return
	 */
	public static Date getBefore1Month(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取当前日期前一天
	 * @return
	 */
	public static Date getBefore1Day(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
}


