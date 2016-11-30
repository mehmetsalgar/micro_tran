package org.salgar.process.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.salgar.annotation.TransactionalFanout;
import org.salgar.annotation.TransactionalOrchestration;
import org.salgar.process.facade.ProcessFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@Transactional
@TransactionalFanout( services = {"proxyProductServiceV1" , "proxyOrderServiceV1", 
		"proxyCustomerServiceV1"})
public class ProcessService {
	private final static Log LOG = LogFactory.getLog(ProcessService.class);
	private boolean routeRestProductV1 = false;
	private boolean routeRestProductV2 = false;
	private boolean routeRestOrderV1 = false;
	private boolean routeRestOrderV2 = false;
	private boolean routeRestCustomerV1 = false;
	private boolean routeRestCustomerV2 = false;
	
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
	private ProcessFacade processFacade;
	
	@PostConstruct
	private void defineRoutes() {
		if(productServiceV1 == null) {
			routeRestProductV1 = true;
		} else {
			try {
				String healthCheck = productServiceV1.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestProductV1 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestProductV1 = true;
			}
		}
		
		if(productServiceV2 == null) {
			routeRestProductV2 = true;
		} else {
			try {
				String healthCheck = productServiceV2.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestProductV2 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestProductV2 = true;
			}
		}
		
		if(orderServiceV1 == null) {
			routeRestOrderV1 = true;
		} else  {
			try {
				String healthCheck = orderServiceV1.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestOrderV1 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestOrderV1 = true;
			}
		}
		
		if(orderServiceV2 == null) {
			routeRestOrderV2 = true;
		} else  {
			try {
				String healthCheck = orderServiceV2.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestOrderV2 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestOrderV2 = true;
			}
		}
		
		if(customerServiceV1 == null) {
			routeRestCustomerV1 = true;
		} else  {
			try {
				String healthCheck = customerServiceV1.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestCustomerV1 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestCustomerV1 = true;
			}
		}
		
		if(customerServiceV2 == null) {
			routeRestCustomerV2 = true;
		} else  {
			try {
				String healthCheck = customerServiceV2.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestCustomerV2 = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestCustomerV2 = true;
			}
		}
	}

