package org.salgar.hystrix.transaction.command.factory;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.contrib.javanica.collapser.CommandCollapser;

import com.netflix.hystrix.contrib.javanica.command.GenericObservableCommand;
import com.netflix.hystrix.contrib.javanica.command.HystrixCommandBuilderFactory;
import com.netflix.hystrix.contrib.javanica.command.MetaHolder;
import com.netflix.hystrix.contrib.javanica.command.TransactionalGenericCommand;

public class TransactionalHystrixCommandFactory {
	private static final TransactionalHystrixCommandFactory INSTANCE = new TransactionalHystrixCommandFactory();

	private TransactionalHystrixCommandFactory() {
		
	}
	
	
    public static TransactionalHystrixCommandFactory getInstance() {
        return INSTANCE;
    }

    public HystrixInvokable create(MetaHolder metaHolder) {
        HystrixInvokable executable;
        if (metaHolder.isCollapserAnnotationPresent()) {
            executable = new CommandCollapser(metaHolder);
        } else if (metaHolder.isObservable()) {
            executable = new GenericObservableCommand(HystrixCommandBuilderFactory.getInstance().create(metaHolder));
        } else {
            executable = new TransactionalGenericCommand(HystrixCommandBuilderFactory.getInstance().create(metaHolder));
        }
        return executable;
    }

    public HystrixInvokable createDelayed(MetaHolder metaHolder) {
        HystrixInvokable executable;
        if (metaHolder.isObservable()) {
            executable = new GenericObservableCommand(HystrixCommandBuilderFactory.getInstance().create(metaHolder));
        } else {
            executable = new TransactionalGenericCommand(HystrixCommandBuilderFactory.getInstance().create(metaHolder));
        }
        return executable;
    }
}