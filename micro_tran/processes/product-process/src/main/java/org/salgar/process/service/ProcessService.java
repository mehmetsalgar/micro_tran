package org.salgar.process.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.salgar.annotation.TransactionalFanout;
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
@TransactionalFanout( services = {"proxyProductService" })
public class ProcessService {
	private final static Log LOG = LogFactory.getLog(ProcessService.class);
	private boolean routeRestProduct = false;
	
	
	@Autowired(required = false)
	@Named("proxyProductService")
	private org.salgar.product.api.ProductService productService;
	
	@Autowired
	private ProcessFacade processFacade;
	
	@PostConstruct
	private void defineRoutes() {
		if(productService == null) {
			routeRestProduct = true;
		} else {
			try {
				String healthCheck = productService.giveAlive();
				if(healthCheck == null && "".equals(healthCheck)) {
					routeRestProduct = true;
				}
			} catch (Throwable t) {
				LOG.error(t.getMessage(), t);
				routeRestProduct = true;
			}
		}
	}

	@RequestMapping("/product/{productId}")
	@Transactional(readOnly = true)
	public org.salgar.product.api.model.Product getProduct(@PathVariable int productId)
			throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProduct) {
			return processFacade.executeFallBackProduct(productId);
		}

		org.salgar.product.api.model.Product resut = processFacade.giveProduct(productId);

		return resut;
	}
	
	@RequestMapping(path = "/product/saveProduct", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	public org.salgar.product.api.model.Product saveProduct(@RequestBody org.salgar.product.api.model.Product product)
			throws JsonParseException, JsonMappingException, IOException {
		if (routeRestProduct) {
			return processFacade.executeFallBackSaveProduct(product);
		}

		org.salgar.product.api.model.Product resut = processFacade.saveProduct(product);

		return resut;
	}
}