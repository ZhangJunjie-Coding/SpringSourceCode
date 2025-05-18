package com.zhang.txa.xml;

import com.zhang.txa.xml.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tx.xml");
		BookService bookService = applicationContext.getBean("bookService", BookService.class);
		bookService.checkout("zhangsan", 1);


	}
}
