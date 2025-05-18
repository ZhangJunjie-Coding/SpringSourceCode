package com.zhang.test;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		TestStaticCode staticCode;

	}

	public void test1(){
		try {
			this.test();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("test1 finally");
		}
	}

	public void test() throws Exception{
		try {
			throw new Exception("zhang");
		}finally {
			System.out.println("test finally");
		}
	}
}
