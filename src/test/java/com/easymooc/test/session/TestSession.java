package com.easymooc.test.session;

import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.easymooc.test.util.SpringUtil;

/**
 * @filename TestSession.java
 * @author Reece
 * @description  
 * @version 
 */
public class TestSession extends SpringUtil{
	
	@Test
	public void testSession(){
		HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	}
	
}
