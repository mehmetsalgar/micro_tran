package org.salgar.process.facade;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.salgar.order.api.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.salgar.hystrix.transaction.annotation.TransactionalHystrixCommand;

@Component
public class ProcessFacadeImpl implements ProcessFacade {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessFacadeImpl.class);
	
	@Autowired(required = false)
	@Named("proxyProductService")
	private org.salgar.product.api.ProductService productService;
	
	@Autowired(required = false)
	@Named("proxyCustomerService")
	private org.salgar.customer.api.CustomerService customerService;
	
	@Autowired(required = false)
	@Named("proxyOrderService")
	private org.salgar.order.api.OrderService orderService;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackProduct", commandProperties = {
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
		ServiceInstance instance = loadBalancerClient.choose("product_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_rest_1.0-SNAPSHOT/product/" + productId;

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
		ServiceInstance instance = loadBalancerClient.choose("product_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_rest_1.0-SNAPSHOT/saveProduct";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product", product);
		
		ResponseEntity<org.salgar.product.api.model.Product> result = restTemplate.postForEntity(url, null, org.salgar.product.api.model.Product.class, params);
		
		return result.getBody();
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackCustomer", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.customer.api.model.Customer giveCustomer(int customerId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.customer.api.model.Customer result = customerService.giveCustomer(customerId);

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.salgar.process.facade.ProductProcessFacade#executeFallBackV1(int)
	 */
	@Override
	public org.salgar.customer.api.model.Customer executeFallBackCustomer(int customerId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("customer_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/customer_rest-1.0-SNAPSHOT/customer/" + customerId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.customer.api.model.Customer customer = mapper.readValue(result.getBody(),
				org.salgar.customer.api.model.Customer.class);

		return customer;
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveCustomer", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.customer.api.model.Customer saveCustomer(org.salgar.customer.api.model.Customer customer) throws JsonParseException, JsonMappingException, IOException {
		return customerService.saveCustomer(customer);
	}

	@Override
	public org.salgar.customer.api.model.Customer executeFallBackSaveCustomer(org.salgar.customer.api.model.Customer customer) {
		ServiceInstance instance = loadBalancerClient.choose("customer_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/customer_rest_1.0-SNAPSHOT/saveCustomer";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customer);
		
		ResponseEntity<org.salgar.customer.api.model.Customer> result = restTemplate.postForEntity(url, null, org.salgar.customer.api.model.Customer.class, params);
		
		return result.getBody();
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackOrder", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public Order giveOrder(@PathVariable int orderId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.order.api.model.Order result = orderService.giveOrder(orderId);

		return result;
	}

	@Override
	public Order executeFallBackOrder(int orderId) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_rest-1.0-SNAPSHOT/order/" + orderId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.order.api.model.Order order = mapper.readValue(result.getBody(),
				org.salgar.order.api.model.Order.class);

		return order;
	}

	@Override
	@TransactionalHystrixCommand(fallbackMethod = "executeFallBackSaveOrder", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public Order saveOrder(@RequestBody Order order) throws JsonParseException, JsonMappingException, IOException {
		throw new RuntimeException("fake exception!!!");
		//return orderService.saveOrder(order);
	}

	@Override
	public Order executeFallBackSaveOrder(Order order) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_rest_1.0-SNAPSHOT");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_rest_1.0-SNAPSHOT/saveCustomer";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order", order);
		
		ResponseEntity<org.salgar.order.api.model.Order> result = restTemplate.postForEntity(url, null, org.salgar.order.api.model.Order.class, params);
		
		return result.getBody();
	}
}