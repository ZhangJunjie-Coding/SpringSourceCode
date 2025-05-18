package com.zhang;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		BeanDefinition a = beanFactory.getBeanDefinition("a");
//		System.out.println(a.getBeanClassName());
//		System.out.println(a.getScope());
//		a.setAttribute("com.zhang.A.name","zhangjunjie");
//		System.out.println("设置BeanDefinition");
//		beanFactory.ignoreDependencyType(MyServiceImpl.class);
		System.out.println("---------");

	}
}
