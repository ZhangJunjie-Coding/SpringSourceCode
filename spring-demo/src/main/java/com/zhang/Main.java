package com.zhang;

public class Main {
	public static void main(String[] args) {
		int a = 10;
		int b = 20;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println("a: " + a + "," + "b: " + b);


		a = 10;
		b = 11;
		System.out.println((a & 1) + ": " + (b & 1));
	}
}
