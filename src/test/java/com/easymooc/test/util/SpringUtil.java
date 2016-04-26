package com.easymooc.test.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @filename SpringUtil.java
 * @author Reece
 * @description  
 * @version 
 */
public class SpringUtil {
	public  static ApplicationContext applicationContext;
	
	static{
		applicationContext = new ClassPathXmlApplicationContext("com/easymooc/spring/applicationContext.xml");
	}
}
