package com.zhang.shouldSkip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional({WindowsCondition.class})
@Configuration
public class BeanConfig {
	@Bean(name = "bill")
	public Person person1() {
		return new Person("bill Gates", 62);
	}

	@Conditional({LinuxCondition.class})
	public Person person2() {
		return new Person("linux", 48);
	}
}
