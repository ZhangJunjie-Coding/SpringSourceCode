package com.zhang.factoryMethod;

public class PersonStaticFactory {
	public static Person getPerson(String name) {
		Person person = new Person();
		person.setId(1);
		person.setName(name);
		return person;
	}
	public static Person getPerson(int id) {
		Person person = new Person();
		return person;
	}
}
