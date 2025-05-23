package com.zhang.aop.annotation;

import com.zhang.aop.annotation.config.SpringConfiguration;
import com.zhang.aop.annotation.service.MyCalculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnnotationAop {
	public static void main(String[] args) throws NoSuchMethodException {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(SpringConfiguration.class);
		ac.refresh();
		MyCalculator bean = ac.getBean(MyCalculator.class);
		System.out.println(bean.add(1, 1));

	}
}
