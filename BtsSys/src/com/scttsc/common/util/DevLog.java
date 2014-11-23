package com.scttsc.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DevLog
{
  private static final Log log = LogFactory.getLog(DevLog.class);

  public static void trace(Object paramObject)
  {
    if (!(log.isTraceEnabled()))
      return;
    log.trace(paramObject);
  }

  public static void trace(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isTraceEnabled()))
      return;
    log.trace(paramObject, paramThrowable);
  }

  public static void debug(Object paramObject)
  {
    if (!(log.isDebugEnabled()))
      return;
    log.debug(paramObject);
  }

  public static void debug(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isDebugEnabled()))
      return;
    log.debug(paramObject, paramThrowable);
  }

  public static void info(Object paramObject)
  {
    if (!(log.isInfoEnabled()))
      return;
    log.info(paramObject);
  }

  public static void info(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isInfoEnabled()))
      return;
    log.info(paramObject, paramThrowable);
  }

  public static void warn(Object paramObject)
  {
    if (!(log.isWarnEnabled()))
      return;
    log.warn(paramObject);
  }

  public static void warn(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isWarnEnabled()))
      return;
    log.warn(paramObject, paramThrowable);
  }

  public static void error(Object paramObject)
  {
    if (!(log.isErrorEnabled()))
      return;
    log.error(paramObject);
  }

  public static void error(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isErrorEnabled()))
      return;
    log.error(paramObject, paramThrowable);
  }

  public static void fatal(Object paramObject)
  {
    if (!(log.isFatalEnabled()))
      return;
    log.fatal(paramObject);
  }

  public static void fatal(Object paramObject, Throwable paramThrowable)
  {
    if (!(log.isFatalEnabled()))
      return;
    log.fatal(paramObject, paramThrowable);
  }
}