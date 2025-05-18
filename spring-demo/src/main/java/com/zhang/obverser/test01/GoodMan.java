package com.zhang.obverser.test01;

public class GoodMan implements Observer {
	@Override
	public void make(String str) {
		System.out.println("action!!");
		System.out.println("-----------" + str);
	}
}
