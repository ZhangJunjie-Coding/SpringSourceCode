package com.zhang.selfEditor2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EditorTest {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AddressPropertyConfiguration.class);
		System.out.println(applicationContext.getBean(Customer.class));

	}
}
