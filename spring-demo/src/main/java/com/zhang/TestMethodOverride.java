package com.zhang;

import com.zhang.methodOverrides.replace.OriginalDog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * spring默认的对象都是单例的，spring会在一级缓存中持有对象，方便下次直接获取，
 * 那么如果是原型作用域的话，会创建一个新的对象
 * 如果想在一个单例模式的bean下引用一个原型模式的bean，怎么办？
 * 在此时就需要引用lookup-method标签来解决其问题
 * <p>
 * 通过拦截器的方式每次需要的时候都去创建最新的对象，而不会把原型对象缓存起来
 *
 * lookup-method: 是一种用于解决单例Bean依赖原型Bean时无法动态获取新实例的机制。
 * 它通过方法注入，允许单例Bean在每次调用特定方法时，从容器中获取一个新的原型Bean实例.
 */
public class TestMethodOverride {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("methodOverride.xml");

		OriginalDog bean = applicationContext.getBean(OriginalDog.class);
		bean.sayHello();
		bean.sayHello("zzazzz");



//		FruitPlate fruitplate1 = (FruitPlate) applicationContext.getBean("fruitplate1");
//		System.out.println("fruitplate1"+fruitplate1);
//		Fruit fruit = fruitplate1.getFruit();
//		System.out.println(fruit);
//
//		System.out.println("=============");
//		System.out.println(fruitplate1.getFruit());
//
//		FruitPlate fruitplate2 = (FruitPlate) applicationContext.getBean("fruitplate1");
//		System.out.println("fruitplate2"+fruitplate2);
//		Fruit fruit2 = fruitplate2.getFruit();
//		System.out.println(fruit2);



//		FruitPlate fruitplate2 = (FruitPlate) applicationContext.getBean("fruitplate2");
//		fruitplate2.getFruit();
//
//		FruitPlate fruitcake3 = (FruitPlate) applicationContext.getBean("fruitplate2");
//		fruitcake3.getFruit();

//		Apple bean = applicationContext.getBean(Apple.class);
//		System.out.println(bean.getBanana());
//		Apple bean1 = applicationContext.getBean(Apple.class);
//		System.out.println(bean1.getBanana());


	}
}



//		FruitPlate fruitplate2 = (FruitPlate) applicationContext.getBean("fruitplate2");
//		fruitplate2.getFruit();
//
//		FruitPlate fruitcake3 = (FruitPlate) applicationContext.getBean("fruitplate2");
//		fruitcake3.getFruit();

//		Apple bean = applicationContext.getBean(Apple.class);
//		System.out.println(bean.getBanana());
//		Apple bean1 = applicationContext.getBean(Apple.class);
//		System.out.println(bean1.getBanana());


//	}
//}
