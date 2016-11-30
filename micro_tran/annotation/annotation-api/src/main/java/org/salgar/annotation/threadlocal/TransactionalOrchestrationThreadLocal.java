package org.salgar.annotation.threadlocal;

public class TransactionalOrchestrationThreadLocal {
	private static ThreadLocal<Boolean> transactionalOrchestrationThreadLocal = new ThreadLocal<Boolean>();

	public static Boolean getTransactionalOrchestrationThreadLocal() {
		return transactionalOrchestrationThreadLocal.get();
	}

	public static void setTransactionalOrchestrationThreadLocal(Boolean transactionalOrchestrationThreadLocal) {
		TransactionalOrchestrationThreadLocal.transactionalOrchestrationThreadLocal
				.set(transactionalOrchestrationThreadLocal);
	}
}