package org.salgar.process.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.salgar.order.api.v1.model.Order;
import org.salgar.process.facade.ProcessFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@Transactional
public class ProcessService {
	private boolean routeRestProductV1 = false;
	private boolean routeRestProductV2 = false;
	private boolean routeRestOrderV1 = false;
	
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
				routeRestProductV2 = true;
			}
		}
		
		if(orderServiceV1 == null) {
			routeRestOrderV1 = false;
		} else  {
			try {
				String healthCheck = orderServiceV1.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestOrderV1 = true;
				}
			} catch (Throwable t) {
				routeRestOrderV1 = true;
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

		org.salgar.product.api.v1.model.Product resut = processFacade.getProductV1(productId);

		return resut;
	}

	@RequestMapping("/product/v2/{productId}")
	@Transactional(readOnly = true)
	public org.salgar.product.api.v2.model.Product getProductV2(@PathVariable int productId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProductV2) {
			return processFacade.executeFallBackProductV2(productId);
		}
		
		org.salgar.product.api.v2.model.Product resut = processFacade.getProductV2(productId);

		return resut;
	}
	
	@RequestMapping("/order/v1/{orderId}")
	@Transactional(readOnly = true)
	public org.salgar.order.api.v1.model.Order getOrderV1(@PathVariable int orderId) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestOrderV1) {
			return processFacade.executeFallBackOrderV1(orderId);
		}
		
		org.salgar.order.api.v1.model.Order resut = processFacade.getOrderV1(orderId);

		return resut;
	}
	
	@RequestMapping(path = "/saveOrder/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveOrderV1(@RequestBody org.salgar.order.api.v1.model.Order order) throws JsonParseException, JsonMappingException, IOException {
		if (routeRestOrderV1) {
			processFacade.executeFallBackSaveOrderV1(order);
		}
		
		processFacade.saveOrderV1(order);
	}
	
	
	@RequestMapping(path = "/saveOrderWithProduct/v1", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveOrderWithAnExistingProduct(@RequestBody Order order, @RequestParam("productId") Integer productId) throws JsonParseException, JsonMappingException, IOException {
		org.salgar.product.api.v1.model.Product product;
		if (routeRestProductV1) {
			product = processFacade.executeFallBackProductV1(productId);
		}

		product = processFacade.getProductV1(productId);
		
		if (routeRestOrderV1) {
			processFacade.executeFallBackSaveOrderV1(order);
		}
		
		order.getProducts().add(product);
		processFacade.saveOrderV1(order);
	}
}