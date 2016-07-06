package org.salgar.service.healthchecker;

import javax.inject.Named;

import org.salgar.healthcheck.RestHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealtCheckerV1Factory {
	@Autowired
	@Named("proxyProductService")
	org.salgar.product.api.ProductService productService;
	
	@Bean
	public RestHealthIndicator<org.salgar.product.api.ProductService> getHealtIndicator() {
		return new RestHealthIndicator<org.salgar.product.api.ProductService>(productService);
	}
}