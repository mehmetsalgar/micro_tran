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

import org.salgar.hystrix.transaction.annotation.TransactionalHystrixCommand;

@Component
public class ProcessFacadeImpl implements ProcessFacade {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessFacadeImpl.class);
	
	@Autowired(required = false)
	@Named("proxyProductServiceV1")
	private org.salgar.product.api.v1.ProductService productServiceV1;

	@Autowired(required = false)
	@Named("proxyProductServiceV2")
	private org.salgar.product.api.v2.ProductService productServiceV2;

	@Autowired(required = false)
	@Named("proxyOrderServiceV1")
	private org.salgar.order.api.v1.OrderService orderServiceV1;
	
	@Autowired(required = false)
	@Named("proxyOrderServiceV2")
	private org.salgar.order.api.v2.OrderService orderServiceV2;
	
	@Autowired(required = false)
	@Named("proxyCustomerServiceV1")
	private org.salgar.customer.api.v1.CustomerService customerServiceV1;
	
	@Autowired(required = false)
	@Named("proxyCustomerServiceV2")
	private org.salgar.customer.api.v2.CustomerService customerServiceV2;


	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackProductV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v1.model.Product giveProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v1.model.Product result = productServiceV1.giveProduct(productId);

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
		ServiceInstance instance = loadBalancerClient.choose("product_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_v1_rest/product/" + productId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.product.api.v1.model.Product product = mapper.readValue(result.getBody(),
				org.salgar.product.api.v1.model.Product.class);

		return product;
	}


	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackProductV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v2.model.Product giveProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v2.model.Product result = productServiceV2.giveProduct(productId);

		return result;
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
	@HystrixCommand(fallbackMethod = "executeFallBackGiveOrderV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.order.api.v1.model.Order giveOrderV1(int orderId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.order.api.v1.model.Order result = orderServiceV1.giveOrder(orderId);

		return result;
	}
	
	@Override
	public org.salgar.order.api.v1.model.Order executeFallBackGiveOrderV1(int orderId) throws JsonParseException, JsonMappingException, IOException {
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
	@HystrixCommand(fallbackMethod = "executeFallBackGiveOrderV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.order.api.v2.model.Order giveOrderV2(int orderId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.order.api.v2.model.Order result = orderServiceV2.giveOrder(orderId);

		return result;
	}
	
	@Override
	public org.salgar.order.api.v2.model.Order executeFallBackGiveOrderV2(int orderId) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_v2_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_v2_rest/order/" + orderId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.order.api.v2.model.Order order = mapper.readValue(result.getBody(),
				org.salgar.order.api.v2.model.Order.class);

		return order;
	}


	@Override
	@TransactionalHystrixCommand(fallbackMethod = "executeFallBackSaveOrderV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.order.api.v1.model.Order saveOrderV1(org.salgar.order.api.v1.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		try {
			return orderServiceV1.saveOrder(order);
		} catch(Throwable t) {
			LOG.error(t.getMessage(), t);
			throw t;
		}
	}

	@Override
	public org.salgar.order.api.v1.model.Order executeFallBackSaveOrderV1(org.salgar.order.api.v1.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_v1_rest/save_order";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order", order);
		
		ResponseEntity<org.salgar.order.api.v1.model.Order> result = restTemplate.postForEntity(url, null, org.salgar.order.api.v1.model.Order.class, params);
		
		return result.getBody();
	}
	
	@Override
	@TransactionalHystrixCommand(fallbackMethod = "executeFallBackSaveOrderV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.order.api.v2.model.Order saveOrderV2(org.salgar.order.api.v2.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		try {
			return orderServiceV2.saveOrder(order);
		} catch(Throwable t) {
			LOG.error(t.getMessage(), t);
			throw t;
		}
	}
	
	@Override
	public org.salgar.order.api.v2.model.Order executeFallBackSaveOrderV2(org.salgar.order.api.v2.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("order_v2_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/order_v2_rest/saveOrder";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order", order);
		
		ResponseEntity<org.salgar.order.api.v2.model.Order> result = restTemplate.postForEntity(url, null, org.salgar.order.api.v2.model.Order.class, params);
		
		return result.getBody();
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveCustomerV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.customer.api.v1.model.Customer saveCustomerV1(org.salgar.customer.api.v1.model.Customer customer) throws JsonParseException, JsonMappingException, IOException {
		return customerServiceV1.saveCustomer(customer);
	}
	
	@Override
	public org.salgar.customer.api.v1.model.Customer executeFallBackSaveCustomerV1(org.salgar.customer.api.v1.model.Customer customer)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("customer_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/customer_v1_rest/saveCustomer";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customer);
		
		ResponseEntity<org.salgar.customer.api.v1.model.Customer> result = restTemplate.postForEntity(url, null, org.salgar.customer.api.v1.model.Customer.class, params);
		
		return result.getBody();
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackGiveCustomerV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.customer.api.v1.model.Customer giveCustomerV1(int customerId) throws JsonParseException, JsonMappingException, IOException {
		return customerServiceV1.giveCustomer(customerId);
	}

	@Override
	public org.salgar.customer.api.v1.model.Customer executeFallBackGiveCustomerV1(int customerId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("customer_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/customer_v1_rest/customer/" + customerId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.customer.api.v1.model.Customer order = mapper.readValue(result.getBody(),
				org.salgar.customer.api.v1.model.Customer.class);

		return order;
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackGiveCustomerV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.customer.api.v2.model.Customer giveCustomerV2(int customerId) throws JsonParseException, JsonMappingException, IOException {
		return customerServiceV2.giveCustomer(customerId);
	}
	
	@Override
	public org.salgar.customer.api.v2.model.Customer executeFallBackGiveCustomerV2(int customerId)
			throws JsonParseException, JsonMappingException, IOException {
		ServiceInstance instance = loadBalancerClient.choose("customer_v2_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/customer_v2_rest/customer/" + customerId;

		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

		ObjectMapper mapper = new ObjectMapper();
		org.salgar.customer.api.v2.model.Customer order = mapper.readValue(result.getBody(),
				org.salgar.customer.api.v2.model.Customer.class);

		return order;
	}

	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveProductV1", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v1.model.Product saveProductV1(org.salgar.product.api.v1.model.Product product) throws JsonParseException, JsonMappingException, IOException {
		return productServiceV1.saveProduct(product);
	}

	@Override
	public org.salgar.product.api.v1.model.Product executeFallBackSaveProductV1(org.salgar.product.api.v1.model.Product product) {
		ServiceInstance instance = loadBalancerClient.choose("product_v1_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_v1_rest/saveProduct";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product", product);
		
		ResponseEntity<org.salgar.product.api.v1.model.Product> result = restTemplate.postForEntity(url, null, org.salgar.product.api.v1.model.Product.class, params);
		
		return result.getBody();
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "executeFallBackSaveProductV2", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public org.salgar.product.api.v2.model.Product saveProductV2(org.salgar.product.api.v2.model.Product product) throws JsonParseException, JsonMappingException, IOException {
		return productServiceV2.saveProduct(product);
	}

	@Override
	public org.salgar.product.api.v2.model.Product executeFallBackSaveProductV2(org.salgar.product.api.v2.model.Product product) {
		ServiceInstance instance = loadBalancerClient.choose("product_v2_rest");

		URI uri = instance.getUri();
		String url = uri.toString() + "/product_v2_rest/saveProduct";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product", product);
		
		ResponseEntity<org.salgar.product.api.v2.model.Product> result = restTemplate.postForEntity(url, null, org.salgar.product.api.v2.model.Product.class, params);
		
		return result.getBody();
	}
}