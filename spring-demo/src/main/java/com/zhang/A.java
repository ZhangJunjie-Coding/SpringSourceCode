package com.zhang;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class A implements ApplicationContextAware, BeanNameAware {
    private String name;
    private ApplicationContext applicationContext;
    private String beanName;

    public A() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init() {
        System.out.println("init");
        System.out.println(this.name);
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    public void show() {
        System.out.println("beanName: " + beanName);
    }
}
