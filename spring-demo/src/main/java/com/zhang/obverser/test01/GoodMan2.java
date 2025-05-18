package com.zhang.obverser.test01;

public class GoodMan2 implements Observer {
	@Override
	public void make(String str) {
		System.out.println("goodman2 action!!");
		System.out.println("========" + str);
	}
}
