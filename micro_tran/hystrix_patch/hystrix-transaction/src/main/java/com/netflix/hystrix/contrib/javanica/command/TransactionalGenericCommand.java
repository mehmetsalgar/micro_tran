package com.netflix.hystrix.contrib.javanica.command;

import org.slf4j.Logger;
import static com.netflix.hystrix.contrib.javanica.utils.CommonUtils.createArgsForFallback;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.netflix.hystrix.contrib.javanica.command.HystrixCommandBuilder;
import com.netflix.hystrix.contrib.javanica.command.AbstractHystrixCommand;
import com.netflix.hystrix.contrib.javanica.exception.CommandActionExecutionException;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import com.netflix.hystrix.contrib.javanica.exception.FallbackInvocationException;

import org.salgar.annotation.threadlocal.TransactionalOrchestrationThreadLocal;

public class TransactionalGenericCommand extends AbstractHystrixCommand<Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalGenericCommand.class);
	
	public TransactionalGenericCommand(HystrixCommandBuilder builder) {
		super(builder);
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    protected Object run() throws Exception {
        LOGGER.debug("execute command: {}", getCommandKey().name());
        return process(new Action() {
            @Override
            Object execute() {
                return getCommandAction().execute(getExecutionType());
            }
        });
    }

    /**
     * The fallback is performed whenever a command execution fails.
     * Also a fallback method will be invoked within separate command in the case if fallback method was annotated with
     * HystrixCommand annotation, otherwise current implementation throws RuntimeException and leaves the caller to deal with it
     * (see {@link super#getFallback()}).
     * The getFallback() is always processed synchronously.
     * Since getFallback() can throw only runtime exceptions thus any exceptions are thrown within getFallback() method
     * are wrapped in {@link FallbackInvocationException}.
     * A caller gets {@link com.netflix.hystrix.exception.HystrixRuntimeException}
     * and should call getCause to get original exception that was thrown in getFallback().
     *
     * @return result of invocation of fallback method or RuntimeException
     */
    @Override
    protected Object getFallback() {
        if (getFallbackAction() != null) {
            final CommandAction commandAction = getFallbackAction();
            try {
                return process(new Action() {
                    @Override
                    Object execute() {
                        MetaHolder metaHolder = commandAction.getMetaHolder();
                        Object[] args = createArgsForFallback(metaHolder, getExecutionException());
                        return commandAction.executeWithArgs(commandAction.getMetaHolder().getFallbackExecutionType(), args);
                    }
                });
            } catch (Throwable e) {
                LOGGER.error(FallbackErrorMessageBuilder.create()
                        .append(commandAction, e).build());
                throw new FallbackInvocationException(e.getCause());
            }
        } else {
            return super.getFallback();
        }
    }
    
    Object process(Action action) throws Exception {
        Object result;
        try {
            result = action.execute();
            flushCache();
        } catch (CommandActionExecutionException throwable) {
            Throwable cause = throwable.getCause();
            if (isIgnorable(cause)) {
                throw new HystrixBadRequestException(cause.getMessage(), cause);
            }
            if(TransactionalOrchestrationThreadLocal.getTransactionalOrchestrationThreadLocal()) {
            	throw new HystrixBadRequestException(cause.getMessage(), cause);
            }
            if (cause instanceof Exception) {
                throw (Exception) cause;
            } else {
                throw Throwables.propagate(cause);
            }
        }
        return result;
    }
}