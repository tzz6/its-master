package com.its.servers.aop.annotation;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.its.model.dao.domain.User;

/**
 * AOP Annotation 方式
 *
 */
@Aspect
@Service("userServiceInterceptor")
public class UserServiceInterceptor {

	protected final transient Logger log = Logger.getLogger(UserServiceInterceptor.class);

	@Pointcut("execution(* com.its.service.impl.UserServiceImpl.*(..))")
	private void anyMethod() {}// 定义切点

	@Before("anyMethod() && args(user)")
	public void doAccessCheck(User user) {
		System.out.println(user.getName());
		log.info("***AOP***前置通知***");
	}

	@AfterReturning("anyMethod()")
	public void doAfter() {
		log.info("***AOP***后置通知***");
	}

	@After("anyMethod()")
	public void after() {
		log.info("***AOP***最终通知***");
	}

	@AfterThrowing("anyMethod()")
	public void doAfterThrow() {
		log.info("***AOP***异常通知***");
	}

	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		log.info("***AOP***进入环绕通知***");
		// 获取参数
		// proceedingJoinPoint.getArgs();
		Object object = proceedingJoinPoint.proceed();// 执行该方法
		log.info("***AOP***退出方法***");
		return object;
	}
}