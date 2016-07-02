package org.salgar.annotation.processor;

import java.util.ArrayList;
import java.util.List;

import org.salgar.annotation.TransactionalFanout;
import org.salgar.healthcheck.HealthCheck;
import org.salgar.healthcheck.ProcessHealthIndicatorImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TransactionalFanoutProcessor implements BeanPostProcessor {
	private ConfigurableListableBeanFactory configurableListableBeanFactory;
	
	@Autowired
	public TransactionalFanoutProcessor(ConfigurableListableBeanFactory configurableListableBeanFactory) {
		this.configurableListableBeanFactory = configurableListableBeanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		boolean result = bean.getClass().isAnnotationPresent(TransactionalFanout.class);
		
		if(result) {
			TransactionalFanout transactionalFanout = bean.getClass().getAnnotation(TransactionalFanout.class);
			List<HealthCheck> services = new ArrayList<>();
			
			for (String  serviceName : transactionalFanout.services()) {
				HealthCheck healthCheck = (HealthCheck) configurableListableBeanFactory.getBean(serviceName);
				services.add(healthCheck);
			}
			
			ProcessHealthIndicatorImpl processHealthIndicator = new ProcessHealthIndicatorImpl();
			processHealthIndicator.setServices(services);
			configurableListableBeanFactory.registerSingleton(bean.getClass().getSimpleName() + "ProcessHealthIndicator", processHealthIndicator);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
