package com.scttsc.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtil
  implements ApplicationContextAware
{
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext paramApplicationContext)
    throws BeansException
  {
    applicationContext = paramApplicationContext;
  }

  public static ApplicationContext getApplicationContext()
  {
    return applicationContext;
  }

  public static Object getBean(String paramString)
    throws BeansException
  {
    return applicationContext.getBean(paramString);
  }

  public static Object getBean(String paramString, Class paramClass)
    throws BeansException
  {
    return applicationContext.getBean(paramString, paramClass);
  }

  public static boolean containsBean(String paramString)
  {
    return applicationContext.containsBean(paramString);
  }

  public static boolean isSingleton(String paramString)
    throws NoSuchBeanDefinitionException
  {
    return applicationContext.isSingleton(paramString);
  }

  public static Class getType(String paramString)
    throws NoSuchBeanDefinitionException
  {
    return applicationContext.getType(paramString);
  }

  public static String[] getAliases(String paramString)
    throws NoSuchBeanDefinitionException
  {
    return applicationContext.getAliases(paramString);
  }
}