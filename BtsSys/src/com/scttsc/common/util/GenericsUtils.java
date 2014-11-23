package com.scttsc.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenericsUtils
{
  private static final Log log = LogFactory.getLog(GenericsUtils.class);

  public static Class getSuperClassGenricType(Class paramClass)
  {
    return getSuperClassGenricType(paramClass, 0);
  }

  public static Class getSuperClassGenricType(Class paramClass, int paramInt)
  {
    Type localType = paramClass.getGenericSuperclass();
    if (!(localType instanceof ParameterizedType))
    {
      log.warn(paramClass.getSimpleName() + "'s superclass not ParameterizedType");
      return Object.class;
    }
    Type[] arrayOfType = ((ParameterizedType)localType).getActualTypeArguments();
    if ((paramInt >= arrayOfType.length) || (paramInt < 0))
    {
      log.warn("Index: " + paramInt + ", Size of " + paramClass.getSimpleName() + "'s Parameterized Type: " + arrayOfType.length);
      return Object.class;
    }
    if (!(arrayOfType[paramInt] instanceof Class))
    {
      log.warn(paramClass.getSimpleName() + " not set the actual class on superclass generic parameter");
      return Object.class;
    }
    return ((Class)arrayOfType[paramInt]);
  }
}