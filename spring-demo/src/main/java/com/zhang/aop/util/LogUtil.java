package com.zhang.aop.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class LogUtil {
	public void myPointCut() {
	}

	public void myPointCut1() {
	}

	private int start(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		Object[] args = joinPoint.getArgs();
		System.out.println("before前置通知============================");
//		System.out.println("log---" + signature.getName() + "方法开始执行: 参数是" + Arrays.asList(args));
		return 100;
	}

	public static void stop(JoinPoint joinPoint, Object result) {
		Signature signature = joinPoint.getSignature();
//		System.out.println("log---" + signature.getName() + "方法执行结束. 结果是: " + result);
		System.out.println("after-returning最终返回通知============================");
//		result = 10;
	}

	public static void logException(JoinPoint joinPoint, Exception e) {
		Signature signature = joinPoint.getSignature();
		System.out.println("after-throwing异常通知============================");
	}

	public static void logFinally(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		System.out.println("after后置通知============================");
	}

	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		Object[] args = pjp.getArgs();
		Object result = null;
		try {
			System.out.println("around环绕通知前============================");
			// 通过反射的方式调用目标方法，相当于执行method.invoke() 可以给自己修改结果值
			result = pjp.proceed(args);
			System.out.println("around环绕通知后============================");
		} catch (Throwable throwable) {
//			System.out.println("log---环绕异常通知: " + signature.getName() + "方法执行异常");
			throw throwable;
		} finally {
//			System.out.println("log---环绕返回通知: " + signature.getName() + "方法返回结果是: "+result);
		}
		return result;

	}


}

