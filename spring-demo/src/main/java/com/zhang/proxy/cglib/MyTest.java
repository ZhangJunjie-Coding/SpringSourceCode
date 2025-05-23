package com.zhang.proxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

public class MyTest {
	public static void main(String[] args) {
		// 动态代理创建的class文件存储到本地
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"d:\\code");
		// 通过cglib动态代理获取代理对象的过程，创建调用的对象,在后续创建过程中，EnhanceKey的对象，所以在进行enhancer对象创建的时候需要把EnhanceKey（newInstance）对象准备好， 恰好这个对象也需要动态代理来生成
		Enhancer enhancer = new Enhancer();
		// 设置enhancer对象的父类
		enhancer.setSuperclass(MyCalculator.class);
		// 设置enhancer对象的回调
		enhancer.setCallback(new MyCglib());
		// 创建代理对象
		MyCalculator  myCalculator = (MyCalculator) enhancer.create();
		// 通过代理对象调用目标方法
		System.out.println(myCalculator.add(1, 1));
		System.out.println(myCalculator.getClass());
	}
}
