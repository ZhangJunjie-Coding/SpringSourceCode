package com.zhang.populateBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PersonInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {


	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("PersonInstantiationAwareBeanPostProcessor---- 被调用执行: " + beanName);
		Person person = null;
		if (bean instanceof Person ||  bean instanceof MapFactoryBean ) {
			if(bean instanceof Person){
				person = (Person) bean;
				person.setName("zhangsan");
			}
			return true;
		} else {
			return false;
		}
	}
}
