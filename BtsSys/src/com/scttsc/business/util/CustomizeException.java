package com.scttsc.business.util;

/**
 * User: Administrator
 * Date: 14-1-21
 * Time: 下午2:26
 */
public class CustomizeException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public CustomizeException() {
        super();
    }

    public CustomizeException(String msg) {
        super(msg);
    }

}
