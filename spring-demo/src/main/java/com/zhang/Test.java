package com.zhang;

import com.zhang.selftag.User;
import org.springframework.context.ApplicationContext;

public class Test {
	public static void main(String[] args) {

//		MyClassPathXmlApplicationContext mc = new MyClassPathXmlApplicationContext("applicationContext.xml");

//		System.out.println(user);
//		MyClassPathXmlApplicationContext mc = new MyClassPathXmlApplicationContext("applicationContext.xml");

//		MyFactoryBean bean = (MyFactoryBean) mc.getBean("&myFactoryBean");
//		System.out.println(bean);
//
//		User user = (User) mc.getBean("myFactoryBean");
//		System.out.println(user.getUsername());
//
//		User user2 = (User) mc.getBean("myFactoryBean");
//		System.out.println(user.getUsername());

//		ApplicationContext ac = new MyClassPathXmlApplicationContext("person.xml");
//		Person person = ac.getBean(Person.class);
//		Person bean2 = ac.getBean(Person.class);

		MyClassPathXmlApplicationContext mc = new MyClassPathXmlApplicationContext("test.xml");
		Person person = mc.getBean("person", Person.class);
		mc.close();

	}
}
