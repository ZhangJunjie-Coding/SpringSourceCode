package com.zhang.aop.annotation.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogUtil {
	@Pointcut("execution(public Integer com.zhang.aop.annotation.service.MyCalculator.*(Integer,Integer))")
	public void myPointCut() {
	}

	public void myPointCut1() {
	}
	@Around(value = "myPointCut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		Object[] args = pjp.getArgs();
		Object result = null;
		try {
			System.out.println("around环绕通知前============================");
//			System.out.println("log---环绕通知start: " + signature.getName() + "方法开始执行,参数为: " + Arrays.asList(args));
			// 通过反射的方式调用目标方法，相当于执行method.invoke() 可以给自己修改结果值
			result = pjp.proceed(args);
			System.out.println("around环绕通知后============================");
//			System.out.println("log---环绕通知stop: " + signature.getName() + "方法执行结束");
		} catch (Throwable throwable) {
//			System.out.println("log---环绕异常通知: " + signature.getName() + "方法执行异常");
			throw throwable;
		} finally {
//			System.out.println("log---环绕返回通知: " + signature.getName() + "方法返回结果是: "+result);
		}
		return result;
	}

	@Before(value = "myPointCut()")
	private int start(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		Object[] args = joinPoint.getArgs();
		System.out.println("before前置通知============================");
//		System.out.println("log---" + signature.getName() + "方法开始执行: 参数是" + Arrays.asList(args));

		return 100;
	}


	@AfterThrowing(value = "myPointCut()", throwing = "e")
	public static void logException(JoinPoint joinPoint, Exception e) {
		Signature signature = joinPoint.getSignature();
		System.out.println("after-throwing异常通知============================");
//		System.out.println("log---" + signature.getName() + "方法执行异常. 异常信息是: " + e.getMessage());

	}

	@After(value ="myPointCut()")
	public static void logFinally(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
//		System.out.println("log---" + signature.getName() + "方法执行结束. ....over ");
		System.out.println("after后置通知============================");
	}


	@AfterReturning(value = "myPointCut()", returning = "result")
	public static void stop(JoinPoint joinPoint, Object result) {
		Signature signature = joinPoint.getSignature();
//		System.out.println("log---" + signature.getName() + "方法执行结束. 结果是: " + result);
		System.out.println("after-returning最终返回通知============================");
//		result = 10;
	}

}

