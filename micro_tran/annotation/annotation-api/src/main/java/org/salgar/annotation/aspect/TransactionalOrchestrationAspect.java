package org.salgar.annotation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.salgar.annotation.threadlocal.TransactionalOrchestrationThreadLocal;

@Aspect
public class TransactionalOrchestrationAspect {

	@Pointcut("@annotation(org.salgar.annotation.TransactionalOrchestration)")
	public void transactionalOrchestrationAnnotationPointcut() {
	}

	@Around("transactionalOrchestrationAnnotationPointcut()")
	public Object methodsAnnotatedWithHystrixCommand(final ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try {
			TransactionalOrchestrationThreadLocal.setTransactionalOrchestrationThreadLocal(Boolean.TRUE);
			result = joinPoint.proceed();
		} finally {
			TransactionalOrchestrationThreadLocal.setTransactionalOrchestrationThreadLocal(Boolean.FALSE);
		}
		
		return result;
	}
}