package org.salgar.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@ImportResource(locations = {"classpath:/META-INF/spring/org/salgar/customer_v1_rest/applicationContext.xml"})
public class CustomerServiceRestApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CustomerServiceRestApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceRestApplication.class, args);
	}
}