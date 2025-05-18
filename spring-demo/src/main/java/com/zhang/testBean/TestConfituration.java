package com.zhang.testBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfituration {
	@Bean
	public Person person() {
		Person person = new Person();
		person.setName("zhang");
		return new Person();
	}
}
