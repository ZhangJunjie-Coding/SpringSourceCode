package com.zhang;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class MyMember {
	@Component
	@Configuration
	class innerClass {
		@Component
		@Configuration
		class InnerInnerClass {

		}
	}
}
