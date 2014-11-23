package com.scttsc.business.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-8-1
 * Time: 上午11:19
 */
public abstract class Entity {
    List<String> igFields = new ArrayList<String>();

    public abstract void setIgFields();

    public String compareEntity(Object object) {
        if (this == object) return "";
        StringBuilder sb = new StringBuilder();
        try {
            Field field = this.getClass().getDeclaredField("name");
            field.setAccessible(true);
            if (field != null) {
                sb.append("[" + field.get(this) + "]");
            }
            Field fields1[] = this.getClass().getDeclaredFields();
            Field fields2[] = object.getClass().getDeclaredFields();
            for (int i = 0; i < fields1.length; i++) {
                Field f1 = fields1[i];
                Field f2 = fields2[i];
                f1.setAccessible(true);
                f2.setAccessible(true);
                String fieldName = f1.getName();
                String fieldName2 = f2.getName();
                //排除比较字段：
                setIgFields();
                if (igFields.contains(fieldName)) {
                    continue;
                }
                //开始比较
                if (fieldName.equals(fieldName2)) {
                    Object obj1 = f1.get(this);
                    Object obj2 = f2.get(object);
                    boolean flag = false;
                    if (obj1 != null && obj2 != null) {
                        if (!obj1.equals(obj2)) {
                            flag = true;
                        }
                    } else if (obj1 == null && obj2 != null && !"".equals(obj2)) {
                        flag = true;
                    } else if (obj2 == null && obj1 != null && !"".equals(obj1)) {
                        flag = true;
                    }
                    if (flag) {
                        sb.append(fieldName + ":" + obj1 + "==>" + obj2 + ";");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("出现比较异常;");
        }
        String result = (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "";
        return result;
    }
}
