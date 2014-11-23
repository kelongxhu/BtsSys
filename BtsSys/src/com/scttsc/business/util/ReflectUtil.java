package com.scttsc.business.util;

import java.lang.reflect.Field;

/**
 * User: Administrator
 * Date: 14-4-9
 * Time: 下午1:53
 */
public class ReflectUtil {
    /**
     * 获取对象属性值
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFiledValue(Object object, String fieldName) {
        Object retvalue = null;
        try {
            Class c = object.getClass();
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            retvalue = field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retvalue;
    }
}
