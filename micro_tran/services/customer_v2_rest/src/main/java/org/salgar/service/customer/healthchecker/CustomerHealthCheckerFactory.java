package org.salgar.service.customer.healthchecker;

import javax.inject.Named;

import org.salgar.healthcheck.RestHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerHealthCheckerFactory {
	@Autowired
	@Named("proxyCustomerService")
	org.salgar.customer.api.v2.CustomerService customerService;

	@Bean
	public RestHealthIndicator<org.salgar.customer.api.v2.CustomerService> getHealtIndicator() {
		return new RestHealthIndicator<org.salgar.customer.api.v2.CustomerService>(customerService);
	}
}