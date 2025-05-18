package com.zhang;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Person implements InitializingBean {
	private ClassLoader classLoader;
	private Integer id;
	private String name;

	public Person() {
		System.out.println("构造方法");
	}

//	@Autowired(required = false)
	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Person(String name, Integer id) {

		this.id = id;
		this.name = name;
	}
//	@Autowired
	public Person(Integer id) {
		this.id = id;
	}

	@PostConstruct
	public void init(){
		System.out.println("init............");

	}

	@PreDestroy
	public void destroy(){
		System.out.println("destroy.........");

	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.name.length() <10){

		}else{

		}
	}
}
