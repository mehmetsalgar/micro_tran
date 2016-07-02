package org.salgar.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ImportResource(locations = {"classpath:/META-INF/spring/org/salgar/product/v1/applicationContext.xml",
		"classpath:/META-INF/spring/org/salgar/product/v2/applicationContext.xml",
		"classpath:/META-INF/spring/org/salgar/order/v1/applicationContext.xml",
		"classpath:/META-INF/spring/org/salgar/customer/v1/applicationContext.xml",
		"classpath:/META-INF/spring/org/salgar/dao/applicationContext-dao.xml",
		"classpath:/META-INF/spring/org/salgar/aop/applicationContext-aop.xml"})
public class ProductProcessApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProductProcessApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProductProcessApplication.class, args);
	}

}