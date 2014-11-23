package com.scttsc.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil
{
  static final String COMMON_TIME_PATTERN = "HH:mm";
  static final String COMMON_DATE_PATTERN = "yyyy-MM-dd";
  static final String COMMON_DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

  public static String formatDate(Date paramDate)
  {
    return format(paramDate, "yyyy-MM-dd");
  }

  public static String formatDateTime(Date paramDate)
  {
    return format(paramDate, "yyyy-MM-dd HH:mm");
  }

  public static String format(Date paramDate, String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(paramDate);
  }

  public static final Date parse(String paramString1, String paramString2)
    throws ParseException
  {
    SimpleDateFormat localSimpleDateFormat = null;
    Date localDate = null;
    localSimpleDateFormat = new SimpleDateFormat(paramString2);
    try
    {
      localDate = localSimpleDateFormat.parse(paramString1);
    }
    catch (ParseException localParseException)
    {
      throw new ParseException(localParseException.getMessage(), localParseException.getErrorOffset());
    }
    return localDate;
  }

  public static Date parseDate(String paramString)
    throws ParseException
  {
    return parse(paramString, "yyyy-MM-dd");
  }

  public static Date parseDateTime(String paramString)
    throws ParseException
  {
    return parse(paramString, "yyyy-MM-dd HH:mm");
  }
}