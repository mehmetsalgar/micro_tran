package org.salgar.process.facade;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class ProcessFacadeImpl implements ProcessFacade {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessFacadeImpl.class);
	
	@Autowired(required = false)
	@Named("proxyProductService")
	private org.salgar.product.api.ProductService productService;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackProductV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.model.Product giveProduct(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.model.Product result = productService.giveProduct(productId);

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.salgar.process.facade.ProductProcessFacade#executeFallBackV1(int)
	 */
	@Override
	public org.salgar.product.api.model.Product executeFallBackProduct(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("product_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_rest-2.0-SNAPSHOT/product/" + productId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.product.api.model.Product product = mapper.readValue(result.getBody(),
				org.salgar.product.api.model.Product.class);

		return product;
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveProduct", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.model.Product saveProduct(org.salgar.product.api.model.Product product) throws JsonParseException, JsonMappingException, IOException {
		return productService.saveProduct(product);
	}

	@Override
	public org.salgar.product.api.model.Product executeFallBackSaveProduct(org.salgar.product.api.model.Product product) {
		ServiceInstance instance = loadBalancerClient.choose("product_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_v1_rest-2.0-SNAPSHOT/saveProduct";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product", product);
		
		ResponseEntity<org.salgar.product.api.model.Product> result = restTemplate.postForEntity(url, null, org.salgar.product.api.model.Product.class, params);
		
		return result.getBody();
	}
}