package com.zhang.txa.annotation;

import com.zhang.txa.annotation.config.MyConfiguratoin;
import com.zhang.txa.annotation.config.TransactionConfig;
import com.zhang.txa.annotation.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(TransactionConfig.class);
		applicationContext.register(MyConfiguratoin.class);
		applicationContext.refresh();
		BookService bookService = applicationContext.getBean(BookService.class);
		bookService.checkout("zhangsan", 1);
	}

	public static void main1(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(TransactionConfig.class);
		applicationContext.register(MyConfiguratoin.class);
		applicationContext.refresh();
		TransactionConfig bean = applicationContext.getBean(TransactionConfig.class);
		System.out.println(bean.bookDao());
		System.out.println(bean.bookDao());

	}
}
 