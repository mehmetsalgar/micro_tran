package org.salgar.process.facade;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.salgar.order.api.v1.model.Order;
import org.salgar.product.api.v1.model.Product;
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
	@Autowired(required = false)
	@Named("proxyProductServiceV1")
	private org.salgar.product.api.v1.ProductService productServiceV1;

	@Autowired(required = false)
	@Named("proxyProductServiceV2")
	private org.salgar.product.api.v2.ProductService productServiceV2;

	@Autowired(required = false)
	@Named("proxyOrderServiceV1")
	private org.salgar.order.api.v1.OrderService orderServiceV1;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	@HystrixCommand(fallbackMethod = "executeFallBackProductV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v1.model.Product getProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v1.model.Product result = productServiceV1.giveProduct(productId);

		return result;
	}

	@HystrixCommand(fallbackMethod = "executeFallBackProductV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v2.model.Product getProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v2.model.Product result = productServiceV2.giveProduct(productId);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.salgar.process.facade.ProductProcessFacade#executeFallBackV1(int)
	 */
	@Override
	public org.salgar.product.api.v1.model.Product executeFallBackProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("product_service_rest_v1");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product/v1/" + productId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.product.api.v1.model.Product product = mapper.readValue(result.getBody(),
				org.salgar.product.api.v1.model.Product.class);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.salgar.process.facade.ProductProcessFacade#executeFallBackV2(int)
	 */
	@Override
	public org.salgar.product.api.v2.model.Product executeFallBackProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("product_v2_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_v2_rest/product/" + productId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.product.api.v2.model.Product product = mapper.readValue(result.getBody(),
				org.salgar.product.api.v2.model.Product.class);

		return product;
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackOrderV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public Order getOrderV1(int orderId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.order.api.v1.model.Order result = orderServiceV1.giveOrder(orderId);

		return result;
	}

	@Override
	public Order executeFallBackOrderV1(int orderId) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_v1_rest/order/" + orderId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.order.api.v1.model.Order order = mapper.readValue(result.getBody(),
				org.salgar.order.api.v1.model.Order.class);

		return order;
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveOrderV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public void saveOrderV1(Order order) throws JsonParseException, JsonMappingException, IOException {
		orderServiceV1.saveOrder(order);
	}

	@Override
	public void executeFallBackSaveOrderV1(Order order) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_v1_rest/save_order";

		Map<String, Object> params = new HashMap<String, Object>();
		
		ResponseEntity<Void> result = restTemplate.postForEntity(url, null, Void.class, params);
	}
}
