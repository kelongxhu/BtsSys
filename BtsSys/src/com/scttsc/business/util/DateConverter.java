package com.scttsc.business.util;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.util.Date;

/**
 * User: Administrator
 * Date: 13-8-19
 * Time: 下午5:14
 */
public class DateConverter extends DateTimeConverter {
    @SuppressWarnings("unchecked")
    @Override
    protected Class getDefaultType() {
        return Date.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object convertToType(Class targetType, Object value)
            throws Exception {
        if (value == null || "".equals(value)) {
            return null;
        }
        return super.convertToType(targetType, value);
    }

    public DateConverter() {
        super();
    }

    public DateConverter(Object defaultValue) {
        super(defaultValue);
    }
}
