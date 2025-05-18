package com.zhang.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

	private B b;

	public MyConfig(B b){
		this.b = b ;
	}

	@Bean
	public B b() {
		return new B();
	}

}
