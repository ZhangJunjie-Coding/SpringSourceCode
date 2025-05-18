package com.zhang.cycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCycle {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("cycle.xml");
		MyConfig config = classPathXmlApplicationContext.getBean(MyConfig.class);

		System.out.println(config);
//		A bean = classPathXmlApplicationContext.getBean(A.class);
//		System.out.println(bean);
//		B r = classPathXmlApplicationContext.getBean(B.class);
//		System.out.println(r);
//
//
//		classPathXmlApplicationContext.close();
	}
}
