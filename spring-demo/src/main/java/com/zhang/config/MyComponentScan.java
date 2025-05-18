package com.zhang.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zhang.selftag")
public class MyComponentScan {

	@ComponentScan("com.zhang.selftag")
	@Configuration
	class InnerClass{
	}
}
