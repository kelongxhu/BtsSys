package com.scttsc.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateConvert {
private static Logger log4j=Logger.getLogger(DateConvert.class);
	public static final String dateFL = "yyyy-MM-dd HH:mm:ss";

	public static final String dateFH = "yyyy-MM-dd HH:mm:ss SSS";

	public static final String dateFM = "yyyy-MM-dd HH:mm";

	public static final String dateFS = "yyyy-MM-dd";

	public static final String dateFB = "yy-MM-dd HH:mm";

	public static final String dateFD = "yyMMddHHmmss";

	public static final String dateFF = "MM/dd/yyyy";

	public static final String dateFA = "yyyyMMdd";

	public static final String dateFE = "yyyyMM";

	public static final String dateFC = "MMdd";

	public static final String dateFG = "HHmmss";

	public static final String dateFK = "HH:mm:ss";

	public static final String dateFJ = "MM-dd HH:mm";
	
	public static final String dateFQ = "yyyy年MM月dd�?";

	public static String getNowDate() {

		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat(dateFA);

		return format.format(date);
	}

	public static String getYYYYMMDD_HHMMSS() {

		Calendar cal = Calendar.getInstance();

		String yr = String.valueOf(cal.get(Calendar.YEAR));

		String mon = String.valueOf(cal.get(Calendar.MONTH) + 1);

		if (mon.length() < 2) {

			mon = "0" + mon;

		}

		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

		if (day.length() < 2) {

			day = "0" + day;

		}

		String hr = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));

		if (hr.length() < 2) {

			hr = "0" + hr;

		}

		String min = String.valueOf(cal.get(Calendar.MINUTE));

		if (min.length() < 2) {

			min = "0" + min;

		}

		String sec = String.valueOf(cal.get(Calendar.SECOND));

		if (sec.length() < 2) {

			sec = "0" + sec;

		}

		cal = null;

		return yr + "-" + mon + "-" + day + " " + hr + ":" + min + ":" + sec;

	}

	public static String getDateStryByXmlDate(XMLGregorianCalendar xmlDate) {
		if (xmlDate==null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(xmlDate.getYear());
		sb.append("-");
		sb.append(xmlDate.getMonth());
		sb.append("-");
		sb.append(xmlDate.getDay());
		sb.append(" ");
		sb.append(xmlDate.getHour());
		sb.append(":");
		sb.append(xmlDate.getMinute());
		sb.append(":");
		String sec = "";
		int s=xmlDate.getSecond();
		if (s < 10) {
			sec = "0" + String.valueOf(s);
		}else{
			sec=String.valueOf(s);
		}
		sb.append(sec);
		return sb.toString();
	}

	/**
	 * convert string to date：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return Date
	 */
	@SuppressWarnings("static-access")
	public static Date getStrToDate() {
		DateConvert dc = new DateConvert();

		String str = dc.getYYYYMMDD_HHMMSS();

		SimpleDateFormat sm = new SimpleDateFormat(dateFL);

		try {
			return sm.parse(str);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return null;

	}

	public static String getDateToStr(Date date) {
		String result = "";
		SimpleDateFormat sm = new SimpleDateFormat(dateFL);
		result = sm.format(date);
		return result;
	}

	public static String getDateToStrYYYYMMDD(Date date) {
		String result = "";
		SimpleDateFormat sm = new SimpleDateFormat(dateFS);
		result = sm.format(date);
		return result;
	}

	public static String getDateYYYYmmDDSS() {
		String result = "";
		SimpleDateFormat sm = new SimpleDateFormat(dateFD);
		result = sm.format(new Date());
		return result;
	}

	public static String formatDateToString(Date date, String formate) {
		String result = "";
		if (null != date) {
			SimpleDateFormat sm = new SimpleDateFormat(formate);
			result = sm.format(date);
		}
		return result;
	}

	public static Date formatStringToDate(String dateStr, String formate) {
		Date result = null;
		if (null != dateStr) {
			SimpleDateFormat sm = new SimpleDateFormat(formate);
			try {
				result = sm.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Date parse(String date, String format) {
		Date d = null;
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			try {
				d = sdf.parse(date);
			} catch (Exception e) {
				log4j.debug("error");
			}
		}
		return d;
	}

	public static String format(Date date, String format) {
		String d = null;
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				d = sdf.format(date);
			} catch (Exception e) {
				log4j.debug("error");
			}
		}
		return d;
	}

	// public static void main(String args[]) {
	//
	// DateConvert t = new DateConvert();
	//
	// log4j.debug(t.getDateYYYYmmDDSS());
	// }

	public static String getNowDate_yyyy_mm_dd() {
		String nowDate = DateConvert.format(new Date(), DateConvert.dateFS);
		return nowDate;
	}

	public static String getPreSixMonthDate_yyyy_mm_dd() {
		long sixMonthFromCurDate = ((new Date()).getTime() - (180 * 24 * 3600L * 1000));
		String preSixDate = DateConvert.format(new Date(sixMonthFromCurDate), DateConvert.dateFS);
		return preSixDate;
	}

	public static void main(String[] args) {
		Date d = new Date();

		String m = DateConvert.format(d, DateConvert.dateFH);
		log4j.debug(m);
		Date d1 = new Date();
		String m1 = DateConvert.format(d1, DateConvert.dateFH);

		log4j.debug(m1);
	}

	// get the month day file path
	public static String getMonth_DayFilePath(Date date) {
		return DateConvert.format(date, DateConvert.dateFE) + "/" + DateConvert.format(date, DateConvert.dateFC) + "/";
	}
}
