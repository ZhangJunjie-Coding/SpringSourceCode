package com.zhang.test;

public class TestStaticCodeSub extends TestStaticCode{
	static {
		System.out.println("子类静态代码块");
	}
	static int b = 2;
}
