package com.zhang.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CalculatorProxy {
	public static Calculator getProxy(final Calculator calculator) {
		ClassLoader loader = calculator.getClass().getClassLoader();
		Class<?>[] interfaces = calculator.getClass().getInterfaces();
		InvocationHandler h = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object result = null;
				try {
					System.out.println("=====proxy=====");
					result = method.invoke(calculator, args);
				} catch (Exception e) {

				}
				return result;
			}
		};

		Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
		return (Calculator) proxy;
	}
}
