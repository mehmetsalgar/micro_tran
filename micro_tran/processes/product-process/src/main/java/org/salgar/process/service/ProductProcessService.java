package org.salgar.process.service;

import java.io.IOException;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.salgar.process.facade.ProductProcessFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ProductProcessService {
	private boolean routeRestV1 = false;
	private boolean routeRestV2 = false;
	
	@Autowired(required = false)
	@Named("proxyProductServiceV1")
	private org.salgar.product.api.v1.ProductService productServiceV1;

	@Autowired(required = false)
	@Named("proxyProductServiceV2")
	private org.salgar.product.api.v2.ProductService productServiceV2;
	
	@Autowired
	private ProductProcessFacade productProcessFacade;
	
	@PostConstruct
	private void defineRoutes() {
		if(productServiceV1 == null) {
			routeRestV1 = true;
		} else {
			try {
				String healthCheck = productServiceV1.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestV1 = true;
				}
			} catch (Throwable t) {
				routeRestV1 = true;
			}
		}
		
		if(productServiceV2 == null) {
			routeRestV2 = true;
		} else {
			try {
				String healthCheck = productServiceV2.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestV2 = true;
				}
			} catch (Throwable t) {
				routeRestV2 = true;
			}
		}
	}

	@RequestMapping("/product/v1/{productId}")
	public org.salgar.product.api.v1.model.Product getProductV1(@PathVariable int productId)
			throws JsonParseException, JsonMappingException, IOException {
		if (routeRestV1) {
			return productProcessFacade.executeFallBackV1(productId);
		}

		org.salgar.product.api.v1.model.Product resut = productProcessFacade.getProductV1(productId);

		return resut;
	}

	@RequestMapping("/product/v2/{productId}")
	public org.salgar.product.api.v2.model.Product getProductV2(@PathVariable int productId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestV2) {
			return productProcessFacade.executeFallBackV2(productId);
		}
		
		org.salgar.product.api.v2.model.Product resut = productProcessFacade.getProductV2(productId);

		return resut;
	}
}
