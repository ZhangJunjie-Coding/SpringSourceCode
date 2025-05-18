package com.zhang.methodOverrides.lookup;

public class Apple extends Fruit {
	Banana banana;
	public Apple() {
		System.out.println("I got a fresh apple" + this);
	}

	public Banana getBanana() {
		return banana;
	}

	public void setBanana(Banana banana) {
		this.banana = banana;
	}
}