	@RequestMapping("/product/v1/{productId}")
	@Transactional(readOnly = true)
	public org.salgar.product.api.v1.model.Product getProductV1(@PathVariable int productId)
			throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProductV1) {
			return processFacade.executeFallBackProductV1(productId);
		}

		org.salgar.product.api.v1.model.Product resut = processFacade.giveProductV1(productId);

		return resut;
	}
	
	@RequestMapping(path = "/product/v1/saveProduct", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public org.salgar.product.api.v1.model.Product saveProductV1(@RequestBody org.salgar.product.api.v1.model.Product product)
			throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProductV1) {
			return processFacade.executeFallBackSaveProductV1(product);
		}

		org.salgar.product.api.v1.model.Product resut = processFacade.saveProductV1(product);

		return resut;
	}

	@RequestMapping("/product/v2/{productId}")
	@Transactional(readOnly = true)
	public org.salgar.product.api.v2.model.Product getProductV2(@PathVariable int productId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProductV2) {
			return processFacade.executeFallBackProductV2(productId);
		}
		
		org.salgar.product.api.v2.model.Product resut = processFacade.giveProductV2(productId);

		return resut;
	}
	
	@RequestMapping(path = "/product/v2/saveProduct", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public org.salgar.product.api.v2.model.Product saveProductV2(@RequestBody org.salgar.product.api.v2.model.Product product) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProductV2) {
			return processFacade.executeFallBackSaveProductV2(product);
		}
		
		org.salgar.product.api.v2.model.Product resut = processFacade.saveProductV2(product);

		return resut;
	}
	
	@RequestMapping("/order/v1/{orderId}")
	@Transactional(readOnly = true)
	public org.salgar.order.api.v1.model.Order giveOrderV1(@PathVariable int orderId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestOrderV1) {
			return processFacade.executeFallBackGiveOrderV1(orderId);
		}
		
		org.salgar.order.api.v1.model.Order resut = processFacade.giveOrderV1(orderId);

		return resut;
	}
	
	@RequestMapping("/order/v2/{orderId}")
	@Transactional(readOnly = true)
	public org.salgar.order.api.v2.model.Order giveOrderV2(@PathVariable int orderId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestOrderV2) {
			return processFacade.executeFallBackGiveOrderV2(orderId);
		}
		
		org.salgar.order.api.v2.model.Order resut = processFacade.giveOrderV2(orderId);

		return resut;
	}
	
	@RequestMapping(path = "/saveOrder/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveOrderV1(@RequestBody org.salgar.order.api.v1.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestOrderV1) {
			processFacade.executeFallBackSaveOrderV1(order);
			return;
		}
		
		processFacade.saveOrderV1(order);
	}
	
	
	@RequestMapping(path = "/saveOrderWithProduct/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@TransactionalOrchestration
	public void saveOrderWithAnExistingProduct(@RequestBody org.salgar.order.api.v1.model.Order order, @RequestParam("productId") Integer productId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v1.model.Product product;
		if (routeRestProductV1) {
			product = processFacade.executeFallBackProductV1(productId);
		} else {
			product = processFacade.giveProductV1(productId);
		}
		
		order.getProducts().add(product);
		if (routeRestOrderV1) {
			processFacade.executeFallBackSaveOrderV1(order);
		} else {
			processFacade.saveOrderV1(order);
		}
	}
	
	@RequestMapping(path = "/saveCustomer/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveCustomerV1(@RequestBody org.salgar.customer.api.v1.model.Customer customer) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestCustomerV1) {
			processFacade.executeFallBackSaveCustomerV1(customer);
			return;
		}
		
		processFacade.saveCustomerV1(customer);
	}
	
	@RequestMapping("/customer/v1/{customerId}")
	@Transactional(readOnly = true)
	public org.salgar.customer.api.v1.model.Customer giveCustomerV1(@PathVariable int customerId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestCustomerV1) {
			return processFacade.executeFallBackGiveCustomerV1(customerId);
		}
		
		org.salgar.customer.api.v1.model.Customer result = processFacade.giveCustomerV1(customerId);

		return result;
	}
	
	@RequestMapping("/customer/v2/{customerId}")
	@Transactional(readOnly = true)
	public org.salgar.customer.api.v2.model.Customer giveCustomerV2(@PathVariable int customerId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestCustomerV1) {
			return processFacade.executeFallBackGiveCustomerV2(customerId);
		}
		
		org.salgar.customer.api.v2.model.Customer result = processFacade.giveCustomerV2(customerId);

		return result;
	}
	
	@RequestMapping(path = "/saveOrderWProductWCustomer/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@TransactionalOrchestration
	public void saveOrderV1WithProductWithCustomer(@RequestBody org.salgar.process.context.v1.OrderContext orderContext) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.customer.api.v1.model.Customer customerInternal = null;
		
		if (routeRestCustomerV1) {
			customerInternal = processFacade.executeFallBackGiveCustomerV1(orderContext.getCustomer().getId());
		} else {
			customerInternal = processFacade.giveCustomerV1(orderContext.getCustomer().getId());
		}
		org.salgar.product.api.v1.model.Product productInternal;
		if (routeRestProductV1) {
			productInternal = processFacade.executeFallBackProductV1(orderContext.getProduct().getProductId());
		} else {
			productInternal = processFacade.giveProductV1(orderContext.getProduct().getProductId());
		}
		
		List<org.salgar.product.api.v1.model.Product> products = new ArrayList<org.salgar.product.api.v1.model.Product>();
		products.add(productInternal);
		orderContext.getOrder().setProducts(products);
		orderContext.getOrder().setCustomer(customerInternal);
		
		if (routeRestOrderV1) {
			processFacade.executeFallBackSaveOrderV1(orderContext.getOrder());
		} else {
			processFacade.saveOrderV1(orderContext.getOrder());
		}
	}
	
	@RequestMapping(path = "/saveOrderWProductWCustomer/v2", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@TransactionalOrchestration
	public void saveOrderV2WithProductWithCustomer(@RequestBody org.salgar.process.context.v2.OrderContext orderContext) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.customer.api.v2.model.Customer customerInternal = null;
		
		if (routeRestCustomerV2) {
			customerInternal = processFacade.executeFallBackGiveCustomerV2(orderContext.getCustomer().getId());
		} else {
			customerInternal = processFacade.giveCustomerV2(orderContext.getCustomer().getId());
		}
		org.salgar.product.api.v2.model.Product productInternal;
		if (routeRestProductV2) {
			productInternal = processFacade.executeFallBackProductV2(orderContext.getProduct().getProductId());
		} else {
			productInternal = processFacade.giveProductV2(orderContext.getProduct().getProductId());
		}
		
		List<org.salgar.product.api.v2.model.Product> products = new ArrayList<org.salgar.product.api.v2.model.Product>();
		products.add(productInternal);
		orderContext.getOrder().setProducts(products);
		orderContext.getOrder().setCustomer(customerInternal);
		
		if (routeRestOrderV2) {
			processFacade.executeFallBackSaveOrderV2(orderContext.getOrder());
		} else {
			processFacade.saveOrderV2(orderContext.getOrder());
		}
	}
	
	@RequestMapping(path = "/saveOrderWProductWCustomerTransactionProof/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@TransactionalOrchestration
	public void saveOrderWithProductWithCustomerTransactionProof(@RequestBody org.salgar.process.context.v1.OrderContext orderContext) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.customer.api.v1.model.Customer customerInternal = null;
		
		if (routeRestCustomerV1) {
			customerInternal = processFacade.executeFallBackSaveCustomerV1(orderContext.getCustomer());
		} else {
			customerInternal = processFacade.saveCustomerV1(orderContext.getCustomer());
		}
		org.salgar.product.api.v1.model.Product productInternal;
		if (routeRestProductV1) {
			productInternal = processFacade.executeFallBackSaveProductV1(orderContext.getProduct());
		} else {
			productInternal = processFacade.saveProductV1(orderContext.getProduct());
		}
		
		List<org.salgar.product.api.v1.model.Product> products = new ArrayList<org.salgar.product.api.v1.model.Product>();
		products.add(productInternal);
		orderContext.getOrder().setProducts(products);
		orderContext.getOrder().setCustomer(customerInternal);
		
		throw new RuntimeException("Fake exception to prove transaction feature!");
	
		
//		if (routeRestOrderV1) {
//			processFacade.executeFallBackSaveOrderV1(orderContext.getOrder());
//		} else {
//			processFacade.saveOrderV1(orderContext.getOrder());
//		}
	}
}