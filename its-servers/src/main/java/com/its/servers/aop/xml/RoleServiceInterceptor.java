package com.its.servers.aop.xml;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.its.model.dao.domain.Role;


/**
 * AOP XML方式
 */
public class RoleServiceInterceptor {
	
	protected final transient Logger log = Logger.getLogger(RoleServiceInterceptor.class);

	/** 在核心业务执行前执行，不能阻止核心业务的调用 */
	public void doBefore(JoinPoint joinPoint) {
		// 可通过joinPoint来获取所需要的内容
		Object[] objects = joinPoint.getArgs();
		if(objects != null && objects.length > 0 && objects[0] instanceof Role){
			Role role = (Role) objects[0];
			log.info("role:"+role.getName());
		}
		log.info("***AOP***前置通知***");
	}

	/** 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice @param joinPoint */
	public void doAfter(JoinPoint joinPoint) {
		System.out.println("***AOP***最终通知");
	}

	/**核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice*/
	public void doAfterReturning(JoinPoint joinPoint) {
		log.info("***AOP***后置通知***");
	}

	/** 异常通知 */
	public void doAfterThrowing(Throwable ex) {
		log.info("***AOP***异常通知：" + ex.getMessage() + "***");
	}

	/** 环绕通知 */
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		log.info("***AOP***进入环绕通知***");
		Object object = pjp.proceed();// 执行该方法
		log.info("***AOP***退出方法***");
		return object;
	}
}