package com.scttsc.admin.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class appTest {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		// HxMenuManager manager=(HxMenuManager)ctx.getBean("hxMenuManager");

	}

}
