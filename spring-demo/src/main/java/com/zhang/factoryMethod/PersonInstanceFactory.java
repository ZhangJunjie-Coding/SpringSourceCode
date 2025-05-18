package com.zhang.factoryMethod;

public class PersonInstanceFactory {
	public Person getPerson(String name) {
		Person person = new Person();
		person.setId(1);
		person.setName(name);
		return person;
	}
	public Person getPerson(String name,int id) {
		Person person = new Person();
		person.setId(1);
		person.setName(name);
		return person;
	}
}
