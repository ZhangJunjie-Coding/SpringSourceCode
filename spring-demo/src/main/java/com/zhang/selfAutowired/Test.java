package com.zhang.selfAutowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("lianTest.xml");
		LianController bean = applicationContext.getBean(LianController.class);
		bean.show();

	}
}
