package com.zhang.selfEditor2;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:customer.properties")
public class AddressPropertyConfiguration {
	@Bean
	public Customer customer() {
		return new Customer();
	}

	@Bean
	public static CustomEditorConfigurer editorConfigurer(){
		CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
		customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new AddressPropertyEditorRegistrar()});
		return customEditorConfigurer;
	}
}
