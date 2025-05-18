package com.zhang.methodOverrides.replace;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReplaceDog implements MethodReplacer {
	@Override
	public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
		System.out.println("Hello,I am a white dog....");
		/**
		 * 这里若想调用原本对象的方法，不能直接使用method.invoke(obj,args); 因为这里的obj是使用Cglib生成的原类型的子类对象
		 * com.zhang.methodOverrides.replace.OriginalDog$$EnhancerBySpringCGLIB$$4701081e@2a70a3d8
		 * 若直接使用上面的方法调用的话，会陷入无限递归。
		 */
		// method.invoke(obj,args);



		System.out.println(obj);
		Class<?> originClass = obj.getClass().getSuperclass();
		Constructor<?>[] constructors = originClass.getConstructors();
		System.out.println(constructors.length);
		Constructor<?> nonArgsConstructor = constructors[0];
		Object o = nonArgsConstructor.newInstance();
		System.out.println(o);
		method.invoke(o,args);

		Arrays.stream(args).forEach(str -> System.out.println("参数： " + str));
		return obj;
	}
}
