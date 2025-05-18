package com.zhang.populateBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPopulate {
	public static void main(String[] args) {
 		ApplicationContext ac = new ClassPathXmlApplicationContext("populateBean.xml");
// 		ApplicationContext ac = new ClassPathXmlApplicationContext("testMap.xml");
//		Object map = ac.getBean("myMap");
//		System.out.println(map);
		System.out.println("zhan");
	}
}
